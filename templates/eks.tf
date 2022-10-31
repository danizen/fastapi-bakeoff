data "aws_iam_user" "system_master" {
  user_name = var.aws_system_master
}

module "eks" {
  source  = "terraform-aws-modules/eks/aws"
  version = "18.30.2"

  cluster_name    = var.cluster_name
  cluster_version = "1.23"

  vpc_id     = module.vpc.vpc_id
  subnet_ids = module.vpc.private_subnets
  cluster_endpoint_private_access = true
  cluster_endpoint_public_access  = true

  // These are on by default if the AWS console 
  // is used, so why not.
  cluster_addons = {
    coredns = {
      resolve_conflicts = "OVERWRITE"
    }
    kube-proxy = {}
    vpc-cni = {
      resolve_conflicts = "OVERWRITE"
    }
  }

  // Allow kubernetes IAM for some AWS principals
  # cluster_identity_providers = {
  #   sts = {
  #     client_id = "sts.amazonaws.com"
  #   }
  # }

  # manage_aws_auth_configmap = true

  # aws_auth_users = [
  #   {
  #     userarn  = data.aws_iam_user.system_master.arn
  #     username = data.aws_iam_user.system_master.user_name
  #     groups   = ["system:masters"]
  #   },
  # ]

  // Enable secrets encryption but no rotation
  create_kms_key = true
  cluster_encryption_config = [{
    resources = ["secrets"]
  }]
  enable_kms_key_rotation         = false

  eks_managed_node_group_defaults = {
    ami_type = "AL2_x86_64"

    attach_cluster_primary_security_group = true

    # Disabling and using externally provided security groups
    create_security_group = false
  }

  eks_managed_node_groups = {
    one = {
      name = "node-group-1"

      instance_types = [var.low_node_instance_type]

      min_size     = 1
      max_size     = 3
      desired_size = 2

      pre_bootstrap_user_data = <<-EOT
      echo 'foo bar'
      EOT

      vpc_security_group_ids = [
        aws_security_group.eks_node.id
      ]
    }

    two = {
      name = "node-group-2"

      instance_types = [var.high_node_instance_type]

      min_size     = 1
      max_size     = 2
      desired_size = 1

      pre_bootstrap_user_data = <<-EOT
      echo 'foo bar'
      EOT

      vpc_security_group_ids = [
        aws_security_group.eks_node.id
      ]
    }
  }
}
