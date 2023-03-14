variable "region_name" {
<<<<<<< HEAD
  type = string
}

variable "zone_name" {
  type = string
}

variable "database_name" {
  type    = string
  default = "bakeoff"
=======
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
>>>>>>> b0b083be7ed69839f87237ad091ec804797bf129
}

variable "repository_name" {
  type    = string
  default = "bakeoff"
}

variable "database_project" {
<<<<<<< HEAD
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
=======
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
>>>>>>> b0b083be7ed69839f87237ad091ec804797bf129
}
