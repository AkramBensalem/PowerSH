package akram.bensalem.powersh.ui.main.screens

import akram.bensalem.powersh.LocalStrings
import akram.bensalem.powersh.R
import akram.bensalem.powersh.data.model.CardItem
import akram.bensalem.powersh.lyricist
import akram.bensalem.powersh.ui.components.cartListProducts
import akram.bensalem.powersh.ui.theme.PowerSHTheme
import akram.bensalem.powersh.utils.Constants
import akram.bensalem.powersh.utils.localization.Locales
import android.content.res.Configuration
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.navigationBarsPadding


private enum class CartModeState { FILL, EMPTY }


@OptIn(ExperimentalAnimationApi::class)
@ExperimentalMaterialApi
@Composable
fun cartScreen(
    navController: NavController,
    cartProduct: MutableList<CardItem>,
) {


    //Scale animation
    val animatedProgress = remember {
        androidx.compose.animation.core.Animatable(initialValue = 0.7f)
    }
    LaunchedEffect(key1 = Unit) {
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(300, easing = FastOutSlowInEasing)
        )
    }

    val animatedModifier = Modifier
        .graphicsLayer(
            scaleX = animatedProgress.value,
            scaleY = animatedProgress.value
        )



    val alphaAnimatedProgress = remember {
        androidx.compose.animation.core.Animatable(initialValue = 0f)
    }
    LaunchedEffect(key1 = Unit) {
        alphaAnimatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(300, easing = FastOutSlowInEasing)
        )
    }


    val alphaAnimatedModifier = Modifier
        .graphicsLayer(
            alpha = alphaAnimatedProgress.value,
        )


    val totalPrice = remember {
        mutableStateOf(0)
    }


    totalPrice.value = 0

    for (item in cartProduct) {
        totalPrice.value += item.price * item.quantity
    }


    val transition = updateTransition(
        if (totalPrice.value > 0) CartModeState.FILL else CartModeState.EMPTY,
        label = ""
    )


    val backgroundColor by transition.animateColor { state ->
        when (state) {
            CartModeState.FILL -> MaterialTheme.colors.primary
            CartModeState.EMPTY -> MaterialTheme.colors.surface
        }
    }

    val contentColor by transition.animateColor { state ->
        when (state) {
            CartModeState.FILL -> MaterialTheme.colors.primary
            CartModeState.EMPTY -> LocalContentColor.current
        }
    }



    Column(
        modifier = animatedModifier
            .fillMaxSize()
            .padding(top = 16.dp, bottom = 0.dp, start = 16.dp, end = 16.dp)
            .navigationBarsPadding()
    ) {

        if (
            cartProduct.size != 0
        ) {
            cartListProducts(
                cartProduct = cartProduct
            ) {
                cartProduct.removeAt(it)
            }
        } else {
            Spacer(modifier = Modifier.weight(1f))
        }



        AnimatedVisibility(
            visible = cartProduct.size == 0,
            enter = fadeIn(initialAlpha = 0f, tween(200)),
            exit = fadeOut(targetAlpha = 0f, tween(200)),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .align(Alignment.CenterHorizontally)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
            ) {


                Image(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(start = 32.dp, end = 32.dp)
                        .graphicsLayer {
                            rotationY = if (lyricist.languageTag == Locales.AR) 180f else 0f
                        },
                    painter = painterResource(id = R.drawable.ic_empty_cart),
                    contentDescription = LocalStrings.current.addToCart,
                    alpha = 0.90F,
                    )

                Text(
                    color = MaterialTheme.colors.onSurface,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    text = LocalStrings.current.cartIsEmpty,
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .align(Alignment.CenterHorizontally)
                )



            }


        }


        Spacer(modifier = Modifier.weight(1f))


        Row(
            modifier = alphaAnimatedModifier
                .fillMaxWidth()
                .padding(start = 0.dp, end = 0.dp, bottom = 8.dp, top = 16.dp)
        ) {
            Column(
                modifier = Modifier
            ) {
                Text(
                    color = MaterialTheme.colors.onBackground,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start,
                    text = LocalStrings.current.totalPrice,
                    modifier = Modifier
                        .align(Alignment.Start)
                )

                Text(
                    color = MaterialTheme.colors.primary,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    fontSize = 18.sp,
                    text = LocalStrings.current.totalPriceValue(totalPrice.value),
                    modifier = Modifier
                        .align(Alignment.Start)
                )

            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                enabled = totalPrice.value > 0,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = backgroundColor,
                    contentColor = contentColor,
                    disabledBackgroundColor = backgroundColor,
                    disabledContentColor = contentColor

                ),
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                onClick = {
                    navController.navigate(PowerSHScreens.CheckoutScreen.name)
                }) {
                Text(
                    text = if (totalPrice.value > 0)LocalStrings.current.checkout else LocalStrings.current.emptyCart,
                    color = if (totalPrice.value > 0) Color.White else MaterialTheme.colors.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(
                        start = 24.dp,
                        end = 24.dp,
                        top = 6.dp,
                        bottom = 6.dp
                    )
                )
            }


        }

    }

}


@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun emptyCartPreview() {

    val navController = rememberNavController()
    val cartProduct = remember { Constants.cartListEmpty }
    PowerSHTheme {
        cartScreen(
            navController = navController,
            cartProduct = cartProduct,
        )
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun fullCartPreview() {

    val navController = rememberNavController()
    val cartProduct = remember { Constants.cartList }
    PowerSHTheme {
        cartScreen(
            navController = navController,
            cartProduct = cartProduct,
        )
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun fullCartNightPreview() {

    val navController = rememberNavController()
    val cartProduct = remember { Constants.cartList }
    PowerSHTheme {
        cartScreen(
            navController = navController,
            cartProduct = cartProduct,
        )
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun EmptyCartNightPreview() {

    val navController = rememberNavController()
    val cartProduct = remember { Constants.cartListEmpty }
    PowerSHTheme {
        cartScreen(
            navController = navController,
            cartProduct = cartProduct,
        )
    }
}
