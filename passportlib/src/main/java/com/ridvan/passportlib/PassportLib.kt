package com.ridvan.passportlib

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ridvan.passportlib.Country.Companion.countryList
import com.ridvan.passportlib.utils.clickableNoRipple
import com.ridvan.passportlib.utils.filterCountries
import com.ridvan.passportlib.utils.getCountryPassports
import java.util.Locale

@Composable
fun PassportLib(
    modifier: Modifier = Modifier,
    searchModifier: Modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()
        .border(
            width = 1.dp,
            color = Color(0xFFDADADA),
            shape = RoundedCornerShape(8.dp)
        ),
    searchPlaceholder: @Composable ((txtPlaceHolder: String?) -> Unit) = { txtPlaceHolder ->
        Text(
            text = txtPlaceHolder.orEmpty(),
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.ExtraLight
            ),
        )
    },
    onClicked: (item: Country) -> Unit,
    searchFieldColors: TextFieldColors = TextFieldDefaults.colors(),
) {
    var showPassport by remember { mutableStateOf(Country.TURKEY) }

    Box {
        SearchCountry(
            modifier = searchModifier,
            searchFieldColors = searchFieldColors,
            searchPlaceholder = searchPlaceholder,
            onSelected = {
                onClicked?.invoke(it)
                showPassport = it
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchCountry(
    modifier: Modifier = Modifier,
    searchFieldColors: TextFieldColors = TextFieldDefaults.colors(),
    searchPlaceholder: @Composable ((txtPlaceHolder: String?) -> Unit) = { txtPlaceHolder ->
        Text(
            text = txtPlaceHolder.orEmpty(),
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.ExtraLight
            ),
        )
    },
    onSelected: (item: Country) -> Unit
) {
    var filteredCountries: List<Country> = emptyList()
    var searchValue by remember { mutableStateOf("") }

    LazyColumn(
        modifier = modifier
            .height(300.dp)
    ) {
        stickyHeader {
            SearchField(
                searchFieldColors = searchFieldColors,
                searchPlaceholder = searchPlaceholder,
                filteredCountries = {
                    filteredCountries = it
                },
                searchValue = searchValue,
                onChangeSearchValue = {
                    searchValue = it
                }
            )

            Divider(
                modifier = Modifier
                    .fillMaxWidth(),
                color = Color(0xFFDADADA)
            )
        }

        val countries = if (searchValue.isEmpty()) countryList else filteredCountries

        items(countries) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickableNoRipple {
                        onSelected.invoke(it)
                    },
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(width = 50.dp, height = 75.dp),
                    painter = painterResource(
                        id = getCountryPassports(
                            it.countryAlphaCode.lowercase(
                                Locale.getDefault()
                            )
                        )
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )

                Text(
                    text =  it.countryName,
                    style = TextStyle.Default,
                    color = Color(0xFF212121),
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun SearchField(
    searchFieldColors: TextFieldColors = TextFieldDefaults.colors(),
    searchValue: String,
    onChangeSearchValue: (String) -> Unit,
    searchPlaceholder: @Composable ((txtPlaceHolder: String?) -> Unit) = { txtPlaceHolder ->
        Text(
            text = txtPlaceHolder.orEmpty(),
            color = MaterialTheme.colorScheme.onSurface,
        )
    },
    filteredCountries: (MutableList<Country>) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val context = LocalContext.current

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .focusRequester(focusRequester),
        value = searchValue,
        onValueChange = { searchStr ->
            onChangeSearchValue.invoke(searchStr)
            filteredCountries.invoke(countryList.filterCountries(context = context, searchStr = searchStr))
        },
        placeholder = {
            searchPlaceholder.invoke("Search...")
        },
        colors = searchFieldColors,
        textStyle = MaterialTheme.typography.labelLarge,
        leadingIcon = {
            Icon(
                modifier = Modifier
                    .padding(4.dp)
                    .size(16.dp),
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = Color(0xFF8C8C8C)
            )
        }
    )
}