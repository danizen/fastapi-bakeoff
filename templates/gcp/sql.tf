
resource "google_sql_database_instance" "db" {
  name             = var.database_name
  database_version = "POSTGRES_13"
  depends_on       = [google_service_networking_connection.private_vpc_connection]
  settings {
    tier              = "db-f1-micro"
    availability_type = "ZONAL"
    disk_size         = 20
    ip_configuration {
      ipv4_enabled    = false
      require_ssl     = false
      private_network = data.google_compute_network.default.self_link
    }
  }
}

resource "random_password" "db" {
  length  = 12
  special = false
}

resource "google_sql_user" "user" {
  name            = "appuser"
  instance        = google_sql_database_instance.db.name
  password        = random_password.db.result
  deletion_policy = "ABANDON"
}

resource "google_sql_database" "db" {
  name            = "appdb"
  instance        = google_sql_database_instance.db.name
  deletion_policy = "ABANDON"
}
