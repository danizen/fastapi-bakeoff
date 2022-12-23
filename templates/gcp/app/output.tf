output "app_service_account_id" {
    value = google_service_account.app.id
}

output "app_service_account_email" {
    value = google_service_account.app.email
}