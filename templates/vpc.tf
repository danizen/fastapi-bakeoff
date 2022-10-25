module "vpc" {
  source = "terraform-aws-modules/vpc/aws"

  name = var.vpc_name
  cidr = var.vpc_cidr
  azs = var.vpc_subnet_zones

  public_subnets  = local.public_subnet_cidrs
  private_subnets = local.private_subnet_cidrs
  database_subnets = local.backend_subnet_cidrs

  database_subnet_group_name = "${var.vpc_name}-db-subnets"
  database_subnet_suffix = "backend"

  enable_nat_gateway  = true
  single_nat_gateway  = local.single_nat_gateway

  enable_dns_support = true
  enable_dns_hostnames = local.enable_dns_hostnames
}

# bastion security group - public just port 22 and ephemeral
# instance security group - port 22, 80, and 443
