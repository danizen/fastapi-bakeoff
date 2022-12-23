
data "google_iam_policy" "noauth" {
  binding {
    role = "roles/run.invoker"
    members = [
      "allUsers",
    ]
  }
}

resource "google_cloud_run_service" "app" {
  name     = var.app_name
  location = var.region_name

  template {
    spec {
      service_account_name = google_service_account.app.email
      containers {
        image = var.app_container
        env {
          name = "PGDB_HOST"
          value = "/cloudsql/${data.google_sql_database_instance.db.connection_name}"
        }
        env {
          name = "PGDB_DATABASE"
          value = "postgres"
        }
        env {
          name = "PGDB_USERNAME"
          value = "postgres"
        }
        env {
          name = "PGDB_PASSWORD"
          value_from {
            secret_key_ref {
              key = "latest"
              name = "${var.database_name}_password"
            }
          }
        }
      }
    }
  }

  autogenerate_revision_name = true

  metadata {
    annotations = {
      "autoscaling.knative.dev/minScale"      = 1
      "autoscaling.knative.dev/maxScale"      = 3
      "run.googleapis.com/cloudsql-instances" = data.google_sql_database_instance.db.connection_name
      "run.googleapis.com/client-name"        = "terraform"
    }
  }
  
  traffic {
    percent         = 100
    latest_revision = true
  }
}
