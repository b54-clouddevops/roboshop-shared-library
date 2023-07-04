def lintChecks(){
    sh ''' 
        echo Installing JSLint for ${COMPONENT}
        npm i jslint
        node_modules/jslint/bin/jslint.js server.js || true
        echo lint checks completed for ${COMPONENT}

    ''' 
}

def sonarChecks(){
    sh ''' 
        echo Sonar Checks In Progress
        sonar-scanner -Dsonar.sources=. -Dsonar.login=1cbe0f6f0cdbd9bb3ff8d78b880f7fb417eb4321  -Dsonar.host.url=http://172.31.86.248:9000 -Dsonar.projectKey=${COMPONENT}
        echo Sonar Checks Completed

    ''' 
}


def call(COMPONENT) {
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

            stage('Sonar Checks') {
                steps {
                    script {                
                        sonarChecks()
                    }
                }
            }

        }                                                                             
    }
}