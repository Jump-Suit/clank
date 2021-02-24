#!/bin/bash

# Run an example Clank MAS server as a container using the `config/mas.json` file.

HOSTNAME="clank-mas"
CONTAINER_NAME="clank-mas"
CLANK_CONFIG="mas.json"

# Set the directory to this script's current directory
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd $DIR

docker run \
	-it \
	--rm \
	-h ${HOSTNAME} \
	-e CLANK_CONFIG=${CLANK_CONFIG} \
	--name ${CONTAINER_NAME} \
	clank

