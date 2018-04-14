// a simple little jenkins job, proof of concept
job('my-first-job') {
    steps {
        shell """#!/bin/bash
echo "Hello world!"
"""
    }
}

// This job will fire whenever a Pull Request is created and/or whenever
// one of the trigger phrases occurs in the PR's comments
// (e.g. OK to test).
// There's a bit of magic here, check out the Github Pull Request Builder
// plugin docs...
job('fpull-request-to-demo-cimr') {
  scm {
    git {
      remote {
        // It's tempting to use the `github()` helper here, but that
        // way lies madness (it assumes `https://`, sigh...).
        url('git@github.com:hartzell/demo-cimr')
        credentials('git-key')
        refspec('+refs/pull/*:refs/remotes/origin/pr/*')
      }
      branch('${sha1}')
      extensions {
        wipeOutWorkspace()
      }
    }
    properties {
      githubProjectUrl('https://github.com/hartzell/demo-cimr')
    }
  }
  triggers {
    githubPullRequest {
      admins(['hartzell'])
      useGitHubHooks()
      whiteListTargetBranches(['develop'])
      allowMembersOfWhitelistedOrgsAsAdmin()
      extensions {
        commitStatus {
          context('Pull Request tests demo')
        }
      }
    }
  }
  steps {
    shell '''#!/bin/sh
sleep 42
echo "Hello world!"
true
'''
  }
}

