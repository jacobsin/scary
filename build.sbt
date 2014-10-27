name := "scary"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "org.freemarker" % "freemarker" % "2.3.21"
)     

play.Project.playJavaSettings
