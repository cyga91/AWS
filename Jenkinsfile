pipeline {
    agent any
    environment {
       HOME="."
       BUILD_TIME = "${sh(script:'date -u +%Y-%m-%d_%H-%M-%S-UTC', returnStdout: true).trim()}"
       CHANGE_AUTHOR = "${sh(script: "git --no-pager show -s --format='%ae'", returnStdout: true).trim()}"
    }

    stages {
        stage('Checkout'){
            steps{
                git 'https://github.com/cyga91/AWS/'
            }
        }
        stage('Build') {
            steps {
                sh "./mvnw clean package"

                sh "sls devploy -v"
//                 stash includes: 'build/libs/*.jar', name: 'jar'
//                 stash includes: 'build/reports/**', name: 'reports'
//                 stash includes: 'build/test-results/**', name: 'testresults'
            }

//             post {
//                 // If Maven was able to run the tests, even if some of the test
//                 // failed, record the test results and archive the jar file.
//                 always {
//                     junit '**/target/surefire-reports/TEST-*.xml'
//                     archiveArtifacts 'target/*.jar'
// //                     emailext
// //                         subject: 'Job \'${JOB_NAME}\' (${BUILD_NUMBER}) is waiting for input',
// //                         body: 'Please go to  ${BUILD_URL} and verify the build',
// //                         to: 'radcyg91@gmail.com'
// //                         attachLog: true,
// //                         compressLog: true,
// //                         recipientProviders: [buildUser()]
//                 }
//             }
        }
    }
}
