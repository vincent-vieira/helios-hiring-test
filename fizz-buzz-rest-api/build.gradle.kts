plugins {
    java
    id("org.springframework.boot") version "3.1.3"
    kotlin("jvm") version "1.9.10"
}

apply(plugin = "io.spring.dependency-management")

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.ninja-squad:springmockk:4.0.2")
}

tasks.test {
    useJUnitPlatform()
}