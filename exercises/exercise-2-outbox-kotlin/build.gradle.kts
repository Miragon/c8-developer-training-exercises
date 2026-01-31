import io.github.emaarco.bpmn.adapter.GenerateBpmnModelsTask
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.jpa)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.springframework)
    alias(libs.plugins.spring.dependency)
    alias(libs.plugins.bpmnToCode)
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
}

tasks.named<GenerateBpmnModelsTask>("generateBpmnModelApi") {
    baseDir = projectDir.toString()
    filePattern = "src/main/resources/bpmn/*.bpmn"
    outputFolderPath = "$projectDir/src/main/kotlin"
    packagePath = "de.emaarco.example.adapter.process.generated"
    outputLanguage = io.github.emaarco.bpmn.domain.shared.OutputLanguage.KOTLIN
    processEngine = io.github.emaarco.bpmn.domain.shared.ProcessEngine.ZEEBE
    useVersioning = false
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

kotlin {
    jvmToolchain(21)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
