ThisBuild / scalaVersion := "3.3.6"
ThisBuild / organization := "akeptous"

val http4sVersion = "0.23.30"

val commonDependencies = Seq(
  "org.typelevel" %% "cats-core" % "2.13.0",
  "org.scalameta" %% "munit" % "1.1.1" % Test
)

lazy val root = project
  .in(file("."))
  .aggregate(
    apiRisktechBase,
    bffRisktechBase,
    libServer,
    libSession,
    libStore,
    libUi
  )

lazy val apiRisktechBase = project
  .in(file("backend/api_risktech_base"))
  .dependsOn(libServer, libSession)
  .settings(
    name := "apiRisktechBase",
    libraryDependencies ++= commonDependencies
  )

lazy val bffRisktechBase = project
  .in(file("frontend/bff_risktech_base"))
  .dependsOn(libServer, libUi)
  .settings(
    name := "bffRisktechBase",
    libraryDependencies ++= commonDependencies
  )

lazy val libServer = project
  .in(file("library/lib_server"))
  .settings(
    name := "libServer",
    libraryDependencies ++= commonDependencies ++ Seq(
      "org.http4s" %% "http4s-dsl" % http4sVersion,
      "org.http4s" %% "http4s-ember-server" % http4sVersion,
      "org.http4s" %% "http4s-circe" % http4sVersion,
      "org.slf4j" % "slf4j-simple" % "2.0.17",
      "io.circe" %% "circe-generic" % "0.14.14"
    )
  )
lazy val libSession = project
  .in(file("library/lib_session"))
  .settings(
    name := "libSession",
    libraryDependencies ++= commonDependencies
  )
lazy val libStore = project
  .in(file("library/lib_store"))
  .settings(
    name := "libStore",
    libraryDependencies ++= commonDependencies
  )
lazy val libUi = project
  .in(file("library/lib_ui"))
  .settings(
    name := "libUi",
    libraryDependencies ++= commonDependencies ++ Seq(
      "com.lihaoyi" %% "scalatags" % "0.13.1"
    )
  )
