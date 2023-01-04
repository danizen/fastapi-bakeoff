variable "region_name" {
  type = string
}

variable "zone_name" {
  type = string
}

variable "database_name" {
  type    = string
  default = "bakeoff"
}

variable "repository_name" {
  type    = string
  default = "bakeoff"
}

variable "database_project" {
  type = string
}

variable "app_project" {
  type = string
}

variable "app_image" {
  type = string
}

variable "app_name" {
  type = string
}

variable "vpcaccess_cidr" {
  type = string
}
