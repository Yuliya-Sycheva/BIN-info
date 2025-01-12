plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("androidx.navigation.safeargs.kotlin")
    id("androidx.room")
}

android {
    namespace = "com.example.bin_info"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.bin_info"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
    room {
        schemaDirectory("$projectDir/schemas")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Network
    implementation(libs.network.retrofit)
    implementation(libs.network.converterGson)

    // Dependency Injection
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.navigation)

    // Navigation
    implementation(libs.navigation.fragmentKtx)
    implementation(libs.navigation.uiKtx)

    // Database
    implementation(libs.db.roomRuntime)
    annotationProcessor(libs.db.roomCompiler)
    implementation(libs.db.roomKtx)
}