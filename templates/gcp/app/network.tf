data "google_compute_network" "default" {
  name = "default"
}

# resource "google_vpc_access_connector" "connector" {
#   name          = "vpc-con"
#   network       = data.google_compute_network.default.name
#   ip_cidr_range = var.vpcaccess_cidr

#   machine_type  = "e2-standard-4"
#   min_instances = 2
#   max_instances = 3
#   region        = var.region_name
# }