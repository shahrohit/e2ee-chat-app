package com.shahrohit.chat.presentation.register

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.shahrohit.chat.navigation.Screen
import com.shahrohit.chat.presentation.common.AppTextField

@Composable
fun RegisterBody(navController: NavController){
    // **************** State *****************
    val context = LocalContext.current
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email  by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // **************** Functions *****************
    fun onLogin(){
        if(email.isEmpty()){
            Toast.makeText(context, "Email is Empty", Toast.LENGTH_SHORT).show()
        } else if(password.isEmpty()){
            Toast.makeText(context, "Password is Required", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "Login As $email", Toast.LENGTH_SHORT).show()
            navController.navigate(Screen.Home.route){
                popUpTo(Screen.Login.route){ inclusive = true }
            }
        }
    }

    // ***************** UI ***********************
    Column {

        AppTextField(
            value = firstName,
            onChange = {firstName= it},
            modifier = Modifier.fillMaxWidth(),
            placeholder = "First Name",
            leadingIcon = {
                Icon(imageVector = Icons.Default.Person, contentDescription = null)
            },
            capitalize = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        AppTextField(
            value = lastName,
            onChange = {lastName= it},
            modifier = Modifier.fillMaxWidth(),
            placeholder = "Last Name",
            leadingIcon = {
                Icon(imageVector = Icons.Default.Person, contentDescription = null)
            },
            capitalize = true
        )
        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            value = email,
            onChange = {email = it},
            modifier = Modifier.fillMaxWidth(),
            placeholder = "Email Address",
            keyboardType = KeyboardType.Email,
            leadingIcon = {
                Icon(imageVector = Icons.Default.Email, contentDescription = null)
            },
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            value = username,
            onChange = {username = it},
            modifier = Modifier.fillMaxWidth(),
            placeholder = "Username",
            leadingIcon = {
                Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null)
            },
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            value = password,
            onChange = {password = it},
            placeholder = "Password",
            modifier = Modifier.fillMaxWidth(),
            keyboardType = KeyboardType.Password,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = icon, contentDescription = null)
                }
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription = null)
            },
            isLastField = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { onLogin() }, modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)) {
            Text(text = "Register")
        }
    }
}