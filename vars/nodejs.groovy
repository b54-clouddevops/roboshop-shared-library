def lintChecks(){
    sh ''' 
        echo Installing JSLint
        npm i jslint
        node_modules/jslint/bin/jslint.js server.js || true

    ''' 
}


def call() {
    pipeline {
        agent {  label 'WS' }
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
        }                                                                             
    }
}