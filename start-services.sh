#!/bin/bash
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" >/dev/null 2>&1 && pwd)"
echo DIR=${DIR} > ./.env

docker network create crawler 2> /dev/null
docker-compose -f  "${DIR}"/docker-compose.yml up -d --force-recreate
read -n 1 -s -r -p "Press any key to continue"