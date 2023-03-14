resource "random_password" "root_sql_user" {
  length  = 12
  special = false
}

resource "google_sql_database_instance" "instance" {
  name             = var.database_name
  database_version = "POSTGRES_14"

  # so we can right size to 0 and save some money
  deletion_protection = false

  root_password = random_password.root_sql_user.result

  depends_on = [google_service_networking_connection.private_vpc_connection]

  settings {
    tier              = "db-custom-1-3840"
    availability_type = "ZONAL"
    disk_size         = 20


    backup_configuration {
      enabled                        = true
      point_in_time_recovery_enabled = true
    }

    ip_configuration {
      ipv4_enabled    = true
      require_ssl     = false
      private_network = data.google_compute_network.default.self_link

      authorized_networks {
        name  = "myip"
        value = "${local.myip_address}/32"
      }
    }
  }
}
