@file:Suppress("UnstableApiUsage")
plugins {
    id(libs.plugins.android.application.get().pluginId)
    id(libs.plugins.hilt.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
    alias(libs.plugins.ksp)
}

android {
    compileSdk = 33

    defaultConfig.apply {
        applicationId = "dev.logickoder.qrpay"
        minSdk = 23
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        vectorDrawables {
            useSupportLibrary = true
        }

        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }

    buildTypes.apply {
        maybeCreate("release").apply {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions.apply {
        // Flag to enable support for the new language APIs
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures.apply {
        compose = true
    }

    composeOptions.apply {
        kotlinCompilerExtensionVersion = rootProject.libs.versions.compose.compiler.get()
    }

    packagingOptions.apply {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // for using some java 8 classes like LocalDate with older versions of android
    coreLibraryDesugaring(libs.core.java8)

    implementation(libs.core)
    implementation(libs.core.appcompat)

    // Material Design
    implementation(libs.core.material)
    implementation(libs.compose.material)
    implementation(libs.compose.material.icons)

    // Constraint Layout
    implementation(libs.core.constraintlayout)
    implementation(libs.compose.constraintlayout)

    // Google Accompanist
    implementation(libs.accompanist.swiperefresh)
    implementation(libs.accompanist.placeholdermaterial)

    // Compose
    implementation(libs.compose.activity)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    // Compose tooling support (Previews, etc.)
    debugImplementation(libs.compose.custom.view)
    debugImplementation(libs.compose.custom.view.pooling)
    debugImplementation(libs.compose.ui.tooling)

    // qrcode scanner
    implementation("com.journeyapps:zxing-android-embedded:4.3.0") { isTransitive = false }
    implementation("com.google.zxing:core:3.5.1")

    // lifecycle
    implementation(libs.lifecycle.runtime)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.viewmodel.compose)

    // preferences datastore
    implementation(libs.datastore)

    // hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // gson
    implementation("com.google.code.gson:gson:2.10.1")

    // retrofit
    val retrofit_version = "2.9.0"
    implementation(libs.retrofit)
    implementation("com.squareup.retrofit2:retrofit-converters:2.8.1")
    implementation("com.squareup.retrofit2:converter-gson:${retrofit_version}")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11")

    // Room
    ksp(libs.room.compiler)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)

    testImplementation(libs.junit4)
    androidTestImplementation(libs.expresso)
    androidTestImplementation(libs.junit4.androidx)
    androidTestImplementation(libs.compose.ui.test.junit)
}
