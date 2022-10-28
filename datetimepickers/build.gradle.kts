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

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    `maven-publish`
    signing
}

android {
    namespace = "com.marosseleng.compose.material3.datetimepickers"
    compileSdk = 33

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs += listOf("-Xexplicit-api=strict")
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.version.compose.compiler.get()
    }
    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {
    implementation(libs.material3)

    implementation(platform(libs.compose.bom))

    implementation(libs.bundles.compose.library)

    debugImplementation(libs.bundles.compose.debug)

    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.compose.bom))

    androidTestImplementation(libs.bundles.android.test)
}

kotlin {
    explicitApi()
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.marosseleng.android"
            artifactId = "compose-material3-datetime-pickers"
            version = "0.1.1"

            afterEvaluate {
                from(components["release"])
            }

            pom {
                name.set("Compose material3 Date & Time pickers")
                description.set("A Jetpack Compose components with material3 support for date & time picking.")
                inceptionYear.set("2022")
                url.set("https://github.com/marosseleng/compose-material3-datetime-pickers")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("marosseleng")
                        name.set("Maroš Šeleng")
                        email.set("maros@marosseleng.com")
                    }
                }
                scm {
                    connection.set("scm:git:github.com/marosseleng/compose-material3-datetime-pickers.git")
                    developerConnection.set("scm:git:ssh://github.com/marosseleng/compose-material3-datetime-pickers.git")
                    url.set("https://github.com/marosseleng/compose-material3-datetime-pickers/tree/main")
                }
            }
        }
    }
    repositories {
        maven {
            name = "projectRepository"
            url = uri("${project.buildDir}/repository")
        }
    }
}

signing {
    useInMemoryPgpKeys(
        project.findProperty("signing.keyId") as String? ?: System.getenv("SIGNING_KEY_ID"),
        project.findProperty("signing.key") as String? ?: System.getenv("SIGNING_KEY"),
        project.findProperty("signing.password") as String? ?: System.getenv("SIGNING_KEY_PASSWORD"),
    )
    sign(publishing.publications.getByName("release"))
}

tasks.register<Zip>("generateZippedArtifact") {
    val publishTask = tasks.named("publishReleasePublicationToProjectRepositoryRepository", PublishToMavenRepository::class.java)
    from(publishTask.map { it.repository.url })
    archiveFileName.set("library.zip")
}