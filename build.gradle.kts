plugins {
    kotlin("jvm") version "1.8.20"
    id("application")
    id("org.graalvm.buildtools.native") version "0.9.21"
}

application.mainClassName = "functional.ApplicationKt"

graalvmNative {
    // Download graal jdk `bash <(curl -sL https://get.graalvm.org/jdk)`
    // Set environment variable GRAALVM_HOME
    binaries {
        named("main") {
            verbose.set(true)
        }
    }
}

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