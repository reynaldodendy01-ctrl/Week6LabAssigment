package com.reyy.week6labassigment.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reyy.week6labassigment.view.ProfileDashboardScreen
import com.reyy.week6labassigment.view.ClubProjectsScreen
import com.reyy.week6labassigment.viewmodel.CampusViewModel

@Composable
fun AppNavigation(viewModel: CampusViewModel = viewModel()) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "dashboard") {
        composable("dashboard") {
            ProfileDashboardScreen(viewModel = viewModel, navController = navController)
        }
        composable("club_projects/{communityId}") { backStackEntry ->
            val communityId = backStackEntry.arguments?.getString("communityId") ?: ""
            ClubProjectsScreen(
                viewModel = viewModel,
                communityId = communityId,
                navController = navController
            )
        }
    }
}