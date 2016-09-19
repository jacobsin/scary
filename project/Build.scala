import play.ebean.sbt.PlayEbean
import play.routes.compiler.InjectedRoutesGenerator
import play.sbt.PlayImport._
import play.sbt.PlayJava
import play.sbt.routes.RoutesKeys._
import sbt.Keys._
import sbt._

object ApplicationBuild extends Build {

  val appName         = "scary"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    javaJdbc,
    cache,
    "org.freemarker" % "freemarker" % "2.3.23",
    "org.easytesting" % "fest-assert" % "1.4" % Test,
    specs2 % Test
  )

  val main = Project(appName, file("."))
    .enablePlugins(PlayJava, PlayEbean)
    .settings(
      version := appVersion,
      scalaVersion := "2.11.6",
      libraryDependencies ++= appDependencies,
      resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases",

      routesGenerator := InjectedRoutesGenerator,

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