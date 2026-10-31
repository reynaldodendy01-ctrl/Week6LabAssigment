package com.reyy.week6labassigment.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PhoneIphone
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.reyy.week6labassigment.viewmodel.CampusViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProjectDetailScreen(viewModel: CampusViewModel, projectId: String, navController: NavController) {
    val projectsList by viewModel.projects.collectAsState()
    val communities by viewModel.communities.collectAsState()
    val activeProject = projectsList.find { it.id == projectId }

    if (activeProject == null) return

    val community = communities.find { it.id == activeProject.communityId }

    Scaffold(
        containerColor = Color(0xFFF9FAFB),
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth().background(Color(0xFFF9FAFB)).padding(horizontal = 8.dp, vertical = 12.dp).statusBarsPadding(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.Black)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text("Project Detail", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Black)
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)) {
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A))
            ) {
                Column(Modifier.padding(20.dp)) {
                    Box(modifier = Modifier.background(Color(0xFF1E3A8A), RoundedCornerShape(16.dp)).padding(horizontal = 10.dp, vertical = 4.dp)) {
                        Text(community?.name ?: "COMMUNITY", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(activeProject.title, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(1.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text("Mark as Done", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Black)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Status: ", fontSize = 12.sp, color = Color.DarkGray)
                            ProjectDetailStatusBadge(if (activeProject.isCompleted) "Completed" else "In Progress")
                        }
                    }
                    Switch(
                        checked = activeProject.isCompleted,
                        onCheckedChange = { viewModel.toggleProjectStatus(activeProject.id) },
                        colors = SwitchDefaults.colors(checkedTrackColor = Color(0xFF2563EB))
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(1.dp)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Deskripsi Lengkap Proyek", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Black)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(activeProject.description, fontSize = 13.sp, color = Color.DarkGray, lineHeight = 20.sp)
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(1.dp)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Tech Stack Terpilih", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Black)
                    Spacer(modifier = Modifier.height(12.dp))

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        activeProject.techStack.forEach { techStackItem ->
                            val icon = when {
                                techStackItem.contains("Kotlin", true) -> Icons.Default.Code
                                techStackItem.contains("Compose", true) -> Icons.Default.PhoneIphone
                                techStackItem.contains("Room", true) -> Icons.Default.Storage
                                techStackItem.contains("Map", true) -> Icons.Default.LocationOn
                                else -> Icons.Default.Code
                            }
                            Row(
                                modifier = Modifier.background(Color(0xFFF1F5F9), RoundedCornerShape(8.dp)).padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(icon, contentDescription = null, tint = Color(0xFF2563EB), modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(techStackItem, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color.Black)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ProjectDetailStatusBadge(status: String) {
    val bgColor = if (status == "Completed") Color(0xFFD1FAE5) else Color(0xFFFEF3C7)
    val textColor = if (status == "Completed") Color(0xFF059669) else Color(0xFFD97706)
    Row(modifier = Modifier.clip(RoundedCornerShape(16.dp)).background(bgColor).padding(horizontal = 8.dp, vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(textColor))
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = status, color = textColor, fontSize = 11.sp, fontWeight = FontWeight.Bold)
    }
}