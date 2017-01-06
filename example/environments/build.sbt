import nl.anchormen.sbt.EnvironmentSettings

name := "environments"
organization := "nl.anchormen"
version := "1.0"

lazy val commonDependencies = Seq(
	libraryDependencies += "com.typesafe" % "config" % "1.3.1"
)

lazy val commonSettings = Seq(
	fork in Test				:= false,
	ivyScala					:= ivyScala.value map { _.copy(overrideScalaVersion = true) },
	logBuffered in Test			:= false,
	parallelExecution in Test	:= false,
	scalacOptions				+= "-feature",
	scalacOptions				+= "-deprecation",
	scalaVersion				:= "2.11.8",
	test in assembly			:= {}	// Discard if you are not using the assembly plugin
)

// ## Staging configuration
// This is how you create a new environment.
lazy val Staging = config("staging") extend Compile
lazy val stagingSettings = EnvironmentSettings(Staging.name)

// ## Assembly Plugin Settings
// Discard if you are not using the assembly plugin
lazy val devJarName = Seq(assemblyJarName in assembly := s"${name.value}-assembly-${Dev.name}-${version.value}.jar")
lazy val prodJarName = Seq(assemblyJarName in assembly := s"${name.value}-assembly-${Prod.name}-${version.value}.jar")
lazy val stagingJarName = Seq(assemblyJarName in assembly := s"${name.value}-assembly-${Staging.name}-${version.value}.jar")

// ## Aggregated settings.
// Discard assemblySettings if you are not using the assembly plugin
lazy val assemblySettings = sbtassembly.AssemblyPlugin.assemblySettings
lazy val aggregatedCommons = commonDependencies ++ commonSettings ++ assemblySettings

lazy val app = project
	.in(file("."))
	.settings(aggregatedCommons)
	.settings(inConfig(Staging)(stagingSettings ++ aggregatedCommons ++ stagingJarName))
	.settings(inConfig(Dev)(aggregatedCommons ++ devJarName))
	.settings(inConfig(Prod)(aggregatedCommons ++ prodJarName))
