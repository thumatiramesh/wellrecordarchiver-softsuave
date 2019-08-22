pipeline {
    agent any

    parameters {
        // Edit these to suit your project

        string(name: 'SPHINX_PATH', defaultValue: './documentation')
        string(name: 'SPHINX_BUILD_DIRNAME', defaultValue: 'docs')
        string(name: 'SPHINX_SOURCE_DIRNAME', defaultValue: '')
        string(name: 'SPHINX_HTPASSWORD_CONTENT', defaultValue: 'geolinkis:$apr1$lrr5xKNH$qRftykN6aJt4junc8yNN51')
        string(name: 'SPHINX_DEPLOY_HOST', defaultValue: 'docs.bowriverstudio.com')
        string(name: 'SPHINX_DEPLOY_PATH', defaultValue: '/geolinkis/')
        string(name: 'GIT', defaultValue: 'git@gitlab.com/geolinkis/wellrecordarchiver.git')
        string(name: 'GIT_BRANCH', defaultValue: 'master')
        string(name: 'CREDENTIAL_ID', defaultValue: 'jenkins-gitlab-ssh-maurice')
        string(name: 'ROCKETCHAT_CHANNEL', defaultValue: '#documentation')

    }
    environment {

        // Don't touch this
        SPHINX_CONTAINER_NAME = JOB_BASE_NAME.toLowerCase()
    }


    stages {
        stage('Call Pipeline-Documentation') {
            steps {
                script{
                    build job: 'pipeline-documentation',
                        parameters: [[$class: 'StringParameterValue', name: 'SPHINX_PATH', value: "${SPHINX_PATH}"],
                            [$class: 'StringParameterValue', name: 'SPHINX_BUILD_DIRNAME', value: "${SPHINX_BUILD_DIRNAME}"],
                            [$class: 'StringParameterValue', name: 'SPHINX_SOURCE_DIRNAME', value: "${SPHINX_SOURCE_DIRNAME}"],
                            [$class: 'StringParameterValue', name: 'SPHINX_HTPASSWORD_CONTENT', value: "${SPHINX_HTPASSWORD_CONTENT}"],
                            [$class: 'StringParameterValue', name: 'SPHINX_DEPLOY_HOST', value: "${SPHINX_DEPLOY_HOST}"],
                            [$class: 'StringParameterValue', name: 'SPHINX_DEPLOY_PATH', value: "${SPHINX_DEPLOY_PATH}"],
                            [$class: 'StringParameterValue', name: 'GIT', value: "${GIT}"],
                            [$class: 'StringParameterValue', name: 'GIT_BRANCH', value: "${GIT_BRANCH}"],
                            [$class: 'StringParameterValue', name: 'CREDENTIAL_ID', value: "${CREDENTIAL_ID}"],
                            [$class: 'StringParameterValue', name: 'ROCKETCHAT_CHANNEL', value: "${ROCKETCHAT_CHANNEL}"],
                            [$class: 'StringParameterValue', name: 'SPHINX_CONTAINER_NAME', value: "${SPHINX_CONTAINER_NAME}"]
                         ], wait: true
                }
            }
        }
    }
}




