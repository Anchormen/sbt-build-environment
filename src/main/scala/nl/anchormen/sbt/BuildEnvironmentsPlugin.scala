package nl.anchormen.sbt

import sbt.Keys._
import sbt._
import sbt.plugins.JvmPlugin

/**
  * Created by lawrence on 11-10-16.
  */
object BuildEnvironmentsPlugin extends AutoPlugin {
	override def requires: JvmPlugin.type = plugins.JvmPlugin
	override def trigger: PluginTrigger = allRequirements

	object autoImport {
		lazy val Dev: Configuration = config("dev") extend Compile
		lazy val Prod: Configuration = config("prod") extend Compile
	}

	import autoImport._

	lazy val baseSettings: Seq[Def.Setting[_]] = Classpaths.configSettings ++
		Defaults.baseClasspaths ++
		Defaults.configSettings ++
		Seq(
			managedResourceDirectories := (managedResourceDirectories in Compile).value,
			managedSourceDirectories := (managedSourceDirectories in Compile).value,
			unmanagedSourceDirectories := (unmanagedSourceDirectories in Compile).value,
			unmanagedResourceDirectories := (unmanagedResourceDirectories in Compile).value
		)

	lazy val baseDevSettings: Seq[Def.Setting[_]] = EnvironmentSettings("dev")
	lazy val baseProdSettings: Seq[Def.Setting[_]] = EnvironmentSettings("prod")
	override lazy val projectSettings: Seq[Def.Setting[_]] = inConfig(Dev)(baseDevSettings) ++ inConfig(Prod)(baseProdSettings)
}

object EnvironmentSettings {
	def apply(name: String): Seq[Def.Setting[_]] = {
		BuildEnvironmentsPlugin.baseSettings ++
			Seq(unmanagedResourceDirectories += baseDirectory.value / "src" / name / "resources")
	}
}
