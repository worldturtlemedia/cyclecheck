/**
 * Find which updates are available by running
 *     `$ ./gradlew buildSrcVersions`
 * This will only update the comments.
 *
 * YOU are responsible for updating manually the dependency version. */
object Versions {
    const val appcompat: String = "1.0.2"

    const val core_testing: String = "2.0.1"

    const val constraintlayout: String = "2.0.0-alpha2"

    const val core_ktx: String = "1.0.1"

    const val androidx_databinding: String = "3.3.2"

    const val legacy_support_v4: String = "1.0.0"

    const val lifecycle_extensions: String = "2.0.0"

    const val androidx_navigation: String = "2.0.0"

    const val androidx_test_espresso: String = "3.1.1"

    const val androidx_test_runner: String = "1.1.1"

    const val customtabs: String = "28.0.0"

    const val com_android_tools_build_gradle: String = "3.3.2"

    const val lint_gradle: String = "26.3.2"

    const val com_chibatching_kotpref: String = "2.7.0"

    const val crashlytics: String = "2.9.9"

    const val com_facebook_stetho: String = "1.5.1"

    const val timberkt: String = "1.5.1"

    const val eiffel: String = "master-SNAPSHOT" // available: "4.1.0"

    const val quickpermissions_kotlin: String = "0.4.1"

    const val ktlint: String = "0.31.0"

    const val oss_licenses_plugin: String = "0.9.4"

    const val material: String = "1.0.0"

    const val com_google_dagger: String = "2.22.1"

    const val firebase_core: String = "16.0.8"

    const val google_services: String = "4.2.0"

    const val retrofit2_kotlin_coroutines_adapter: String = "0.9.2"

    const val timber: String = "4.7.1"

    const val livedata_ktx: String = "3.0.0"

    const val lives: String = "1.2.1"

    const val leakcanary_android: String = "1.6.3"

    const val moshi: String = "1.8.0"

    const val com_squareup_okhttp3: String = "3.14.1"

    const val com_squareup_retrofit2: String = "2.5.0"

    const val com_star_zero_gradle_githook_gradle_plugin: String = "1.1.0"

    const val assertk: String = "0.10"

    const val com_willowtreeapps_hyperion: String = "0.9.27"

    const val com_xwray: String = "2.3.0"

    const val de_fayard_buildsrcversions_gradle_plugin: String = "0.3.2"

    const val mockk: String = "1.9.3"

    const val joda_time: String = "2.10.1"

    const val junit: String = "4.12"

    const val org_jetbrains_dokka_android_gradle_plugin: String = "0.9.18"

    const val org_jetbrains_kotlin: String = "1.3.30"

    const val org_jetbrains_kotlinx: String = "1.2.0"

    const val org_jlleitschuh_gradle_ktlint_gradle_plugin: String = "7.3.0"

    const val robolectric: String = "4.2.1"

    const val se_ansman_kotshi: String = "1.0.6"

    /**
     *
     *   To update Gradle, edit the wrapper file at path:
     *      ./gradle/wrapper/gradle-wrapper.properties
     */
    object Gradle {
        const val runningVersion: String = "5.1.1"

        const val currentVersion: String = "5.3.1"

        const val nightlyVersion: String = "5.5-20190414000043+0000"

        const val releaseCandidate: String = "5.4-rc-1"
    }
}
