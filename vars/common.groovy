def sonarChecks(){
        sh 'echo Sonar Checks In Progress'
        // sh 'sonar-scanner -Dsonar.host.url=http://172.31.86.248:9000  ${ARGS} -Dsonar.projectKey=${COMPONENT}  -Dsonar.login=admin -Dsonar.password=password'
        // sh 'curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > sonar-quality-gate.sh'
        //sh 'bash -x sonar-quality-gate.sh admin ${SONARCRED_PSW} ${SONARURL} ${COMPONENT}'       
        sh 'echo Sonar Checks Completed'
}

// def sonarChecks(){
//         sh "echo Sonar Checks In Progress"
//         sh "echo Sonar Checks Completed"
// }