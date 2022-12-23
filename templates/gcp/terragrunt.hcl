remote_state {
  backend = "gcs"
  generate = {
    path      = "backend.tf"
    if_exists = "overwrite_terragrunt"
  }
  config = {
    bucket = "danizen-tfstate"
    prefix = "${path_relative_to_include()}"
  }
}
