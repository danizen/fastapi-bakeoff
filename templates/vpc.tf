locals {
  # divide the address space into public, private and backend
  public_cidr  = cidrsubnet(var.cidr, 4, 0)
  private_cidr = cidrsubnet(var.cidr, 4, 1)
  backend_cidr = cidrsubnet(var.cidr, 4, 2)

  # invert logic 
  single_nat_gateway = (var.redundant_nat ? false : true)

  # calculate public subnet cidrs
  public_subnet_cidrs = [
    for i, az in var.availability_zones :
    cidrsubnet(local.public_cidr, 4, i)
  ]

  # calculate private subnet cidrs
  private_subnet_cidrs = [
    for i, az in var.availability_zones :
    cidrsubnet(local.private_cidr, 4, i)
  ]

  # caldulate backend subnet cidrs
  backend_subnet_cidrs = [
    for i, az in var.availability_zones :
    cidrsubnet(local.backend_cidr, 4, i)
  ]
}

module "vpc" {
  source = "terraform-aws-modules/vpc/aws"
  version = "3.18.1"

  name = var.cluster_name
  cidr = var.cidr
  azs  = var.availability_zones

  public_subnets   = local.public_subnet_cidrs
  private_subnets  = local.private_subnet_cidrs
  database_subnets = local.backend_subnet_cidrs

  database_subnet_group_name = "${var.cluster_name}-db-subnets"
  database_subnet_suffix     = "backend"

  enable_nat_gateway   = true
  single_nat_gateway   = local.single_nat_gateway
  enable_dns_support   = true
  enable_dns_hostnames = true

  vpc_tags = {
    "kubernetes.io/cluster/${var.cluster_name}" : "shared"
  }

  public_subnet_tags = {
    "kubernetes.io/cluster/${var.cluster_name}" : "shared",
    "kubernetes.io/role/elb" : "1"
  }

  private_subnet_tags = {
    "kubernetes.io/cluster/${var.cluster_name}" : "shared",
    "kubernetes.io/role/internal-elb" : "1"
  }
}
