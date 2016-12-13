name := "sbt-build-environments"
organization := "nl.anchormen.sbt"
version := "0.1.3-SNAPSHOT"
scalaVersion := "2.10.6"
scalacOptions += "-feature"
scalacOptions += "-deprecation"
sbtPlugin := true
resolvers += Resolver.jcenterRepo

lazy val publicationSettings = Seq(
	publishMavenStyle := true,
	publishArtifact in Test := false
)

lazy val app = (project in file("."))
	.settings(publicationSettings)