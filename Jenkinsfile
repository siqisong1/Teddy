pipeline {
    agent any

    options {
        timestamps()
    }

    parameters {
        string(name: 'DOCKER_IMAGE',
                defaultValue: 'wetuqq/teedy',
                description: 'Docker Hub image name, for example username/teedy')
        string(name: 'DOCKER_CREDENTIALS_ID',
                defaultValue: 'dockerhub_credentials',
                description: 'Jenkins credentials ID for Docker Hub')
    }

    environment {
        DOCKER_IMAGE = "${params.DOCKER_IMAGE}"
        DOCKER_CREDENTIALS_ID = "${params.DOCKER_CREDENTIALS_ID}"
        DOCKER_TAG = "${env.BUILD_NUMBER}"
        PATH = "/Applications/Docker.app/Contents/Resources/bin:${env.PATH}"
    }

    stages {
        stage('Clean') {
            steps {
                sh 'mvn -B clean'
            }
        }

        stage('Compile') {
            steps {
                sh 'mvn -B -DskipTests compile'
            }
        }

        stage('Test') {
            steps {
                sh "mvn -B -pl docs-core -Dtest='TestMimeTypeUtil,TestResourceUtil,TestGoogleAuthenticator,TestCss,TestImageUtil' test"
            }
        }

        stage('PMD') {
            steps {
                sh 'mvn -B pmd:pmd'
            }
        }

        stage('Site') {
            steps {
                sh 'mvn -B -DskipTests -Dmaven.javadoc.skip=true site'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn -B -DskipTests package'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${env.DOCKER_IMAGE}:${env.DOCKER_TAG}")
                    sh "docker tag ${env.DOCKER_IMAGE}:${env.DOCKER_TAG} ${env.DOCKER_IMAGE}:latest"
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                withCredentials([usernamePassword(credentialsId: env.DOCKER_CREDENTIALS_ID,
                                                  usernameVariable: 'DOCKER_USERNAME',
                                                  passwordVariable: 'DOCKER_PASSWORD')]) {
                    sh '''
                        echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
                        docker push "$DOCKER_IMAGE:$DOCKER_TAG"
                        docker push "$DOCKER_IMAGE:latest"
                    '''
                }
            }
        }

        stage('Run Docker Containers') {
            steps {
                sh '''
                    for port in 8082 8083 8084; do
                        name="teedy-container-${port}"
                        docker stop "$name" || true
                        docker rm "$name" || true
                        docker run -d --name "$name" -p "${port}:8080" "${DOCKER_IMAGE}:${DOCKER_TAG}"
                    done

                    docker ps --filter "name=teedy-container"
                '''
            }
        }
    }

    post {
        always {
            junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'

            archiveArtifacts artifacts: '**/target/*.jar,**/target/*.war,**/target/site/**',
                    allowEmptyArchive: true,
                    fingerprint: true
        }
    }
}
