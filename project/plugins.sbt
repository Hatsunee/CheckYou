// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

// Use the Play sbt plugin for Play projects
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.2.6")

// 管理画面追加
addSbtPlugin("jp.greative" %% "kurad" % "0.2.0")

resolvers += "Greative Repository" at "https://github.com/karad/maven-repo/raw/master/release/"

libraryDependencies += "sampleapp" % "sampleapp_2.10" % "1.0-SNAPSHOT"

