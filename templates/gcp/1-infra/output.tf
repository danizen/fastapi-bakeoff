output "local_ip_address" {
  value       = local.myip_address
  description = "Local IP Address that is allowed to reach SQL instance"
}

output "sql_connection_name" {
  description = "Postgres instance connection string"
  value       = google_sql_database_instance.instance.connection_name
}

output "sql_private_ip" {
  description = "Postgres instance private ip"
  value       = google_sql_database_instance.instance.private_ip_address
}

output "sql_public_ip" {
  description = "Postgres instance public ip"
  value       = google_sql_database_instance.instance.public_ip_address
}

output "sql_password" {
  description = "Postgres instance user password, stored as a secret"
  value       = random_password.root_sql_user.result
  sensitive   = true
}
