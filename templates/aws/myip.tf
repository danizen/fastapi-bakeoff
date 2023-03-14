data "http" "myip" {
  url = "http://ipv4.icanhazip.com"
  request_headers = {
    Accept = "text/plain"
  }
}

# my IP address
locals {
  myip_address = chomp(data.http.myip.response_body)
}