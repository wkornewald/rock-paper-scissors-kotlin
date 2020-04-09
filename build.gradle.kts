plugins {
    kotlin("jvm") version "1.3.71"
}

group = "com.ensody.rockpaperscissors"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.6.1")
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.22")
    testImplementation("io.mockk:mockk:1.9.3")
}

tasks {
    test {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}