lazy val scalaSettings = Seq(
	scalacOptions	+= "-feature",
	scalacOptions	+= "-deprecation",
	scalaVersion	:= "2.10.6"
)

lazy val pluginSettings = Seq(
	fork in Test						:= false,
	ivyScala							:= ivyScala.value map { _.copy(overrideScalaVersion = true) },
	logBuffered in Test					:= false,
	mappings in (Compile, packageBin)	~= {
		_.filterNot {
			case (file, filename) => file.isDirectory && filename.equalsIgnoreCase("example")
		}
	},
	name								:= "sbt-build-environments",
	organization						:= "nl.anchormen.sbt",
	parallelExecution in Test			:= false,
	sbtPlugin							:= true,
	version								:= "0.1.3"
)

lazy val publicationSettings = Seq(
	publishMavenStyle		:= true,
	publishArtifact in Test	:= false
)

lazy val plugin = project
    .in(file("."))
	.settings(pluginSettings)
	.settings(scalaSettings)
	.settings(publicationSettings)
