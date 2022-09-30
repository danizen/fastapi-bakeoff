resource "aws_ecr_repository" "repo" {
  name = var.repo_name
  image_tag_mutability = "MUTABLE"
}
