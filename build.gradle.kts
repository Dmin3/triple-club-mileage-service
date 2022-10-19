import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.4"
	id("io.spring.dependency-management") version "1.0.14.RELEASE"
	kotlin("jvm") version "1.6.10"
	kotlin("plugin.spring") version "1.6.10"
	kotlin("plugin.jpa") version "1.6.10"

	// for spock
	id("java")
	id("groovy")

	// for spring rest docs
//	id("org.asciidoctor.convert") version "1.5.8"
	id("org.asciidoctor.jvm.convert") version "3.3.2"
}

val asciidoctorExtensions: Configuration by configurations.creating

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	runtimeOnly("com.h2database:h2")
	runtimeOnly("mysql:mysql-connector-java")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

val snippetsDir by extra { file("build/generated-snippets") }

tasks.test {
	outputs.dir(snippetsDir)
}

tasks.asciidoctor {
	inProcess = org.asciidoctor.gradle.base.process.ProcessMode.JAVA_EXEC
	forkOptions {
		jvmArgs("--add-opens", "java.base/sun.nio.ch=ALL-UNNAMED", "--add-opens", "java.base/java.io=ALL-UNNAMED")
	}
	inputs.dir(snippetsDir)
	dependsOn(tasks.test)
}

tasks.bootJar {
	dependsOn(tasks.asciidoctor)
	from("$snippetsDir/html5") {
		into("static/docs")
	}
}

tasks.bootJar {
	archiveFileName.set("triple-club-mileage-service.jar")
}
