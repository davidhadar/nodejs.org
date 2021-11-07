node('master') {
    stage('checkout code') {
        cleanWs()
        checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/lidorg-dev/nodejs.org.git']]])
    }
    stage('build') {
        sh 'npm install' 
    }
    stage('test') {
      //  sh "node server.js &"
    }
    stage('Package') {
        sh "tar -czvf package.tar.gz *"
        archiveArtifacts artifacts: 'package.tar.gz', followSymlinks: false
    }
}
