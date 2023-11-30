package com.ridvan.passportlib

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ridvan.passportlib.ui.theme.PassportLibTheme
import com.ridvan.passportlib.utils.getCountryPassports
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PassportLibTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    color = Color.White
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        val selectedCountry = remember { mutableStateOf(Country.TURKEY) }

                        PassportLib(
                            modifier = Modifier
                                .background(Color.White)
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            onClicked = { selectedCountry.value = it },
                        )

                        Text(
                            modifier = Modifier.padding(bottom = 16.dp),
                            text = selectedCountry.value.countryName,
                            fontSize = 20.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )

                        Image(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            painter = painterResource(
                                id = getCountryPassports(
                                    selectedCountry.value.countryAlphaCode.lowercase(
                                        Locale.getDefault()
                                    )
                                )
                            ),
                            contentDescription = selectedCountry.value.countryAlphaCode
                        )
                    }
                }
            }
        }
    }
}