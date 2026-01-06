import gg.jte.ContentType

plugins {
    kotlin("jvm") version "1.9.20"
    application
    id("com.gradleup.shadow") version "9.0.0"
    id("gg.jte.gradle") version "3.2.1"
}


group = "hexlet.code"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("hexlet.code.App");
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("gg.jte:jte:3.2.1")
    implementation("io.javalin:javalin:6.7.0")
    implementation("io.javalin:javalin-rendering:6.7.0")

    implementation("ch.qos.logback:logback-classic:1.5.6")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}


tasks.test {
    useJUnitPlatform()
}

jte {
    sourceDirectory.set(project.file("src/main/resources/templates").toPath())
    contentType.set(ContentType.Html)
}