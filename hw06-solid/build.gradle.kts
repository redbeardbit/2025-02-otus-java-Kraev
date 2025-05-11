plugins {
    id ("java")
}

dependencies {
    implementation ("com.google.guava:guava")
    implementation ("ch.qos.logback:logback-classic")

    testImplementation ("org.junit.jupiter:junit-jupiter-api")
    testImplementation ("org.junit.jupiter:junit-jupiter-engine")
    testImplementation ("org.assertj:assertj-core")
    testImplementation ("org.mockito:mockito-junit-jupiter")
}