import org.jreleaser.model.Active

plugins {
    id("java")
    id("java-library")
    id("maven-publish")
    id("org.jreleaser") version "1.17.0"
    id("io.freefair.lombok") version "8.4"
}

group = "org.smoodi.physalus"
version = "0.1.0"

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    withJavadocJar()
    withSourcesJar()
}

dependencies {
    api("org.smoodi.annotation:docs-annotations:1.3.0")

    // Jackson
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.0")

    // Logger
    api("org.slf4j:slf4j-api:2.0.9")
    implementation("ch.qos.logback:logback-core:1.5.13")
    implementation("ch.qos.logback:logback-classic:1.5.13")

    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    // Test
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.javadoc {
    options {
        (this as StandardJavadocDocletOptions).apply {
            // Unable warnings of missing documentation
            addStringOption("Xdoclint:all,-missing", "-quiet")
        }
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.addAll(
        listOf(
            "--add-exports", "jdk.compiler/com.sun.tools.javac.processing=ALL-UNNAMED",
            "--add-exports", "jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED",
            "--add-exports", "jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED"
        )
    )
}

publishing {

    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            pom {
                name.set("Physalus The Engine")
                description.set("The server engine for Smoodi Framework.")
                url.set("https://github.com/Project-Smoodi/Physalus")

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                developers {
                    developer {
                        id.set("Daybreak312")
                        name.set("Daybreak312")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/Project-Smoodi/Physalus.git")
                    developerConnection.set("scm:git:ssh://git@github.com:Project-Smoodi/Physalus.git")
                    url.set("https://github.com/Project-Smoodi/Physalus")
                }
            }
        }
    }

    repositories {
        maven {
            name = "staging"
            url = uri(layout.buildDirectory.dir("staging-deploy").get().asFile.absolutePath)
        }
    }
}

jreleaser {
    signing {
        active.set(Active.RELEASE)
        armored = true
    }
    deploy {
        maven {
            mavenCentral {
                create("sonatype") {
                    active.set(Active.RELEASE)
                    url.set("https://central.sonatype.com/api/v1/publisher")
                    stagingRepository(layout.buildDirectory.dir("staging-deploy").get().asFile.absolutePath)
                }
            }
            nexus2 {
                create("sonatype-snapshots") {
                    active.set(Active.SNAPSHOT)
                    url.set("https://s01.oss.sonatype.org/content/repositories/snapshots/")
                    snapshotUrl.set("https://s01.oss.sonatype.org/content/repositories/snapshots/")
                    applyMavenCentralRules.set(true)
                    stagingRepository(layout.buildDirectory.dir("staging-deploy").get().asFile.absolutePath)
                }
            }
        }
    }
    release {
        github {
            tagName.set("v{{projectVersion}}")
            releaseName.set("Release v{{projectVersion}}")
            changelog {
                formatted.set(Active.ALWAYS)
                preset.set("conventional-commits")
            }
        }
    }
}
