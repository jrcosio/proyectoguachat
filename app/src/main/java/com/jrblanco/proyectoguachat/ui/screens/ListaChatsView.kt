package com.jrblanco.proyectoguachat.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jrblanco.proyectoguachat.R
import com.jrblanco.proyectoguachat.domain.model.Chats
import com.jrblanco.proyectoguachat.ui.theme.Azul40
import com.jrblanco.proyectoguachat.ui.theme.Green10
import com.jrblanco.proyectoguachat.ui.theme.PurpleGrey40
import com.jrblanco.proyectoguachat.ui.theme.PurpleGrey80
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun ListaChatsView(listaChats: List<Chats>, onClickItem: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(listaChats) {
            ItemCard(item = it) {idGoogle ->
                onClickItem(idGoogle)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemCard(item: Chats, onClickItem: (String) -> Unit) {
    val context = LocalContext.current

    val formatFecha = SimpleDateFormat("HH:mm dd/MMM", Locale("es", "ES"))
    val fecha = formatFecha.format(item.date?.toDate())

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 6.dp, end = 6.dp, top = 6.dp),
        colors = CardDefaults.cardColors(containerColor = PurpleGrey80),
        elevation = CardDefaults.cardElevation(5.dp),
        onClick = { onClickItem(item.idGoogle) }
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(32.dp))
            ) {
                AsyncImage(
                    model = item.icon,
                    contentDescription = "Imagen de usuario",
                    modifier = Modifier
                        .clip(CircleShape)
                        .border(1.dp, Azul40, CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {

                Text(
                    text = item.title,
                    style = TextStyle(fontSize = 16.sp),
                    fontWeight = FontWeight.Bold,
                )
                if (item.isGrupo) {            //is es un grupo
                    Text(
                        text = item.subTitle,
                        style = TextStyle(fontSize = 14.sp),
                    )
                } else {
                    Spacer(modifier = Modifier.size(10.dp))
                }
                Text(
                    text = if (item.lastMessage.length < 30) item.lastMessage else "${
                        item.lastMessage.take(
                            30
                        )
                    }...",
                    style = TextStyle(fontSize = 14.sp),
                    color = Green10
                )

            }
            Column() {

                Text(
                    text = fecha,
                    modifier = Modifier.padding(bottom = 22.dp),
                    style = TextStyle(fontSize = 14.sp),
                    color = Green10
                )
//                Box(
//                    modifier = Modifier
//                        .align(Alignment.End)
//                        .clip(RoundedCornerShape(16.dp))
//                        .background(PurpleGrey40)
//                ) {
//                    Text(
//                        text = "0",
//                        style = TextStyle(fontSize = 12.sp),
//                        modifier = Modifier.padding(3.dp),
//                        color = Color.White
//                    )
//                }
            }
        }
    }
}