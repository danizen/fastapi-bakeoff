resource "random_password" "db" {
  length  = 12
  special = false
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

resource "aws_db_parameter_group" "db" {
  name   = "${var.cluster_name}-pg-parameter-group"
  family = "postgres13"

  parameter {
    name  = "timezone"
    value = "UTC"
    apply_method = "pending-reboot"
  }

  parameter {
    name  = "effective_io_concurrency"
    value = "10"
    apply_method = "pending-reboot"
  }

  parameter {
    name  = "log_lock_waits"
    value = "1"
    apply_method = "pending-reboot"
  }

  // log queries that take longer than 5 seconds
  parameter {
    name  = "log_min_duration_statement"
    value = "5000"
    apply_method = "pending-reboot"
  }

  // role HAL logs based only on age
  parameter {
    name  = "log_rotation_age"
    value = "1440"
    apply_method = "pending-reboot"
  }

  parameter {
    name  = "log_rotation_size"
    value = "0"
    apply_method = "pending-reboot"
  }

  parameter {
    name  = "log_temp_files"
    value = "0"
    apply_method = "pending-reboot"
  }

  parameter {
    name  = "max_connections"
    value = "100"
    apply_method = "pending-reboot"
  }

  parameter {
    name  = "random_page_cost"
    value = "1.5"
    apply_method = "pending-reboot"
  }

  parameter {
    name  = "rds.force_ssl"
    value = "1"
    apply_method = "pending-reboot"
  }

  parameter {
    name  = "rds.log_retention_period"
    value = "5760"
    apply_method = "pending-reboot"
  }
}

resource "aws_db_subnet_group" "db" {
  name       = "${var.cluster_name}-pg-subnets"
  subnet_ids = module.vpc.database_subnets
}

resource "aws_db_instance" "db" {
  allocated_storage   = 5
  db_name             = var.cluster_name
  engine              = "postgres"
  engine_version      = "13.7"
  instance_class      = var.database_instance_type
  username            = var.database_user
  password            = random_password.db.result
  skip_final_snapshot = true

  enabled_cloudwatch_logs_exports = [
    "postgresql", "upgrade"
  ]

  vpc_security_group_ids = [aws_security_group.db.id]

  parameter_group_name = aws_db_parameter_group.db.name
  db_subnet_group_name = aws_db_subnet_group.db.name
}
