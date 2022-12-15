package com.binar.gosky.data.network.model.users


import com.google.gson.annotations.SerializedName

data class EditEmailUserRequestBody(
    @SerializedName("otp")
    val otp: String? = null,
    @SerializedName("otpToken")
    val otpToken: String? = null
)