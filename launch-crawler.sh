#!/bin/bash
docker exec coordinator bash crawl.sh
read -n 1 -s -r -p "Press any key to continue"
