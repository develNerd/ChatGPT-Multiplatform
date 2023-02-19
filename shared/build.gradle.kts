plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization") version "1.7.10"
    id("com.rickclephas.kmp.nativecoroutines") version "0.13.0"
}

kotlin {
    android()
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies{
                with(Deps.Ktor){
                    implementation(core)
                    implementation(cio)
                    implementation(serialization)
                    implementation(json_serialization)
                    implementation(logging)
                    implementation(content_negotiation)
                    implementation(cbor)
                    implementation(Deps.Ktor.encoding)
                    //implementation(protoBurf)
                }

                with(Deps.KotlinX){
                    implementation(serialization)
                    implementation(dateTime)
                }

                with(Deps.DataStore){
                    implementation(datasore_okio)
                    implementation(datasore)
                }

                with(Deps.Koin) {
                    api(core)
                    api(test)
                }
                with(Deps.Kermit) {
                    implementation(kermitMain)
                }

                with(Deps.RealDatabase){
                    implementation(this.coroutines)
                    implementation(this.realBase)
                }

                implementation(kotlin("stdlib-common"))
                implementation("app.cash.turbine:turbine:0.12.1")


                with(Deps.Coroutines){
                    implementation(coroutines)
                    implementation(coroutinesNative)
                }
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.jetbrains.kotlin:kotlin-test:1.6.21")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
            }
        }
        val androidMain by getting{
            dependencies{
                implementation("org.brotli:dec:0.1.2")
            }
        }
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "org.flepper.chatgpt"
    compileSdk = 32
    defaultConfig {
        minSdk = 21
        targetSdk = 32
    }
}