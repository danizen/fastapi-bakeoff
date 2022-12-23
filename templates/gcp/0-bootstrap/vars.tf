variable "region_name" {
  type    = string
}

variable "zone_name" {
  type    = string
}

variable "bucket_name" {
  type    = string
}

variable "bootstrap_project" {
  type    = string
}

variable "service_account_name" {
  type    = string
}

variable "service_account_users" {
  type    = list(string)
}
