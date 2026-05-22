package com.example.studentform.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun appFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = AppPrimary,
    unfocusedBorderColor = AppBorder,
    focusedTextColor = AppText,
    unfocusedTextColor = AppText,
    focusedContainerColor = AppSurface,
    unfocusedContainerColor = AppSurface
)

@Composable
fun appDateFieldColors() = OutlinedTextFieldDefaults.colors(
    disabledBorderColor = AppBorder,
    disabledTextColor = AppText,
    disabledPlaceholderColor = AppHint,
    disabledTrailingIconColor = AppBorder,
    disabledContainerColor = AppSurface
)

@Composable
fun appRadioColors() = RadioButtonDefaults.colors(
    selectedColor   = AppText,
    unselectedColor = AppBorder
)

@Composable
fun appSwitchColors() = SwitchDefaults.colors(
    checkedThumbColor   = AppBackground,
    checkedTrackColor   = AppText,
    uncheckedThumbColor = AppBorder,
    uncheckedTrackColor = AppGrey
)

@Composable
fun appButtonColors() = ButtonDefaults.buttonColors(
    containerColor = AppPrimary
)

val directionOptions = listOf("Android", "QA", "Web")
