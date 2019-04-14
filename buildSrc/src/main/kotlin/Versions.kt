/**
 * Find which updates are available by running
 *     `$ ./gradlew buildSrcVersions`
 * This will only update the comments.
 *
 * YOU are responsible for updating manually the dependency version. */
@Suppress("SpellCheckingInspection")
object Versions {
    const val android_arch_navigation: String = "2.0.0"

    const val appcompat: String = "1.0.2"

    const val core_testing: String = "2.0.0"

    const val constraintlayout: String = "2.0.0-alpha2"

    const val core_ktx: String = "1.0.1"

    const val androidx_databinding: String = "3.3.2"

    const val legacy_support_v4: String = "1.0.0"

    const val lifecycle_extensions: String = "2.0.0"

    const val androidx_test_espresso: String = "3.1.1"

    const val androidx_test_runner: String = "1.1.1"

    const val customtabs: String = "28.0.0"

    const val aapt2: String = "3.3.2-5309881"

    const val com_android_tools_build_gradle: String = "build 'whalesay'"

    const val lint_gradle: String = "26.3.2"

    const val com_chibatching_kotpref: String = "2.6.0"

    const val com_facebook_stetho: String = "1.5.1"

    const val timberkt: String = "1.5.1"

    const val eiffel: String = "master-SNAPSHOT" // available: "4.1.0"

    const val quickpermissions_kotlin: String = "0.4.1"

    const val ktlint: String = "0.31.0"

    const val oss_licenses_plugin: String = "0.9.4"

    const val play_services_oss_licenses: String = "16.0.2"

    const val material: String = "1.0.0"

    const val com_google_dagger: String = "2.16"

    const val retrofit2_kotlin_coroutines_adapter: String = "0.9.2"

    const val timber: String = "4.7.1"

    const val livedata_ktx: String = "3.0.0"

    const val lives: String = "1.2.1"

    const val leakcanary_android: String = "1.6.3"

    const val moshi: String = "1.8.0"

    const val com_squareup_okhttp3: String = "3.14.0"

    const val com_squareup_retrofit2: String = "2.5.0"

    const val assertk: String = "0.10"

    const val com_willowtreeapps_hyperion: String = "0.9.27"

    const val com_xwray: String = "2.3.0"

    const val de_fayard_buildsrcversions_gradle_plugin: String = "0.3.2"

    const val githook: String = "1.1.0"

    const val ktlint_gradle: String = "6.2.1"

    const val mockk: String = "1.9.2"

    const val joda_time: String = "2.10.1"

    const val junit: String = "4.12"

    const val dokka_android_gradle_plugin: String = "0.9.18"

    const val org_jetbrains_kotlin: String = "1.3.21"

    const val org_jetbrains_kotlinx: String = "1.1.1"

    const val robolectric: String = "4.2.1"

    const val se_ansman_kotshi: String = "1.0.6"

    /**
     *
     *   To update Gradle, edit the wrapper file at path:
     *      ./gradle/wrapper/gradle-wrapper.properties
     */
    object Gradle {
        const val runningVersion: String = "5.1.1"

        const val currentVersion: String = "5.3"

        const val nightlyVersion: String = "5.4-20190322000036+0000"

        const val releaseCandidate: String = ""
    }
}
