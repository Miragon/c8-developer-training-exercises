plugins {
    java
    alias(libs.plugins.spring.dependency)
    id("com.gradleup.shadow") version "9.0.0-beta12"
}

group = "io.miragon"
version = "0.1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = uri("https://artifacts.camunda.com/artifactory/connectors/")
    }
    maven {
        url = uri("https://artifacts.camunda.com/artifactory/connectors-snapshots/")
    }
}

dependencies {
    // Connector SDK (provided scope - available at runtime in connector runtime)
    compileOnly(libs.connectorCore)
    compileOnly(libs.connectorValidation)

    // Element template generator annotations (compile-time only)
    compileOnly(libs.elementTemplateGenerator)

    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter:3.5.7")

    // Logging
    implementation(libs.slf4jApi)

    // Lombok
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    testCompileOnly(libs.lombok)
    testAnnotationProcessor(libs.lombok)

    // Test dependencies
    testImplementation(libs.connectorTest)
    testImplementation(platform("org.junit:junit-bom:5.12.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.mockito:mockito-junit-jupiter:5.16.0")
    testImplementation(libs.assertj)

    // For testing connectors locally in bundle with runtime (Spring Boot app with Logback)
    testImplementation(libs.connectorRuntime)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.test {
    useJUnitPlatform()
}

// Shadow JAR configuration (equivalent to maven-shade-plugin)
tasks.shadowJar {
    archiveClassifier.set("with-dependencies")
    mergeServiceFiles()

    // Exclude signatures and module-info
    exclude("module-info.class")
    exclude("META-INF/*.SF")
    exclude("META-INF/*.DSA")
    exclude("META-INF/*.RSA")
}

// Build shadow jar when running 'build' task
tasks.build {
    dependsOn(tasks.shadowJar)
}
