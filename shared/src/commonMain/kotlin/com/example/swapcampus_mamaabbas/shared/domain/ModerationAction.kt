package com.example.swapcampus_mamaabbas.shared.domain

import kotlinx.datetime.LocalDateTime

enum class ModerationActionType {
    LISTING_REMOVAL,
    ACCOUNT_DEACTIVATION,
}


data class ModerationAction(
    val id: Int,
    val targetId: Int,
    val type: ModerationActionType,
    val reason: String,
    val administratorId: Int,
    val date: LocalDateTime,
) {
    init {
        require(reason.isNotBlank()) { "A moderation reason is required" }
    }
}
