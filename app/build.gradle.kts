import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.gradle)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.kapt)
}


android {
    namespace = "com.example.newsfeed"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.newsfeed"
        minSdk = 24
        //noinspection OldTargetApi
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val file = rootProject.file("local.properties")
        val properties = Properties()
        properties.load(FileInputStream(file))
        val apiKey= properties.getProperty("NEWS_API_KEY")
        buildConfigField("String", "API_KEY",  "\"$apiKey\"")
    }

    hilt {
        enableAggregatingTask = false
    }

    flavorDimensions += "default"

    productFlavors {
        create("free") {
            dimension = "default"
            applicationIdSuffix = ".free"
            versionNameSuffix = "-free"
            resValue ("string", "app_name", "NewsFeed Free")
            buildConfigField("boolean", "SHOW_ADS", "true")
        }
        create("premium") {
            dimension = "default"
            applicationIdSuffix = ".premium"
            versionNameSuffix = "-premium"
            resValue ("string", "app_name", "NewsFeed Pro")
            buildConfigField("boolean", "SHOW_ADS", "false")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig=true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.coil.compose)

    // Components
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // WorkManager
    implementation(libs.androidx.work.runtime.ktx)

    //Hilt DI
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.retrofit.logging.interceptor)
    implementation(libs.retrofit.adapter.rxjava2)

    // Hilt WorkManager
    implementation(libs.androidx.hilt.work)
    kapt(libs.androidx.hilt.compiler)

    // Permissions library
    implementation(libs.accompanist.permissions)



}