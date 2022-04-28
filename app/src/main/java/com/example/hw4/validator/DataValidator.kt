package com.example.hw4.validator

import java.util.regex.Pattern

class DataValidator : Validator {
    override val pattern: Pattern
        get() = Companion.pattern

    companion object {
        private val pattern: Pattern by lazy { Pattern.compile("^(0[1-9]|[12][0-9]|3[01])[.](0[1-9]|1[012])[.](19|20)\\d\\d$") }
    }

}