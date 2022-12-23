# Bootstrap

## Summary

Initialize a GCP project to house remote terraform state, create a terraform service account,
and setup that project's IAM policy to be correct.

## Disclaimer

I think having terraform manage its own IAM permissions is dangerous, but it is a little
hard to get started with using GCP with service accounts without a declarative way to 
set the permissions terraform will have in each project.

## How to use

Most of these projects should be used with terragrunt - this one can and should be used
with terraform with an argument to the bootstrap variables.
