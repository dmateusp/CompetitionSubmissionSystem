resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
scalaVersion := "2.11.8"
libraryDependencies += "com.typesafe.akka" % "akka-actor_2.11" % "2.4.16"

lazy val commonSettings =  Seq(
  name := "File comparer",
  version := "1.0",
  test in assembly := {}
)



lazy val app = (project in file("project")).
  settings(commonSettings: _*).
  settings(
    mainClass in assembly := Some("Main")
  )