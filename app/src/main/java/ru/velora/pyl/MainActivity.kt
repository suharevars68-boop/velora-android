package ru.velora.pyl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Ink = Color(0xFF17191F)
private val Paper = Color(0xFFF4F1EA)
private val Lime = Color(0xFFD7FF62)
private data class Chat(val name:String,val msg:String,val time:String,val unread:Int,val color:Color)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); setContent { PylApp() } }
}

@Composable fun PylApp() {
    var selected by remember { mutableStateOf("Все чаты") }
    var query by remember { mutableStateOf("") }
    val chats = listOf(
        Chat("Мама","Ты купил хлеб?","сейчас",2,Color(0xFFFFB4A2)),
        Chat("Совет директоров кухни","собрание переносится на никогда","12:41",5,Color(0xFFB8C0FF)),
        Chat("Денис без контекста","голосовое на 0:03","11:08",0,Color(0xFFA8DADC)),
        Chat("Поддержка здравого смысла","Ваше обращение отклонено","вчера",0,Color(0xFFE9C46A)),
        Chat("Избранное (сам себе)","Не забудь забыть","пн",0,Color(0xFFCDB4DB)),
        Chat("Кот","мяу","пн",1,Color(0xFFFFD6A5))
    )
    MaterialTheme(colorScheme = lightColorScheme(primary=Ink, background=Paper, surface=Paper)) {
        Column(Modifier.fillMaxSize().background(Paper).padding(horizontal=20.dp)) {
            Row(Modifier.fillMaxWidth().padding(top=22.dp,bottom=18.dp),verticalAlignment=Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("ПЫЛЬ",fontSize=34.sp,fontWeight=FontWeight.Black,color=Ink,letterSpacing=(-2).sp)
                    Text("мессенджер для важных ничего",fontSize=12.sp,color=Ink.copy(alpha=.55f))
                }
                Surface(color=Lime,shape=CircleShape) { Text("β",Modifier.padding(horizontal=13.dp,vertical=8.dp),fontWeight=FontWeight.Bold,color=Ink) }
            }
            OutlinedTextField(query,{query=it},Modifier.fillMaxWidth(),placeholder={Text("Найти смысл...")},leadingIcon={Icon(Icons.Default.Search,null)},singleLine=true,shape=RoundedCornerShape(18.dp),colors=OutlinedTextFieldDefaults.colors unfocusedBorderColor=Ink.copy(alpha=.12f),focusedBorderColor=Ink)
            Row(Modifier.padding(vertical=16.dp),horizontalArrangement=Arrangement.spacedBy(8.dp)) {
                listOf("Все чаты","Непрочитанные","Работа").forEach { label ->
                    FilterChip(selected==label,{selected=label},label={Text(label)},colors=FilterChipDefaults.filterChipColors(selectedContainerColor=Lime,selectedLabelColor=Ink))
                }
            }
            Row(verticalAlignment=Alignment.CenterVertically,modifier=Modifier.padding(bottom=10.dp)) {
                Text("ВАШИ ДИАЛОГИ",fontSize=11.sp,fontWeight=FontWeight.Bold,color=Ink.copy(alpha=.45f),letterSpacing=1.5.sp)
                Spacer(Modifier.weight(1f)); Text("6 контактов",fontSize=11.sp,color=Ink.copy(alpha=.45f))
            }
            LazyColumn(verticalArrangement=Arrangement.spacedBy(5.dp),modifier=Modifier.weight(1f)) {
                items(chats.filter { it.name.contains(query,true) || it.msg.contains(query,true) }) { chat ->
                    Row(Modifier.fillMaxWidth().clickable { selected=chat.name }.padding(vertical=11.dp),verticalAlignment=Alignment.CenterVertically) {
                        Box(Modifier.size(50.dp).background(chat.color,CircleShape),contentAlignment=Alignment.Center) { Text(chat.name.take(1).uppercase(),fontWeight=FontWeight.Bold,fontSize=20.sp,color=Ink) }
                        Column(Modifier.weight(1f).padding(start=13.dp)) {
                            Text(chat.name,fontWeight=FontWeight.SemiBold,color=Ink,fontSize=15.sp)
                            Text(chat.msg,color=Ink.copy(alpha=.52f),fontSize=12.sp,maxLines=1)
                        }
                        Column(horizontalAlignment=Alignment.End) {
                            Text(chat.time,fontSize=10.sp,color=Ink.copy(alpha=.45f))
                            if(chat.unread>0) Box(Modifier.padding(top=6.dp).background(Ink,CircleShape).padding(horizontal=7.dp,vertical=3.dp)) { Text(chat.unread.toString(),color=Lime,fontSize=10.sp,fontWeight=FontWeight.Bold) }
                        }
                    }
                    HorizontalDivider(color=Ink.copy(alpha=.07f))
                }
            }
            Row(Modifier.fillMaxWidth().padding(vertical=15.dp),horizontalArrangement=Arrangement.SpaceAround) {
                Icon(Icons.Default.ChatBubble,null,tint=Ink); Icon(Icons.Default.People,null,tint=Ink.copy(alpha=.35f)); Icon(Icons.Default.Settings,null,tint=Ink.copy(alpha=.35f))
            }
            Text("Никаких звонков. Никаких обязательств. Только ПЫЛЬ.",Modifier.align(Alignment.CenterHorizontally).padding(bottom=12.dp),fontSize=9.sp,color=Ink.copy(alpha=.35f))
        }
    }
}
