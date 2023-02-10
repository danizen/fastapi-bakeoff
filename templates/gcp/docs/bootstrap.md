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
## Providers

| Name                                                      | Version |
| --------------------------------------------------------- | ------- |
| <a name="provider_google"></a> [google](#provider_google) | ~> 4.0  |

## Inputs

| Name                                                                                             | Description | Type           | Default | Required |
| ------------------------------------------------------------------------------------------------ | ----------- | -------------- | ------- | :------: |
| <a name="input_bootstrap_project"></a> [bootstrap_project](#input_bootstrap_project)             | n/a         | `string`       | n/a     |   yes    |
| <a name="input_bucket_name"></a> [bucket_name](#input_bucket_name)                               | n/a         | `string`       | n/a     |   yes    |
| <a name="input_region_name"></a> [region_name](#input_region_name)                               | n/a         | `string`       | n/a     |   yes    |
| <a name="input_service_account_name"></a> [service_account_name](#input_service_account_name)    | n/a         | `string`       | n/a     |   yes    |
| <a name="input_service_account_users"></a> [service_account_users](#input_service_account_users) | n/a         | `list(string)` | n/a     |   yes    |
| <a name="input_zone_name"></a> [zone_name](#input_zone_name)                                     | n/a         | `string`       | n/a     |   yes    |

## Outputs

| Name                                                                                               | Description |
| -------------------------------------------------------------------------------------------------- | ----------- |
| <a name="output_bucket_uri"></a> [bucket_uri](#output_bucket_uri)                                  | n/a         |
| <a name="output_service_account_email"></a> [service_account_email](#output_service_account_email) | n/a         |
| <a name="output_service_account_id"></a> [service_account_id](#output_service_account_id)          | n/a         |
<!-- END_TF_DOCS -->