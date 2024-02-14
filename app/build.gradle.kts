@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
    alias(libs.plugins.serilization)
    alias(libs.plugins.sqldelight)
}

android {
    namespace = "com.tareq.gittrack"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.tareq.gittrack"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.9"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    sqldelight {
        databases {
            create("GHUsersDatabase") {
                packageName.set("com.tareq.gittrack")
            }
        }
    }
}
kapt {
    correctErrorTypes = true
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    implementation(libs.bundles.lifecycle)

    implementation(libs.navigation.compose)

    implementation(libs.bundles.hilt)
    kapt(libs.hilt.compiler)

    implementation(libs.serilization)

    implementation(libs.bundles.sqldelight)
    kapt(libs.sqldelight.plugin)

    implementation(platform(libs.okhttp.bom))
    implementation(libs.bundles.okhttp)

    implementation(libs.bundles.retrofit)

    implementation(libs.lottie)

    implementation(libs.coil)

    implementation(libs.splash)
}