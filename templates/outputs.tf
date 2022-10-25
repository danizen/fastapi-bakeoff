output "cluster_id" {
  description = "EKS cluster ID"
  value       = module.eks.cluster_id
}

output "cluster_endpoint" {
  description = "Endpoint for EKS control plane"
  value       = module.eks.cluster_endpoint
}

output "cluster_security_group_id" {
  description = "Security group ids attached to the cluster control plane"
  value       = module.eks.cluster_security_group_id
}

output "region" {
  description = "AWS region"
  value       = var.region_name
}

output "cluster_name" {
  description = "Kubernetes Cluster Name"
  value       = var.cluster_name
}

output "database_host" {
  description = "Postgres database endpoint"
  value       = aws_db_instance.db.endpoint
}

output "database_name" {
  description = "Postgres database name"
  value       = aws_db_instance.db.db_name
}

output "database_user" {
  description = "Postgres database user"
  value       = "postgres"
}

output "database_password" {
  description = "Postgres database password"
  value       = random_password.db.result
  sensitive   = true
}