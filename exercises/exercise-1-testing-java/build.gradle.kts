import io.github.emaarco.bpmn.adapter.GenerateBpmnModelsTask
import io.github.emaarco.bpmn.domain.shared.OutputLanguage
import io.github.emaarco.bpmn.domain.shared.ProcessEngine

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
    implementation(libs.zeebeSdk)
    implementation("org.slf4j:slf4j-api")
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
    outputLanguage = OutputLanguage.JAVA
    processEngine = ProcessEngine.ZEEBE
    useVersioning = false
}

tasks.test {
    useJUnitPlatform()
}


java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

/**
 * Use this for more reliable zeebe-test
 * They sometimes cause issues, because of their async nature
 */
tasks.test {
    retry {
        maxRetries.set(3)
        maxFailures.set(3)
        failOnPassedAfterRetry.set(false)
    }
}
