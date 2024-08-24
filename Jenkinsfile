pipeline {
    agent any

 environment {
        IMAGE_NAME = 'truongthanh8498/spring'
        CREDENTIALS_ID = 'dockerhub' // ID của credentials đã tạo trong Jenkins
    }

    stages {
        stage('Build Docker Image') {
                    steps {
                        // Xây dựng Docker image từ Dockerfile
                        script {
                            def imageTag = "latest" // Hoặc bạn có thể dùng một tag khác, ví dụ: "v1.0.0"
                            sh "docker build -t ${IMAGE_NAME}:${imageTag} ."
                        }
                    }
                }

        stage('Login to Docker Hub') {
                   steps {
                       // Đăng nhập vào Docker Hub
                       script {
                           withCredentials([usernamePassword(credentialsId: "${CREDENTIALS_ID}", passwordVariable: 'DOCKER_HUB_PASSWORD', usernameVariable: 'DOCKER_HUB_USERNAME')]) {
                               sh 'echo $DOCKER_HUB_PASSWORD | docker login -u $DOCKER_HUB_USERNAME --password-stdin'
                           }
                       }
                   }
               }
stage('Push Docker Image') {
            steps {
                // Đẩy Docker image lên Docker Hub
                script {
                    def imageTag = "latest"
                    sh "docker push ${IMAGE_NAME}:${imageTag}"
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
            // Đăng xuất khỏi Docker Hub sau khi hoàn thành
            sh 'docker logout'
        }
        success {
            echo 'Docker image pushed successfully.'
        }
        failure {
            echo 'Failed to push Docker image.'
        }
    }
}