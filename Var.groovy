def call(String gitRepo, String gitProj) {
  def pomData =  readFileFromWorkspace('pom.xml')
  def buildStatus 
  pipelineJob('Your App Pipeline') { 
    agent any

    parameters {
		choiceParam('SOURCE_BRANCH', ['Develop', 'Master'], 'Source branch from code is merged to Destination')
		choiceParam('DESTINATION_BRANCH', ['Master', 'Release'], 'Destination branch where the code should be merged')
		choiceParam('TAG_REQUIRED', ['Yes', 'No'], 'Do you require a tag creation')
	}

    stages {
      stage('git-checkout') {
        steps {
			definition { 
				cpsScm { 
					scm {
						git {
							remote {	
							url('https://github.com/msharathraj/Maven-Test.git')
							branch('develop')
							extensions {
								localBranch('develop')
							}
						}
				}
			}
						
    } 
 		
		
          
        }
      }
      stage('git-merge') {
		release =  getReleasedVersion()
	     batchFile("echo Hello World!  ${release} ")
	     batchFile('echo DESTINATION_BRANCH ${DESTINATION_BRANCH}! ')
		 batchFile('git branch')
		 batchFile('git checkout master')
		 batchFile('git merge origin/test')
		batchFile('echo Hello Merge! ' )
		
		batchFile('echo {currentBuild.result}')
		
		
		script {
                def status = currentBuild.result

                print 'status is ' + status

                if (status == "SUCCESS" ) {
					batchFile('echo Hello Success! ' )
					buildStatus = true
                }    
        
             }
		}
		stage ('git-release'){
			batchFile('echo test release')
		}
    }
  }
}
def getReleasedVersion() {
	return (readFileFromWorkspace('pom.xml') =~ '<version>(.+)-SNAPSHOT</version>')[0][1]
}