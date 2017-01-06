logLevel := Level.Warn
resolvers += Resolver.url(
	"bintray-anchormen-sbt-plugins",
	url("http://dl.bintray.com/anchormen/sbt-plugins"))(
	Resolver.ivyStylePatterns)
addSbtPlugin("nl.anchormen.sbt" %% "sbt-build-environments" % "0.1.6")
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.3")