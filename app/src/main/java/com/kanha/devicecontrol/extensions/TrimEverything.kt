package com.kanha.devicecontrol.extensions

fun String.trimEverythingExceptPlusAndNumbers(): String {
    return this.replace("[^+0-9]".toRegex(), "")
}