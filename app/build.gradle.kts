

plugins {
    id("com.android.application")
    id("kotlin-android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 30
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "com.example.samplerecipieapp"
        minSdk = 30
        targetSdk = 30
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
        useIR = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.0.0-beta08"
        kotlinCompilerVersion = "1.5.10"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    val kotlinVersion = "1.4.10"
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.appcompat:appcompat:1.3.0")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
//    testImplementation("junit:junit:4.+")
//    androidTestImplementation("androidx.test.ext:junit:1.1.3")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    val androidxUi = "1.0.0-alpha07"
    implementation("androidx.ui:ui-tooling:$androidxUi")

    val compose = "1.0.0-rc01"
    implementation("androidx.compose.ui:ui:$compose")
    implementation ("androidx.compose.foundation:foundation:$compose")
    implementation ("androidx.compose.runtime:runtime-livedata:$compose")
    implementation ("androidx.compose.runtime:runtime-rxjava2:$compose")
    implementation ("androidx.compose.material:material:$compose")
    implementation ("androidx.compose.material:material-icons-core:$compose")
    implementation ("androidx.compose.material:material-icons-extended:$compose")
    implementation ("androidx.compose.compiler:compiler:$compose")

    val composeConstraint = "1.0.0-alpha08"
    implementation ("androidx.constraintlayout:constraintlayout-compose:$composeConstraint")

    val composeActivity = "1.3.0-rc01"
    implementation ("androidx.activity:activity-compose:$composeActivity")

    val navVersion = "2.3.5"
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    val retrofitVersion = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    val hilt_version = "2.37"
    implementation("com.google.dagger:hilt-android:$hilt_version")
    kapt("com.google.dagger:hilt-android-compiler:$hilt_version")

    implementation("io.coil-kt:coil-compose:1.3.0")

}