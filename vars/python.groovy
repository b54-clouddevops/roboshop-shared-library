def lintChecks(){
    sh ''' 
        echo Installing PYLint for ${COMPONENT}
        #pip3 install pylint
        #pylint *.py
        echo lint checks completed for ${COMPONENT}

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

        }                                                                             
    }
}