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
        namespace = "com.lyhoangvinh.sample.commonui"
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
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Lib.Androidx.composeVersion
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

// Required for annotation processing plugins like Dagger
kapt {
    generateStubs = true
    correctErrorTypes = true
}

dependencies {

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