data "google_sql_database_instance" "db" {
  name = var.database_name
}
