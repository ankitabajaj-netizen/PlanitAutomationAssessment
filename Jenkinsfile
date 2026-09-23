pipeline {
    agent any

    options {
        timestamps()
        timeout(time: 30, unit: 'MINUTES')
        disableConcurrentBuilds()
    }

    environment {
        // Point at a different environment by overriding BASE_URL in the job config.
        BASE_URL = "${env.BASE_URL ?: 'http://jupiter.cloud.planittesting.com'}"
    }

    stages {

        stage('Build') {
            steps {
                sh 'mvn -B -ntp clean compile test-compile'
            }
        }

        stage('Install Playwright browsers') {
            steps {
                // Ships with the Playwright Java dependency once it's resolved;
                // installs Chromium (plus its OS-level dependencies) for this agent.
                sh 'mvn -B -ntp exec:java -Dexec.mainClass="com.microsoft.playwright.CLI" -Dexec.classpathScope=test -Dexec.args="install --with-deps chromium"'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn -B -ntp test'
            }
        }
    }

    post {
        always {
            junit allowEmptyResults: true, testResults: 'target/cucumber-reports/cucumber.xml'
            archiveArtifacts artifacts: 'target/cucumber-reports/**, target/surefire-reports/**', allowEmptyArchive: true
        }
    }
}
