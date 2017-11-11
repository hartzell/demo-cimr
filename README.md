# A demo CI Made Reliable setup

This contains the specific bits needed to use CIMR to set up a CI
demo.

See [cimr](https://github.com/hartzell/cimr) for a discusison of how to
use CIMR.

This directory contains:

- `README.md` -- this bit of documentation.
- `cimr_config.yaml` -- the cime configuration file.
- `docker-run.sh` -- the script for starting the jenkins docker
  container.
- `jobs.groovy` -- the Jobs DSL script watched by the seed job,
  defines all of the other jobs and views.
