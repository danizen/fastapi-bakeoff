resource "aws_vpc" "vpc" {
  cidr_block = var.vpc_cidr

  enable_dns_support   = true
  enable_dns_hostnames = true

  tags = {
    Name = var.vpc_name
  }
}

resource "aws_internet_gateway" "gw" {
  vpc_id = aws_vpc.vpc.id

  tags = {
    Name = "${var.vpc_name}-gw"
  }
}

resource "aws_egress_only_internet_gateway" "gw" {
  vpc_id = aws_vpc.vpc.id

  tags = {
    Name = "${var.vpc_name}-gw6"
  }
}

# route table for public subnets
resource "aws_default_route_table" "rt" {
  default_route_table_id = aws_vpc.vpc.default_route_table_id

  tags = {
    Name = "${var.vpc_name}-public-rt"
  }
}

resource "aws_route" "gw" {
  route_table_id         = aws_default_route_table.rt.id
  destination_cidr_block = "0.0.0.0/0"
  gateway_id             = aws_internet_gateway.gw.id
  depends_on             = [aws_default_route_table.rt]
}

resource "aws_route" "gw6" {
  route_table_id              = aws_default_route_table.rt.id
  destination_ipv6_cidr_block = "::0/0"
  egress_only_gateway_id      = aws_egress_only_internet_gateway.gw.id
  depends_on                  = [aws_default_route_table.rt]
}

# public subnets
resource "aws_subnet" "public" {
  vpc_id = aws_vpc.vpc.id

  for_each          = toset(var.subnet_azs)
  cidr_block        = cidrsubnet(local.public_cidr, 4, index(var.subnet_azs, each.key))
  availability_zone = each.value

  tags = {
    Name = "${var.vpc_name}-public-${index(var.subnet_azs, each.key) + 1}"
  }
}

# network acl
resource "aws_default_network_acl" "acl" {
  default_network_acl_id = aws_vpc.vpc.default_network_acl_id

  ingress {
    protocol   = -1
    rule_no    = 100
    action     = "allow"
    cidr_block = aws_vpc.vpc.cidr_block
    from_port  = 0
    to_port    = 0
  }

  tags = {
    Name = "${var.vpc_name}-acl"
  }
}
