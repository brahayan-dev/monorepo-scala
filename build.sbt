ThisBuild / scalaVersion := "3.3.6"
ThisBuild / organization := "akeptous"

val commonDependencies = Seq(
  "org.scalameta" %% "munit" % "1.1.1" % Test
)

lazy val apiRisktechBase = project
  .in(file("backend/api-risktech-base"))
  .dependsOn(libServer, libSession)
  .settings(
    name := "apiRisktechBase",
    libraryDependencies ++= commonDependencies
  )

lazy val bffRisktechBase = project
  .in(file("frontend/bff-risktech-base"))
  .dependsOn(libServer, libUi)
  .settings(
    name := "bffRisktechBase",
    libraryDependencies ++= commonDependencies
  )

lazy val libServer = project
  .in(file("library/lib-server"))
  .settings(
    name := "libServer",
    libraryDependencies ++= commonDependencies ++ Seq(
      "dev.zio" %% "zio-http" % "3.3.3"
    )
  )
lazy val libSession = project
  .in(file("library/lib-session"))
  .settings(
    name := "libSession",
    libraryDependencies ++= commonDependencies
  )
lazy val libUi = project
  .in(file("library/lib-ui"))
  .settings(
    name := "libUi",
    libraryDependencies ++= commonDependencies ++ Seq(
      "com.lihaoyi" %% "scalatags" % "0.13.1"
    )
  )
