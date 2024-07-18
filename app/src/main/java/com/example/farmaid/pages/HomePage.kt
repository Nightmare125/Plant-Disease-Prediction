package com.example.farmaid.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.farmaid.AuthViewModel
import com.example.farmaid.Authstate
import com.example.farmaid.R

@Composable
fun HomePage(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {

    val authstate = authViewModel.authstate.observeAsState()

    LaunchedEffect(authstate.value){
        when(authstate.value){
            is Authstate.Unauthenticated -> navController.navigate("login")
            else -> Unit
        }
    }

    Row (
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ){
        TextButton(onClick = {
            authViewModel.logout()
        }) {
            Text(text = "Sign Out")
        }
    }

    Column (
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
       Image(painter = painterResource(id = R.drawable.farm), contentDescription ="Logo image",
           modifier = modifier.size(200.dp))

        Text(text = "Welcome to FarmAid", fontSize = 28.sp, fontWeight = FontWeight.Bold )

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(40.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
            navController.navigate("ImageCapture") }) {
                Text(text = "Take a pic")
            }

             Button(onClick = {
                navController.navigate("Classification")
              }) {
                Text(text = "Upload")
        }


    }

    }

}