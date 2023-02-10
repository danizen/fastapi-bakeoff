variable "region_name" {
  description = "Default region name for GCP provider"
  type        = string
}

variable "zone_name" {
  description = "Default region name for GCP provider"
  type        = string
}

variable "database_name" {
  type        = string
  default     = "bakeoff"
  description = "Name of Cloud SQL database to use"
}

variable "repository_name" {
  type    = string
  default = "bakeoff"
}

variable "database_project" {
  type        = string
  description = "GCP project containing the database and secret password"
}

variable "app_project" {
  type        = string
  description = "GCP project containing this application"
}

variable "app_image" {
  type        = string
  description = "URI for the container image to deploy"
}

variable "app_name" {
  type        = string
  description = "CloudRun service name for the application"
}

variable "vpcaccess_cidr" {
  type        = string
  description = "CIDR to use for VPC Access"
}
