def sonarChecks(){
    sh ''' 
        echo Sonar Checks In Progress
        sonar-scanner -Dsonar.login="${SONARCRED_USR}" -Dsonar.password=${SONARCRED_PSW}  -Dsonar.host.url=http://172.31.86.248:9000 -Dsonar.projectKey=${COMPONENT} ${ARGS}
        curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > sonar-quality-gate.sh
        sonar-quality-gate.sh ${SONARCRED_USR} ${SONARCRED_PSW} ${SONATURL} ${COMPONENT}
        echo Sonar Checks Completed
    ''' 
}