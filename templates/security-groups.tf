
# bastion security group - public just port 22 and ephemeral
resource "aws_security_group" "bastion" {
  name        = "${var.cluster_name}-bastion"
  description = "Allow SSH inbound from home"
  vpc_id      = module.vpc.vpc_id

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["${local.myip_address}/32"]
  }

  ingress {
    from_port   = 5432
    to_port     = 5432
    protocol    = "tcp"
    cidr_blocks = ["${local.myip_address}/32"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "${var.cluster_name}-bastion"
  }
}

# instance security group - port 22, 80, and 443
resource "aws_security_group" "eks_node" {
  name        = "${var.cluster_name}-eks-node"
  description = "Allow SSH, HTTPS, HTTP from VPC"
  vpc_id      = module.vpc.vpc_id

  ingress {
    from_port = 22
    to_port   = 22
    protocol  = "tcp"
    cidr_blocks = [
      var.cidr,
      "${local.myip_address}/32"
    ]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "${var.cluster_name}-eks-node"
  }
}

resource "aws_security_group" "db" {
  name        = "${var.cluster_name}-postgres-sg"
  description = "Allow SSH, HTTPS, HTTP from VPC"
  vpc_id      = module.vpc.vpc_id

  ingress {
    from_port   = 5432
    to_port     = 5432
    protocol    = "tcp"
    cidr_blocks = [var.cidr]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "${var.cluster_name}-postgres-sg"
  }
}
