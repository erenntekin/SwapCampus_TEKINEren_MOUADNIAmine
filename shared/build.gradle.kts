plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
}

kotlin {
    android {
        namespace = "com.example.swapcampus_mamaabbas.shared"
        compileSdk = 37
        minSdk = 26
        withHostTest {}
    }

    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            // api : ces types (LocalDateTime, StateFlow) apparaissent dans l'API publique de :shared
            api(libs.kotlinx.datetime)
            api(libs.kotlinx.coroutines.core)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}
