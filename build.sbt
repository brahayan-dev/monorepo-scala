ThisBuild / scalaVersion := "3.3.6"
ThisBuild / organization := "akeptous"

lazy val apiRisktechMain = (project in file("api_risktech_main")).dependsOn(libSession)

lazy val libDatabase = (project in file("lib_database"))
lazy val libServer = (project in file("lib_server"))
lazy val libSession = (project in file("lib_session"))

lazy val root = (project in file("."))
  .aggregate(apiRisktechMain, libDatabase, libServer, libSession)
