pipeline {
    agent any

    stages {

        stage('PULL') {
            steps {
               git 'https://github.com/Gaurav1244/Final.git'
            }
        }

        stage('FRONTEND-DOCKER-BUILD') {
            steps {
                dir('frontend') {
                    sh 'docker build -t gaurav262004/easy-frontend:latest .'
                }
            }
        }

        stage('BACKEND-DOCKER-BUILD') {
            steps {
                dir('backend') {
                    sh 'docker build -t gaurav262004/easy-backend:latest .'
                }
            }
        }

        stage('DOCKER-PUSH') {
            steps {
                sh '''
                docker push gaurav262004/easy-frontend:latest
                docker push gaurav262004/easy-backend:latest
                '''
            }
        }

        stage('DOCKER-CLEAN') {
            steps {
                sh '''
                docker rmi -f gaurav262004/easy-frontend:latest
                docker rmi -f gaurav262004/easy-backend:latest
                '''
            }
        }

        stage('DEPLOY') {
            steps {
                // Use workspace path to the deployment folder
                sh 'kubectl apply -f ${WORKSPACE}/simple-deploy/'
            }
        }
    }
}

