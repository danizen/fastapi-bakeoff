resource "google_secret_manager_secret" "sql_password" {
  secret_id = "${var.database_name}_password"
  replication {
    automatic = true
  }
}

resource "google_secret_manager_secret_version" "sql_password" {
  secret = google_secret_manager_secret.sql_password.id

  secret_data = random_password.root_sql_user.result
}
