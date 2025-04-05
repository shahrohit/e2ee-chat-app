package com.shahrohit.chat.utils

import android.util.Patterns

object FormValidator {

    fun validateName(username: String): String? =
        when {
            username.trim().isBlank() -> "Name is Required"
            username.length < 2 -> "Name must be at least 3 characters long"
            else -> null
        }

    fun validateUsername(username: String): String? =
        when {
            username.isBlank() -> "Username is Required"
            !Regex("^[a-zA-Z][a-zA-Z0-9_]{2,}$").matches(username) ->
                "Invalid username (letters, numbers, underscores; must start with a letter)"
            else -> null
        }

    fun validateEmail(email: String): String? =
        when {
            email.isBlank() -> "Email is Required"
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                "Invalid email format"
            else -> null
        }

    fun validatePassword(password: String): String? =
        when {
            password.isBlank() -> "Password is Required"
            password.length < 6 -> "Password must be at least 6 characters long"
            else -> null
        }

    fun validateIdentifier(identifier: String): String?{
        return when {
            identifier.isBlank() -> "Email or username is Required"
            else -> null
        }

    }
}