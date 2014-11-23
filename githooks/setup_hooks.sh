#!/bin/bash
set -x
set -e
set -u
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cp ${DIR}/pre-push ${DIR}/../.git/hooks/
