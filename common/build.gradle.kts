plugins {
    id(BuildPlugins.ANDROID_LIBRARY_PLUGIN)
    id(BuildPlugins.KOTLIN_ANDROID_PLUGIN)
    id(BuildPlugins.KOTLIN_PARCELABLE_PLUGIN)
    id(BuildPlugins.KOTLIN_KAPT)
    id(BuildPlugins.DAGGER_HILT)
    id(BuildPlugins.ktLint)
}

android {
    compileSdk = AppVersions.COMPILE_SDK

    defaultConfig {
        namespace = "com.lyhoangvinh.sample.common"
        minSdk = (AppVersions.MIN_SDK)
        targetSdk = (AppVersions.TARGET_SDK)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
}

// Required for annotation processing plugins like Dagger
kapt {
    generateStubs = true
    correctErrorTypes = true
}

dependencies {
    api(project(":domain"))

    implementation(platform("androidx.compose:compose-bom:${Lib.Androidx.composeBom}"))
    Lib.Androidx.list.forEach(::implementation)
    Lib.Androidx.Compose.list.forEach(::implementation)
    Lib.ThirdParty.list.forEach(::implementation)
    Lib.Accompanist.list.forEach(::implementation)
    Lib.Google.list.forEach(::implementation)
    Lib.Kotlin.list.forEach(::implementation)

    /*DI*/
    implementation(Lib.Di.hilt)
    implementation(Lib.Di.hiltNavigationCompose)
//    implementation(Lib.Di.viewmodel)
    kapt(Lib.Di.hiltCompiler)
    kapt(Lib.Di.hiltAndroidCompiler)

    // Room
    implementation(Lib.Room.roomKtx)
    implementation(Lib.Room.roomRuntime)
    add("kapt", Lib.Room.roomCompiler)
    testImplementation(Lib.Room.testing)

    UnitTesting.list.forEach(::testImplementation)
    DevDependencies.debugList.forEach(::debugImplementation)
    DevDependencies.list.forEach(::implementation)
}