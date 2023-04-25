plugins {
    kotlin("jvm") version "1.8.0"
}

group = "net.time4tea"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation (platform("org.http4k:http4k-bom:4.41.0.0"))
    implementation ("org.http4k:http4k-core")
    implementation ("org.http4k:http4k-server-undertow")
    implementation ("org.http4k:http4k-template-handlebars")
    implementation ("org.http4k:http4k-format-jackson")
    implementation ("org.http4k:http4k-client-okhttp")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}