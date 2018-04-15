lazy val akkaHttpVersion = "10.1.1"

lazy val root = (project in file("."))
  .settings(
    inThisBuild(List(
      organization := "fr.devoxx",
      scalaVersion := "2.12.4"
    )),
    name := "akka-http-scala-4-devoxx",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http"           % akkaHttpVersion,
      "de.heikoseeberger" %% "akka-http-play-json" % "1.20.1",

      "com.typesafe.akka" %% "akka-http-testkit"   % akkaHttpVersion % Test,
      "org.scalatest"     %% "scalatest"           % "3.0.5" % Test
    )
  )
