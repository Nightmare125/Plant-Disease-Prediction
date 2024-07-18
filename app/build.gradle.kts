plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.farmaid"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.farmaid"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
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
configurations.all {
    resolutionStrategy.force("org.jetbrains:annotations:23.0.0")
    exclude(group = "com.intellij", module = "annotations")
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
    implementation(libs.androidx.navigation.compose)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.firebase.crashlytics.buildtools)
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.room.compiler)
    implementation(libs.tensorflow.lite)
    //implementation(libs.tensorflow.lite.gpu)  // If you need GPU support
    //implementation(libs.tensorflow.lite.support)
    //implementation(libs.jetbrains.annotations)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
//jetbrains-annotations = { group = "org.jetbrains", name = "annotations", version.ref = "jetbrains" }
//jetbrains = "23.0.0"

//tflitegpu = "2.12.0"
//tfsupport ="0.4.3"
//tflite = "2.12.0"
//tensorflow-lite-gpu = { group = "org.tensorflow", name = "tensorflow-lite-gpu", version.ref = "tflitegpu" }
//tensorflow-lite-support = { group = "org.tensorflow", name = "tensorflow-lite-support", version.ref = "tfsupport" }
//[org.tensorflow:tensorflow-lite:2.12.0] C:\Users\aroun\.gradle\caches\transforms-4\bf9b0ff49042fe195c25fbead5ebe8ac\transformed\tensorflow-lite-2.12.0\AndroidManifest.xml Warning:
//	Namespace 'org.tensorflow.lite' is used in multiple modules and/or libraries: org.tensorflow:tensorflow-lite:2.12.0, org.tensorflow:tensorflow-lite-api:2.12.0. Please ensure that all modules and libraries have a unique namespace. For more information, See https://developer.android.com/studio/build/configure-app-module#set-namespace
//> Task :app:stripDebugDebugSymbols
//Unable to strip the following libraries, packaging them as they are: libimage_processing_util_jni.so, libtensorflowlite_jni.so.