package com.reyy.week6labassigment.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.reyy.week6labassigment.data.ClubProjectsRoute
import com.reyy.week6labassigment.data.CommunitiesRoute
import com.reyy.week6labassigment.data.DashboardRoute
import com.reyy.week6labassigment.data.ProjectDetailRoute
import com.reyy.week6labassigment.viewmodel.CampusViewModel

@Composable
fun MainAppScreen(viewModel: CampusViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val showBottomBar = currentDestination?.hasRoute<DashboardRoute>() == true || currentDestination?.hasRoute<CommunitiesRoute>() == true

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(containerColor = Color.White) {
                    NavigationBarItem(
                        selected = currentDestination?.hasRoute<DashboardRoute>() == true,
                        onClick = {
                            navController.navigate(DashboardRoute) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Dashboard") },
                        label = { Text("Dashboard") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF2563EB),
                            selectedTextColor = Color(0xFF2563EB),
                            indicatorColor = Color(0xFFEFF6FF)
                        )
                    )
                    NavigationBarItem(
                        selected = currentDestination?.hasRoute<CommunitiesRoute>() == true,
                        onClick = {
                            navController.navigate(CommunitiesRoute) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(Icons.Default.People, contentDescription = "Communities") },
                        label = { Text("Communities") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF2563EB),
                            selectedTextColor = Color(0xFF2563EB),
                            indicatorColor = Color(0xFFEFF6FF)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = DashboardRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<DashboardRoute> {
                ProfileDashboardScreen(viewModel = viewModel, navController = navController)
            }
            composable<CommunitiesRoute> {
                CommunitiesScreen(viewModel = viewModel, navController = navController)
            }
            composable<ClubProjectsRoute> { backStackEntry ->
                val route: ClubProjectsRoute = backStackEntry.toRoute()
                ClubProjectsScreen(
                    viewModel = viewModel,
                    communityId = route.communityId,
                    navController = navController
                )
            }
            composable<ProjectDetailRoute> { backStackEntry ->
                val route: ProjectDetailRoute = backStackEntry.toRoute()
                ProjectDetailScreen(
                    viewModel = viewModel,
                    projectId = route.projectId,
                    navController = navController
                )
            }
        }
    }
}