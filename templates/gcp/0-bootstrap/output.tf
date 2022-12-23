output "bucket_uri" {
    value = "gs://${var.bucket_name}"
}

output "service_account_id" {
    value = google_service_account.terraform.id
}

output "service_account_email" {
    value = google_service_account.terraform.email
}
