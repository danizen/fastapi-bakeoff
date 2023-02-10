# Microservice

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
<!-- END_TF_DOCS -->
