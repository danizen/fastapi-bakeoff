resource "aws_ecr_repository" "repo" {
  for_each             = toset(var.repo_names)
  name                 = each.key
  image_tag_mutability = "MUTABLE"
}
