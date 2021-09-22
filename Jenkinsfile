
pipeline {
    agent any
    environment {
        serviceName = 'account-service'
        //awsRegion = 'us-east-1'
        mavenProfile = 'dev'
        commitIDShort = sh(returnStdout: true, script: "git rev-parse --short HEAD")
    }
    stages {
        stage('Clean and Test') {
            steps {
                sh 'mvn clean test'
            }
        }
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQubeServer') {
                    sh 'mvn sonar:sonar'
                }
            }
        }
        stage('Quality Gate') {
            steps {
                timeout(time: 10, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage('Maven Build') {
            steps {
                sh 'mvn clean package -P ${mavenProfile} -Dskiptests'
            }
        }
        stage('Docker Image Build and ECR Image Push') {
            steps {
                withCredentials([string(credentialsId: 'publicNumber', variable: 'awsID')]) {
                    sh '''
                        aws ecr get-login-password --region us-east-2 | docker login --username AWS --password-stdin ${awsID}.dkr.ecr.us-east-2.amazonaws.com

                        docker build -t ${awsID}.dkr.ecr.us-east-2.amazonaws.com/dev-account-service:latest .
                        docker push ${awsID}.dkr.ecr.us-east-2.amazonaws.com/dev-account-service:latest

                    '''
                }
            }
        }
    }
    post {
        success {
            sh 'docker image prune -af'
        }
    }
}