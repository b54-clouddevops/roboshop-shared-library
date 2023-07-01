def lintChecks(){
    sh ''' 
        echo Performing lintCheck for ${COMPONENT}
        mvn checkstyle:check || true                          
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
                        sh "mvn clean compile"
                }
            }

        }                                                                             
    }
}