output "local_ip_address" {
  value = local.myip_address
}

output "sql_connection_name" {
  description = "postgres instance connection string"
  value       = google_sql_database_instance.instance.connection_name
}

output "sql_private_ip" {
  description = "postgres instance private ip"
  value       = google_sql_database_instance.instance.private_ip_address
}

output "sql_public_ip" {
  description = "postgres instance public ip"
  value       = google_sql_database_instance.instance.public_ip_address
}

output "sql_password" {
  description = "postgres instance user password"
  value       = random_password.root_sql_user.result
  sensitive   = true
}
