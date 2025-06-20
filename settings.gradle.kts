rootProject.name = "2025-02-otus-java-Kraev"

include("hw01-gradle")
include("hw02-generics")
include("hw03-annotations")
include("hw04-gc")
include("hw05-bytecodes")
include("hw06-solid")
include("hw07-patterns")
include("hw08-serialization")

rootProject.children.forEach {
    project(it.path).projectDir.mkdirs()
}
pluginManagement {
    val jgitver: String by settings
    val dependencyManagement: String by settings
    val springframeworkBoot: String by settings
    val johnrengelmanShadow: String by settings
    val jib: String by settings
    val protobufVer: String by settings
    val sonarlint: String by settings
    val spotless: String by settings
    val lombok: String by settings

    plugins {
        id("fr.brouillard.oss.gradle.jgitver") version jgitver
        id("io.spring.dependency-management") version dependencyManagement
        id("org.springframework.boot") version springframeworkBoot
        id("com.github.johnrengelman.shadow") version johnrengelmanShadow
        id("com.google.cloud.tools.jib") version jib
        id("com.google.protobuf") version protobufVer
        id("name.remal.sonarlint") version sonarlint
        id("com.diffplug.spotless") version spotless
        id("io.freefair.lombok") version lombok
    }

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}