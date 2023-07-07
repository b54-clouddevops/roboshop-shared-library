def call() {
    node {
        common.lintChecks()
    }
}


// def lintChecks(){
//     sh ''' 
//         echo Installing PYLint for ${COMPONENT}
//         #pip3 install pylint
//         #pylint *.py
//         echo lint checks completed for ${COMPONENT}

//     ''' 
// }


// def call(COMPONENT) {
//     pipeline {
//         agent {  label 'WS' }
//         environment {
//             SONARCRED = credentials('SONARCRED') 
//             SONATURL  = "172.31.86.248"
//         }
//         stages {      
//             stage('Lint Checks') {
//                 steps { 
//                     script {
//                         lintChecks()
//                     }
//                 }
//             }

//             stage('Sonar Checks') {
//                 steps {                
//                         script {
//                             env.ARGS="-Dsonar.sources=."
//                             common.sonarChecks()
//                         }
//                     }
//                 }

//             stage('Testing') {
//                 steps {                
//                         sh "echo Testing"
//                     }
//                 }
//             }                                                                             
//         }
//     }