plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    //    hilt
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
//    sonarqube
    id ("org.sonarqube") version "4.4.1.3373"
}

sonar {
    properties {
        property("sonar.host.url", "http://localhost:9000")
        property("sonar.token", project.findProperty("sonarToken")?.toString() ?: "")
//       Use Token generated in sonarqube or below properties with credentials
//        property("sonar.login", "loginID")
//        property("sonar.password","SonarqubePassword")
        property("sonar.projectName", rootProject.name)
        property("sonar.projectKey", "com.vishnu.featuresxml")
        property("sonar.projectVersion", "1")
    }
}

android {
    namespace = "com.vishnu.featuresxml"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.vishnu.featuresxml"
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
//    for view binding
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

//    Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

// Dagger Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-compiler:2.48")
    kapt("androidx.hilt:hilt-compiler:1.1.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

// Compose
    implementation("androidx.compose.foundation:foundation:1.5.4")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.compose.ui:ui-tooling:1.5.4")

// Material Design for Card
    implementation("androidx.compose.material:material:1.5.4")

// CardView and Navigation Compose
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.navigation:navigation-compose:2.7.6")

// OkHttp
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

// Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

// View Model
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

// Gson
    implementation("com.google.code.gson:gson:2.10")
//Text to speech
}


// TODO check if its really needed for hilt
// Added to support hilt dependencies
kapt {
    correctErrorTypes = true
}