plugins {
    kotlin("jvm") version "1.8.0"
    id("application")
    id("org.graalvm.buildtools.native") version "0.9.21"
    id("com.google.cloud.tools.jib") version "3.3.1"
}

application.mainClassName = "functional.ApplicationKt"

graalvmNative {
    // Download graal jdk `bash <(curl -sL https://get.graalvm.org/jdk)`
    // Set environment variable GRAALVM_HOME
    binaries {
        named("main") {
            verbose.set(true)
//            buildArgs.add("-H:+StaticExecutableWithDynamicLibC")
            buildArgs.add("--static")
        }
    }
}

jib.from.image="eclipse-temurin:17-jre-alpine@sha256:f59c1acc26975859545eabb2051f4b9a41d5ef278aad9dfe42bdb0aff5611613"
jib.to.image = "http4k-functional-jib"  // same as IMAGE in Makefile
jib.container.jvmFlags = listOf(
    "-XX:+AlwaysActAsServerClassMachine",
    "-XX:InitialRAMPercentage=70.0",
    "-XX:MinRAMPercentage=70.0",
    "-XX:MaxRAMPercentage=70.0",
    "-XX:+ExitOnOutOfMemoryError",
    "-XshowSettings:vm",
)

group = "net.time4tea"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation (platform("org.http4k:http4k-bom:4.42.1.0"))
    implementation ("org.http4k:http4k-core")
    implementation ("org.http4k:http4k-server-undertow")
    implementation ("org.http4k:http4k-template-handlebars")
    implementation ("org.http4k:http4k-format-moshi")
    implementation ("org.http4k:http4k-client-okhttp")

    implementation("org.slf4j:slf4j-nop:2.0.6")
    testImplementation ("org.http4k:http4k-testing-strikt")

    testImplementation(kotlin("test"))
    testImplementation ("org.jsoup:jsoup:1.15.4")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}