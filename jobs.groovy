// a simple little jenkins job, proof of concept
job('my-first-job') {
    steps {
        shell """#!/bin/bash
echo "Hello world!"
"""
    }
}
