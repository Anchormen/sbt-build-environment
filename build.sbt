name := "sbt-build-environments"
organization := "nl.anchormen.sbt"
version := "0.1.2-SNAPSHOT"
scalaVersion := "2.10.6"
scalacOptions += "-feature"
scalacOptions += "-deprecation"
sbtPlugin := true
resolvers += Resolver.jcenterRepo

val credentialsPath: File = Path.userHome / "nexus.cred"

lazy val publicationSettings = if (credentialsPath.exists()) Seq(
	publishTo := {
		val nexus = "http://callisto.anchormen.local:8081/nexus"

		if (version.value.endsWith("SNAPSHOT"))
			Some("snapshots" at nexus + "/content/repositories/snapshots")
		else
			Some("releases"  at nexus + "/content/repositories/releases")
	},
	publishMavenStyle := true,
	publishArtifact in Test := false,
	credentials += Credentials(credentialsPath)
) else Nil

lazy val app = (project in file("."))
	.settings(publicationSettings)