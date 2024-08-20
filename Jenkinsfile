pipeline {
    agent any

    tools {
        maven 'my-maven'
    }

    stages {
        stage('Build with maven...') {
            steps {
                sh 'mvn --version'
                sh 'java --version'
                sh 'mvn clean package -Dmaven.test.failure.ignore=true'
            }
        }

        stage('Packing/push image') {
                steps {
                withDockerRegistry(credentialsId: 'dockerhub', url: 'https://index.docker.io/v1') {
                    sh 'docker build -t yuld/spb-halolo .'  // Assuming Dockerfile is in the workspace
                    sh 'docker push yuld/spb-halolo'
                }
            }
        }

        stage('Deploy spring dev') {
            steps {
                echo 'Deploying and cleaning...'
                sh 'docker image pull yuld/spb-halolo'
                sh 'docker container stop yuld/spb-halolo || echo "this container not exists" '
                sh 'docker network create dev || echo "this network exists" '
                sh 'echo y | docker container prune '

                sh 'docker container run -d --rm --name yuld/halolo -p 24001:24001 --network dev yuld/halolo'
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}