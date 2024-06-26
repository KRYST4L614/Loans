plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.googleDevtoolsKsp)
}

android {
    namespace = "com.example.afinal"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.afinal"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
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

    viewBinding {
        enable = true
    }

}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.com.google.dagger)
    ksp(libs.com.google.dagger.compiler)
    implementation(libs.com.github.terrakok.cicerone)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.com.squareup.retrofit2)
    implementation(libs.com.squareup.retrofit2.gson)
    implementation(libs.com.squareup.okhttp3.logging.interceptor)
    implementation(libs.com.squareup.retrofit2.scalars)
    implementation(libs.androidx.security.crypto)

    implementation(project(":feature:auth"))
    implementation(project(":feature:onboarding"))
    implementation(project(":feature:home"))
    implementation(project(":feature:homepage"))
    implementation(project(":feature:menupage"))
    implementation(project(":feature:myloanspage"))
    implementation(project(":feature:loandetails"))
    implementation(project(":feature:language"))
    implementation(project(":feature:special"))
    implementation(project(":feature:splash"))
    implementation(project(":feature:support"))
    implementation(project(":feature:addresses"))
    implementation(project(":feature:requestloan"))
    implementation(project(":feature:rejectloan"))
    implementation(project(":feature:acceptloan"))
    implementation(project(":component:resources"))
    implementation(project(":shared:fragmentdependencies"))
    implementation(project(":shared:viewmodelfactory"))
    implementation(project(":shared:loans"))
    implementation(project(":shared:resourceprovider"))
}