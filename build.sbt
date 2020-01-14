import Dependencies._

ThisBuild / scalaVersion     := "2.13.1"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "xyz.casperkoning"
ThisBuild / organizationName := "casperkoning"

lazy val root = (project in file("."))
  .settings(
    name := "kafka-streams-multiple-apps",
    libraryDependencies += scalaTest % Test
  )
