package com.example.swapcampus_mamaabbas.composeapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Chair
import androidx.compose.material.icons.outlined.Checkroom
import androidx.compose.material.icons.outlined.Laptop
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.SportsSoccer
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.swapcampus_mamaabbas.composeapp.resources.*
import com.example.swapcampus_mamaabbas.shared.domain.Available
import com.example.swapcampus_mamaabbas.shared.domain.Category
import com.example.swapcampus_mamaabbas.shared.domain.ListingStatus
import com.example.swapcampus_mamaabbas.shared.domain.Reserved
import com.example.swapcampus_mamaabbas.shared.domain.Sold
import com.example.swapcampus_mamaabbas.shared.domain.UserRole
import org.jetbrains.compose.resources.StringResource

fun Category.labelRes(): StringResource = when (this) {
    Category.BOOKS -> Res.string.category_books
    Category.COURSES -> Res.string.category_courses
    Category.ELECTRONICS -> Res.string.category_electronics
    Category.SPORTS_LEISURE -> Res.string.category_sports_leisure
    Category.FURNITURE -> Res.string.category_furniture
    Category.CLOTHING -> Res.string.category_clothing
    Category.OTHER -> Res.string.category_other
}

fun Category.icon(): ImageVector = when (this) {
    Category.BOOKS -> Icons.AutoMirrored.Outlined.MenuBook
    Category.COURSES -> Icons.Outlined.School
    Category.ELECTRONICS -> Icons.Outlined.Laptop
    Category.SPORTS_LEISURE -> Icons.Outlined.SportsSoccer
    Category.FURNITURE -> Icons.Outlined.Chair
    Category.CLOTHING -> Icons.Outlined.Checkroom
    Category.OTHER -> Icons.Outlined.Category
}

fun ListingStatus.labelRes(): StringResource = when (this) {
    Available -> Res.string.status_available
    is Reserved -> Res.string.status_reserved
    is Sold -> Res.string.status_sold
}

fun UserRole.labelRes(): StringResource = when (this) {
    UserRole.STUDENT -> Res.string.role_student
    UserRole.ADMINISTRATOR -> Res.string.role_administrator
}
