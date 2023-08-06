def call() {
    node {
        ansiColor('xterm') {
            git branch: 'main', url: 'https://github.com/b54-clouddevops/${REPONAME}.git'
            
            stage('terraform init') {
                sh "cd ${TFDIR}"
                sh "terrafile -f env-${ENV}/Terrafile"
                sh "terraform init -backend-config=env-${ENV}/${ENV}-backend.tfvars"            
            }

            stage('terraform plan') {
                sh "cd ${TFDIR}"
                sh "terraform plan -var-file=env-${ENV}/${ENV}.tfvars"
            }

            stage('Terraform Action') {
                sh "terraform ${ACTION} -auto-approve -var-file=env-${ENV}/${ENV}.tfvars"
                }
            }
        }
    }

// pipeline {
//     agent any
//     options {
//         ansiColor('xterm')
//     }
//     parameters {
//         choice(name: 'ENV', choices: ['dev', 'prod'], description: 'Select The Environment')
//         choice(name: 'ACTION', choices: ['apply', 'destroy'], description: 'Select Create or Destroy')
//     }

//     stages {
//         stage('terraform init') {
//             steps {    
//                     sh "terrafile -f env-${ENV}/Terrafile"
//                     sh "terraform init -backend-config=env-${ENV}/${ENV}-backend.tfvars"
//             }
//         }

//         stage('terraform plan') {
//             steps {    
//                     sh "terraform plan -var-file=env-${ENV}/${ENV}.tfvars"
//             }
//         }

//         stage('terraform apply') {
//             steps {    
//                     sh "terraform ${ACTION} -auto-approve -var-file=env-${ENV}/${ENV}.tfvars"
//             }
//         }
//     }
// }
