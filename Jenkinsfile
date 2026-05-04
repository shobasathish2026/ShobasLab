pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/shobasathish2026/ShobasLab.git'
            }
        }

        stage('Build with Maven') {
            steps {
                bat 'mvn clean package'
            }
        }

        stage('Static Analysis') {
            steps {
                bat 'mvn org.owasp:dependency-check-maven:check'
                bat 'mvn spotbugs:spotbugs'
            }
        }

        stage('Unit Tests') {
            steps {
                bat 'mvn test'
            }
        }

        /*
        stage('Dynamic Analysis') {
            steps {
                // your ZAP scan steps
            }
        }
        */
    }

    post {
        always {
            archiveArtifacts artifacts: '**/target/*.xml, **/dependency-check-report.html, **/spotbugsXml.xml, zap-report.html', fingerprint: true
        }
    }
}
