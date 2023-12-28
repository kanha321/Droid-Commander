package com.kanha.devicecontrol.models

data class PowerControlItem(
    val name: String,
    val icon: Int,
    val onClick: () -> Unit
)
