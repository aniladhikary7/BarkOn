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

import androidx.annotation.DrawableRes
import java.util.ArrayList
import kotlin.random.Random.Default.nextInt

data class Dog(
    val id: Int,
    val name: String,
    val breed: String,
    @DrawableRes val image: Int,
    val age: Int,
    val gender: String,
    val color: String,
    val price: Int
)

object DataProvider {

    private val names: Array<String> = arrayOf(
        "Charlie", "Max", "Buddy", "Oscar", "Milo", "Archie", "Ollie",
        "Toby", "Jack", "Teddy", "Bella", "Coco", "Ruby", "Lucy", "Bailey", "Daisy", "Rosie",
        "Lola", "Frankie"
    )

    private val breeds = arrayOf(
        "Affenpinscher", "Afghan Hound", "African Hunting Dog",
        "Airedale Terrier", "Akbash Dog", "Akita", "Alapaha Blue Blood Bulldog", "Alaskan Husky",
        "Alaskan Malamute", "American Bulldog"
    )

    private val images = arrayOf(
        R.drawable.pexels_charles_1851164, R.drawable.pexels_helena_lopes_1938126,
        R.drawable.pexels_helena_lopes_2253275, R.drawable.pexels_hoy_1390784,
        R.drawable.pexels_hoy_1390784, R.drawable.pexels_muhannad_alatawi_58997,
        R.drawable.pexels_pixabay_160846, R.drawable.pexels_pixabay_235805,
        R.drawable.pexels_poodles_doodles_1458916, R.drawable.pexels_simona_kidri_2607544,
        R.drawable.pexels_steshka_willems_1390361, R.drawable.pexels_valeria_boltneva_1805164
    )

    private val genders = arrayOf("Male", "Female")

    private val colors = arrayOf(
        "Brown", "Red", "Black", "White", "Gold", "Yellow", "Cream",
        "Blue", "Grey"
    )

    fun dogs(): List<Dog> {
        val dogs: MutableList<Dog> = ArrayList<Dog>(20)
        for (i in 0 until 19) {
            val dog = Dog(
                id = i,
                name = names[i],
                breed = breeds[i % breeds.size],
                image = images[i % images.size],
                age = nextInt(1, 15),
                gender = genders[nextInt(0, genders.size)],
                color = colors[nextInt(0, colors.size)],
                price = nextInt(50, 100)
            )
            dogs.add(dog)
        }
        return dogs
    }
}
