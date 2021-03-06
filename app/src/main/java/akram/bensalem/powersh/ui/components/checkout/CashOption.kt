package akram.bensalem.powersh.ui.components.checkout

import akram.bensalem.powersh.LocalStrings
import akram.bensalem.powersh.R
import akram.bensalem.powersh.ui.theme.CardCoverPink
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CashOption(
    visible: Boolean,
    price: Int,
    modifier: Modifier,
    onCallUsClicked: () -> Unit = {},
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideInHorizontally(),
        exit = fadeOut(),
        modifier = Modifier.fillMaxSize()
    )
    {
            Card(
                backgroundColor = MaterialTheme.colors.background,
                elevation = 2.dp,
                shape = RoundedCornerShape(12.dp),
                modifier =
                modifier
                    .fillMaxSize()
                    .padding(top = 8.dp, bottom = 48.dp, start = 16.dp, end = 16.dp)
            ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(
                            color = CardCoverPink.copy(alpha = 0.23f),
                            shape= RoundedCornerShape(12.dp),
                        )
                        .verticalScroll(state = rememberScrollState())

                ) {}

                Column(
                    Modifier
                        .fillMaxSize()
                ) {


                    Image(
                        painter = painterResource(id = R.drawable.ic_wallet),
                        contentDescription = LocalStrings.current.cashOnDelivery,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(120.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    Text(
                        text = LocalStrings.current.cashOnDelivery,
                        color = MaterialTheme.colors.onBackground,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp)
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    Text(
                        text = LocalStrings.current.cashDeliveryWillArriveWithin,
                        color = MaterialTheme.colors.onBackground,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
                    )

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )

                    Text(
                        text = LocalStrings.current.totalAmountYouWillPay(price) ,
                        color = MaterialTheme.colors.onBackground,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(2f)
                    )

                    Button(
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.primary,
                            contentColor = Color.White,
                            disabledBackgroundColor = CardCoverPink,
                            disabledContentColor = CardCoverPink,
                        ),
                        modifier = Modifier
                            .background(color = CardCoverPink, shape = RoundedCornerShape(14.dp))
                            .align(Alignment.CenterHorizontally),
                        onClick = {
                            onCallUsClicked()
                        }) {
                        Text(
                            text = LocalStrings.current.callUs,
                            fontSize = 11.sp,
                            color = Color.White,
                            style = TextStyle(
                                background = MaterialTheme.colors.primary,
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(
                                start = 12.dp,
                                end = 12.dp,
                                top = 0.dp,
                                bottom = 0.dp
                            )
                        )
                    }


                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(2f)
                    )

                    Text(
                        text = LocalStrings.current.dearCustomerVerifyYourInfo,
                        color = MaterialTheme.colors.onBackground,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )

                }
            }
    }
}
