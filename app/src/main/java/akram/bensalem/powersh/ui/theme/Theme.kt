package akram.bensalem.powersh.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.SettingsSuggest
import androidx.compose.material.lightColors
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@SuppressLint("ConflictingOnColor")
/*private val DarkColorPalette = darkColors(
    primaryVariant = Color.White,
    secondary = YellowDefault,
    onSecondary = YellowDefaultDarkerOn,
    secondaryVariant = Color.Black,
)*/

private val DarkColorPalette = darkColors(
    primary = PowerSHRed,
    onPrimary = YellowDefaultDarkerOn,
    primaryVariant = Color.Black,
    secondary = Color.Black,
    onSecondary = Color.Black,
    secondaryVariant = Color.Black,
    background = Color.Black,
    onBackground = LightGrayBackground,
    surface = LightDark,
    onSurface = LightGrayTextDark
)



private val LightColorPalette = lightColors(
    primary = PowerSHRed,
    onPrimary = YellowDefaultDarkerOn,
    primaryVariant = Color.Black,
    secondary = YellowDefault,
    onSecondary = YellowDefaultDarkerOn,
    secondaryVariant = Color.White,
    background = LightGrayBackground,
    onBackground = LightDark,
    onSurface = LightGrayText
)




@Composable
fun PowerSHTheme(
    theme: Int = 0,
    content: @Composable () -> Unit
) {
    val autColors = if (isSystemInDarkTheme()) DarkColorPalette else LightColorPalette

    val colors = when (theme) {
        1 -> LightColorPalette
        2 -> DarkColorPalette
        else -> autColors
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes
    ) {
        CompositionLocalProvider(
            LocalRippleTheme provides PowerSHRippleTheme,
            content = content
        )
    }
}


private object PowerSHRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor(): Color = MaterialTheme.colors.primary

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleTheme.defaultRippleAlpha(
        Color.Black,
        lightTheme =!isSystemInDarkTheme()
    )

}

enum class Theme(
    val themeName: String,
    val icon: ImageVector,
    val themeValue: Int
) {
    FOLLOW_SYSTEM(
        themeName = "Follow System Settings",
        icon = Icons.Outlined.SettingsSuggest,
        themeValue = -1
    ),
    LIGHT_THEME(
        themeName = "Light Theme",
        icon = Icons.Outlined.LightMode,
        themeValue = 1
    ),
    DARK_THEME(
        themeName = "Dark Theme",
        icon = Icons.Outlined.DarkMode,
        themeValue = 2
    );
}
