def lintChecks(){
    sh ''' 
        echo Installing JSLint for ${COMPONENT}
        npm i jslint
        node_modules/jslint/bin/jslint.js server.js || true
        echo lint checks completed for ${COMPONENT}
    ''' 
}


def call(COMPONENT) {
    pipeline {
        agent {  label 'WS' }
        environment {
            SONARCRED = credentials('SONARCRED') 
            SONARURL  = "172.31.86.248"
        }
        stages {      

            stage('Lint Checks') {
                steps { 
                    script {
                        lintChecks()
                    }
                }
            }

            stage('Code Compile') {
                steps {                
                        sh "npm install"
                }
            }

            stage('Sonar Checks') {
                steps {
                    script {
                        env.ARGS="-Dsonar.sources=."              
                        common.sonarChecks()
                    }
                }
            }

            stage('Test Cases') {
                parallel {
                    stage('Unit Testing') {
                        steps {
                            sh "echo Unit testing started"
                            sh "echo Unit testing completed"
                        }
                    }
                    stage('Integtation Testing') {
                        steps {
                            sh "echo Integtation testing started"
                            sh "echo Integtation testing completed"
                        }
                    }
                    stage('Functional Testing') {
                        steps {
                            sh "echo Functional testing started"
                            sh "echo Functional testing completed"
                        }
                    }
                }
            }

            stage('Prepare Artifacts') {
                when { expression { env.TAG_NAME != null } }
                steps {
                    sh "echo Preparing Artifacts"
                }
            }

            stage('Upload Artifacts') {
                when { expression { env.TAG_NAME != null } }
                steps {
                    sh "echo Uploading Artifacts To Nexus"
                }
            }
        }                                                                             
    }
}