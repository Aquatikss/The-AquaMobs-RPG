plugins {
    id("java")
    id("com.gradleup.shadow") version "8.3.0"
}

group = "com.aquamobs"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.minestom:minestom-snapshots:ebaa2bbf64")
    implementation("com.googlecode.json-simple:json-simple:1.1.1")
    implementation("dev.hollowcube:schem:1.3.1")
    implementation("org.slf4j:slf4j-nop:2.0.17")
    implementation("org.fusesource.jansi:jansi:2.4.1")
    implementation("net.kyori:adventure-text-minimessage:4.14.0")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks {
    jar {
        manifest {
            attributes["Main-Class"] = "com.aquamobs.Main"
        }
    }

    build {
        dependsOn(shadowJar)
    }
    shadowJar {
        mergeServiceFiles()
        archiveClassifier.set("")
        destinationDirectory.set(file("R:/"))
        archiveFileName.set("server.jar")
    }
}