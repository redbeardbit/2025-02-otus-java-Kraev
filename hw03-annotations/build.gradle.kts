plugins {
    id ("java")
}

tasks.register<JavaExec>("runOtusTests") {
    description = "Runs this project as a JVM application"
    group = "Execution"
    classpath(tasks.existing, configurations.existing)
    mainClass.set("ru.otus.tests.examples.Main")
}