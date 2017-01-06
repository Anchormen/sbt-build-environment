package nl.anchormen.environment

import com.typesafe.config.{Config, ConfigFactory}

import scala.annotation.tailrec

/**
  * Created by lawrence on 31-10-16.
  */
object Environment {
	protected implicit lazy val config : Config = {
		val defaultConfig = ConfigFactory.load
		val specificConf = findConfigInClassPath(defaultConfig)

		ConfigFactory.load(specificConf)
			.withFallback(defaultConfig)
	}

	def main(args: Array[String]): Unit = {
		val environment: Config = config.getConfig("environment")
		println(s"Environment: ${environment.getString("name")}")
	}

	def findConfigInClassPath(defaultConfig : Config) : Config = {
		val chunks = this.getClass.getName
			.replaceAll("\\$", "")
			.split("\\.")

		val configFile = s"${chunks.last}.conf"
		val dirChunks = chunks.slice(0, chunks.size - 1)

		@tailrec
		def loadConfig(path : String, dirChunks : Array[String]) : Config = {
			if (dirChunks.length == 0) {
				return ConfigFactory.empty()
			}

			val url = getClass.getResource(s"$path$configFile")

			if (url != null) {
				return ConfigFactory.parseURL(url).resolveWith(defaultConfig)
			}

			loadConfig(s"$path/${dirChunks.head}", dirChunks.tail)
		}

		loadConfig("/", dirChunks)
	}
}
