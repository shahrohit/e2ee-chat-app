package com.shahrohit.chat.presentation.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.shahrohit.chat.enums.AuthStatus
import com.shahrohit.chat.enums.OtpFor
import com.shahrohit.chat.navigation.Screen
import com.shahrohit.chat.presentation.common.AppTextField
import com.shahrohit.chat.presentation.common.AppTextFieldError
import com.shahrohit.chat.presentation.common.ProgressButton
import com.shahrohit.chat.presentation.common.showToast
import com.shahrohit.chat.remote.dto.User
import com.shahrohit.chat.utils.ApiRequestState
import com.shahrohit.chat.viewmodels.LoginViewModel

@Composable
fun LoginBody(navController: NavController, viewModel: LoginViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val loginState by viewModel.loginState

    fun handleSuccess(status : String, user : User){
        if(status == AuthStatus.USER_VERIFIED.name){
            navController.navigate(Screen.Connection.route){
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }
        } else if(status == AuthStatus.USER_VERIFICATION_PENDING.name){
            navController.navigate(Screen.VerifyOtp.createRoute(user.email,user.username,OtpFor.USER.name))
        } else if(status == AuthStatus.DEVICE_VERIFICATION_PENDING.name){
            navController.navigate(Screen.VerifyOtp.createRoute(user.email,user.username,OtpFor.DEVICE.name))
        }
    }

    fun handleError(message : String) = showToast(context, message)

    LaunchedEffect(loginState) {
        when (val currentState = loginState) {
            is ApiRequestState.Success -> handleSuccess(currentState.data.status, currentState.data.user)
            is ApiRequestState.Error -> handleError(currentState.message)
            else -> {}
        }
    }

    Column {
        AppTextField(
            value = viewModel.identifier,
            onChange = viewModel::onIdentifierChanged,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "Username or Email Address",
            keyboardType = KeyboardType.Email,
            leadingIcon = { Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null) },
        )
        viewModel.identifierError?.let { AppTextFieldError(text = it) }

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            value = viewModel.password,
            onChange = viewModel::onPasswordChanged,
            placeholder = "Password",
            modifier = Modifier.fillMaxWidth(),
            keyboardType = KeyboardType.Password,
            visualTransformation = if (viewModel.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
            trailingIcon = {
                val icon = if (viewModel.passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = viewModel::changePasswordVisibility) {
                    Icon(imageVector = icon, contentDescription = null)
                }
            },
            isLastField = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        ProgressButton(
            text = "Login",
            onClick = {viewModel.login(context)},
            modifier = Modifier.fillMaxWidth().height(50.dp),
            loading = loginState == ApiRequestState.Loading
        )
    }
}