packer {
  required_plugins {
    docker = {
      version = ">= 0.0.7"
      source  = "github.com/hashicorp/docker"
    }
  }
}

variable "docker_username" {
  type    = string
  default = ""
}

variable "docker_password" {
  type    = string
  default = ""
}

variable "tomcat_home" {
  type    = string
  default = "/usr/local/tomcat-9"
}

source "docker" "ubuntu" {
  image   = "ubuntu:20.04"
  commit  = true
  changes = [
    "EXPOSE 8080",
    "CMD [\"${var.tomcat_home}/bin/catalina.sh\", \"run\"]",
  ]
}

build {
  name = "spring-calculator"
  sources = [
    "source.docker.ubuntu"
  ]

  provisioner "shell" {
    inline = [
      "apt-get update",
      "apt-get install -y python3"
    ]
  }

  provisioner "ansible" {
    playbook_file = "./Jenkins/Job2/job2_playbook.yml"
    user = "root"
  }

  provisioner "file" {
    source      = "spring-calculator-1.0.war"
    destination = "${var.tomcat_home}/webapps/"
  }

  post-processors {
    post-processor "docker-tag" {
      repository = "femelloffm/spring-calculator"
      tags        = ["1.0"]
    }

    post-processor "docker-push" {
      login = true
      login_username = var.docker_username
      login_password = var.docker_password
    }
  }
}
