node('master') {
  stage('trigger Terraform) {
        build wait: false, job: 'terraform-run', parameters: [credentials(name: 'aws-creds', value: 'aws')]
        }
    stage('checkout code') {
        cleanWs()
        checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/lidorg-dev/nodejs.org.git']]])
    }
    stage('build') {
        sh 'npm install' 
        sleep 15s
    }
    stage('test') {
      //  sh "node server.js &"
    }
     stage('Terraform Destroy ') {
      build wait: false, job: 'terraform-destroy', parameters: [credentials(name: 'aws-creds', value: 'aws')]
    }
    stage('Package') {
        sh "tar -czvf package.tar.gz *"
        archiveArtifacts artifacts: 'package.tar.gz', followSymlinks: false
    }
}
