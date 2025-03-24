plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt) // Use alias for Hilt plugin
    alias(libs.plugins.jetbrainsKotlinPluginSerialization) // Use alias for Hilt plugin

    kotlin("kapt")
}

android {
    namespace = "com.example.stylefeed"  // ✅ 앱 기본 namespace
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.stylefeed"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.example.stylefeed.HiltTestRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Dagger Hilt dependencies
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // Test libraries (Unit Tests)
    testImplementation(libs.junit)
    testImplementation("io.mockk:mockk:1.13.17")  // 👈 MockK
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.1") // 👈 Coroutine Test
    testImplementation("app.cash.turbine:turbine:1.2.0") // 👈 Turbine for Flow testing
    testImplementation("com.google.truth:truth:1.4.4")  // 👈 Truth for assertions
    testImplementation("com.airbnb.android:mavericks-testing:3.0.9")

    // Android Instrumentation & Compose UI Tests
    testImplementation(libs.hilt.android.testing)
    // Hilt testing dependencies
    androidTestAnnotationProcessor(libs.hilt.compiler)
    testAnnotationProcessor(libs.hilt.compiler)
    kaptTest(libs.hilt.compiler)
    androidTestImplementation(libs.hilt.android.testing)
    kaptAndroidTest(libs.hilt.compiler)

    testImplementation(libs.mockwebserver)
    androidTestImplementation(libs.mockwebserver)

    // ----- Retrofit + Kotlin Serialization -----
    implementation(libs.retrofit)
    implementation(libs.okhttp3.logging.interceptor)
    implementation(libs.okhttp)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.kotlinx.serialization.json)

    // ----- Mavericks -----
    implementation(libs.mavericks)
    implementation(libs.mavericks.compose) // Compose 연동
    implementation(libs.mavericks.hilt)    // Hilt 연동
    implementation(libs.mavericks.navigation) // Navigate 사용시


    implementation(libs.coil.compose)
}