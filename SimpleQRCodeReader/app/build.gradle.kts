plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.simpleqrcodereader"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.simpleqrcodereader"
        minSdk = 26
        targetSdk = 33
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

    buildFeatures{
        viewBinding = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    // CameraX 코어 라이브러리
    implementation("androidx.camera:camera-camera2:1.2.3")
    // CameraX 생명 주기 관련 라이브러리
    implementation("androidx.camera:camera-lifecycle:1.2.3")
    // CameraX 뷰 관련 라이브러리
    implementation("androidx.camera:camera-view:1.2.3")
    // ML kit 라이브러리
    implementation("com.google.mlkit:barcode-scanning:17.1.0")

}