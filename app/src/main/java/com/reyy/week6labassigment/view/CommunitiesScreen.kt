package com.reyy.week6labassigment.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.reyy.week6labassigment.data.ClubProjectsRoute
import com.reyy.week6labassigment.viewmodel.CampusViewModel

@Composable
fun CommunitiesScreen(viewModel: CampusViewModel, navController: NavController) {
    val communities by viewModel.communities.collectAsState()
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF9FAFB))) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text("Communities Directory", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    Text("Pilih komunitas untuk membuka Club Projects", fontSize = 12.sp, color = Color.DarkGray)
                }
                Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Black)
            }
        }

        LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
            items(communities) { community ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp).clickable {
                        if (community.isJoined) {
                            navController.navigate(ClubProjectsRoute(community.id))
                        } else {
                            Toast.makeText(context, "Harap bergabung ke komunitas terlebih dahulu!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        when (community.name) {
                                            "GDSC" -> Color(0xFFEFF6FF)
                                            "CyberSecurity Guild" -> Color(0xFFFEF2F2)
                                            "AI Society" -> Color(0xFFF3E8FF)
                                            "Mobile Dev Community" -> Color(0xFFF0F9FF)
                                            "SU IMT" -> Color(0xFFFFFBEB)
                                            "Mentoring Department" -> Color(0xFFF3E8FF)
                                            else -> Color(0xFFF0FDF4)
                                        }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                val icon = when (community.name) {
                                    "GDSC" -> Icons.Default.Code
                                    "CyberSecurity Guild" -> Icons.Default.Security
                                    "AI Society" -> Icons.Default.AutoAwesome
                                    "Mobile Dev Community" -> Icons.Default.PhoneIphone
                                    "SU IMT" -> Icons.Default.People
                                    "Mentoring Department" -> Icons.Default.School
                                    else -> Icons.Default.Business
                                }
                                val tint = when (community.name) {
                                    "GDSC" -> Color(0xFF2563EB)
                                    "CyberSecurity Guild" -> Color.Red
                                    "AI Society" -> Color(0xFFA855F7)
                                    "Mobile Dev Community" -> Color(0xFF0EA5E9)
                                    "SU IMT" -> Color(0xFFD97706)
                                    "Mentoring Department" -> Color(0xFFA855F7)
                                    else -> Color(0xFF16A34A)
                                }
                                Icon(icon, contentDescription = null, tint = tint)
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(community.name, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color.Black, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                Text("${community.type} • ${community.memberCount} Mahasiswa", fontSize = 12.sp, color = Color.DarkGray, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(community.description, fontSize = 13.sp, color = Color.Black)
                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(color = Color(0xFFEAECF0))
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                Icon(if (community.isJoined) Icons.Default.LockOpen else Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(14.dp), tint = if (community.isJoined) Color(0xFF2563EB) else Color.Gray)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(if (community.isJoined) "Unlocked • Buka Proyek →" else "Terkunci (Gate Akses)", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = if (community.isJoined) Color(0xFF2563EB) else Color.Gray, maxLines = 1, softWrap = false)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            if (community.isJoined) {
                                Box(modifier = Modifier.clip(RoundedCornerShape(16.dp)).background(Color(0xFFFEF2F2)).border(1.dp, Color(0xFFEF4444), RoundedCornerShape(16.dp)).padding(horizontal = 8.dp, vertical = 4.dp)) {
                                    Text("Joined ✓", color = Color(0xFFEF4444), fontSize = 11.sp, fontWeight = FontWeight.Bold, maxLines = 1, softWrap = false)
                                }
                            } else {
                                Button(
                                    onClick = { viewModel.toggleJoinCommunity(community.id) },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB)),
                                    shape = RoundedCornerShape(16.dp),
                                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                                    modifier = Modifier.height(32.dp)
                                ) {
                                    Text("+ Join", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White, maxLines = 1, softWrap = false)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}