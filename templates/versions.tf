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
