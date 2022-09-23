import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
	id("org.springframework.boot") version "2.7.3"
	id("io.spring.dependency-management") version "1.0.13.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	id("org.sonarqube") version "3.4.0.2513"
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
		property("sonar.projectKey", "m4rc0s_quality-and-delivery-pipelines")
		property("sonar.organization", "quality-and-delivery-pipelines")
		property("sonar.host.url", "https://sonarcloud.io")
		property("sonar.jacoco.xmlReportPaths", "$buildDir/testReports/test/jacocoTestReport.xml")
	}
}


/*sonarqube {
	properties {
		property("sonar.projectKey", "QualityAssuranceDemo")
		property("sonar.host.url", "http://localhost:9000")
		property("sonar.login", "sqp_0d6fd44cedc28960251dad0846198fb471151d24")
		property("sonar.jacoco.xmlReportPaths", "$buildDir/testReports/test/jacocoTestReport.xml")
	}
}*/

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

jacoco {
	toolVersion = "0.8.8"
	reportsDirectory.set(layout.buildDirectory.dir("testReports"))
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
