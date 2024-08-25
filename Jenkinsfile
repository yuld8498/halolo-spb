pipeline {
    agent any

    tools {
        maven 'my-maven'
    }

    stages {
        stage('Build with maven......') {
            steps {
                sh 'mvn --version'
                sh 'java --version'
                sh 'mvn clean package -Dmaven.test.failure.ignore=true'
            }
        }

        stage('Packing/push image') {
                steps {
                    sh 'pwd'
                    withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                        sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                        sh 'docker build -t yuld/spb-halolo .'  // Assuming Dockerfile is in the workspace
                        sh 'docker tag yuld/spb-halolo truongthanh8498/spring:spb-halolo'
                        sh 'docker push truongthanh8498/spring:spb-halolo'
                    }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}