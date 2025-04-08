package com.shahrohit.chat.presentation.verifyotp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.shahrohit.chat.enums.OtpFor
import com.shahrohit.chat.navigation.Screen
import com.shahrohit.chat.presentation.common.AppTextField
import com.shahrohit.chat.presentation.common.showToast
import com.shahrohit.chat.utils.ApiRequestState
import com.shahrohit.chat.viewmodels.VerifyOtpViewModel

@Composable
fun VerifyOtpBody(navController: NavController, email: String, username: String, otpFor : OtpFor, viewModel: VerifyOtpViewModel = hiltViewModel()) {

    val context = LocalContext.current;

    val verifyOtpState by viewModel.verifyOtpState
    val otpDigits = viewModel.otpDigits
    val focusRequester = remember { List(6) {FocusRequester()} }
    val localFocusManager = LocalFocusManager.current

    fun handleSuccess(message : String){
        navController.navigate(Screen.Connection.route){
            popUpTo(0) { inclusive = true }
            launchSingleTop = true
        }
        showToast(context, message)
    }

    fun handleError(message : String){
        showToast(context, message)
    }

    LaunchedEffect(verifyOtpState) {
        when (val currentState = verifyOtpState) {
            is ApiRequestState.Success -> handleSuccess(currentState.data.message)
            is ApiRequestState.Error -> handleError(currentState.message)
            else -> {}
        }
    }

    fun onBackPressed(event: KeyEvent, digit: String, index : Int) : Boolean{
        if (event.type == KeyEventType.KeyDown && event.key == Key.Backspace) {
            if (digit.isEmpty() && index > 0) {
                viewModel.onOtpDigitChanged(index - 1, "")
                focusRequester[index - 1].requestFocus()
                return true
            } else {
                return false
            }
        } else {
            return false
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Text(text = "Your Email: $email", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            otpDigits.forEachIndexed { index, digit ->
                AppTextField(
                    value = digit,
                    onChange = {
                        val newDigit = if(it.isBlank()) "" else it.last().toString()
                        if(newDigit.isDigitsOnly()){
                            viewModel.onOtpDigitChanged(index, newDigit)
                            if(newDigit.isNotEmpty() && index < 5){
                                focusRequester[index + 1].requestFocus()
                            }
                        }

                        if(index == 5){
                            localFocusManager.clearFocus()
                            viewModel.verifyOtp(context,username, otpFor);
                        }
                    },
                    modifier = Modifier.width(48.dp).height(56.dp)
                        .focusRequester(focusRequester[index])
                        .onKeyEvent { onBackPressed(it, digit, index) },
                    textAlign = TextAlign.Center,
                    isLastField = index == 5,
                    keyboardType = KeyboardType.Number,
                    keyboardActions = KeyboardActions(
                        onNext = {
                            if(index < 5) focusRequester[index + 1].requestFocus()
                        },
                        onDone = {
                            localFocusManager.clearFocus()
                            viewModel.verifyOtp(context, username, otpFor)
                        },
                    ),
                )
            }
        }
    }
}