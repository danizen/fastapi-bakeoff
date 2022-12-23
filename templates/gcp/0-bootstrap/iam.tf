/**
 * The service account terraform will be using
 */
resource "google_service_account" "terraform" {
    account_id   = var.service_account_name
    display_name = "Terraform"
}

/**
 * Which users may impersonate this service account.
 */
resource "google_service_account_iam_binding" "terraform_users" {
    service_account_id = google_service_account.terraform.name
    role               = "roles/iam.serviceAccountUser"
  
    members = sort(var.service_account_users)
}

/** 
 * Give the service account permission to access the bucket.
 */
 resource "google_project_iam_custom_role" "terraform_state_editor" {
    role_id     = "terraform_state_editor"
    title       = "Terraform Remote State Editor"
    description = "A service account with this role can update objects in the remote state bucket"
    permissions = [
        "storage.buckets.list",
        "storage.objects.get",
        "storage.objects.create",
        "storage.objects.delete",
        "storage.objects.update"
    ]
}

resource "google_project_iam_binding" "terraform" {
    project = var.bootstrap_project
    role    = google_project_iam_custom_role.terraform_state_editor.id
  
    members = [
      "serviceAccount:${google_service_account.terraform.email}"
    ]
}
