plugins {
    java
    alias(libs.plugins.springframework)
    alias(libs.plugins.spring.dependency)
}

group = "de.emaarco.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("io.camunda:camunda-spring-boot-starter:8.8.2")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}
