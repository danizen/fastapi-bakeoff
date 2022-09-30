# public subnets
resource "aws_subnet" "public" {
  vpc_id     = aws_vpc.vpc.id

  for_each = toset(var.subnet_azs)
  cidr_block = cidrsubnet(local.public_cidr, 4, index(var.subnet_azs, each.key))
  availability_zone = each.value

  tags = {
    Name = "${var.vpc_name}-public-${index(var.subnet_azs, each.key)}"
  }
}

# private subnets
resource "aws_subnet" "private" {
  vpc_id = aws_vpc.vpc.id

  for_each = toset(var.subnet_azs)
  cidr_block = cidrsubnet(local.private_cidr, 4, index(var.subnet_azs, each.key))
  availability_zone = each.value

  tags = {
    Name = "${var.vpc_name}-private-${index(var.subnet_azs, each.key)}"
  }
}

# backend subnets
resource "aws_subnet" "backend" {
  vpc_id = aws_vpc.vpc.id

  for_each = toset(var.subnet_azs)
  cidr_block = cidrsubnet(local.backend_cidr, 4, index(var.subnet_azs, each.key))
  availability_zone = each.value

  tags = {
    Name = "${var.vpc_name}-backend-${index(var.subnet_azs, each.key)}"
  }
}
