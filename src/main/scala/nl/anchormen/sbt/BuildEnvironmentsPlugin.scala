package nl.anchormen.sbt

import sbt.Keys._
import sbt._

/**
  * Created by lawrence on 11-10-16.
  */
object BuildEnvironmentsPlugin extends AutoPlugin {
	override def requires = plugins.JvmPlugin
	override def trigger = allRequirements

	object autoImport {
		lazy val Dev = config("dev") extend Runtime
		lazy val Prod = config("prod") extend Runtime
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

	lazy val baseDevSettings: Seq[Def.Setting[_]] = baseSettings ++
		Seq(unmanagedResourceDirectories += baseDirectory.value / "src" / "dev" / "resources")

	lazy val baseProdSettings: Seq[Def.Setting[_]] = baseSettings ++
		Seq(unmanagedResourceDirectories += baseDirectory.value / "src" / "prod" / "resources")

	override lazy val projectSettings = inConfig(Dev)(baseDevSettings) ++ inConfig(Prod)(baseProdSettings)
}

//object EnvironmentSettings {
//	def apply(resources: Seq[File]): Seq[File] = {
//		for (resource <- resources) {
//			println(s"${resource.name}")
//		}
//
//		resources
//	}
//}
