output "app_service_account_id" {
<<<<<<< HEAD
  value = google_service_account.app.id
}

output "app_service_account_email" {
  value = google_service_account.app.email
=======
  value       = google_service_account.app.id
  description = "Identifier for the service account which runs the CloudRun service"
}

output "app_service_account_email" {
  value       = google_service_account.app.email
  description = "Email for the service account which runs the CloudRun service"
}

output "app_uri" {
  value       = google_cloud_run_v2_service.app.uri
  description = "URI for the CloudRun service"
>>>>>>> b0b083be7ed69839f87237ad091ec804797bf129
}
