package com.example.farmaid

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.farmaid.pages.Classification
import com.example.farmaid.pages.HomePage
import com.example.farmaid.pages.ImageCapture
import com.example.farmaid.pages.LoginPage
import com.example.farmaid.pages.SignUp

@Composable
fun MyAppNavigation(modifier: Modifier = Modifier,authViewModel: AuthViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login", builder = {
        composable("login"){
            LoginPage(modifier,navController,authViewModel)
        }
        composable("signup"){
            SignUp(modifier,navController,authViewModel)
        }
        composable("home"){
            HomePage(modifier,navController,authViewModel)
        }
        composable("Classification"){
            Classification(modifier,navController,authViewModel)
        }
        composable("ImageCapture"){
            ImageCapture(modifier)
        }
    })
}





















