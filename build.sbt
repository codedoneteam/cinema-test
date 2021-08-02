import sbtrelease.ReleasePlugin.autoImport.ReleaseTransformations._

lazy val scalaTest = "3.0.8"
lazy val cinemaFramework = "3.10.0"

lazy val root = (project in file("."))
  .settings(
    name := "cinema-framework-test",
    organization := "net.codedone",
    description := "cinema",
    scalaVersion := "2.13.3",
    publishMavenStyle := true,
    resolvers ++= Seq(Resolver.mavenLocal,
                      Resolver.jcenterRepo,
                      "cakesolution" at "https://dl.bintray.com/cakesolutions/maven/", 
                      "cakesolution" at "https://dl.bintray.com/cakesolutions/maven/", 
                      "Bintray API Realm" at "https://dl.bintray.com/codedonenet/cinema/"),
    credentials += Credentials(Path.userHome / ".sbt" / ".credentials"),
    bintrayRepository := "cinema",
    bintrayOrganization in bintray := None,
    licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html")),
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
      "net.codedone" % "cinema-framework_2.13" % cinemaFramework,
      "org.scalatest" %% "scalatest" % scalaTest)
  )
