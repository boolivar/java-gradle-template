plugins {
    java
    id("jacoco")
    id("pmd")
    id("checkstyle")
    id("com.github.spotbugs")
    id("io.freefair.lombok")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
    withJavadocJar()
    withSourcesJar()
}

tasks.withType<Javadoc>().configureEach {
    options {
        this as CoreJavadocOptions
        addBooleanOption("Xdoclint:none", true)
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
    finalizedBy("jacocoTestReport")
}

tasks.named<JacocoReport>("jacocoTestReport") {
    reports {
        xml.required = true
    }
}

tasks.named<JacocoCoverageVerification>("jacocoTestCoverageVerification") {
    dependsOn("test")
    violationRules {
        rule {
            limit {
                minimum = providers.gradleProperty("jacocoMinRatio").get().toBigDecimal()
            }
        }
    }
}

tasks.named("check") {
    dependsOn("jacocoTestCoverageVerification")
}

tasks.named("jar") {
    dependsOn("check")
}

checkstyle {
    configDirectory = file("$rootDir/gradle/config/checkstyle")
    toolVersion = com.puppycrawl.tools.checkstyle.Checker::class.java.`package`.implementationVersion
}

pmd {
    isConsoleOutput = true
    ruleSets = listOf("ruleset.xml")
    toolVersion = net.sourceforge.pmd.PMDVersion.VERSION
}

tasks.named<Pmd>("pmdTest") {
    ruleSets = listOf("test-ruleset.xml")
}

tasks.withType<Pmd>().configureEach {
    pmdClasspath += files("$rootDir/gradle/config/pmd")
}

spotbugs {
    omitVisitors = listOf("FindReturnRef")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform(project(":platform")))

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.springframework")
        exclude(group = "org.springframework.boot")
    }
}