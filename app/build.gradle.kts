plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.thuc_hanh_di_dong"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.thuc_hanh_di_dong"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    lint {
        disable += setOf(
            "HardcodedText",
            "SetTextI18n",
            "Autofill",
            "TextFields",
            "ButtonStyle",
            "ButtonOrder",
            "Overdraw",
            "RtlHardcoded",
            "UnusedResources",
            "UseCompoundDrawables",
            "GradleDependency"
        )
    }
}

dependencies {
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}