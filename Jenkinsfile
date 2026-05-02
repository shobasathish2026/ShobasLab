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
		        bat 'start /B java -jar target/InsecureBankApp-1.0.jar'
		        bat '''
		            zap-cli start --start-options "-config api.disablekey=true"
		            zap-cli quick-scan http://localhost:8080
		            zap-cli report -o zap-report.html -f html
		            zap-cli stop
		        '''
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
