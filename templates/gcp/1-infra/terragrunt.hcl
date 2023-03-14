inputs = yamldecode(file(find_in_parent_folders("vars.yaml")))

include "root" {
    path = find_in_parent_folders()
}
