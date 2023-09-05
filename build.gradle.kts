allprojects {
    group = "io.vieira"
    version = "1.0-SNAPSHOT"
}

plugins {
    kotlin("jvm") version "1.9.10" apply false
}

subprojects {
    repositories {
        mavenCentral()
    }
}