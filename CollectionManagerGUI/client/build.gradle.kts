import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
//    `java-library`
    application
    id("com.github.seanrl.jaxb") version "2.5.1"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.openjfx.javafxplugin") version "0.0.14"
    idea
}

group = "org.example"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":core"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

application {
    mainClass.set("Main")
}

javafx {
    modules("javafx.controls", "javafx.fxml")
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<ShadowJar> {
    archiveVersion.set("1.0")
    archiveClassifier.set("final")
}