import sbtrelease.ReleasePlugin.autoImport.ReleaseTransformations._

ThisBuild / organization := "net.codedone"
ThisBuild / description := "cinema"

ThisBuild / resolvers += "cakesolution" at "https://dl.bintray.com/cakesolutions/maven/"
ThisBuild /resolvers += "Bintray API Realm" at "https://dl.bintray.com/codedonenet/cinema/"
ThisBuild / resolvers += Resolver.mavenLocal
ThisBuild / resolvers += Resolver.jcenterRepo
ThisBuild / credentials += Credentials(Path.userHome / ".sbt" / ".credentials")

ThisBuild / licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))

scalaVersion := "2.12.7"

lazy val scalaTest = "3.0.8"
lazy val cinemaFramework = "3.9.2"
lazy val scala = "2.12.7"


lazy val root = (project in file("."))
  .settings(
    name := "cinema-framework-test",
    scalaVersion := scala,
    publishMavenStyle := true,
    bintrayRepository := "cinema",
    bintrayOrganization in bintray := None,
    releaseProcess := Seq[ReleaseStep](
      checkSnapshotDependencies,
      inquireVersions,
      runClean,
      runTest,
      setReleaseVersion,
      commitReleaseVersion,
      tagRelease,
      publishArtifacts,
      setNextVersion,
      commitNextVersion,
      pushChanges
    ),
    assemblyMergeStrategy in assembly := {
      case PathList("reference.conf") => MergeStrategy.concat
      case PathList("application.conf") => MergeStrategy.concat
      case PathList("META-INF", xs @ _*) => MergeStrategy.discard
      case x => MergeStrategy.first
    },
    libraryDependencies ++= Seq(
      "net.codedone" % "cinema-framework_2.12" % cinemaFramework,
      "org.scalatest" %% "scalatest" % scalaTest)
  )