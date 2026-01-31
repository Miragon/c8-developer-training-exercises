import io.github.emaarco.bpmn.adapter.GenerateBpmnModelsTask

plugins {
    java
    alias(libs.plugins.springframework)
    alias(libs.plugins.spring.dependency)
    alias(libs.plugins.bpmnToCode)
    alias(libs.plugins.gradleRetryTesting)
}

group = "de.emaarco.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.bundles.defaultService)
    implementation(libs.bundles.database)
    implementation(libs.kotlin.jackson)

    testImplementation(libs.bundles.test)
    testImplementation(libs.zeebeProcessTest)
    testImplementation("com.h2database:h2")
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.mockito:mockito-junit-jupiter")
}

tasks.named<GenerateBpmnModelsTask>("generateBpmnModelApi") {
    baseDir = projectDir.toString()
    filePattern = "src/main/resources/bpmn/*.bpmn"
    outputFolderPath = "$projectDir/src/main/java"
    packagePath = "de.emaarco.example.adapter.process.generated"
    outputLanguage = io.github.emaarco.bpmn.domain.shared.OutputLanguage.JAVA
    processEngine = io.github.emaarco.bpmn.domain.shared.ProcessEngine.ZEEBE
    useVersioning = false
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.test {
    useJUnitPlatform()
    retry {
        maxRetries.set(3)
        maxFailures.set(3)
        failOnPassedAfterRetry.set(false)
    }
}
