def lintChecks(){
    sh ''' 
        sh "echo Installing JSLint"
        sh "npm i jslint"
        sh "node_modules/jslint/bin/jslint.js server.js || true"

    ''' 
}


def call() {
    pipeline {
        agent {  label 'WS' }
        stages {      
                                          
            stage('Lint Checks') {
                steps { 
                    script {
                        nodejs.lintChecks()
                    }
                }
            }

            stage('Code Compile') {
                steps {                
                        sh "npm install"
                }
            }
        }                                                                             
    }
}