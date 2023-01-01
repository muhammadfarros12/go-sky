package com.binar.gosky.data.network.service

object ApiEndPoints {
    // Tickets
    const val GET_TICKETS_ENDPOINT = "tickets"

    // Auth
    const val GET_OTP = "auth/otp"
    const val GET_WHO_AM_I = "auth/whoami"
    const val POST_REGISTER = "auth/register"
    const val POST_LOGIN = "auth/login"
    const val PUT_PASSWORD_FORGOT = "auth/password"
    const val PUT_PASSWORD_RESET = "users/password"

    // Users
    const val PUT_USER_DATA = "users"
    const val PUT_USER_EMAIL = "users/email"

    // Image
    const val POST_IMAGE = "images"
    const val DELETE_IMAGE = "images"
}