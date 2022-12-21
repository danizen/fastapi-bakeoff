# get data about the default nework and avoid building one
data "google_compute_network" "default" {
  name = "default"
}

# Create a Service network address raqnge
resource "google_compute_global_address" "sql_private_ip" {
  name          = "sql-private-ip"
  purpose       = "VPC_PEERING"
  address_type  = "INTERNAL"
  prefix_length = 20
  network       = data.google_compute_network.default.id
}

resource "google_service_networking_connection" "private_vpc_connection" {
  network                 = data.google_compute_network.default.id
  service                 = "servicenetworking.googleapis.com"
  reserved_peering_ranges = [google_compute_global_address.sql_private_ip.name]
}
