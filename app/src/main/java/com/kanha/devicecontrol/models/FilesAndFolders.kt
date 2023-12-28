package com.kanha.devicecontrol.models

data class FilesAndFolders(
    // store all the data given by shell command ls -l
    val type: String,
    val permissions: String,
    val hardLinks: String,
    val owner: String,
    val group: String,
    val size: String,
    val date: String,
    val time: String,
    val name: String,
)
