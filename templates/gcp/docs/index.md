# Terraform Modules for GCP

## Background

FastAPI bakeoff is a benchmark of a FastAPI PostgreSQL pattern against a couple of other
architectures. These include asyncpg, SQLAlchemy on top of asyncpg, Django, and a Java/Spring backend as a control.

## GCP Summary

This code includes three Terraform script sub-directories which are mean't to be run sequentially.
The assumption here is that a company will have admins who provision projects appropriately in the
resource hierarchy, so this will not create projects.

## Stack sub-directories

* One project, `0-bootstrap`, to create the backend state storage and service account for 
  terraform. This one must be run by a principal with rights to IAM and service accounts.

* One project, `1-sql`, creates some bakend requirements for the microservice, these include:
  - An Cloud SQL instance running PostgreSQL
  - VPC Ingress for that Database
  - A Secret Manager secret for the Cloud SQL instance
  - A Artifact Registry repository for the container image to run.

* The final project, `2-app`, is meant to be run after an images built from
  the sub-directories in https://github.com/fastapi-bakeoff are transferred into the artifact registry.

## Managing Variables with Terragrunt

This repository is setup to use terragrunt to manage input variables.  The bootstrap modules is parameterized by `bootstrap.yaml`, and the others are parameterized by `vars.yaml`.
