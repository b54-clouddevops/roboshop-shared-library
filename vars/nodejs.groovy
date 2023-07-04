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
        curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > sonar-quality-gate.sh
        sonar-quality-gate.sh ${SONARCRED_USR} ${SONARCRED_PSW} ${SONATURL} ${COMPONENT}
        echo Sonar Checks Completed

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
                        sonarChecks()
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