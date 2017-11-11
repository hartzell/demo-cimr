#!/bin/bash

# simple error checking, complain about using unset variables
set -o errexit
set -o nounset

# Perhaps this should be path to $0...
mydir=$(pwd)

# Set vars and export them in separate stmts so errexit can catch
# errors, e.g. missing files.
JENKINS_GIT_PRIVATE_KEY="$(cat $HOME/.cimr-secrets/git-key)"
JENKINS_SLAVES_PRIVATE_KEY="$(cat $HOME/.cimr-secrets/slaves-key)"
JENKINS_GITHUB_TOKEN="$(cat ${HOME}/.cimr-secrets/github-api-token)"
JENKINS_ADMIN_PASSWORD="$(cat ${HOME}/.cimr-secrets/admin-password)"
export JENKINS_GIT_PRIVATE_KEY JENKINS_SLAVES_PRIVATE_KEY \
       JENKINS_GITHUB_TOKEN JENKINS_ADMIN_PASSWORD

docker run \
  --name demo-cimr \
  --detach \
  --restart=on-failure:5 \
  -v ${mydir}/cimr_config.yaml:/cimr_config.yaml \
  -p 4242:8080 \
  -e JENKINS_OPTS="--prefix=/foo/" \
  -e JENKINS_SLAVES_PRIVATE_KEY \
  -e JENKINS_GIT_PRIVATE_KEY \
  -e JENKINS_GITHUB_TOKEN \
  -e JENKINS_ADMIN_PASSWORD \
  cimr:0.0.6
