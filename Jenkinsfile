pipeline {
    agent any
    environment {
       BRANCH='DEVELOP'
       VERSION="1.0"
       HOME="."
    }
    parameters {
        booleanParam(name: 'DEV', defaultValue: false, description: 'Is this a develop candidate?')
        booleanParam(name: 'PROD', defaultValue: false, description: 'Is this a prod candidate?')
        // booleanParam(name: 'deployOverride', defaultValue: false, description: 'Force Deploy (used for deploying non-master branches)')
        // booleanParam(name: 'runTests', defaultValue: true, description: 'Run integration tests (if tests fail build will be failed)')
    }
    stages {
        stage('Audit tools') {
            steps {
                sh '''
                  git version
                  mvn --version
                '''
            }
        }
        stage('Checkout'){
            environment {
            CHANGE_AUTHOR = "${sh(script: "git --no-pager show -s --format='%ae'", returnStdout: true).trim()}"
            }
            steps{
                git 'https://github.com/cyga91/AWS/'
                echo "This build is on branch: ${BRANCH}"
                echo "This is stage: ${STAGE_NAME}"
                echo "This build was triggered by: ${CHANGE_AUTHOR}"
            }
        }
        stage('Build') {
            environment {
            VERSION_DEV_SUFFIX = "${sh(script:'if [ "${DEV}" == "false" ] ; then echo -n "${VERSION}+ci.${BUILD_NUMBER}"; else echo -n "${VERSION}"; fi', returnStdout: true)}"
            VERSION_PROD_SUFFIX = "${sh(script:'if [ "${PROD}" == "false" ] ; then echo -n "${VERSION}+ci.${BUILD_NUMBER}"; else echo -n "${VERSION}"; fi', returnStdout: true)}"
            BUILD_TIME = "${sh(script:'date -u +%Y-%m-%d_%H-%M-%S-UTC', returnStdout: true).trim()}"
            }
            steps {
                sh "./mvnw clean install -DskipTests"
                // sh "./mvnw clean package"
                echo "This build is on branch: ${BRANCH}"
                echo "This is stage: ${STAGE_NAME}"
                echo "Building version: ${VERSION} with suffix: ${VERSION_DEV_SUFFIX}"
                echo "Building version: ${VERSION} with suffix: ${VERSION_PROD_SUFFIX}"
                echo "This build was created at: ${BUILD_TIME}"
//                 sh "sls devploy -v"
//                 stash includes: 'build/libs/*.jar', name: 'jar'
//                 stash includes: 'build/reports/**', name: 'reports'
//                 stash includes: 'build/test-results/**', name: 'testresults'
            }
        }
        stage('Tets'){
            when {
                branch 'Jenkinsfile'
            }
            steps {
                sh "./mvnw test"
                echo "Testing release ${BRANCH}"
                writeFile file: 'test-results.txt', text: 'passed'
                // stash includes: 'build/reports/**', name: 'reports'
                // stash includes: 'build/test-results/**', name: 'testresults'
            }
        }
        // stage('Deploy') {
        //     steps {

        //     }
        // }
        stage('Publish') {
            when {
                anyOf{
                    expression { return params.DEV }
                    expression { return params.PROD }
                }
            }
            steps {
                echo "Is version develop: ${DEV}"
                echo "Is version prod: ${PROD}"
                // sh 'dotnet publish -p:VersionPrefix="${VERSION}" --version-suffix "${VERSION_RC}" ./m3/src/Pi.Web/Pi.Web.csproj -o ./out'
                // archiveArtifacts('out/')
            }
        }
    }
    post {
        // If Maven was able to run the tests, even if some of the test
        // failed, record the test results and archive the jar file.
        success {
            echo 'Deploy success'
            archiveArtifacts 'test-results.txt'
            // junit '**/target/surefire-reports/TEST-*.xml'
            // archiveArtifacts 'target/*.jar'
        }
        failure {
            echo 'Deploy failure'
        }
        always {
            echo 'Deploy ends'
            // junit '**/target/surefire-reports/TEST-*.xml'
            // archiveArtifacts 'target/*.jar'
            // emailext
            //     subject: 'Job \'${JOB_NAME}\' (${BUILD_NUMBER}) is waiting for input',
            //     body: 'Please go to  ${BUILD_URL} and verify the build',
            //     to: 'radcyg91@gmail.com'
            //     attachLog: true,
            //     compressLog: true,
            //     recipientProviders: [buildUser()]
        }
    }
}
