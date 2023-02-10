variable "region_name" {
  type        = string
  description = "Default region name for GCP provider"
}

variable "zone_name" {
  type        = string
  description = "Default zone name for GCP provider"
}

variable "database_name" {
  default     = "bakeoff"
  description = "Name of the Cloud SQL instance to be created"
}

variable "repository_name" {
  default     = "bakeoff"
  description = "name of the artifact registry repository to be created"
}

variable "database_project" {
  type        = string
  description = "GCP project that will hold the Cloud SQLdatabase"
}
