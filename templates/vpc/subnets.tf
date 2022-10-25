# private subnets
resource "aws_subnet" "private" {
  vpc_id = aws_vpc.vpc.id

  for_each          = toset(var.subnet_azs)
  cidr_block        = cidrsubnet(local.private_cidr, 4, index(var.subnet_azs, each.key))
  availability_zone = each.value

  tags = {
    Name = "${var.vpc_name}-private-${index(var.subnet_azs, each.key) + 1}"
  }
}

# EIP for the NAT Gateway
resource "aws_eip" "nat" {
  vpc = true

  tags = {
    Name = "${var.vpc_name}-nat-eip"
  }
}

# NAT Gateway in designated public subnet
resource "aws_nat_gateway" "nat" {
  allocation_id = aws_eip.nat.id
  subnet_id     = aws_subnet.public[local.nat_az].id

  tags = {
    Name = "${var.vpc_name}-nat"
  }

  depends_on = [aws_internet_gateway.gw]
}

# route table for private subnets
resource "aws_route_table" "priv" {
  vpc_id = aws_vpc.vpc.id

  tags = {
    Name = "${var.vpc_name}-private-rt"
  }
}

resource "aws_route" "privgw" {
  route_table_id         = aws_route_table.priv.id
  destination_cidr_block = "0.0.0.0/0"
  nat_gateway_id         = aws_nat_gateway.nat.id
  depends_on             = [aws_route_table.priv]
}

resource "aws_route_table_association" "priv" {
  for_each       = toset(var.subnet_azs)
  subnet_id      = aws_subnet.private[each.key].id
  route_table_id = aws_route_table.priv.id
}

# backend subnets
# resource "aws_subnet" "backend" {
#   vpc_id = aws_vpc.vpc.id

#   for_each = toset(var.subnet_azs)
#   cidr_block = cidrsubnet(local.backend_cidr, 4, index(var.subnet_azs, each.key))
#   availability_zone = each.value

#   tags = {
#     Name = "${var.vpc_name}-backend-${index(var.subnet_azs, each.key)+1}"
#   }
# }
