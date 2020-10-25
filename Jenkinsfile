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
        booleanParam(name: 'runTests', defaultValue: true, description: 'Run integration tests (if tests fail build will be failed)')
    }
    stages {
        stage('Audit tools') {
            steps {
                auditTools()
            }
        }
        stage('Checkout'){
            environment {
            CHANGE_AUTHOR = "${sh(script: "git --no-pager show -s --format='%ae'", returnStdout: true).trim()}"
            }
            steps{
                git 'https://github.com/cyga91/AWS/'
                echo "This build is on branch: ${BRANCH}, stage: ${STAGE_NAME}, triggered by: ${CHANGE_AUTHOR}"
            }
        }
        stage('Build') {
            environment {
            VERSION_DEV_SUFFIX =  getVersionDevSuffix()
            VERSION_PROD_SUFFIX = getVersionProdSuffix()
            BUILD_TIME = "${sh(script:'date -u +%Y-%m-%d_%H-%M-%S-UTC', returnStdout: true).trim()}"
            }
            steps {
                sh "./mvnw clean install -DskipTests"
                //stash includes: 'build/libs/*.jar', name: 'jar'
                //stash includes: 'build/reports/**', name: 'reports'
                //stash includes: 'build/test-results/**', name: 'testresults'
                echo "This build is on branch: ${BRANCH}, stage: ${STAGE_NAME}, was created at: ${BUILD_TIME}"
                echo "Building version: ${VERSION} with suffix: ${VERSION_DEV_SUFFIX}"
                echo "Building version: ${VERSION} with suffix: ${VERSION_PROD_SUFFIX}"
            }
        }
        stage('Tets'){
            when {
                anyOf{
                    allOf{
                        branch 'master'
                    }
                    allOf{
                        expression { return params.runTests }
                    }
                }
            }
            steps {
                sh "./mvnw test"
                echo "Testing release ${BRANCH}"
                writeFile file: 'test-results.txt', text: 'passed'
            }
        }
        // stage('Deploy') {
        //     steps {
        //         sh "sls devploy -v"
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
            }
        }
    }
    post {
        success {
            echo 'Deploy success'
            // archiveArtifacts 'test-results.txt'
        }
        failure {
            echo 'Deploy failure'
        }
        always {
            echo 'Deploy ends'
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

void auditTools() {
    sh '''
        git version
        mvn --version
        sls -version
    '''
}

String getVersionDevSuffix() {
    if (params.DEV) {
        return env.VERSION
    } else {
        return env.VERSION + '+ci.' + env.BUILD_NUMBER
    }
}

String getVersionProdSuffix() {
    if (params.PROD) {
        return env.VERSION
    } else {
        return env.VERSION + '+ci.' + env.BUILD_NUMBER
    }
}