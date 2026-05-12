package com.example.lab09

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun ScreenPosts(navController: NavHostController, servicio: PostApiService) {
    val listaPosts = remember { mutableStateListOf<PostModel>() }

    LaunchedEffect(Unit) {
        try {
            val listado = servicio.getUserPosts()
            listaPosts.clear()
            listaPosts.addAll(listado)
        } catch (e: Exception) { e.printStackTrace() }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Encabezado Azul de la Imagen
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xFF586BA4)
        ) {
            Text(
                text = "JSONPlaceHolder Access",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center
            )
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(listaPosts) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${item.id} ${item.title}",
                        modifier = Modifier.weight(0.85f),
                        fontSize = 16.sp
                    )
                    IconButton(
                        onClick = { navController.navigate("postsVer/${item.id}") },
                        modifier = Modifier.weight(0.15f)
                    ) {
                        Icon(imageVector = Icons.Outlined.Search, contentDescription = null)
                    }
                }
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp, color = Color.LightGray)
            }
        }
    }
}

@Composable
fun ScreenPost(navController: NavHostController, servicio: PostApiService, id: Int) {
    var post by remember { mutableStateOf<PostModel?>(null) }

    LaunchedEffect(id) {
        try {
            post = servicio.getUserPostById(id)
        } catch (e: Exception) { e.printStackTrace() }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xFF586BA4)
        ) {
            Text(
                text = "JSONPlaceHolder Access",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center
            )
        }

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            post?.let {
                OutlinedTextField(value = it.id.toString(), onValueChange = {}, label = { Text("id") }, modifier = Modifier.fillMaxWidth(), readOnly = true)
                OutlinedTextField(value = it.userId.toString(), onValueChange = {}, label = { Text("userId") }, modifier = Modifier.fillMaxWidth(), readOnly = true)
                OutlinedTextField(value = it.title, onValueChange = {}, label = { Text("title") }, modifier = Modifier.fillMaxWidth(), readOnly = true)
                OutlinedTextField(value = it.body, onValueChange = {}, label = { Text("body") }, modifier = Modifier.fillMaxWidth(), readOnly = true)

                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF586BA4))
                ) {
                    Text("REGRESAR")
                }
            } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}