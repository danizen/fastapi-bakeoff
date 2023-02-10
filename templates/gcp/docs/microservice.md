# CloudRun Service

This stack creates a Cloud Run service which runs a micro service. It can only be run after the
other stacks have been run and a container image has been built and deployed to the artifact registry.

# Documentation

## Running the module

Use terragrunt in the parent directory:

```bash
cd 2-app
terragrunt init
terragrunt plan -out /tmp/tfplan
terragrunt apply /tmp/tfplan
```

This will use the file `vars.yaml` in the parent directory to define the inputs below and
the `terragrunt.hcl` file in the parent directory to define the backend state storage.

<!-- BEGIN_TF_DOCS -->
## Requirements

| Name                                                                     | Version  |
| ------------------------------------------------------------------------ | -------- |
| <a name="requirement_terraform"></a> [terraform](#requirement_terraform) | >= 1.1.0 |
| <a name="requirement_google"></a> [google](#requirement_google)          | ~> 4.0   |

## Inputs

| Name                                                                              | Description                                             | Type     | Default     | Required |
| --------------------------------------------------------------------------------- | ------------------------------------------------------- | -------- | ----------- | :------: |
| <a name="input_app_image"></a> [app_image](#input_app_image)                      | URI for the container image to deploy                   | `string` | n/a         |   yes    |
| <a name="input_app_name"></a> [app_name](#input_app_name)                         | CloudRun service name for the application               | `string` | n/a         |   yes    |
| <a name="input_app_project"></a> [app_project](#input_app_project)                | GCP project containing this application                 | `string` | n/a         |   yes    |
| <a name="input_database_name"></a> [database_name](#input_database_name)          | Name of Cloud SQL database to use                       | `string` | `"bakeoff"` |    no    |
| <a name="input_database_project"></a> [database_project](#input_database_project) | GCP project containing the database and secret password | `string` | n/a         |   yes    |
| <a name="input_region_name"></a> [region_name](#input_region_name)                | Default region name for GCP provider                    | `string` | n/a         |   yes    |
| <a name="input_repository_name"></a> [repository_name](#input_repository_name)    | n/a                                                     | `string` | `"bakeoff"` |    no    |
| <a name="input_vpcaccess_cidr"></a> [vpcaccess_cidr](#input_vpcaccess_cidr)       | CIDR to use for VPC Access                              | `string` | n/a         |   yes    |
| <a name="input_zone_name"></a> [zone_name](#input_zone_name)                      | Default region name for GCP provider                    | `string` | n/a         |   yes    |

## Outputs

| Name                                                                                                           | Description                                                        |
| -------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------ |
| <a name="output_app_service_account_email"></a> [app_service_account_email](#output_app_service_account_email) | Email for the service account which runs the CloudRun service      |
| <a name="output_app_service_account_id"></a> [app_service_account_id](#output_app_service_account_id)          | Identifier for the service account which runs the CloudRun service |
| <a name="output_app_uri"></a> [app_uri](#output_app_uri)                                                       | URI for the CloudRun service                                       |
<!-- END_TF_DOCS -->
