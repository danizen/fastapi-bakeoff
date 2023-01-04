/**
 * Service Account that will be used by CloudRun
 */
resource "google_service_account" "app" {
  account_id   = "sa-${var.app_name}"
  display_name = "Used by CloudRun for app ${var.app_name}"
}

/**
 * Policy bindings for that service Account
 */
resource "google_project_iam_binding" "sa_sql_client" {
  project = var.app_project
  role    = "roles/cloudsql.client"

  members = [
    "serviceAccount:${google_service_account.app.email}",
  ]
}

/* let it list and view all secrets */
resource "google_project_iam_binding" "sa_secret_viewer" {
  project = var.app_project
  role    = "roles/secretmanager.viewer"

  members = [
    "serviceAccount:${google_service_account.app.email}",
  ]

}

/* let it read this particular secret's versions */
resource "google_secret_manager_secret_iam_member" "secret_access" {
  secret_id = "${var.database_name}_password"
  role      = "roles/secretmanager.secretAccessor"
  member    = "serviceAccount:${google_service_account.app.email}"
}
