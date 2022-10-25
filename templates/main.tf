terraform {
  required_version = ">= 1.1.0"
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 4.0"
    }
    random = {
      source  = "hashicorp/random"
      version = "~> 3.4.3"
    }
    http = {
      version = ">= 1.1.1"
    }
  }
}

provider "aws" {
  region = var.region_name
}

provider "random" {}


# derived from vars
locals {
  # divide the address space into public, private and backend
  public_cidr  = cidrsubnet(var.vpc_cidr, 4, 0)
  private_cidr = cidrsubnet(var.vpc_cidr, 4, 1)
  backend_cidr = cidrsubnet(var.vpc_cidr, 4, 2)

  # invert logic 
  single_nat_gateway = (var.redundant_nat ? false : true)

  # make sure is bool
  enable_dns_hostnames = true

  # calculate public subnet cidrs
  public_subnet_cidrs = [
    for i, az in var.vpc_subnet_zones :
    cidrsubnet(local.public_cidr, 4, i)
  ]

  # calculate private subnet cidrs
  private_subnet_cidrs = [
    for i, az in var.vpc_subnet_zones :
    cidrsubnet(local.private_cidr, 4, i)
  ]

  # caldulate backend subnet cidrs
  backend_subnet_cidrs = [
    for i, az in var.vpc_subnet_zones :
    cidrsubnet(local.backend_cidr, 4, i)
  ]
}
