/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import coil.transform.CircleCropTransformation
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun DogItemCard(
    id: Int,
    @DrawableRes imageUri: Int,
    name: String,
    breed: String,
    price: Int,
    onClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(id) },
        elevation = 4.dp,
        shape = RoundedCornerShape(18.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CoilImage(
                data = imageUri,
                contentDescription = "Dog images",
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .height(96.dp)
                    .width(96.dp),
                alignment = Alignment.CenterStart,
                requestBuilder = {
                    transformations(CircleCropTransformation())
                }
            ) {
            }
            Spacer(modifier = Modifier.width(20.dp))
            Column {
                Text(text = name, fontSize = 18.sp, fontFamily = FontFamily.SansSerif)
                Text(text = breed, fontFamily = FontFamily.Serif)
                Text(text = "$$price", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun ShowDogsList(navController: NavController, dogs: List<Dog>) {
    LazyColumn {
        items(dogs) { dog ->
            DogItemCard(
                id = dog.id,
                imageUri = dog.image,
                name = dog.name,
                breed = dog.breed,
                price = dog.price
            ) {
                Log.e(TAG, "ShowDogsList: $it")
                navController.navigate("details/$it")
            }
        }
    }
}

@Composable
fun DogsList(navController: NavController, viewModel: BarkOnViewModel) {
    val dogs = viewModel.dogsLiveData.observeAsState()
    dogs.value?.let { ShowDogsList(navController, it) }
}
