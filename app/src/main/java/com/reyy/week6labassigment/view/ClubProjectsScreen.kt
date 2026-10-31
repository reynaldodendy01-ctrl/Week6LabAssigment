package com.reyy.week6labassigment.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.reyy.week6labassigment.data.ProjectDetailRoute
import com.reyy.week6labassigment.viewmodel.CampusViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ClubProjectsScreen(viewModel: CampusViewModel, communityId: String, navController: NavController) {
    val communities by viewModel.communities.collectAsState()
    val projects by viewModel.projects.collectAsState()
    val community = communities.find { it.id == communityId }
    val clubProjects = projects.filter { it.communityId == communityId }

    var isDialogVisible by remember { mutableStateOf(false) }

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
                Column {
                    Text("${community?.name ?: ""} Projects", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Black)
                    Text("Daftar Proyek Mahasiswa", fontSize = 12.sp, color = Color.DarkGray)
                }
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { isDialogVisible = true },
                containerColor = Color(0xFFEEF2FF),
                contentColor = Color(0xFF2563EB),
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Add Project", fontWeight = FontWeight.Bold) }
            )
        }
    ) { paddingValues ->
        LazyColumn(contentPadding = paddingValues, modifier = Modifier.padding(16.dp)) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column {
                            Text(community?.type ?: "", fontWeight = FontWeight.Bold, color = Color(0xFF2563EB), fontSize = 15.sp)
                            Text("${community?.memberCount ?: 0} Anggota • ${clubProjects.size} Proyek Aktif", fontSize = 12.sp, color = Color(0xFF2563EB))
                        }
                        Box(modifier = Modifier.clip(RoundedCornerShape(16.dp)).background(Color(0xFFFEF2F2)).border(1.dp, Color(0xFFEF4444), RoundedCornerShape(16.dp)).padding(horizontal = 8.dp, vertical = 4.dp)) {
                            Text("Joined ✓", color = Color(0xFFEF4444), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            items(clubProjects) { projectData ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp).clickable { navController.navigate(ProjectDetailRoute(projectData.id)) },
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(1.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Row(verticalAlignment = Alignment.Top, modifier = Modifier.weight(1f)) {
                                Box(modifier = Modifier.size(40.dp).clip(RoundedCornerShape(8.dp)).background(if (projectData.isCompleted) Color(0xFFF0FDF4) else Color(0xFFEFF6FF)), contentAlignment = Alignment.Center) {
                                    val icon = if (projectData.isCompleted) Icons.Default.Code else Icons.Default.Android
                                    val tint = if (projectData.isCompleted) Color(0xFF22C55E) else Color(0xFF2563EB)
                                    Icon(icon, contentDescription = null, tint = tint)
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(projectData.title, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color.Black)
                                    Text(projectData.description, fontSize = 12.sp, color = Color.DarkGray, maxLines = 2)
                                }
                            }
                            IconButton(onClick = { viewModel.deleteProject(projectData.id) }, modifier = Modifier.size(24.dp).background(Color(0xFFFEF2F2), CircleShape)) {
                                Icon(Icons.Default.Remove, contentDescription = "Delete", tint = Color.Red, modifier = Modifier.size(16.dp))
                            }
                        }

                        Row(modifier = Modifier.fillMaxWidth().padding(top = 12.dp, bottom = 12.dp).horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            projectData.techStack.forEach { techItem ->
                                Box(modifier = Modifier.background(Color(0xFFF3F4F6), RoundedCornerShape(4.dp)).padding(horizontal = 6.dp, vertical = 2.dp)) {
                                    Text(techItem, fontSize = 10.sp, color = Color.DarkGray)
                                }
                            }
                        }

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

        if (isDialogVisible) {
            AddProjectDialog(
                communityName = community?.name ?: "",
                communityType = community?.type ?: "",
                onDismiss = { isDialogVisible = false },
                onSaveAction = { titleInput, descInput, selectedTechList ->
                    viewModel.addProject(communityId, titleInput, descInput, selectedTechList)
                    isDialogVisible = false
                }
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AddProjectDialog(
    communityName: String,
    communityType: String,
    onDismiss: () -> Unit,
    onSaveAction: (String, String, List<String>) -> Unit
) {
    var titleText by remember { mutableStateOf("") }
    var descriptionText by remember { mutableStateOf("") }
    var selectedTechSet by remember { mutableStateOf(setOf<String>()) }

    val techOptions = listOf("Kotlin", "Jetpack Compose", "Room", "Firebase", "Ktor", "Python", "Google Maps SDK")

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text("Add Project", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Text("Komunitas: $communityName ($communityType)", fontSize = 12.sp, color = Color.DarkGray)
                Spacer(modifier = Modifier.height(16.dp))

                Text("Judul Proyek *", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = titleText,
                    onValueChange = { titleText = it },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color(0xFFEAECF0), focusedBorderColor = Color(0xFF2563EB))
                )
                Spacer(modifier = Modifier.height(12.dp))

                Text("Deskripsi Proyek (Minimal 3 Baris) *", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = descriptionText,
                    onValueChange = { descriptionText = it },
                    modifier = Modifier.fillMaxWidth().height(100.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color(0xFFEAECF0), focusedBorderColor = Color(0xFF2563EB))
                )
                Spacer(modifier = Modifier.height(12.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Tech Stack (Pilihan Multi-Selection) *",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${selectedTechSet.size} Dipilih",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2563EB),
                        maxLines = 1,
                        softWrap = false
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    techOptions.forEach { techName ->
                        val isChosen = selectedTechSet.contains(techName)
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isChosen) Color(0xFFEFF6FF) else Color.White)
                                .border(1.dp, if (isChosen) Color(0xFF2563EB) else Color(0xFFEAECF0), RoundedCornerShape(8.dp))
                                .clickable { selectedTechSet = if (isChosen) selectedTechSet - techName else selectedTechSet + techName }
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (isChosen) Icons.Default.Check else Icons.Default.Add,
                                    contentDescription = null,
                                    tint = if (isChosen) Color(0xFF2563EB) else Color.Gray,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = techName,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = if (isChosen) Color(0xFF2563EB) else Color.Black
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel", color = Color(0xFF2563EB), fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { if (titleText.isNotBlank() && descriptionText.isNotBlank() && selectedTechSet.isNotEmpty()) onSaveAction(titleText, descriptionText, selectedTechSet.toList()) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB)),
                        shape = RoundedCornerShape(24.dp),
                        enabled = titleText.isNotBlank() && descriptionText.isNotBlank() && selectedTechSet.isNotEmpty()
                    ) {
                        Text("Save", fontWeight = FontWeight.Bold, color = Color.White)
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
    Row(modifier = Modifier.clip(RoundedCornerShape(16.dp)).background(bgColor).padding(horizontal = 8.dp, vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(textColor))
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = status, color = textColor, fontSize = 11.sp, fontWeight = FontWeight.Bold)
    }
}