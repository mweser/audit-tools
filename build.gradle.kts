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
    implementation(Dependencies.EXPOSED_JAVA_TIME)
    implementation(Dependencies.EXPOSED_JDBC)

    implementation(Dependencies.FLYWAY_CORE)
    implementation(Dependencies.HIKARI_CP)
    implementation(Dependencies.SQLITE_JDBC)

    implementation(Dependencies.KTOR_CLIENT_CORE)
    implementation(Dependencies.KTOR_CLIENT_CIO)
    implementation(Dependencies.KTOR_CLIENT_CONTENT_NEGOTIATION)
    implementation(Dependencies.KTOR_SERIALIZATION_JSON)
    implementation(Dependencies.KOTLINX_COROUTINES_CORE)

    api("org.json", "json", "20220320")

    /** Test dependencies */
    testImplementation(Dependencies.JUNIT5_KOTLIN)  // Use the Kotlin JUnit 5 integration.
    testImplementation(libs.junit.jupiter.engine)  // Use the JUnit 5 integration.
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation(libs.guava)  // This dependency is used by the application.
}

// Apply a specific Java toolchain to ease working on different environments.
java { toolchain { languageVersion = JavaLanguageVersion.of(21) } }
application { mainClass = "com.undercurrent.MainKt" }
tasks.named<Test>("test") { useJUnitPlatform() } // Use JUnit Platform for unit tests.

object Versions {
    const val EXPOSED = "0.47.0"
    const val FLYWAY_CORE = "9.18.0"
    const val HIKARI_CP = "5.0.1"
    const val KOTLIN = "1.9.22"
    const val KOTLINX_COROUTINES = "1.8.0"
    const val KTOR = "2.3.4"

    const val LOG4J_CORE = "2.20.0"
    const val LOG4J_API = LOG4J_CORE
    const val LOG4J_SLF4J_IMPL = LOG4J_CORE
    const val LOG4J_ASYNC = LOG4J_CORE

    const val SQLITE_JDBC = "3.43.0.0"
}

object Dependencies {
    const val EXPOSED_CORE = "org.jetbrains.exposed:exposed-core:${Versions.EXPOSED}"
    const val EXPOSED_DAO = "org.jetbrains.exposed:exposed-dao:${Versions.EXPOSED}"
    const val EXPOSED_JAVA_TIME = "org.jetbrains.exposed:exposed-java-time:${Versions.EXPOSED}"
    const val EXPOSED_JDBC = "org.jetbrains.exposed:exposed-jdbc:${Versions.EXPOSED}"

    const val FLYWAY_CORE = "org.flywaydb:flyway-core:${Versions.FLYWAY_CORE}"
    const val HIKARI_CP = "com.zaxxer:HikariCP:${Versions.HIKARI_CP}"

    const val JUNIT5_KOTLIN = "org.jetbrains.kotlin:kotlin-test-junit5"
    const val KOTLINX_COROUTINES_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.KOTLINX_COROUTINES}"

    const val KTOR_CLIENT_CIO = "io.ktor:ktor-client-cio:${Versions.KTOR}"
    const val KTOR_CLIENT_CORE = "io.ktor:ktor-client-core:${Versions.KTOR}"

    const val KTOR_CLIENT_CONTENT_NEGOTIATION = "io.ktor:ktor-client-content-negotiation:${Versions.KTOR}"
    const val KTOR_SERIALIZATION_JSON = "io.ktor:ktor-serialization-kotlinx-json:${Versions.KTOR}"

    const val KTOR_SERVER_AUTH = "io.ktor:ktor-server-auth:${Versions.KTOR}"
    const val KTOR_SERVER_CALL_LOGGING = "io.ktor:ktor-server-call-logging:${Versions.KTOR}"
    const val KTOR_SERVER_CONTENT_NEGOTIATION = "io.ktor:ktor-server-content-negotiation:${Versions.KTOR}"
    const val KTOR_SERVER_CORE = "io.ktor:ktor-server-core:${Versions.KTOR}"
    const val KTOR_SERVER_LOGGING = "io.ktor:ktor-server-logging:${Versions.KTOR}"
    const val KTOR_SERVER_NETTY = "io.ktor:ktor-server-netty:${Versions.KTOR}"

    const val LOG4J_API = "org.apache.logging.log4j:log4j-api:${Versions.LOG4J_API}"
    const val LOG4J_ASYNC = "org.apache.logging.log4j:log4j-async:${Versions.LOG4J_ASYNC}"
    const val LOG4J_CORE = "org.apache.logging.log4j:log4j-core:${Versions.LOG4J_CORE}"
    const val LOG4J_SLF4J_IMPL = "org.apache.logging.log4j:log4j-slf4j-impl:${Versions.LOG4J_SLF4J_IMPL}"

    const val SQLITE_JDBC = "org.xerial:sqlite-jdbc:${Versions.SQLITE_JDBC}"
}