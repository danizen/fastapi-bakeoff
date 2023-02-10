# Bootstrap

The bootstrap module generates a GCP Bucket and a GCP service account that will be used to run
the other templates

# Documentation

## Running the module

Use terragrunt in the parent directory:

```bash
cd 0-bootstrap
terragrunt init
terragrunt plan -out /tmp/tfplan
terragrunt apply /tmp/tfplan
```

This will use the file `bootstrap.yaml` to define the inputs below.

<!-- BEGIN_TF_DOCS -->
## Requirements

| Name                                                                     | Version  |
| ------------------------------------------------------------------------ | -------- |
| <a name="requirement_terraform"></a> [terraform](#requirement_terraform) | >= 1.1.0 |
| <a name="requirement_google"></a> [google](#requirement_google)          | ~> 4.0   |

## Inputs

| Name                                                                                             | Description                                                       | Type           | Default | Required |
| ------------------------------------------------------------------------------------------------ | ----------------------------------------------------------------- | -------------- | ------- | :------: |
| <a name="input_bootstrap_project"></a> [bootstrap_project](#input_bootstrap_project)             | GCP project to contain remote state bucket and service accouint   | `string`       | n/a     |   yes    |
| <a name="input_bucket_name"></a> [bucket_name](#input_bucket_name)                               | GCS bucket name to create for remote state                        | `string`       | n/a     |   yes    |
| <a name="input_region_name"></a> [region_name](#input_region_name)                               | Default region name for GCP provider                              | `string`       | n/a     |   yes    |
| <a name="input_service_account_name"></a> [service_account_name](#input_service_account_name)    | name for the GCP service account used to run terraform scripts    | `string`       | n/a     |   yes    |
| <a name="input_service_account_users"></a> [service_account_users](#input_service_account_users) | GCP principals who will be able to assume/use the service account | `list(string)` | n/a     |   yes    |
| <a name="input_zone_name"></a> [zone_name](#input_zone_name)                                     | Default zone name for GCP provider                                | `string`       | n/a     |   yes    |

## Outputs

| Name                                                                                               | Description                                    |
| -------------------------------------------------------------------------------------------------- | ---------------------------------------------- |
| <a name="output_bucket_uri"></a> [bucket_uri](#output_bucket_uri)                                  | gsutil URI for the GCS bucket for remote state |
| <a name="output_service_account_email"></a> [service_account_email](#output_service_account_email) | Service account email                          |
| <a name="output_service_account_id"></a> [service_account_id](#output_service_account_id)          | Service account identifier                     |
<!-- END_TF_DOCS -->