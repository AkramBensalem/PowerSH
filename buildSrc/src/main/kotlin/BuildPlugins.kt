
object BuildPlugins {
    val android by lazy { "com.android.tools.build:gradle:${Versions.gradlePlugin}" }
    val kotlin by lazy { "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}" }
    val daggerHilt by lazy { "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}" }
    val ksp by lazy {"com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:${Versions.kspVersion}"}

}