plugins {
    id("java")
    id("java-library")
    id("maven-publish")
}

group = "org.smoodi.physalus"

repositories {
    maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/Project-Smoodi/Physalus")
        credentials {
            username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
            password = project.findProperty("gpr.token") as String? ?: System.getenv("TOKEN")
        }
    }
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

dependencies {
    api("org.smoodi.framework:docs-annotations:1.2.0")

    // Jackson
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.5")

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

tasks.register<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(sourceSets["main"].allSource)
}

publishing {

    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            groupId = "org.smoodi.framework"
            artifactId = "physalus"
            version = "0.0.1-SNAPSHOT"

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
                        email.set("leetyxodud312@gmail.com")
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
            name = "Smoodi-Physalus"
            url = uri("https://maven.pkg.github.com/Project-Smoodi/Physalus")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.token") as String? ?: System.getenv("TOKEN")
            }
        }
    }
}