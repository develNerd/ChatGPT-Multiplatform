plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("plugin.serialization") version "1.7.10"
}

android {
    namespace = "org.flepper.chatgpt.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "org.flepper.chatgpt.android"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.0"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(project(":shared"))

    val nav_version = "2.5.0"
    val compose_version = "1.2.1"

    implementation("androidx.compose.ui:ui:$compose_version")
    implementation("androidx.compose.ui:ui-tooling:$compose_version")
    implementation("androidx.compose.ui:ui-tooling-preview:$compose_version")
    implementation("androidx.compose.foundation:foundation:$compose_version")
    implementation("androidx.compose.material:material:$compose_version")
    implementation("androidx.activity:activity-compose:$compose_version")

    implementation("com.google.firebase:firebase-analytics")
    // Kotlin
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")
    implementation("androidx.navigation:navigation-compose:$nav_version")
// Java language implementation
    implementation("androidx.navigation:navigation-fragment:$nav_version")
    implementation("androidx.navigation:navigation-ui:$nav_version")

    // Integration with activities
    implementation("androidx.activity:activity-compose:1.6.1")

    // Compose Material Design
    implementation("androidx.compose.material:material:1.3.1")
    // Animations
    implementation("androidx.compose.animation:animation:$compose_version")
    // Tooling support (Previews, etc.)
    implementation("androidx.compose.ui:ui-viewbinding:$compose_version")    // Integration with ViewModels
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")

    // Testing Navigation
    androidTestImplementation("androidx.navigation:navigation-testing:$nav_version")



    // Koin main features for Android

    with(Deps.Koin) {
        implementation(core)
        implementation(android)
    }

    with(Deps.KotlinX){
        api(serialization)
    }

    // Accompanist
    implementation("com.google.accompanist:accompanist-pager:0.23.0") // Pager
    implementation("com.google.accompanist:accompanist-pager-indicators:0.23.0")// Pager Indicators

    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.0-beta02")

    //Coil
    implementation("io.coil-kt:coil-compose:2.1.0")
    api("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
    api("io.ktor:ktor-client-logging:1.6.6")
    implementation("com.google.accompanist:accompanist-flowlayout:0.26.0-alpha")

    //Glide
    implementation("com.github.bumptech.glide:glide:4.13.2")
    annotationProcessor("com.github.bumptech.glide:compiler:4.13.2")

    implementation("androidx.compose.ui:ui-util:1.1.1")


    //Auth
    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:30.3.1"))

    // Declare the dependency for the Firebase Authentication library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-auth-ktx")

    //Test
    implementation("com.google.android.material:material:1.6.1")
    val retrofit_version = "2.8.1"
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit_version")
    implementation("com.squareup.okhttp3:logging-interceptor:4.7.2")
    implementation("org.brotli:dec:0.1.2")

    // Test rules and transitive dependencies:
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$compose_version")
// Needed for createAndroidComposeRule, but not createComposeRule:
    debugImplementation("androidx.compose.ui:ui-test-manifest:$compose_version")


}