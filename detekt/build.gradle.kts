plugins {
    alias(libs.plugins.jetbrainsKotlinJvm)
    `maven-publish`
}

group = "org.example.detekt"
version = "1.0-SNAPSHOT"

dependencies {
    compileOnly(libs.detekt.api)

    testImplementation(libs.detekt.test)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.junit)
}

kotlin {
    jvmToolchain(22)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    systemProperty("junit.jupiter.testinstance.lifecycle.default", "per_class")
    systemProperty("compile-snippet-tests", project.hasProperty("compile-test-snippets"))
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}
