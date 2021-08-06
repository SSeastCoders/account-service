pipeline {
    agent any
    stages {
        stage('SonarQube analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh "mvn sonar:sonar"
                }
            }
        }
        stage('Quality Gate'){
            steps {
                waitForQualityGate abortPipeline= true
            }
        }
        stage('Build') {
            steps {
                sh 'mvn -DskipTests clean package'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
    }
    post {
        always {
            junit 'target/surefire-reports/*.xml'
        }
        success {
            archiveArtifacts artifacts: '**/*.jar'
        }
    }
}
