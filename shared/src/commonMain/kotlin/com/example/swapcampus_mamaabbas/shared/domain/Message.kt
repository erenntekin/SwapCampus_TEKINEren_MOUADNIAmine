package com.example.swapcampus_mamaabbas.shared.domain

import kotlinx.datetime.LocalDateTime

data class Message(
    val id: Int,
    val listingId: Int,
    val senderId: Int,
    val recipientId: Int,
    val content: String,
    val sentDate: LocalDateTime,
)
