lazy val root = (project in file("."))
  .enablePlugins(
    PlayScala,
    BuildInfoPlugin
  )
  .settings(
    name := "play-scala-4-devoxx",
    resolvers in ThisBuild ++= Seq(
      "Typesafe repository".at("https://repo.typesafe.com/typesafe/maven-releases/")
    ),
    scalaVersion in ThisBuild := "2.12.4",
    version in ThisBuild := "1.0-SNAPSHOT",
    organization in ThisBuild := "devoxx",
    crossScalaVersions in ThisBuild := Seq("2.11.12", "2.12.4"),
    scalafmtOnCompile in ThisBuild := true,
    scalafmtTestOnCompile in ThisBuild := true
  )
  .settings(
    fork in Test := true,
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    buildInfoOptions += BuildInfoOption.BuildTime,
    libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
  )
