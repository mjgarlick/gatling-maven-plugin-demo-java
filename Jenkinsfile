pipeline {
  agent any
  stages {
    stage("build") {
      steps {
        echo 'Build stage' 
      }
    }
    
    stage("test") {
      steps {
        step {
          echo 'Testing stage' 
        }
      }
    }
    
    stage("deploy"){
      steps {
        step {
          echo 'Deploy stage' 
        }
      }
    }
  }
}
