output "bucket_uri" {
  value       = "gs://${var.bucket_name}"
  description = "gsutil URI for the GCS bucket for remote state"
}

output "service_account_id" {
  value       = google_service_account.terraform.id
  description = "Service account identifier"
}

output "service_account_email" {
  value       = google_service_account.terraform.email
  description = "Service account email"
}
