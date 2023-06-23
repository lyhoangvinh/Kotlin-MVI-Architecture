plugins {
  id(BuildPlugins.ANDROID_APPLICATION_PLUGIN)
  id(BuildPlugins.KOTLIN_ANDROID_PLUGIN)
  id(BuildPlugins.KOTLIN_PARCELABLE_PLUGIN)
  id(BuildPlugins.KOTLIN_KAPT)
  id(BuildPlugins.DAGGER_HILT)
  id(BuildPlugins.ktLint)
}


android {
  compileSdk = (AppVersions.COMPILE_SDK)

  defaultConfig {
    applicationId = (AppVersions.APPLICATION_ID)
    minSdk = (AppVersions.MIN_SDK)
    targetSdk = (AppVersions.TARGET_SDK)
    versionCode = AppVersions.versionCode
    versionName = AppVersions.versionName
    testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    vectorDrawables.useSupportLibrary = true

    javaCompileOptions {
      annotationProcessorOptions {
        arguments += mapOf(
          "room.schemaLocation" to "$projectDir/schemas",
          "room.incremental" to "true",
          "room.expandProjection" to "true"
        )
      }
    }
  }

  buildTypes {
    getByName("release") {
      isDebuggable = false
      versionNameSuffix = "-release"

      isMinifyEnabled = true
      isShrinkResources = true

      proguardFiles(
        getDefaultProguardFile("proguard-android.txt"), "proguard-common.txt",
        "proguard-specific.txt"
      )
    }
    getByName("debug") {
      isDebuggable = true
      versionNameSuffix = "-debug"
      applicationIdSuffix = ".debug"
    }
  }

  buildFeatures {
    dataBinding = true
  }

  buildFeatures {
    compose = true
  }

  composeOptions {
    kotlinCompilerExtensionVersion = Lib.Androidx.composeVersion
  }

  packagingOptions {
    resources.excludes.add("META-INF/LICENSE.txt")
    resources.excludes.add("META-INF/NOTICE.txt")
    resources.excludes.add("LICENSE.txt")
    resources.excludes.add( "/META-INF/{AL2.0,LGPL2.1}")
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  kotlinOptions {
    jvmTarget = "1.8"
  }
}

kapt {
  generateStubs = true
  correctErrorTypes = true
}


hilt {
  enableAggregatingTask = true
  enableExperimentalClasspathAggregation = true
}

dependencies {
  

  Lib.Androidx.list.forEach(::api)
  Lib.Androidx.Compose.list.forEach(::api)
  Lib.ThirdParty.list.forEach(::api)
  Lib.Accompanist.list.forEach(::api)
  Lib.Google.list.forEach(::api)
  Lib.Kotlin.list.forEach(::api)

  api(project(":navigator"))
  api(project(":data"))
  api(project(":domain"))
  api(project(":common"))
  api(project(":commonui"))

  /*DI*/
  api(Lib.Di.hilt)
  api(Lib.Di.hiltNavigationCompose)
  api(Lib.Di.viewmodel)
  kapt(Lib.Di.hiltCompiler)
  kapt(Lib.Di.hiltAndroidCompiler)

  // Room
  api(Lib.Room.roomKtx)
  api(Lib.Room.roomRuntime)
  add("kapt", Lib.Room.roomCompiler)
  testApi(Lib.Room.testing)

  UnitTesting.list.forEach(::testApi)
  DevDependencies.debugList.forEach(::debugApi)
  DevDependencies.list.forEach(::api)
}
