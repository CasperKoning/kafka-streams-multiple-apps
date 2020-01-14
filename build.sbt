import Dependencies._

ThisBuild / scalaVersion     := "2.12.10"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "xyz.casperkoning"
ThisBuild / organizationName := "casperkoning"

resolvers += "Confluent" at "https://packages.confluent.io/maven/"

lazy val root = (project in file("."))
  .settings(
    name := "kafka-streams-multiple-apps",
    libraryDependencies ++= Seq(
      kafkaStreams,
      scalaLogging,
      scalaTest % Test,
      logback % Test,
      logback % Runtime
    )
  )
