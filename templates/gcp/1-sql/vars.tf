variable "region_name" {
  type    = string
}

variable "zone_name" {
  type    = string
}

variable "database_name" {
  default = "bakeoff"
}

variable "app_project" {
  type    = string
}

variable "database_project" {
  type    = string
}

variable "app_container" {
  type    = string
}
