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
                    sh 'docker tag yuld/spb-halolo truongthanh8498/spring:spb-halolo'
                    sh ' docker push truongthanh8498/spring:spb-halolo'
                }
            }
        }

        // stage('Deploy spring dev') {
        //     steps {
        //         sh 'echo "pull image..."'
        //         sh 'docker image pull truongthanh8498/spring:spb-halolo'
        //         sh 'echo "pull image 2..."'
        //         sh 'docker container stop yuld-halolo || echo "this container not exists" '
        //         sh 'docker network create dev || echo "this network exists" '
        //         sh 'echo y | docker container prune '

        //         sh 'docker container run -d --rm --name yuld-halolo -p 24001:24001 --network dev truongthanh8498/spring:spb-halolo'
        //     }
        // }
    }

    post {
        always {
            cleanWs()
        }
    }
}