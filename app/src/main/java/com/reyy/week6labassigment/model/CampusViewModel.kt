package com.reyy.week6labassigment.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.reyy.week6labassigment.data.Community
import com.reyy.week6labassigment.data.Project

class CampusViewModel : ViewModel() {
    private val _communities = MutableStateFlow(
        listOf(
            Community("c1", "GDSC", "Fokus pada ekosistem Google: Android Compose, Flutter, Firebase, dan Google Cloud Platform.", 142, true, "Google Developer Student Club"),
            Community("c2", "CyberSecurity Guild", "Eksplorasi ethical hacking, CTF challenges, reverse engineering, dan pertahanan jaringan kampus.", 88, true, "Security & Pentesting"),
            Community("c3", "AI Society", "Riset terapan deep learning, computer vision, agen cerdas, dan fine-tuning large language models.", 115, false, "Machine Learning & LLM"),
            Community("c4", "Mobile Dev Community", "Native Jetpack Compose & iOS", 95, true, "Jetpack Compose & KMP"),
            Community("c5", "SU IMT", "Badan eksekutif mahasiswa yang mengatur organisasi dan kegiatan kemahasiswaan.", 200, false, "Student Union"),
            Community("c6", "Mentoring Department", "Fokus pada program bimbingan, pengembangan akademik, dan mentoring antar mahasiswa.", 75, false, "Education"),
            Community("c7", "Student Affairs", "Pusat layanan administrasi, regulasi, dan urusan kegiatan kemahasiswaan kampus.", 50, false, "Administration")
        )
    )
    val communities: StateFlow<List<Community>> = _communities.asStateFlow()

    private val _projects = MutableStateFlow(
        listOf(
            Project("proj-gdsc-01", "c1", "Smart Campus Navigation", "Sistem navigasi peta gedung kampus interaktif berbasis Jetpack Compose dan Google Maps SDK. Memfasilitasi mahasiswa dan dosen menemukan ruang perkuliahan, laboratorium komputer, perpustakaan, serta fasilitas umum kampus secara akurat disertai rute tercepat dan visualisasi lokasi real-time.", listOf("Kotlin", "Jetpack Compose", "Room", "Google Maps SDK"), false),
            Project("proj-gdsc-02", "c1", "Student Attendance QR Scanner", "Pemindai barcode & QR presensi kehadiran workshop berbasis CameraX & Firebase.", listOf("Jetpack Compose", "Firebase", "CameraX"), true),
            Project("proj-gdsc-03", "c1", "Developer RSVP Portal", "Portal pendaftaran webinar dan bootcamp teknologi sinkronisasi Ktor API server.", listOf("Kotlin", "Ktor", "Compose"), false)
        )
    )
    val projects: StateFlow<List<Project>> = _projects.asStateFlow()

    fun toggleJoinCommunity(communityId: String) {
        _communities.update { list ->
            list.map { if (it.id == communityId) it.copy(isJoined = !it.isJoined) else it }
        }
    }

    fun toggleProjectStatus(projectId: String) {
        _projects.update { list ->
            list.map { if (it.id == projectId) it.copy(isCompleted = !it.isCompleted) else it }
        }
    }

    fun addProject(communityId: String, title: String, description: String, techStack: List<String>) {
        val newProject = Project(
            id = "proj-${System.currentTimeMillis().toString().takeLast(6)}",
            communityId = communityId,
            title = title,
            description = description,
            techStack = techStack,
            isCompleted = false
        )
        _projects.update { it + newProject }
    }

    fun deleteProject(projectId: String) {
        _projects.update { list -> list.filter { it.id != projectId } }
    }
}