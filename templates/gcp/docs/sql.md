# SQL

The SQL module creates a Cloud SQL instance and some other stuff

# Documentation

<!-- BEGIN_TF_DOCS -->
## Providers

| Name | Version |
|------|---------|
| <a name="provider_google"></a> [google](#provider_google) | ~> 4.0 |
| <a name="provider_http"></a> [http](#provider_http) | >= 1.1.1 |
| <a name="provider_random"></a> [random](#provider_random) | ~> 3.4.3 |

## Inputs

| Name | Description | Type | Default | Required |
|------|-------------|------|---------|:--------:|
| <a name="input_app_image_tag"></a> [app_image_tag](#input_app_image_tag) | n/a | `string` | n/a | yes |
| <a name="input_app_project"></a> [app_project](#input_app_project) | n/a | `string` | n/a | yes |
| <a name="input_database_name"></a> [database_name](#input_database_name) | n/a | `string` | `"bakeoff"` | no |
| <a name="input_database_project"></a> [database_project](#input_database_project) | n/a | `string` | n/a | yes |
| <a name="input_region_name"></a> [region_name](#input_region_name) | n/a | `string` | n/a | yes |
| <a name="input_repository_name"></a> [repository_name](#input_repository_name) | n/a | `string` | `"bakeoff"` | no |
| <a name="input_zone_name"></a> [zone_name](#input_zone_name) | n/a | `string` | n/a | yes |

## Outputs

| Name | Description |
|------|-------------|
| <a name="output_local_ip_address"></a> [local_ip_address](#output_local_ip_address) | n/a |
| <a name="output_sql_connection_name"></a> [sql_connection_name](#output_sql_connection_name) | postgres instance connection string |
| <a name="output_sql_password"></a> [sql_password](#output_sql_password) | postgres instance user password |
| <a name="output_sql_private_ip"></a> [sql_private_ip](#output_sql_private_ip) | postgres instance private ip |
| <a name="output_sql_public_ip"></a> [sql_public_ip](#output_sql_public_ip) | postgres instance public ip |
<!-- END_TF_DOCS -->