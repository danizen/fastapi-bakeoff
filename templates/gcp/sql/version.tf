terraform {
  required_version = ">= 1.1.0"
  required_providers {
    google = {
      source  = "hashicorp/google"
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

provider "google" {
  project = "${var.project_prefix}-bakeoff"
  region  = var.region_name
  zone    = var.zone_name
}

provider "random" {}
