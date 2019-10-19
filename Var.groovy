freeStyleJob('Artifactory-Deployment'){

		def server
		def buildInfo
		def rtMaven
		
		git url: 'https://github.com/JFrog/project-examples.git'
		
		steps {
			artifactoryConfiguration()
			buildDeployPublish()
		}	
}	

def artifactoryConfiguration(){
		server = Artifactory.server 'jenkins-artifactory-server'

		rtMaven = Artifactory.newMavenBuild()
		rtMaven.tool = 'MAVEN'// Tool name from Jenkins configuration
		rtMaven.deployer releaseRepo: 'sample-repo', snapshotRepo: 'sample-repo-snapshot', server: server
		rtMaven.resolver releaseRepo: 'sample-repo', snapshotRepo: 'sample-repo-snapshot', server: server
		rtMaven.deployer.deployArtifacts = false // Disable artifacts deployment during Maven run

		buildInfo = Artifactory.newBuildInfo()

}
 def buildDeployPublish(){
	
		rtMaven.run pom: 'pom.xml', goals: 'install', buildInfo: 'buildInfo'
		rtMaven.deployer.deployArtifacts 'buildInfo'
		server.publishBuildInfo 'buildInfo'
		
 }
