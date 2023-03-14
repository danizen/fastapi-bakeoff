# 
variable "region_name" {
  type        = string
  description = "Default region name for GCP provider"
}

variable "zone_name" {
  type        = string
  description = "Default zone name for GCP provider"
}

variable "bucket_name" {
  type        = string
  description = "GCS bucket name to create for remote state"
}

variable "bootstrap_project" {
  type        = string
  description = "GCP project to contain remote state bucket and service accouint"
}

variable "service_account_name" {
  type        = string
  description = "name for the GCP service account used to run terraform scripts"
}

variable "service_account_users" {
  type        = list(string)
  description = "GCP principals who will be able to assume/use the service account"
}
