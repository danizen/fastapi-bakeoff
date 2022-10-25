variable "region_name" {
  type    = string
  default = "us-east-1"
}

variable "vpc_cidr" {
  type    = string
  default = "10.77.0.0/16"
}

variable "vpc_name" {
  type    = string
  default = "bakeoff"
}

variable "vpc_subnet_zones" {
  type = list(string)
  default = [
    "us-east-1a",
    "us-east-1d"
  ]
}

variable "redundant_nat" {
  type = bool
  default = false
}
