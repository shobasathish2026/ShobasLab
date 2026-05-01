pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Pull latest code from GitHub
                git branch: 'main', url: 'https://github.com/shobasathish2026/ShobasLab.git'
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

        stage('Unit Tests') {
            steps {
                // Run JUnit tests
                sh 'mvn test'
            }
        }

        stage('Docker Build') {
            steps {
                // Build Docker image using Docker CLI on the host
                sh 'docker build -t insecurebankapp .'
            }
        }

        stage('Dynamic Analysis') {
            steps {
                // Run the container
                sh 'docker run -d -p 8080:8080 --name insecurebank insecurebankapp'

                // Example: OWASP ZAP scan (requires zap-cli installed in Jenkins agent)
                sh '''
                    zap-cli start --start-options "-config api.disablekey=true"
                    zap-cli quick-scan http://localhost:8080
                    zap-cli report -o zap-report.html -f html
                    zap-cli stop
                '''

                // Stop and remove container after scan
                sh 'docker stop insecurebank || true'
                sh 'docker rm insecurebank || true'
            }
        }
    }

    post {
        always {
            // Archive reports so you can view them in Jenkins
            archiveArtifacts artifacts: '**/target/*.xml, **/dependency-check-report.html, **/spotbugsXml.xml, zap-report.html', fingerprint: true
        }
    }
}
