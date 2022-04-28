package com.example.hw4.validator

import java.util.regex.Pattern

interface Validator {
    val pattern: Pattern

    fun isValid(string: String): Boolean {
        return pattern.matcher(string).matches()
    }
}