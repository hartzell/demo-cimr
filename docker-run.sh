#!/bin/bash

set -o errexit
set -o nounset

# perhaps this should be path to $0...
mydir=$(pwd)

# set vars and export them separate stmts so errexit can catch errors
# e.g. missing files
JENKINS_GIT_PRIVATE_KEY="$(cat $HOME/.cimr-secrets/cimr-git-key)"
JENKINS_SLAVES_PRIVATE_KEY="$(cat $HOME/.cimr-secrets/cimr-slaves-key)"
JENKINS_GITHUB_TOKEN="$(cat ${HOME}/.cimr-secrets/github-token)"
JENKINS_ADMIN_PASSWORD="$(cat ${HOME}/.cimr-secrets/admin-password)"
export JENKINS_GIT_PRIVATE_KEY JENKINS_SLAVES_PRIVATE_KEY \
       JENKINS_GITHUB_TOKEN JENKINS_ADMIN_PASSWORD

docker run \
  --name my-project-cimr \
  --detach \
  --restart=on-failure:5 \
  -v ${mydir}/cimr_config.yaml:/cimr_config.yaml \
  -p 4242:8080 \
  -e JENKINS_OPTS="--prefix=/foo/" \
  -e JENKINS_SLAVES_PRIVATE_KEY \
  -e JENKINS_GIT_PRIVATE_KEY \
  -e JENKINS_GITHUB_TOKEN \
  -e JENKINS_ADMIN_PASSWORD \
  hartzell/cimr:0.0.6
