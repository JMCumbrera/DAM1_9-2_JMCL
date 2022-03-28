import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    application
}

group = "me.3kxhodark"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlin:kotlin-test:1.6.0")
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("me.mattak:moment:0.0.4")
    implementation("io.github.microutils:kotlin-logging-jvm:2.1.20")
    implementation("org.postgresql:postgresql:42.3.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}