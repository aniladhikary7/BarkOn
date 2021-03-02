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

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.ui.theme.MyTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }

    @ExperimentalCoroutinesApi
    @Composable
    fun MyApp() {
        Surface(color = MaterialTheme.colors.background) {
            ComposeNavigation()
        }
    }

    @ExperimentalCoroutinesApi
    @Preview("Light Theme", widthDp = 360, heightDp = 640)
    @Composable
    fun LightPreview() {
        MyTheme {
            MyApp()
        }
    }

    @ExperimentalCoroutinesApi
    @Preview("Dark Theme", widthDp = 360, heightDp = 640)
    @Composable
    fun DarkPreview() {
        MyTheme(darkTheme = true) {
            MyApp()
        }
    }

    @Composable
    fun FirstScreen(navController: NavController) {
        val viewModel: BarkOnViewModel = viewModel()
        Scaffold(topBar = { TopAppBar(title = { Text(text = "Bark On") }) }) {
            DogsList(viewModel = viewModel, navController = navController)
        }
    }

    @Composable
    fun SecondScreen(navController: NavController, dogId: Int?) {
        val viewModel: BarkOnViewModel = viewModel()
        Log.e(TAG, "SecondScreen1: $dogId")
        dogId?.let {
            val dog = viewModel.getDogById(it)
            Log.e(TAG, "SecondScreen2: ${dog?.id}")
            if (dog != null) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = dog.name) },
                            navigationIcon = {
                                IconButton(onClick = { navController.navigateUp() }) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowBack,
                                        contentDescription = "Back Button"
                                    )
                                }
                            }
                        )
                    }
                ) {
                    DetailScreen(dog = dog)
                }
            }
        }
    }

    @Composable
    fun ComposeNavigation() {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = "list"
        ) {
            composable("list") {
                FirstScreen(navController = navController)
            }
            composable(
                "details/{id}",
                arguments = listOf(
                    navArgument("id") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                Log.e(TAG, "ComposeNavigation: ${backStackEntry.arguments?.getInt("id")}")
                SecondScreen(
                    navController = navController,
                    backStackEntry.arguments?.getInt("id")
                )
            }
        }
    }
}
