package akram.bensalem.powersh.ui.components

import akram.bensalem.powersh.LocalStrings
import akram.bensalem.powersh.R
import akram.bensalem.powersh.lyricist
import akram.bensalem.powersh.ui.theme.Dimens
import akram.bensalem.powersh.ui.theme.PowerSHTheme
import akram.bensalem.powersh.utils.localization.Locales
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SecurityUpdate
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AboutTopSection(
    modifier: Modifier = Modifier,
    appName: String,
    version: String,
    appLogo: Painter,
    onCheckUpdatesClicked: () -> Unit = { }
) {
    Column(
        modifier = modifier.animateContentSize(
            animationSpec = tween(
                300,
                easing = LinearOutSlowInEasing
            )
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            modifier = Modifier
                .size(100.dp)
                .padding(4.dp),
            painter = appLogo,
            contentDescription = LocalStrings.current.appLogo
        )

        Text(
            text = appName,
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.h5,
            modifier = Modifier
                .padding(4.dp)
        )

        Text(
            text = LocalStrings.current.versionIs(version),
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier
                .padding(
                    top = Dimens.MiniSmallPadding.size,
                    end = Dimens.MiniSmallPadding.size,
                    start = Dimens.MiniSmallPadding.size,
                    bottom = Dimens.SmallPadding.size
                )
        )

        Button(
            modifier = Modifier
                .fillMaxWidth(.6f)
                .padding(4.dp),
            onClick = onCheckUpdatesClicked,
            shape = CircleShape,
            elevation = ButtonDefaults
                .elevation(0.dp)
        ) {
            Icon(
            modifier = Modifier
                               .graphicsLayer {
                                            rotationY = if (lyricist.languageTag == Locales.AR) 180f else 0f
                                        },
                imageVector = Icons.Outlined.SecurityUpdate,
                contentDescription = null,
                tint = Color.White,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = LocalStrings.current.checkUpdate,
                color = Color.White,
                modifier = Modifier
                    .padding(
                        horizontal = 4.dp,
                        vertical = Dimens.SmallPadding.size
                    ),
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                ),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

    }
}

@Preview
@Composable
private fun AboutTopSectionPrev() {
    PowerSHTheme {
        AboutTopSection(
            appName = "PowerSH",
            version = "1.0.0",
            appLogo = painterResource(id = R.drawable.big_circle_powersh)
        )
    }
}
