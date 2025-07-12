ThisBuild / scalaVersion := "3.3.6"
ThisBuild / organization := "akeptous"

lazy val root = project
  .in(file("."))
  .aggregate(apiRisktechBase, libServer, libSession, libStore)

lazy val apiRisktechBase = project
  .in(file("api_risktech_base"))
  .dependsOn(libSession)
  .settings(
    name := "apiRisktechBase"
  )

lazy val libServer = project
  .in(file("lib_server"))
  .settings(
    name := "libServer"
  )
lazy val libSession = project
  .in(file("lib_session"))
  .settings(
    name := "libSession"
  )
lazy val libStore = project
  .in(file("lib_store"))
  .settings(
    name := "libStore"
  )
