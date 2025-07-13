ThisBuild / scalaVersion := "3.3.6"
ThisBuild / organization := "akeptous"

val commonDependencies = Seq(
  "org.typelevel" %% "cats-core" % "2.13.0",
  "org.scalameta" %% "munit" % "1.1.1" % Test
)

lazy val root = project
  .in(file("."))
  .aggregate(apiRisktechBase, libServer, libSession, libStore)

lazy val apiRisktechBase = project
  .in(file("api_risktech_base"))
  .dependsOn(libSession)
  .settings(
    name := "apiRisktechBase",
    libraryDependencies ++= commonDependencies
  )

lazy val libServer = project
  .in(file("lib_server"))
  .settings(
    name := "libServer",
    libraryDependencies ++= commonDependencies
  )
lazy val libSession = project
  .in(file("lib_session"))
  .settings(
    name := "libSession",
    libraryDependencies ++= commonDependencies
  )
lazy val libStore = project
  .in(file("lib_store"))
  .settings(
    name := "libStore",
    libraryDependencies ++= commonDependencies
  )
