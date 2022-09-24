# CI/CD Testing and Kotlin

This Repository will be about Software Delivery Life Cycle (SDLC), specifically quality, aiming on testing practices for continuous integration using 
Kotlin Programming Language and Gradle over an containerized application;

[![CircleCI](https://dl.circleci.com/status-badge/img/gh/m4rc0s/quality-and-delivery-pipelines/tree/main.svg?style=svg)](https://dl.circleci.com/status-badge/redirect/gh/m4rc0s/quality-and-delivery-pipelines/tree/main)

# Whats is it now?

Almost empty workspace with a sample application and markdowns;

# Sample Apps

- [Demo](https://github.com/m4rc0s/quality-and-delivery-pipelines/tree/main/apps/demo): Demo application is the first sample running code of this project. It contains the basic testing tools setup for testing with Kotlin on SpringBoot


# Tools

- [MockK](https://mockk.io/): A mock library for Kotlin and it provides an DSL for mocking with


- [Kotest Assertions](https://kotest.io/docs/assertions/assertions.html): Used for improve assertions development experient with a fluent kotlin dsl sintax, given us the power to do something like `actual shouldBe expected` !




sqp_0d6fd44cedc28960251dad0846198fb471151d24

./gradlew sonarqube \                                                                                 [19:17:55]
  -Dsonar.projectKey=QualityAssuranceDemo \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=sqp_0d6fd44cedc28960251dad0846198fb471151d24
  -Dsonar.jacoco.xmlReportPaths="../apps/demo/build/testReports/test/jacocoTestReport.xml"

