variable "vpc_region" {
  default = "us-east-1"
}

variable "vpc_cidr" {
  default = "10.77.0.0/16"
}

variable "vpc_name" {
  default = "bakeoff"
}

variable "subnet_azs" {
  default = [
    "us-east-1a",
    "us-east-1d",
    "us-east-1e"
  ]
}

