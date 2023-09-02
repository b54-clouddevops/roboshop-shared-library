def call() {
    node('WS') {
        sh "rm -rf *"
        git branch: 'main', url: "https://github.com/b54-clouddevops/${COMPONENT}.git"
        env.APP_TYPE="nodejs"
        common.lintChecks()
        if(env.TAG_NAME != null) {
                stage('Prepare Artifacts') {
                        if(env.APP_TYPE == "nodejs"){
                                sh '''
                                        echo Preparing Artifacts for ${COMPONENT}
                                        npm install                     
                                '''
                        }
                        else if(env.APP_TYPE == "python"){
                                sh '''
                                        echo Preparing Artifacts for ${COMPONENT}
                                        zip -r ${COMPONENT}-${TAG_NAME}.zip *.py *.ini  requirements.txt                     
                                '''
                        }
                        else if(env.APP_TYPE == "java"){
                                sh '''
                                        echo Preparing Artifacts for ${COMPONENT}
                                        mvn clean package
                                        ls -ltr         
                                '''
                        }
                        else {
                                sh '''
                                        echo Preparing Artifacts for ${COMPONENT}
                                        cd static
                                        zip -r ../${COMPONENT}-${TAG_NAME}.zip *                  
                                '''
                        }

                        sh "env"
                        sh "ls -ltr"
                        sh "wget https://truststore.pki.rds.amazonaws.com/global/global-bundle.pem"
                        sh "docker build -t 355449129696.dkr.ecr.us-east-1.amazonaws.com/${COMPONENT}:latest ."
                        sh "docker tag 355449129696.dkr.ecr.us-east-1.amazonaws.com/${COMPONENT}:latest 355449129696.dkr.ecr.us-east-1.amazonaws.com/${COMPONENT}:${TAG_NAME}"
                        sh "aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 355449129696.dkr.ecr.us-east-1.amazonaws.com"
                        sh "docker push 355449129696.dkr.ecr.us-east-1.amazonaws.com/${COMPONENT}:${TAG_NAME}"
                    
                    }
                }
            }
        }
