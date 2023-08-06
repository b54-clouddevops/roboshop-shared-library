def call() {
    properties([
            parameters([
                choice(choices: 'dev\nprod', description: "Select your environment", name: "ENV"),
                choice(choices: 'apply\ndestroy', description: "Chose an action", name: "ACTION")
            ]),
        ])
    node {
        ansiColor('xterm') {
            git branch: 'main', url: "https://github.com/b54-clouddevops/${REPONAME}.git"
            
            stage('terraform init') {
                sh "cd ${TFDIR}"
                sh "ls -ltr"
                sh "terrafile -f env-${ENV}/Terrafile"
                sh "terraform init -backend-config=env-${ENV}/${ENV}-backend.tfvars"            
            }

            stage('terraform plan') {
                sh "cd ${TFDIR}"
                sh "terraform plan -var-file=env-${ENV}/${ENV}.tfvars"
            }

            stage('Terraform Action') {
                sh "cd ${TFDIR}"
                sh "terraform ${ACTION} -auto-approve -var-file=env-${ENV}/${ENV}.tfvars"
                }
            }
        }
    }