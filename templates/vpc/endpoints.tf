resource "aws_vpc_endpoint" "s3" {
  vpc_id       = aws_vpc.vpc.id
  service_name = "com.amazonaws.${var.vpc_region}.s3"
  private_dns_enabled = true
  route_table_ids = [
    aws_default_route_table.rt.id,
    aws_route_table.priv.id
  ]
}

resource "aws_vpc_endpoint" "dynamodb" {
  vpc_id       = aws_vpc.vpc.id
  service_name = "com.amazonaws.${var.vpc_region}.dynamodb"
  private_dns_enabled = true
  route_table_ids = [
    aws_route_table.priv.id
  ]
}
