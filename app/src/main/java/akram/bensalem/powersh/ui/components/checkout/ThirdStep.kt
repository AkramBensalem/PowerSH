package akram.bensalem.powersh.ui.components.checkout

import akram.bensalem.powersh.LocalStrings
import akram.bensalem.powersh.data.model.CardItem
import akram.bensalem.powersh.data.model.OrderItem
import akram.bensalem.powersh.ui.components.ConfirmAlertDialog
import akram.bensalem.powersh.ui.components.finalCartItem
import akram.bensalem.powersh.ui.theme.CardCoverPink
import akram.bensalem.powersh.ui.theme.Dimens
import akram.bensalem.powersh.utils.getCurrentDate
import androidx.compose.animation.*
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.navigationBarsPadding

@ExperimentalMaterialApi
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ThirdStep(
    visible: Boolean,
    openDialog: MutableState<Boolean>,
    cartProduct: MutableList<CardItem>,
    totalPrice: MutableState<Int>,
    selected: MutableState<String>,
    fullAddressState: MutableState<String>,
    phoneState: MutableState<TextFieldValue>,
    lastNameState: MutableState<TextFieldValue>,
    firstNameState: MutableState<TextFieldValue>,
    date: String = getCurrentDate(LocalStrings.current),
    onConfirmClicked: (OrderItem) -> Unit,
) {


    val orderItem = OrderItem(
        id = 22154854,
        date = date,
        total = totalPrice.value,
        Address = fullAddressState.value,
        payment = selected.value,
        productList = cartProduct.toList(),
    )



    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideInHorizontally(),
        exit = fadeOut() + slideOutHorizontally(
            targetOffsetX = { it },
            animationSpec = spring()
        ),
        modifier = Modifier
            .fillMaxSize()
    )
    {



        Card(
            backgroundColor = MaterialTheme.colors.background,
            elevation = 2.dp,
            shape = RoundedCornerShape(12.dp),
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 48.dp, start = 16.dp, end = 16.dp)
                .navigationBarsPadding()
        ) {

            Column(
                Modifier
                    .fillMaxSize()
                    .background(
                        color = CardCoverPink.copy(alpha = 0.23f),
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {}

            LazyColumn(
                modifier = Modifier.padding(
                    start = Dimens.SmallPadding.size,
                    end = Dimens.SmallPadding.size
                ),
                contentPadding = PaddingValues(
                    top = 2.dp,
                    bottom = 16.dp
                )
            ) {

                item {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 16.dp, end = 16.dp)
                    ) {


                        Text(
                            text = LocalStrings.current.yourBilling,
                            color = MaterialTheme.colors.onBackground,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp, bottom = 16.dp)
                        )

                        factureText(
                            title = LocalStrings.current.id,
                            detail = "22154854"
                        )
                        factureText(
                            title = LocalStrings.current.to,
                            detail = "${firstNameState.value.text} ${lastNameState.value.text}"
                        )
                        factureText(
                            title = LocalStrings.current.phoneNumber,
                            detail = phoneState.value.text
                        )
                        factureText(
                            title = LocalStrings.current.shippingAddress,
                            detail = fullAddressState.value
                        )
                        factureText(
                            title = LocalStrings.current.paymentMethod,
                            detail = if (selected.value == MainPayOptions.CCP_OPTION) LocalStrings.current.ccp else LocalStrings.current.cashOnDelivery
                        )
                        factureText(
                            title =LocalStrings.current.date,
                            detail = LocalStrings.current.at(date)
                        )

                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        )

                    }
                }
                item {
                    Text(
                        text = LocalStrings.current.yourPurchases,
                        color = MaterialTheme.colors.onBackground,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .padding(start = 16.dp, top = 4.dp, bottom = 8.dp)
                    )

                }
                itemsIndexed(cartProduct) { _, row ->
                    finalCartItem(product = row)
                    Spacer(modifier = Modifier.padding(Dimens.SmallPadding.size))
                }
                item{
                    Column(Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.weight(1f))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp)
                        ) {
                            Text(
                                text = LocalStrings.current.totalAmount,
                                color = MaterialTheme.colors.onBackground,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(top = 4.dp, bottom = 0.dp)
                            )

                            Text(
                                text = LocalStrings.current.totalPriceValue(totalPrice.value),
                                color = MaterialTheme.colors.primary,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(start = 4.dp, top = 4.dp, bottom = 0.dp)
                            )
                        }
                    }
                }

            }

        }

    }

    ConfirmAlertDialog(
        openDialog = openDialog,
        orderItem = orderItem,
        onConfirmClicked = onConfirmClicked
    )
}