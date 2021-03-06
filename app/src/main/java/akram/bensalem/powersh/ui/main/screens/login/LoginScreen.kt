package akram.bensalem.powersh.ui.main.screens.login

import akram.bensalem.powersh.LocalStrings
import akram.bensalem.powersh.ui.components.checkYourConectivityAlertDialog
import akram.bensalem.powersh.ui.components.CustomTextField
import akram.bensalem.powersh.utils.ErrorMessageOfPassword
import akram.bensalem.powersh.utils.authentification.Authenticate
import akram.bensalem.powersh.utils.isEmailValid
import akram.bensalem.powersh.utils.isOnline
import akram.bensalem.powersh.utils.isValidPasswordFormat
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Password
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import kotlinx.coroutines.launch



private enum class LoginModeState { FILL, EMPTY }



@ExperimentalMaterialApi
@ExperimentalAnimationApi
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    modifier: Modifier,
    bottomSheetScaffoldState: ModalBottomSheetState,
    authentication: MutableState<Authenticate>,
    onLogin: () -> Unit
) {


    val alphaAnimatedProgress = remember {
        Animatable(initialValue = 0f)
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


    val context = LocalContext.current

    val emailState = remember {
        mutableStateOf(TextFieldValue(""))
    }

    val passwordState = remember {
        mutableStateOf(TextFieldValue(""))
    }

    var isForgetButtonPressed by remember {
        mutableStateOf(false)
    }


    val view = LocalView.current

    val mEmailRequester = remember { FocusRequester() }

    val mPasswordFocusRequester = remember { FocusRequester() }
    val coroutineScope = rememberCoroutineScope()

    val isOnProgress = remember {
        mutableStateOf(false)
    }

    val isLogged = remember {
        mutableStateOf(false)
    }

    val isOnline = remember {
        mutableStateOf(true)
    }

    val localString = LocalStrings.current




    val transition = updateTransition(
        if (isValid(
                email = emailState.value.text,
                password = passwordState.value.text,
            )) LoginModeState.FILL else LoginModeState.EMPTY,
        label = ""
    )


    val backgroundColor by transition.animateColor { state ->
        when (state) {
            LoginModeState.FILL -> MaterialTheme.colors.primary
            LoginModeState.EMPTY -> MaterialTheme.colors.surface
        }
    }

    val contentColor by transition.animateColor { state ->
        when (state) {
            LoginModeState.FILL -> MaterialTheme.colors.primary
            LoginModeState.EMPTY -> LocalContentColor.current
        }
    }



    LaunchedEffect(isForgetButtonPressed) {
        if (isForgetButtonPressed) {
            coroutineScope.launch {
                if (!bottomSheetScaffoldState.isVisible) {
                    bottomSheetScaffoldState.show()
                } else {
                    bottomSheetScaffoldState.hide()
                }

                isForgetButtonPressed = false
            }
        }
    }


    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {

        val (email, password, forget, button, progress) = createRefs()


        CustomTextField(
            modifier = Modifier
                .constrainAs(email) {
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                }
                .padding(0.dp, 8.dp)
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            textFieldModifier = Modifier
                .border(1.dp, MaterialTheme.colors.onBackground, RoundedCornerShape(12.dp))
                .background(color = MaterialTheme.colors.surface, RoundedCornerShape(12.dp))
                .padding(12.dp),
            title = LocalStrings.current.email,
            fieldState = emailState,
            icon = Icons.Outlined.Email,
            insideTextColor = MaterialTheme.colors.onBackground,
            iconTint = MaterialTheme.colors.onBackground,
            isPassword = false,
            focusRequester = mEmailRequester,
            autofillType = AutofillType.EmailAddress,
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next,
            isValid = isEmailValid(email = emailState.value.text),
            errorMessage = LocalStrings.current.emailIsNotValid,
                    onNext = {
                mPasswordFocusRequester.requestFocus()
            },
            onDone = {
                view.clearFocus()
            },
        )

        CustomTextField(
            modifier = Modifier
                .constrainAs(password) {
                    top.linkTo(email.bottom, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                }
                .padding(0.dp, 8.dp)
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            textFieldModifier = Modifier
                .border(1.dp, MaterialTheme.colors.onBackground, RoundedCornerShape(12.dp))
                .background(color = MaterialTheme.colors.surface, RoundedCornerShape(12.dp))
                .padding(12.dp),
            title = LocalStrings.current.password,
            fieldState = passwordState,
            icon = Icons.Outlined.Password,
            insideTextColor = MaterialTheme.colors.onBackground,
            iconTint = MaterialTheme.colors.onBackground,
            isPassword = true,
            focusRequester = mPasswordFocusRequester,
            autofillType = AutofillType.Password,
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
            isValid = isValidPasswordFormat(password = passwordState.value.text),
            errorMessage = ErrorMessageOfPassword(
                password = passwordState.value.text,
                localString = LocalStrings.current
            ),
            onNext = {
                view.clearFocus()
            },
            onDone = {
                view.clearFocus()
            }
        )


        ClickableText(
            modifier = alphaAnimatedModifier
                .constrainAs(forget) {
                    top.linkTo(password.bottom, margin = 16.dp)
                    bottom.linkTo(button.top, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                }
                .padding(top = 48.dp, bottom = 8.dp)

                .padding(4.dp),
            text = AnnotatedString(LocalStrings.current.forgetPassword),
            onClick = {
                isForgetButtonPressed = true
            },
            style = TextStyle(
                color = if (isForgetButtonPressed) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface,

                ),
        )



        Button(

            enabled = isValid(
                email = emailState.value.text,
                password = passwordState.value.text,
            ),

            colors = ButtonDefaults.buttonColors(
                backgroundColor = backgroundColor,
                contentColor = contentColor,
                disabledBackgroundColor = backgroundColor,
                disabledContentColor = contentColor
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = alphaAnimatedModifier
                .constrainAs(button) {
                    top.linkTo(forget.bottom, margin = 16.dp)
                    bottom.linkTo(parent.bottom, margin = 32.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                }
                .background(color = MaterialTheme.colors.primary, shape = CircleShape),
            onClick = {
                isOnline.value = isOnline(context = context)

                if (isOnline.value) {
                    authentication.value.signIn(
                        emailState.value.text,
                        passwordState.value.text,
                        isOnProgress,
                        isLogged,
                        localString
                    )
                }


            }) {
            Text(
                text = LocalStrings.current.login,
                textAlign = TextAlign.Center,
                color = if(
                    isValid(
                        email = emailState.value.text,
                        password = passwordState.value.text,
                    )
                ) Color.White else MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 4.dp,
                    bottom = 4.dp
                )
            )
        }


        if (isOnProgress.value) CircularProgressIndicator(
            modifier = Modifier.constrainAs(progress) {
                top.linkTo(parent.top, margin = 16.dp)
                bottom.linkTo(parent.bottom, margin = 16.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)

            })

        if (isLogged.value) {
            onLogin()
        }

        checkYourConectivityAlertDialog(isOnline)


    }

}



private fun isValid(
    email : String,
    password :String,
): Boolean{
    return (
            email.isNotEmpty()
            && password.isNotEmpty()
            && isEmailValid(email)
            && isValidPasswordFormat(password))
}