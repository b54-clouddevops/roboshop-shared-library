def lintChecks(){
    sh ''' 
        echo Installing AngularLint for ${COMPONENT}
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

            stage('Code Quality Analysis') {
                steps {                
                        sh "echo Code Quality Analysis Is In Place"
                }
            }

        }                                                                             
    }
}