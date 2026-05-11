pipeline {
    agent any

    options {
        timestamps()
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
