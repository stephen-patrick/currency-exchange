name := "currency-exchange"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "com.rabbitmq" % "amqp-client" % "3.5.0"
)     

play.Project.playJavaSettings
