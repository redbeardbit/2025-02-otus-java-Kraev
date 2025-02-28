plugins {
    id ("java")
}

dependencies {
    implementation ("com.google.guava:guava")
}

tasks.register<Jar>("uberJar") {
    manifest {
        attributes("Main-Class" to "ru.otus.HelloOtus")
    }
    archiveBaseName = "hello-otus"
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    archiveClassifier = "uber"

    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}