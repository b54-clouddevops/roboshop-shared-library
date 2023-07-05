def sonarChecks(){
        sh "echo Sonar Checks In Progress"
        sh "sonar-scanner -Dsonar.host.url=http://172.31.86.248:9000 -Dsonar.projectKey=${COMPONENT} ${ARGS}  -Dsonar.login=admin -Dsonar.password=password"
        sh "curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > sonar-quality-gate.sh"
        sh "sonar-quality-gate.sh ${SONARCRED_USER} ${SONARCRED_PASS} ${SONARURL} ${COMPONENT} | true" 
        sh "echo Sonar Checks Completed"
}