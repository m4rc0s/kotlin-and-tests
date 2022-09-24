import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
	id("org.springframework.boot") version "2.7.3"
	id("io.spring.dependency-management") version "1.0.13.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	id("org.sonarqube") version "3.4.0.2513"
	id("io.gitlab.arturbosch.detekt").version("1.21.0")
	jacoco
	application
}

group = "org.quality"
version = "1.0"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

sourceSets {
	create("componentTest") {
		kotlin {
			compileClasspath += main.get().output + configurations.testRuntimeClasspath
			runtimeClasspath += main.get().output + compileClasspath
		}
		resources {
			srcDirs("/src/componentTest/resources")
		}
	}
}

sonarqube {
	properties {
		property("sonar.login", System.getenv("SONAR_TOKEN"))
		property("sonar.projectKey", "m4rc0s_quality-and-delivery-pipelines")
		property("sonar.organization", "quality-and-delivery-pipelines")
		property("sonar.host.url", "https://sonarcloud.io")
		property("sonar.jacoco.reportPaths", "$buildDir/jacoco/unitTest.exec")
		property("sonar.jacoco.xmlReportPaths", "$buildDir/testReports/test/jacocoTestReport.xml")
		property("sonar.kotlin.detekt.reportPaths", "$buildDir/reports/detekt/output.xml")
	}
}

jacoco {
	toolVersion = "0.8.8"
	reportsDirectory.set(layout.buildDirectory.dir("testReports"))
}

detekt {
	source = files(
		"src/main/kotlin",
		"src/test/kotlin",
		"src/componentTest/kotlin"
	)

	config = files("$projectDir/detekt.yml")
}


tasks.withType<Detekt>().configureEach {
	reports {
		xml {
			outputLocation.set(file("build/reports/detekt/output.xml"))
		}
	}
	include("**/*.kt")
	include("**/*.kts")
	exclude("resources/")
	exclude("build/")
}

tasks.named("check").configure {
	this.setDependsOn(this.dependsOn.filterNot {
		it is TaskProvider<*> && it.name == "detekt"
	})
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.7.10")
	implementation("org.springframework.boot:spring-boot-starter-web")


	// Test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.mockk:mockk:1.12.7")
	testImplementation("io.kotest:kotest-assertions-core-jvm:5.4.2")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "13"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()

	testLogging {
		events = setOf(
			TestLogEvent.STARTED,
			TestLogEvent.SKIPPED,
			TestLogEvent.FAILED,
			TestLogEvent.PASSED
		)
		showCauses = true
		showStackTraces = true
	}
}

tasks.register<Test>("unitTest") {
	doFirst {
		val sonarToken = System.getenv("SONAR_TOKEN") ?: error("You need to set SONAR_TOKEN env var")
	}
	testClassesDirs = sourceSets["test"].output.classesDirs
	classpath = sourceSets["test"].runtimeClasspath
	finalizedBy(tasks.jacocoTestReport)
}



tasks.register<Test>("componentTest") {
	testClassesDirs = sourceSets["componentTest"].output.classesDirs
	classpath = sourceSets["componentTest"].runtimeClasspath
}

tasks.jacocoTestReport {
	executionData.setFrom("$buildDir/jacoco/unitTest.exec")
	reports {
		csv.required.set(false)
		xml.required.set(true)
		html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
	}
	dependsOn(tasks.findByName("unitTest"))
}
