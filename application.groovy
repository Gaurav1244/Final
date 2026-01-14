pipeline {
    agent any

    stages {

        stage('PULL') {
            steps {
               git 'https://github.com/Gaurav1244/cdec-batch21-2repo.git'
            }
        }

        stage('FRONTEND-DOCKER-BUILD') {
            steps {
                sh '''
                cd frontend
                docker build -t gaurav262004/easy-frontend:latest .
                '''
            }
        }

        stage('BACKEND-DOCKER-BUILD') {
            steps {
                sh '''
                cd backend
                docker build -t gaurav262004/easy-backend:latest .
                '''
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
                sh 'kubectl apply -f simple-deploy/'
            }
        }
    }
}
