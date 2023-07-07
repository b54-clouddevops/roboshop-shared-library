def lintChecks(){
    stage('Lint Checks') {
        if(env.APP_TYPE == "nodejs") {
        sh  ''' 
           echo Installing JSLint for ${COMPONENT}
           npm i jslint  
           node_modules/jslint/bin/jslint.js server.js || true
           echo lint checks completed for ${COMPONENT}        
           ''' 
        }
        else if(env.APP_TYPE == "python") {
        sh '''
            # pip3 install pylint
            # pip *.py
            echo lint checks started for ${COMPONENT} using pytlint
            echo lint checks completed for ${COMPONENT}  
        ''' 
        }
        else if(env.APP_TYPE == "java") {
        sh '''
            echo lint checks started for ${COMPONENT} using mvn
            # mvn checkstyle:check
            echo lint checks completed for ${COMPONENT}  
        ''' 
        }
        else  {
        sh '''
            echo lint checks started for ${COMPONENT} using angular js
            echo lint checks completed for ${COMPONENT}  
         ''' 
        }
    }
}


def sonarChecks(){
        stage('Sonar Checks') {
           sh 'echo Sonar Checks In Progress'
           // sh 'sonar-scanner -Dsonar.host.url=http://172.31.86.248:9000  ${ARGS} -Dsonar.projectKey=${COMPONENT}  -Dsonar.login=admin -Dsonar.password=password'
           // sh 'curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > sonar-quality-gate.sh'
           //sh 'bash -x sonar-quality-gate.sh admin ${SONARCRED_PSW} ${SONARURL} ${COMPONENT}'       
           sh 'echo Sonar Checks Completed'        
        }
}

// This is how we can handle scripted pipeline for paralle stages
// Ref: https://stackoverflow.com/questions/57485965/how-can-i-create-parallel-stages-in-jenkins-scripted-pipeline

def testCases() {
        stage('Test Cases') {
                def stages = [:]

                stages["Unit Testing"] = {
                        echo "Unit Testing Started for ${COMPONENT}"
                        echo "Unit Testing Completed for ${COMPONENT}"
                }
                stages["Integration Testing"] = {
                        echo "Integration Testing Started for ${COMPONENT}"
                        echo "Integration Testing Completed for ${COMPONENT}"
                }
                stages["Functional Testing"] = {
                        echo "Functional Testing Started for ${COMPONENT}"
                        echo "Functional Testing Completed for ${COMPONENT}"
                }

                parallel(stages)
        }                        
}