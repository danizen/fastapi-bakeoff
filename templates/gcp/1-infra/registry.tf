resource "google_artifact_registry_repository" "repo" {
  location = var.region_name
  repository_id = var.repository_name
  format = "DOCKER"
  project = var.app_project
}
