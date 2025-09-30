plugins {
    java
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
    id("jacoco") // ✅ Ajout de JaCoCo
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-hateoas")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.jetbrains:annotations:24.0.0")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")

    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
}

tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport) // ✅ Génère le rapport après les tests

    testLogging {
        events("passed", "skipped", "failed")
        showExceptions = true
        showStackTraces = true
        showStandardStreams = true
    }
}

// ✅ Configuration de JaCoCo
jacoco {
    toolVersion = "0.8.11"
    reportsDirectory.set(layout.buildDirectory.dir("customJacocoReportDir"))
}

tasks.jacocoTestReport {
    dependsOn(tasks.test) // ✅ S'assure que les tests tournent avant le rapport
    reports {
        xml.required.set(false)
        csv.required.set(false)
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = "0.90".toBigDecimal()
            }
        }
    }
}
