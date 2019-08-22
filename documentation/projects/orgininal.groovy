pipeline {
    agent any
    environment {
        // Todo: auto detect config from conf.py
        // Todo: input GIT and GIT_BRANCH
        // Edit these to suit your project
        SPHINX_PATH  = './documentation'
        SPHINX_BUILD_DIRNAME   = 'docs'
        SPHINX_SOURCE_DIRNAME  = ''
        SPHINX_DEPLOY_HOST = 'docs.bowriverstudio.com'
        SPHINX_DEPLOY_PATH = '/geolinkis'

        ROCKETCHAT_CHANNEL = '' // Public channels only

        GIT = 'git@gitlab.com/geolinkis/wellrecordarchiver.git'
        GIT_BRANCH = 'master'
        CRENDENTIAL_ID = 'jenkins-gitlab-ssh'

        // Don't touch this
        SPHINX_CONTAINER_NAME = JOB_BASE_NAME.toLowerCase()
    }
    stages {
        stage('Clone from source') {
            steps {
                script {
                    git branch: env.GIT_BRANCH,
                    credentialsId: env.CRENDENTIAL_ID,
                    url: 'ssh://' + env.GIT
                }
            }
        }
        stage('Install Sphinx extensions/themes') {
            steps {
                script {
                    // Add any requiments for your build. Follow example.
                    sh 'pip install sphinx_rtd_theme'
                }
            }
        }
        stage('Build documentation') {
            steps {
                // clear out old files
                sh 'rm -rf ${SPHINX_PATH}/${SPHINX_BUILD_DIRNAME}'
                sh 'rm -r ${SPHINX_PATH}/sphinx-build.log'

                sh '''
                   sphinx-build \
                   -q -w ${SPHINX_PATH}/sphinx-build.log \
                   -b html \
                   -d ${SPHINX_PATH}/${SPHINX_BUILD_DIRNAME}/doctrees ${SPHINX_PATH}/${SPHINX_SOURCE_DIRNAME} ${SPHINX_PATH}/${SPHINX_BUILD_DIRNAME}
                '''
            }
            post {
                failure {
                    sh 'cat ${SPHINX_PATH}/sphinx-build.log'
                }
            }
        }
        stage('Deployment') {
            steps {
                script {
                    // Todo: Remove dangling image after build
                    // Todo: Format rocketchat message
                    // Copy docker-compose file and sphinx nginx config
                    sh 'cp -r /sphinx/. .'
                    // Substitute env vars to docker-compose and .env
                    sh 'envsubst \\$SPHINX_CONTAINER_NAME < "docker-compose.yml" > "docker-compose.tmp"'
                    sh 'mv docker-compose.tmp docker-compose.yml'
                    sh 'envsubst < ".env" > ".env.tmp"'
                    sh 'mv .env.tmp .env'
                    // Deploy
                    try{
                        sh 'docker stop ${SPHINX_CONTAINER_NAME} && docker rm ${SPHINX_CONTAINER_NAME}'
                    } catch(err){
                        println err
                    }
                    sh 'docker-compose up -d --build'

                    // RocketChat Notification
                    rocketSend channel:env.ROCKETCHAT_CHANNEL, message:'Build was successful'
                }
            }
        }
        stage('Backup container requirements') {
            steps {
                script {
                    // Substitute env vars to Dockerfile
                    sh 'envsubst < "Dockerfile" > "Dockerfile.tmp"'
                    sh 'mv Dockerfile.tmp Dockerfile'
                    env.BACKUP_LOCATION = "/backups/${SPHINX_CONTAINER_NAME}"
                    try {
                        sh 'rm -r ${BACKUP_LOCATION}'
                    } catch (err) {
                        println err
                    }
                    sh 'mkdir -p ${BACKUP_LOCATION}/${SPHINX_PATH#"./"}/${SPHINX_BUILD_DIRNAME}'
                    sh 'cp ./docker-compose.yml ${BACKUP_LOCATION}'
                    sh 'cp ./Dockerfile ${BACKUP_LOCATION}'
                    sh 'cp ./sphinx.conf ${BACKUP_LOCATION}'
                    sh 'cp ./.htpasswd ${BACKUP_LOCATION}'
                    sh 'cp ./.env ${BACKUP_LOCATION}'
                    sh 'cp -r ./${SPHINX_PATH#"./"}/${SPHINX_BUILD_DIRNAME}/. ${BACKUP_LOCATION}/${SPHINX_PATH#"./"}/${SPHINX_BUILD_DIRNAME}/'
                }
            }
        }
    }
}