# Terraform Modules for FastAPI Bakeoff

## Background

FastAPI bakeoff is a benchmark of a FastAPI PostgreSQL pattern against a couple of other
architectures. These include asyncpg, SQLAlchemy on top of asyncpg, Django, and a Java/Spring backend as a control.

## GCP Summary

This code includes three sub-directories which contain Terraform Stacks that are meant to be run sequentially. The assumption here is that a company will have admins who provision projects appropriately in the resource hierarchy, so this will not create projects.

## Terraform Stacks

* `0-bootstrap` creates the backend state storage and service account for 
  terraform. This one must be run by a principal with rights to IAM and service accounts.
  Because it is a bootstrap project, it does not use a remote backend.

* `1-infrastructure` creates some backend requirements for the microservice, these include:
  - An Cloud SQL instance running PostgreSQL
  - VPC Ingress for that Database
  - A Secret Manager secret for the Cloud SQL instance
  - A Artifact Registry repository for the container image to run
  - For simplicity, a custom VPC is not used - it uses the default VPC in the project

*  `2-app` is meant to be run after images built from the sub-directories in https://github.com/fastapi-bakeoff are transferred into the artifact registry.

## Managing Variables with Terragrunt

This repository is setup to use terragrunt to manage input variables.  The bootstrap modules is parameterized by `bootstrap.yaml`, and the others are parameterized by `vars.yaml`.  The parent directory and each stack sub-directory contain a file `terragrunt.hcl` which defines how this works.
