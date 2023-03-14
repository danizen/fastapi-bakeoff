# Infrastructure

This stack creates a Cloud SQL instance, a Secret Manager secret containing its password, and an Artifact Registry repository for images of the microservice to be deployed.

# Documentation

## Running the module

Use terragrunt in the parent directory:

```bash
cd 1-infra
terragrunt init
terragrunt plan -out /tmp/tfplan
terragrunt apply /tmp/tfplan
```

This will use the file `vars.yaml` to define the inputs below and the `terragrunt.hcl` file
in the parent directory to define the backend state storage.

<!-- BEGIN_TF_DOCS -->
## Requirements

| Name | Version |
|------|---------|
| <a name="requirement_terraform"></a> [terraform](#requirement_terraform) | >= 1.1.0 |
| <a name="requirement_google"></a> [google](#requirement_google) | ~> 4.0 |
| <a name="requirement_http"></a> [http](#requirement_http) | >= 1.1.1 |
| <a name="requirement_random"></a> [random](#requirement_random) | ~> 3.4.3 |

## Inputs

| Name | Description | Type | Default | Required |
|------|-------------|------|---------|:--------:|
| <a name="input_database_name"></a> [database_name](#input_database_name) | Name of the Cloud SQL instance to be created | `string` | `"bakeoff"` | no |
| <a name="input_database_project"></a> [database_project](#input_database_project) | GCP project that will hold the Cloud SQLdatabase | `string` | n/a | yes |
| <a name="input_region_name"></a> [region_name](#input_region_name) | Default region name for GCP provider | `string` | n/a | yes |
| <a name="input_repository_name"></a> [repository_name](#input_repository_name) | name of the artifact registry repository to be created | `string` | `"bakeoff"` | no |
| <a name="input_zone_name"></a> [zone_name](#input_zone_name) | Default zone name for GCP provider | `string` | n/a | yes |

## Outputs

| Name | Description |
|------|-------------|
| <a name="output_local_ip_address"></a> [local_ip_address](#output_local_ip_address) | Local IP Address that is allowed to reach SQL instance |
| <a name="output_sql_connection_name"></a> [sql_connection_name](#output_sql_connection_name) | Postgres instance connection string |
| <a name="output_sql_password"></a> [sql_password](#output_sql_password) | Postgres instance user password, stored as a secret |
| <a name="output_sql_private_ip"></a> [sql_private_ip](#output_sql_private_ip) | Postgres instance private ip |
| <a name="output_sql_public_ip"></a> [sql_public_ip](#output_sql_public_ip) | Postgres instance public ip |
<!-- END_TF_DOCS -->