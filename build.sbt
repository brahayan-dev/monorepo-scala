ThisBuild / scalaVersion := "3.3.6"
ThisBuild / organization := "akeptous"

val commonDependencies = Seq(
  "org.typelevel" %% "cats-core" % "2.13.0",
  "org.scalameta" %% "munit" % "1.1.1" % Test
)

val frontendDependencies = Seq(
  "com.lihaoyi" %% "scalatags" % "0.13.1"
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
  .dependsOn(libSession)
  .settings(
    name := "apiRisktechBase",
    libraryDependencies ++= commonDependencies
  )

lazy val bffRisktechBase = project
  .in(file("frontend/bff_risktech_base"))
  .dependsOn(libUi)
  .settings(
    name := "bffRisktechBase",
    libraryDependencies ++= commonDependencies ++ frontendDependencies
  )

lazy val libServer = project
  .in(file("library/lib_server"))
  .settings(
    name := "libServer",
    libraryDependencies ++= commonDependencies
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
    libraryDependencies ++= commonDependencies
  )
