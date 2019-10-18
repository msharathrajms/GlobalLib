pipeline {
    stage('checkout'){
	     checkout([$class: 'GitSCM', branches: [[name: '*/${SOURCE_BRANCH}']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[ url: 'https://github.com/msharathraj/SampleTest.git']]])
    }
    stage('build'){
		when{
				branch 'develop' 
			}
		steps{
			def mvnhome = tool name: 'MAVEN', type: 'maven'
			bat "${mvnhome}/bin/mvn install"
        }
    }
    stage('deploy'){
		when{
				branch 'master' 
			}
			steps {
				def mvnhome = tool name: 'MAVEN', type: 'maven'
				bat "${mvnhome}/bin/mvn deploy"
			}
		
    }
} 