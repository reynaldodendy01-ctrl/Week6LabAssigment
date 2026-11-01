package com.reyy.week6labassigment.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Code
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.reyy.week6labassigment.viewmodel.CampusViewModel
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.PhoneIphone
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star

@Composable
fun ProfileDashboardScreen(viewModel: CampusViewModel, navController: NavController) {
    val communities by viewModel.communities.collectAsState()
    val projects by viewModel.projects.collectAsState()
    val joinedCommunities = communities.filter { it.isJoined }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF9FAFB))) {
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text("CampusTech Hub", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Text("Student Profile & Portfolio Dashboard", fontSize = 12.sp, color = Color.DarkGray)
            }
            Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(Color(0xFF5B21B6)), contentAlignment = Alignment.Center) {
                Text("AP", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }

        LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
            item {
                Card(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp), shape = RoundedCornerShape(16.dp)) {
                    Box(modifier = Modifier.background(Brush.horizontalGradient(listOf(Color(0xFF1E3A8A), Color(0xFF3B82F6)))).fillMaxWidth().padding(20.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(60.dp).clip(CircleShape).background(Color.White), contentAlignment = Alignment.Center) {
                                Text("AP", color = Color(0xFF1E3A8A), fontSize = 24.sp, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Box(modifier = Modifier.background(Color.White, RoundedCornerShape(8.dp)).padding(horizontal = 8.dp, vertical = 4.dp)) {
                                    Text("MAHASISWA AKTIF", color = Color(0xFF1E3A8A), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("Aditya Pratama", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                Text("Teknik Informatika • Angkatan 2022", color = Color.White, fontSize = 12.sp)
                                Text("NIM: 22001045", color = Color.White, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }

            item {
                Row(modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Card(modifier = Modifier.weight(1f), colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(16.dp)) {
                        Column(Modifier.padding(16.dp)) {
                            Icon(Icons.Default.People, contentDescription = null, tint = Color(0xFF2563EB), modifier = Modifier.padding(bottom = 8.dp))
                            Text(joinedCommunities.size.toString(), fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                            Text("Joined Clubs", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.Black)
                            Text("Komunitas yang Diikuti", fontSize = 10.sp, color = Color.DarkGray)
                        }
                    }
                    Card(modifier = Modifier.weight(1f), colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(16.dp)) {
                        Column(Modifier.padding(16.dp)) {
                            Icon(Icons.Default.Code, contentDescription = null, tint = Color(0xFF9333EA), modifier = Modifier.padding(bottom = 8.dp))
                            Text(projects.size.toString(), fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                            Text("Total Projects", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.Black)
                            Text("Seluruh Proyek Dibuat", fontSize = 10.sp, color = Color.DarkGray)
                        }
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFF2563EB), modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Semua Komunitas yang Diikuti (${joinedCommunities.size})",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Status: Joined",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2563EB),
                        maxLines = 1,
                        softWrap = false
                    )
                }
            }

            if (joinedCommunities.isEmpty()) {
                item {
                    Text("Belum bergabung dengan komunitas", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(vertical = 8.dp))
                }
            } else {
                items(joinedCommunities) { community ->
                    val communityProjects = projects.filter { it.communityId == community.id }
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                                Box(modifier = Modifier.size(40.dp).clip(RoundedCornerShape(8.dp)).background(if (community.name == "GDSC") Color(0xFFEFF6FF) else if (community.name == "CyberSecurity Guild") Color(0xFFFEF2F2) else Color(0xFFF0FDF4)), contentAlignment = Alignment.Center) {
                                    val icon = if (community.name == "GDSC") Icons.Default.Code else if (community.name == "CyberSecurity Guild") Icons.Default.Security else Icons.Default.PhoneIphone
                                    val tint = if (community.name == "GDSC") Color(0xFF2563EB) else if (community.name == "CyberSecurity Guild") Color.Red else Color(0xFF0EA5E9)
                                    Icon(icon, contentDescription = null, tint = tint)
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = community.name,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = Color.Black,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = "${community.type} • ${communityProjects.size} Proyek",
                                        fontSize = 12.sp,
                                        color = Color.DarkGray,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Box(modifier = Modifier.clip(RoundedCornerShape(16.dp)).background(Color(0xFFFEF2F2)).border(1.dp, Color(0xFFEF4444), RoundedCornerShape(16.dp)).padding(horizontal = 8.dp, vertical = 4.dp)) {
                                Text(
                                    text = "Joined ✓",
                                    color = Color(0xFFEF4444),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    softWrap = false
                                )
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.CheckBoxOutlineBlank, contentDescription = null, tint = Color(0xFF2563EB), modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Semua Proyek Mahasiswa (${projects.size})",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Total: ${projects.size} Proyek",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2563EB),
                        maxLines = 1,
                        softWrap = false
                    )
                }
            }

            if (projects.isEmpty()) {
                item {
                    Text("Belum ada proyek yang dicatat", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(vertical = 8.dp))
                }
            } else {
                items(projects) { projectData ->
                    val community = communities.find { it.id == projectData.communityId }

                    Card(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(1.dp)
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.Top, modifier = Modifier.fillMaxWidth()) {
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(if (projectData.isCompleted) Color(0xFFF0FDF4) else if (projectData.title.contains("Portal")) Color(0xFFF3E8FF) else Color(0xFFEFF6FF)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    val icon = if (projectData.isCompleted) Icons.Default.Code else if (projectData.title.contains("Portal")) Icons.Default.Cloud else Icons.Default.Android
                                    val tint = if (projectData.isCompleted) Color(0xFF22C55E) else if (projectData.title.contains("Portal")) Color(0xFFA855F7) else Color(0xFF2563EB)
                                    Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(24.dp))
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = community?.name?.uppercase() ?: "",
                                        color = Color(0xFF2563EB),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = projectData.title,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        color = Color.Black,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = projectData.techStack.joinToString(" • "),
                                        fontSize = 12.sp,
                                        color = Color.Gray,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))
                            HorizontalDivider(color = Color(0xFFEAECF0))
                            Spacer(modifier = Modifier.height(12.dp))

                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                Text("ID: ${projectData.id.take(12)}", fontSize = 11.sp, color = Color.Gray)
                                StatusBadgeLocal(if (projectData.isCompleted) "Completed" else "In Progress")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StatusBadgeLocal(status: String) {
    val bgColor = if (status == "Completed") Color(0xFFD1FAE5) else Color(0xFFFEF3C7)
    val textColor = if (status == "Completed") Color(0xFF059669) else Color(0xFFD97706)
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(bgColor)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(textColor))
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = status, color = textColor, fontSize = 11.sp, fontWeight = FontWeight.Bold)
    }
}