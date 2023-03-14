
data "google_iam_policy" "noauth" {
  binding {
    role = "roles/run.invoker"
    members = [
      "allUsers",
    ]
  }
}

resource "google_cloud_run_service_iam_policy" "policy" {
  location = var.region_name
  project = var.app_project
  service = google_cloud_run_v2_service.app.name
  policy_data = data.google_iam_policy.noauth.policy_data
}

locals {
  image = "${var.region_name}-docker.pkg.dev/${var.app_project}/${var.repository_name}/${var.app_image}"
}


resource "google_cloud_run_v2_service" "app" {
  name     = var.app_name
  location = var.region_name
  ingress  = "INGRESS_TRAFFIC_ALL"

  traffic {
    type    = "TRAFFIC_TARGET_ALLOCATION_TYPE_LATEST"
    percent = 100
  }

  template {
    # vpc_access {
    #   connector = google_vpc_access_connector.connector.id
    #   egress = "ALL_TRAFFIC"
    # }

    service_account = google_service_account.app.email

    scaling {
      min_instance_count = 1
      max_instance_count = 3
    }

    volumes {
      name = "cloudsql"
      cloud_sql_instance {
        instances = [data.google_sql_database_instance.db.connection_name]
      }
    }

    containers {
      image = local.image
      volume_mounts {
        name       = "cloudsql"
        mount_path = "/cloudsql"
      }
      env {
        name  = "PGDB_HOST"
        value = "/cloudsql/${data.google_sql_database_instance.db.connection_name}"
      }
      env {
        name  = "PGDB_DATABASE"
        value = "postgres"
      }
      env {
        name  = "PGDB_USERNAME"
        value = "postgres"
      }
      env {
        name = "PGDB_PASSWORD"
        value_source {
          secret_key_ref {
            secret  = "${var.database_name}_password"
            version = "latest"
          }
        }
      }
    }
  }

  depends_on = [
    google_service_account.app,
    google_project_iam_binding.sa_sql_client,
    google_project_iam_binding.sa_secret_viewer,
    google_secret_manager_secret_iam_member.secret_access,
  ]
}
