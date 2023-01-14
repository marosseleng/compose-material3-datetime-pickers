/*
 *    Copyright 2022 Maroš Šeleng
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
import org.gradle.api.tasks.testing.logging.TestLogEvent

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.junit5.plugin) apply false
    alias(libs.plugins.paparazzi) apply false
    // publish plugin has to be applied here
    alias(libs.plugins.nexus.publish) apply true
}

tasks.register("clean", type = Delete::class) {
    delete(rootProject.buildDir)
}

subprojects {
    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
        testLogging {
            if (System.getenv("CI") == "true") {
                events(TestLogEvent.FAILED, TestLogEvent.SKIPPED)
            }
            exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        }

        maxParallelForks = (java.lang.Runtime.getRuntime().availableProcessors() / 2).takeIf { it > 0 } ?: 1
    }
}

nexusPublishing {
    repositories {
        sonatype {
            stagingProfileId.set(project.findProperty("sonatypeProfileId") as String? ?: System.getenv("SONATYPE_PROFILE_ID"))
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
        }
    }
}