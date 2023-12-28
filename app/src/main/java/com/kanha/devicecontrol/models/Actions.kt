package com.kanha.devicecontrol.models

data class Actions(
    val name: String,
    val description: String,
    val icon: Int,
    val action: () -> Unit
)