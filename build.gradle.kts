plugins {
    kotlin("jvm") version "1.9.22"
    application
}

group = "com.undercurrent"
version = "1.0.0"

repositories {
    mavenLocal()
    mavenCentral()
    maven { setUrl("https://jitpack.io") }
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation(Dependencies.EXPOSED_CORE)
    implementation(Dependencies.EXPOSED_DAO)
    implementation(Dependencies.EXPOSED_JDBC)
    implementation(Dependencies.SQLITE_JDBC)

    implementation(Dependencies.KTOR_CLIENT_CORE)
    implementation(Dependencies.KTOR_CLIENT_CIO)
    implementation(Dependencies.KOTLINX_COROUTINES_CORE)


    /** Test dependencies */
    testImplementation(Dependencies.JUNIT5_KOTLIN)  // Use the Kotlin JUnit 5 integration.
    testImplementation(libs.junit.jupiter.engine)  // Use the JUnit 5 integration.
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation(libs.guava)  // This dependency is used by the application.
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass = "com.undercurrent.MainKt"
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}


object Versions {
    const val KOTLIN = "1.9.22"
    const val EXPOSED = "0.47.0"
    const val KTOR = "2.3.4"
    const val KOTLINX_COROUTINES = "1.8.0"
    const val SQLITE_JDBC = "3.43.0.0"
}

object Dependencies {
    const val EXPOSED_CORE = "org.jetbrains.exposed:exposed-core:${Versions.EXPOSED}"
    const val EXPOSED_DAO = "org.jetbrains.exposed:exposed-dao:${Versions.EXPOSED}"
    const val EXPOSED_JDBC = "org.jetbrains.exposed:exposed-jdbc:${Versions.EXPOSED}"
    const val SQLITE_JDBC = "org.xerial:sqlite-jdbc:${Versions.SQLITE_JDBC}"
    const val KTOR_CLIENT_CORE = "io.ktor:ktor-client-core:${Versions.KTOR}"
    const val KTOR_CLIENT_CIO = "io.ktor:ktor-client-cio:${Versions.KTOR}"
    const val KOTLINX_COROUTINES_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.KOTLINX_COROUTINES}"
    const val JUNIT5_KOTLIN = "org.jetbrains.kotlin:kotlin-test-junit5"
}