pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Pull latest code from GitHub
                git 'https://github.com/<your-username>/<your-repo>.git'
            }
        }

        stage('Build with Maven') {
            steps {
                // Compile and package into JAR
                sh 'mvn clean package'
            }
        }

        stage('Static Analysis') {
            steps {
                // Run OWASP Dependency-Check (checks vulnerable libraries)
                sh 'mvn org.owasp:dependency-check-maven:check'

                // Run SpotBugs (detects bugs in code)
                sh 'mvn spotbugs:spotbugs'
            }
        }

        stage('Docker Build') {
            steps {
                // Build Docker image using Docker CLI on the host
                sh 'docker build -t insecurebankapp .'
            }
        }
    }

    post {
        always {
            // Archive reports so you can view them in Jenkins
            archiveArtifacts artifacts: '**/target/*.xml, **/dependency-check-report.html, **/spotbugsXml.xml', fingerprint: true
        }
    }
}