lazy val bintraySettings = Seq(
	bintrayOrganization in ThisBuild		:= Some("anchormen"),
	bintrayReleaseOnPublish in ThisBuild	:= false
)

lazy val scalaSettings = Seq(
	scalacOptions	+= "-feature",
	scalacOptions	+= "-deprecation",
	scalaVersion	:= "2.10.6"
)

lazy val pluginSettings = Seq(
	ivyScala							:= ivyScala.value map { _.copy(overrideScalaVersion = true) },
	licenses							+= ("MIT", url("https://opensource.org/licenses/MIT")),
	mappings in (Compile, packageBin)	~= {
		_.filterNot {
			case (file, filename) => file.isDirectory && filename.equalsIgnoreCase("example")
		}
	},
	name								:= "sbt-build-environments",
	organization						:= "nl.anchormen.sbt",
	sbtPlugin							:= true,
	version								:= "0.1.5"
)

lazy val publicationSettings = Seq(
	publishMavenStyle := false
)

lazy val testSettings = Seq(
	fork in Test				:= false,
	logBuffered in Test			:= false,
	parallelExecution in Test	:= false,
	publishArtifact in Test		:= false
)

lazy val plugin = project
    .in(file("."))
	.settings(bintraySettings)
	.settings(pluginSettings)
	.settings(publicationSettings)
	.settings(scalaSettings)
	.settings(testSettings)
