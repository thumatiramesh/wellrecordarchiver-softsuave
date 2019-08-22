#!/bin/bash

read-paths(){
	echo "Reading paths.."
	path_file="paths.txt"
	# Ignoring comments
	local index=0;
	while read -r line || [ -n "$line" ]; do
		if [[ ${line:0:1} == "#" ]]; then 
			continue;
		fi
	job_paths[$index]=$line
    index=$((index + 1))
	done < "$path_file"
}

register-path-pipeline(){
	echo "Registering pipeline.."
	rm -f ./docker-compose.yml
	local index=0;
	local prcs=''
	for path in "${job_paths[@]}"; do
		path_md5=$(md5 $path)
		prcs="{\"gsub\": {\"field\": \"file.url\", \"pattern\": \"^file:///dirs/${path_md5}\",\"replacement\": \"${path//\\/\\\\\\\\}\"}},${prcs}"
    	index=$((index + 1))
	done
	prcs="${prcs}"'{"gsub": {"field": "file.url", "pattern": "\/", "replacement": "\\\\"}}'
	prcs="[${prcs}]"
	jq --argjson processors "$(echo $prcs)" '.processors += $processors' ./pipeline.template > pipeline.json
	
	# Retry registration until Elasticsearch is up and running
	curl -4 --connect-timeout 5 --max-time 10 --retry 5 --retry-delay 0 --retry-max-time 40 -H 'Content-Type: application/json' -XPUT 'http://elasticsearch:9200/_ingest/pipeline/path-pipeline' -d "$(cat pipeline.json)"
	exit_status=$?
	if [ $exit_status = "7" ]; then
		echo "Retry registration.. Elasticsearch is not ready."
		sleep 5
    	register-path-pipeline
	elif [ $exit_status != 0 ]
	  then
	    exit $exit_status
	fi
	printf "\n"
}


generate-jobs(){
	echo "Generate crawling jobs.."
	# Generate job settings
	local index=0;
	for path in "${job_paths[@]}"; do 
		path_md5=$(md5 $path)
		export JOB_NAME=job_$path_md5
		# Skip if job is defined
		if [ -d "/jobs/job_$path_md5" ]; then continue; fi

	    export JOB_PATH=/dirs/$path_md5
	    export JOB_HOST_PATH=$path
	    mkdir -p ./jobs/$JOB_NAME
	    envsubst < ./_settings.template > ./jobs/$JOB_NAME/_settings.json
	    jobs[$index]=$JOB_NAME
    	index=$((index + 1))
	done
}

md5(){
	echo $1 | md5sum  | cut -d' ' -f1
}

start-fscrawler(){
	echo "Starting crawler.."
	rm -f ./docker-compose.yml

	# Prepare mounts
	export MOUNTED_DIRS=''
	local index=0;
	for path in "${job_paths[@]}"; do 
		path_md5=$(md5 $path)
		# Convert path from Windows to POSIX
		local posix_path="$(echo /$path | sed -e 's/\\/\//g' -e 's/://')"
		MOUNTED_DIRS="- ${posix_path}:/dirs/${path_md5}"$'\n      '"${MOUNTED_DIRS}" 
    	index=$((index + 1))
	done
    envsubst < ./docker-compose.template > ./docker-compose.yml

	# Run Fscrawler
	docker-compose up -d
}


run-crawling-jobs(){
	for job in "${jobs[@]}"; do 
		docker exec fscrawler fscrawler $job --trace --debug --loop 1
	done
	echo "Stopping crawler.."
	echo "Visit http://localhost:5601 to access Kibana"
	# Shutdown FScrawler
	docker-compose down
}

declare -a jobs
declare -a job_paths

read-paths
register-path-pipeline
generate-jobs
start-fscrawler
run-crawling-jobs