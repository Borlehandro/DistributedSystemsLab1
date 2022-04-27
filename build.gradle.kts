import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val koinVersion = "3.1.6"

plugins {
    java
    kotlin("jvm") version "1.5.31"
    application
}

application.mainClassName = "me.borlehandro.distributed_systems.lab1.MainKt"

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
    withType<Jar> {
        manifest {
            attributes["Main-Class"] = "me.borlehandro.distributed_systems.lab1.MainKt"
        }
        from(sourceSets.main.get().output)

        dependsOn(configurations.runtimeClasspath)
        from({
            configurations.runtimeClasspath.get()
                .filter { it.name.endsWith("jar") }
                .map { zipTree(it) }
        })
    }
}

group = "me.borlehandro.distributed_systems"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.apache.logging.log4j", "log4j-core", "2.17.1", null)
    implementation("org.apache.commons", "commons-compress", "1.21", null)
    implementation("commons-cli:commons-cli:1.5.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Koin DI
    implementation("io.insert-koin:koin-core:$koinVersion")
}
