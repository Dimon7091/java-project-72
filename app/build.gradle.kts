import gg.jte.ContentType

plugins {
    kotlin("jvm") version "1.9.20"
    application
    id("com.gradleup.shadow") version "9.0.0"
    id("gg.jte.gradle") version "3.2.1"
    kotlin("plugin.lombok") version "2.2.21"
    id("io.freefair.lombok") version "8.14.2"
    jacoco
}


group = "hexlet.code"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("hexlet.code.App")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("gg.jte:jte:3.2.1")
    implementation("io.javalin:javalin:6.7.0")
    implementation("io.javalin:javalin-rendering:6.7.0")
    testImplementation("io.javalin:javalin-testtools:6.7.0")

    implementation("org.slf4j:slf4j-api:2.0.13")
    implementation("ch.qos.logback:logback-classic:1.5.6")

    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("com.h2database:h2:2.2.220")
    implementation("org.postgresql:postgresql:42.7.8")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-junit-jupiter:5.21.0")
    testImplementation("org.mockito:mockito-core:5.21.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation(platform("org.jacoco:jacoco:0.8.14"))

    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")
    testImplementation("org.assertj:assertj-core:3.22.0")
}



tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run

}

jte {
    sourceDirectory.set(project.file("src/main/resources/templates").toPath())
    contentType.set(ContentType.Html)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)      // Для GitHub Actions
        html.required.set(true)     // Локальный HTML отчет
        csv.required.set(false)
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }}