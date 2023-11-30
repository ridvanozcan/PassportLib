package com.ridvan.passportlib.utils

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.semantics.Role
import com.ridvan.passportlib.Country


fun Modifier.clickableNoRipple(
    interactionSource: MutableInteractionSource? = null,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
) = composed {
    clickable(
        indication = null,
        interactionSource = interactionSource ?: remember { MutableInteractionSource() },
        enabled = enabled,
        onClickLabel = onClickLabel,
        role = role,
        onClick = onClick
    )
}


fun List<Country>.filterCountries(context: Context, searchStr: String): MutableList<Country> {
    return filter { country ->
        country.countryName.contains(searchStr, ignoreCase = true) || country.countryAlphaCode.contains(searchStr, ignoreCase = true)
    }.toMutableList()
}