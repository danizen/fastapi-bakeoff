resource "google_storage_bucket" "tfstate_bucket" {
    name          = var.bucket_name
    location      = "US"
    force_destroy = true

    versioning { 
        enabled = true
    }
  
    public_access_prevention = "enforced"
}
