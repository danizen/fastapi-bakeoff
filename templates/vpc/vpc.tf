resource "aws_vpc" "vpc" {
  cidr_block = var.vpc_cidr

  enable_dns_support = true
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

## Not sure these are needed with the the vpc_id specified
#
# resource "aws_internet_gateway_attachment" "gw" {
#   internet_gateway_id = aws_internet_gateway.gw.id
#   vpc_id              = aws_vpc.vpc.id
# }
#
# resource "aws_internet_gateway_attachment" "gw" {
#   internet_gateway_id = aws_internet_gateway.gw.id
#   vpc_id              = aws_vpc.vpc.id
# }

# route table without subnets
resource "aws_default_route_table" "rt" {
  default_route_table_id = aws_vpc.vpc.default_route_table_id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.gw.id
  }

  route {
    ipv6_cidr_block        = "::/0"
    egress_only_gateway_id = aws_egress_only_internet_gateway.gw.id
  }

  tags = {
    Name = "${var.vpc_name}-rtb"
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
