pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'mvn -DskipTests clean package'
            }
        }
    }
    post {
        success {
            archiveArtifacts artifacts: '**/*.jar'
        }
    }
}