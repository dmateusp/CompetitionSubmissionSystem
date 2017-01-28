resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
scalaVersion := "2.11.8"
libraryDependencies += "com.typesafe.akka" % "akka-actor_2.11" % "2.4.16"
libraryDependencies += "com.typesafe.slick" %% "slick" % "3.1.1"
libraryDependencies ++= Seq("org.slf4j" % "slf4j-api" % "1.7.5",
                            "org.slf4j" % "slf4j-simple" % "1.7.5")
libraryDependencies += "org.xerial" % "sqlite-jdbc" % "3.7.2"

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