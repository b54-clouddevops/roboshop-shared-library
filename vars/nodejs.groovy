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
            SONATURL  = "172.31.86.248"
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
                        common.sonarChecks()
                    }
                }
            }

            stage('Testing') {
                steps {
                    sh "echo Testing In Progress" 
                }
            }

        }                                                                             
    }
}