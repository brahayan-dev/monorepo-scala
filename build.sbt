ThisBuild / scalaVersion := "3.3.6"
ThisBuild / organization := "akeptous"

lazy val root = project
  .in(file("."))
  .aggregate(apiRisktechBase, libDatabase, libServer, libSession)

lazy val apiRisktechBase = project
  .in(file("api_risktech_base"))
  .dependsOn(libSession)
  .settings(
    name := "apiRisktechBase"
  )

lazy val libDatabase = project
  .in(file("lib_database"))
  .settings(
    name := "libDatabase"
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
