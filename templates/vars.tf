variable "region_name" {
  type    = string
  default = "us-east-1"
}

variable "cidr" {
  type    = string
  default = "10.77.0.0/16"
}

variable "availability_zones" {
  type = list(string)
  default = [
    "us-east-1a",
    "us-east-1d"
  ]
}

variable "redundant_nat" {
  type    = bool
  default = false
}

variable "repo_names" {
  type = list(string)
  default = [
    "bakeoff",
  ]
}

variable "cluster_name" {
  type    = string
  default = "bakeoff"
}

variable "low_node_instance_type" {
  type    = string
  default = "t3.small"
}

variable "high_node_instance_type" {
  type    = string
  default = "t3.medium"
}

variable "database_instance_type" {
  type    = string
  default = "db.t3.small"
}

variable "database_user" {
  type    = string
  default = "app"
}