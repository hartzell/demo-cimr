// This job will fire whenever things are pushed to the develop branch
// (pushes, merges via the GUI).
job('push-to-develop') {
  logRotator {
    numToKeep(5)
  }
  concurrentBuild(false)
  scm {
    git {
      remote {
        name('push-to-develop-demo')
        url('git@github.com:hartzell/demo-cimr')
        credentials('git-key')
      }
      branch('develop')
      extensions {
        wipeOutWorkspace()
      }
    }
  }
  triggers {
    githubPush()
  }
  steps {
    shell '''#!/bin/sh
sleep 42
echo "Hello world!"
true
'''
  }
  publishers {
    githubCommitNotifier()
  }
}

// This job will fire whenever a Pull Request is created and/or whenever
// one of the trigger phrases occurs in the PR's comments
// (e.g. OK to test).
// There's a bit of magic here, check out the Github Pull Request Builder
// plugin docs...
job('pull-request-to-develop-demo') {
  scm {
    git {
      remote {
        // It's tempting to use the `github()` helper here, but that
        // way lies madness (it assumes `https://`, sigh...).  Use
        // `name()`, `url()`, and
        // `job/scm/properties/githubProjectUrl()`
        name('demo-cimr')
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
      githubProjectUrl('http://github.com/hartzell/demo-cimr')
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
          context('PR status check demo')
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
