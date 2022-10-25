terraform {
  required_version = ">= 1.1.0"
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 4.0"
    }
  }
}

provider "aws" {
  region = var.vpc_region
}

# derived from vars
locals {
  public_cidr  = cidrsubnet(var.vpc_cidr, 4, 0)
  private_cidr = cidrsubnet(var.vpc_cidr, 4, 1)
  backend_cidr = cidrsubnet(var.vpc_cidr, 4, 2)
  nat_az       = (var.nat_az != null ? var.nat_az : var.subnet_azs[0])
}
