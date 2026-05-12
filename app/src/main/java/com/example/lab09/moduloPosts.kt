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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenPosts(navController: NavHostController, servicio: PostApiService) {

    val listaPosts = remember { mutableStateListOf<PostModel>() }

    LaunchedEffect(Unit) {
        try {
            val listado = servicio.getUserPosts()
            listaPosts.clear()
            listaPosts.addAll(listado)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Publicaciones API",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(listaPosts) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = item.id.toString(), modifier = Modifier.weight(0.1f))
                        Text(text = item.title, modifier = Modifier.weight(0.7f), maxLines = 1)
                        IconButton(
                            onClick = { navController.navigate("postsVer/${item.id}") },
                            modifier = Modifier.weight(0.2f)
                        ) {
                            Icon(imageVector = Icons.Outlined.Search, contentDescription = null)
                        }
                    }
                }
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
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    Column(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        post?.let {
            Text(text = "DETALLE", fontSize = 12.sp, color = Color.Gray)
            Text(text = it.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
            Text(text = it.body, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = { navController.popBackStack() }, modifier = Modifier.fillMaxWidth()) {
                Text("Regresar")
            }
        } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}