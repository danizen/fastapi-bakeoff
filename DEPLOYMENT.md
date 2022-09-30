# Infrastructure and Deployment

## Summary

The templates sub-directory provides terraform templates to create test infrastructure.

## VPC 

The test VPC has three tiers:

* public subnets
* private subnets
* backend subnets

### Backend Subnets

An RDS postgresql server in Multi-AZ mode will be deployed to the backend subnets.

### Private Subnets

The backend subnets contain two EKS clusters:

* One cluster will run the API container under test, which is configured not to scale up.
* One cluster will run Locust.io and be a front-end to the backend cluster.
* Also an internal ALB for the the API container cluster.

### Public Subnets

The public cluster contains an ALB for the locust.io server.

A launch template for a bastion will also be created.

## ECR

There will be an ECR repository created for the containers under test. 

Containers are built manually and deployed there based on the Dockerfile for
each API server, and the locust.io test server
