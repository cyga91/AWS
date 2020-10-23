pipeline {
    agent any

    stages {
        stage('Checkout'){
            steps{
                git 'https://github.com/cyga91/jenkins'
            }
        }
        stage('Build') {
            steps {
                // Run Maven on a Unix agent.
                sh "./mvnw clean package"

                // To run Maven on a Windows agent, use
                // bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                always {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                    emailext
                        subject: 'Job \'${JOB_NAME}\' (${BUILD_NUMBER}) is waiting for input',
                        body: 'Please go to  ${BUILD_URL} and verify the build',
                        to: 'radcyg91@gmail.com'
                        attachLog: true,
                        compressLog: true,
                        recipientProviders: [buildUser()]
                }
            }
        }
    }
}
