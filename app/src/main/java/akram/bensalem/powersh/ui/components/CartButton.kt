package akram.bensalem.powersh.ui.components

import akram.bensalem.powersh.LocalStrings
import akram.bensalem.powersh.lyricist
import akram.bensalem.powersh.ui.theme.Amber500
import akram.bensalem.powersh.ui.theme.PowerSHTheme
import akram.bensalem.powersh.utils.localization.Locales
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CartButton(
    quantity: Int,
    price: Double,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        color = Amber500,
        contentColor = Color.DarkGray,
        elevation = 16.dp
    ) {
        Row(
            modifier = Modifier
                .clickable(onClick = onClick)
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text =
                        LocalStrings.current.totalShoesValue(quantity),
                    color = Color.DarkGray,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.button
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = LocalStrings.current.totalPriceValueString("%.2f".format(price)) ,
                    color = Color.DarkGray,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.button
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = LocalStrings.current.viewCart,
                color = Color.DarkGray,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.button
            )

            Icon(
            modifier = Modifier
                                        .graphicsLayer {
                                            rotationY = if (lyricist.languageTag == Locales.AR) 180f else 0f
                                        },
            imageVector = Icons.Rounded.ChevronRight, contentDescription = "Icon")
        }
    }
}

@Preview("MenuCartButton")
@Composable
private fun MenuCartButtonPreview() {
    PowerSHTheme {
        CartButton(
            quantity = 3,
            price = 0.0,
            onClick = {}
        )
    }
}

