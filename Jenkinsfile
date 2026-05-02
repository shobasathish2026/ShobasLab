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
                bat 'mvn clean package'
            }
        }

        stage('Static Analysis') {
            steps {
                // Run OWASP Dependency-Check (checks vulnerable libraries)
                bat 'mvn org.owasp:dependency-check-maven:check'

                // Run SpotBugs (detects bugs in code)
                bat 'mvn spotbugs:spotbugs'
            }
        }

        stage('Unit Tests') {
            steps {
                // Run JUnit tests
                bat 'mvn test'
            }
        }

        stage('Dynamic Analysis') {
            steps {
                // Start the app
                bat 'start "InsecureBankApp" /B java -jar target/InsecureBankApp-1.0.jar'
                // Wait for app to start
                bat 'ping 127.0.0.1 -n 10 > nul'

                // Trigger spider and active scan via ZAP REST API
                bat 'curl "http://localhost:8081/JSON/spider/action/scan/?url=http://localhost:8080"'
                bat 'curl "http://localhost:8081/JSON/ascan/action/scan/?url=http://localhost:8080"'

                // Generate HTML report
                bat 'curl "http://localhost:8081/OTHER/core/other/htmlreport/" -o zap-report.html'

                // Stop the app
                bat 'taskkill /F /IM java.exe'
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
