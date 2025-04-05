package com.shahrohit.chat.presentation.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.shahrohit.chat.presentation.common.AppTextField
import com.shahrohit.chat.presentation.common.AppTextFieldError
import androidx.hilt.navigation.compose.hiltViewModel
import com.shahrohit.chat.presentation.common.ProgressButton
import com.shahrohit.chat.presentation.common.showToast
import com.shahrohit.chat.utils.ApiRequestState
import com.shahrohit.chat.viewmodels.RegisterViewModel

@Composable
fun RegisterBody(navController: NavController, viewModel: RegisterViewModel = hiltViewModel()){
    val context = LocalContext.current
    val registerState by viewModel.registerState

    LaunchedEffect(registerState) {
        when (val currentState = registerState) {
            is ApiRequestState.Success -> showToast(context, currentState.data)
            is ApiRequestState.Error -> showToast(context, currentState.message)
            else -> {}
        }
    }

    Column {
        AppTextField(
            value = viewModel.name,
            onChange = viewModel::onNameChanged,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "Full Name",
            leadingIcon = {Icon(imageVector = Icons.Default.Person, contentDescription = null)},
            keyboardCapitalization = KeyboardCapitalization.Words
        )
        viewModel.nameError?.let { AppTextFieldError(text = it) }
        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            value = viewModel.email,
            onChange = viewModel::onEmailChanged,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "Email Address",
            keyboardType = KeyboardType.Email,
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) },
        )
        viewModel.emailError?.let { AppTextFieldError(text = it) }
        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            value = viewModel.username,
            onChange = viewModel::onUsernameChanged,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "Username",
            leadingIcon = { Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null) },
            trailingIcon = {
                when {
                    viewModel.isCheckingUsername -> CircularProgressIndicator(Modifier.size(16.dp), strokeWidth = 2.dp)
                    viewModel.isUsernameAvailable == true -> Icon(Icons.Default.Check, contentDescription = null, tint = Color.Green)
                    viewModel.isUsernameAvailable == false -> Icon(Icons.Default.Close, contentDescription = null, tint = Color.Red)
                }
            },
        )
        viewModel.usernameError?.let { AppTextFieldError(text = it) }

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            value = viewModel.password,
            onChange = viewModel::onPasswordChanged,
            placeholder = "Password",
            modifier = Modifier.fillMaxWidth(),
            keyboardType = KeyboardType.Password,
            visualTransformation = if (viewModel.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
            trailingIcon = {
                val icon = if (viewModel.isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = viewModel::changePasswordVisibility) {
                    Icon(imageVector = icon, contentDescription = null)
                }
            },
            isLastField = true
        )
        viewModel.passwordError?.let { AppTextFieldError(text = it) }

        Spacer(modifier = Modifier.height(16.dp))

        ProgressButton(
            text = "Register",
            onClick = viewModel::register,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            loading = registerState == ApiRequestState.Loading
        )
    }
}