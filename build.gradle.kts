import com.diffplug.gradle.spotless.SpotlessExtension
import fr.brouillard.oss.gradle.plugins.JGitverPluginExtension
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import name.remal.gradle_plugins.sonarlint.SonarLintExtension
import org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES
import org.gradle.plugins.ide.idea.model.IdeaLanguageLevel

plugins {
    idea
    id("io.freefair.lombok")
    id("fr.brouillard.oss.gradle.jgitver")
    id("io.spring.dependency-management")
    id("org.springframework.boot") apply false
    id("name.remal.sonarlint") apply false
    id("com.diffplug.spotless") apply false
}

idea {
    project {
        languageLevel = IdeaLanguageLevel(21)
    }
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}


allprojects {
    group = "ru.otus"

    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
    }

    val testcontainersBom: String by project
    val protobufBom: String by project
    val guava: String by project
    val jmh: String by project
    val asm: String by project
    val glassfishJson: String by project
    val gson: String by project
    val junit: String by project
    val assertj: String by project
    val mockito: String by project
    val beanutils: String by project
    val flyway: String by project

    apply(plugin = "io.spring.dependency-management")
    dependencyManagement {
        dependencies {
            imports {
                mavenBom(BOM_COORDINATES)
                mavenBom("org.testcontainers:testcontainers-bom:$testcontainersBom")
                mavenBom("com.google.protobuf:protobuf-bom:$protobufBom")
            }
            dependency("com.google.guava:guava:$guava")
            dependency("commons-beanutils:commons-beanutils:$beanutils")
            dependency("org.openjdk.jmh:jmh-core:$jmh")
            dependency("org.openjdk.jmh:jmh-generator-annprocess:$jmh")
            dependency("org.glassfish:jakarta.json:$glassfishJson")
            dependency("org.ow2.asm:asm-commons:$asm")
            dependency("com.google.code.gson:gson:$gson")
        }
    }

    configurations.all {
        resolutionStrategy {
            failOnVersionConflict()

            force("javax.servlet:servlet-api:2.5")
            force("commons-logging:commons-logging:1.1.1")
            force("commons-lang:commons-lang:2.5")
            force("org.codehaus.jackson:jackson-core-asl:1.8.8")
            force("org.codehaus.jackson:jackson-mapper-asl:1.8.8")
            force("commons-io:commons-io:2.16.1")
            force("org.eclipse.jgit:org.eclipse.jgit:6.9.0.202403050737-r")
            force("org.apache.commons:commons-compress:1.26.1")
            force("com.google.errorprone:error_prone_annotations:2.36.0")
            force("org.jetbrains:annotations:19.0.0")
            force("com.google.j2objc:j2objc-annotations:3.0.0")
        }
    }
}

subprojects {
    plugins.apply(JavaPlugin::class.java)
    extensions.configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.addAll(listOf("-Xlint:all,-serial,-processing"))

        dependsOn("spotlessJavaApply")
    }

    plugins.apply("name.remal.sonarlint")
    extensions.configure<SonarLintExtension> {
        nodeJs {
            detectNodeJs = false
            logNodeJsNotFound = false
        }
    }
    plugins.apply("com.diffplug.spotless")
    extensions.configure<SpotlessExtension> {
        java {
            palantirJavaFormat("2.58.0")
        }
    }

    plugins.apply("fr.brouillard.oss.gradle.jgitver")
    extensions.configure<JGitverPluginExtension> {
        strategy("PATTERN")
        nonQualifierBranches("main,master")
        tagVersionPattern("\${v}\${<meta.DIRTY_TEXT}")
        versionPattern(
            "\${v}\${<meta.COMMIT_DISTANCE}\${<meta.GIT_SHA1_8}" +
                    "\${<meta.QUALIFIED_BRANCH_NAME}\${<meta.DIRTY_TEXT}-SNAPSHOT"
        )
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging.showExceptions = true
        reports {
            junitXml.required.set(true)
            html.required.set(true)
        }
    }
    plugins.apply("io.freefair.lombok")

}

tasks {
    val managedVersions by registering {
        doLast {
            project.extensions.getByType<DependencyManagementExtension>()
                .managedVersions
                .toSortedMap()
                .map { "${it.key}:${it.value}" }
                .forEach(::println)
        }
    }
}