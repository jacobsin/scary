import play.Play.autoImport._
import sbt.Keys._
import sbt._

object ApplicationBuild extends Build {

  val appName         = "scary"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    javaJdbc,
    javaEbean,
    cache,
    "org.freemarker" % "freemarker" % "2.3.23"
  )

  val main = Project(appName, file("."))
    .enablePlugins(play.PlayJava)
    .settings(
      version := appVersion,
      scalaVersion := "2.10.4",
      libraryDependencies ++= appDependencies,

      unmanagedResources in Compile <<= (
        javaSource in Compile,
        classDirectory in Compile,
        unmanagedResources in Compile
        ) map { (app, classes, resources) =>
        IO.copyDirectory(app / "views", classes / "views", overwrite = true)
        resources
      }
    )

}