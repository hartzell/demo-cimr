// a simple little jenkins job, proof of concept
job('my-second-job') {
    steps {
        shell """#!/bin/bash
echo "Hello world!"
"""
    }
}

