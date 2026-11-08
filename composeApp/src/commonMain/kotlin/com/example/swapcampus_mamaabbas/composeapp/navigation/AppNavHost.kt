package com.example.swapcampus_mamaabbas.composeapp.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.swapcampus_mamaabbas.composeapp.AppContainer
import com.example.swapcampus_mamaabbas.composeapp.ui.components.AppNavigationBar
import com.example.swapcampus_mamaabbas.composeapp.ui.screens.DetailScreen
import com.example.swapcampus_mamaabbas.composeapp.ui.screens.ListingFormScreen
import com.example.swapcampus_mamaabbas.composeapp.ui.screens.ListingsScreen
import com.example.swapcampus_mamaabbas.composeapp.ui.screens.LoginScreen
import com.example.swapcampus_mamaabbas.composeapp.ui.screens.MyListingsScreen
import com.example.swapcampus_mamaabbas.composeapp.ui.screens.ProfileScreen
import com.example.swapcampus_mamaabbas.composeapp.viewmodel.AuthViewModel
import com.example.swapcampus_mamaabbas.composeapp.viewmodel.DetailViewModel
import com.example.swapcampus_mamaabbas.composeapp.viewmodel.ListingFormViewModel
import com.example.swapcampus_mamaabbas.composeapp.viewmodel.ListingsViewModel
import com.example.swapcampus_mamaabbas.composeapp.viewmodel.MyListingsViewModel
import com.example.swapcampus_mamaabbas.shared.domain.UserAccount
import com.example.swapcampus_mamaabbas.shared.domain.UserRole
import com.example.swapcampus_mamaabbas.shared.domain.canPost
import com.example.swapcampus_mamaabbas.shared.domain.sellerStats

private val tabRoutes = listOf(ListingsRoute, MyListingsRoute, ProfileRoute, LoginRoute)

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val authViewModel = viewModel { AuthViewModel(AppContainer.authRepository) }
    val currentUser by authViewModel.currentUser.collectAsStateWithLifecycle()
    val canPost = currentUser?.role?.canPost ?: true

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentTab = tabRoutes.firstOrNull { backStackEntry?.destination?.hasRoute(it::class) == true }

    Scaffold(
        contentWindowInsets = WindowInsets(0),
        bottomBar = {
            if (currentTab != null) {
                AppNavigationBar(
                    currentRoute = currentTab,
                    isLoggedIn = currentUser != null,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(ListingsRoute)
                            launchSingleTop = true
                        }
                    },
                )
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ListingsRoute,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable<ListingsRoute> {
                val viewModel = viewModel { ListingsViewModel(AppContainer.listingRepository) }
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val keyword by viewModel.keyword.collectAsStateWithLifecycle()
                val category by viewModel.category.collectAsStateWithLifecycle()

                LaunchedEffect(Unit) { viewModel.refresh() }

                ListingsScreen(
                    uiState = uiState,
                    keyword = keyword,
                    category = category,
                    userName = currentUser?.name,
                    canPost = canPost,
                    onKeywordChange = viewModel::onKeywordChange,
                    onCategoryChange = viewModel::onCategoryChange,
                    onListingClick = { navController.navigate(DetailRoute(it)) },
                    onPostClick = { navController.navigate(PostRoute) },
                    modifier = Modifier.statusBarsPadding(),
                )
            }

            composable<LoginRoute> {
                LaunchedEffect(currentUser) {
                    if (currentUser != null) {
                        navController.navigate(ProfileRoute) {
                            popUpTo(LoginRoute) { inclusive = true }
                        }
                    }
                }
                LoginContent(authViewModel)
            }

            composable<MyListingsRoute> {
                RequireLogin(currentUser, authViewModel) { user ->
                    val viewModel = viewModel {
                        MyListingsViewModel(AppContainer.listingRepository, AppContainer.authRepository)
                    }
                    val listings by viewModel.listings.collectAsStateWithLifecycle()

                    LaunchedEffect(user.id) { viewModel.refresh() }

                    MyListingsScreen(
                        listings = listings,
                        canPost = user.role.canPost,
                        onListingClick = { navController.navigate(DetailRoute(it)) },
                        onPostClick = { navController.navigate(PostRoute) },
                        modifier = Modifier.statusBarsPadding(),
                    )
                }
            }

            composable<ProfileRoute> {
                RequireLogin(currentUser, authViewModel) { user ->
                    val viewModel = viewModel {
                        MyListingsViewModel(AppContainer.listingRepository, AppContainer.authRepository)
                    }
                    val listings by viewModel.listings.collectAsStateWithLifecycle()

                    LaunchedEffect(user.id) { viewModel.refresh() }

                    ProfileScreen(
                        user = user,
                        stats = listings.sellerStats().takeIf { user.role == UserRole.STUDENT },
                        onLogout = {
                            authViewModel.logout()
                            navController.navigate(ListingsRoute) {
                                popUpTo(ListingsRoute) { inclusive = true }
                            }
                        },
                        modifier = Modifier.statusBarsPadding(),
                    )
                }
            }

            composable<DetailRoute> { entry ->
                val id = entry.toRoute<DetailRoute>().id
                val viewModel = viewModel {
                    DetailViewModel(AppContainer.listingRepository, AppContainer.authRepository)
                }
                val listing by viewModel.listing.collectAsStateWithLifecycle()
                val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
                val seller by viewModel.seller.collectAsStateWithLifecycle()
                val isOwner by viewModel.isOwner.collectAsStateWithLifecycle()

                LaunchedEffect(id) { viewModel.load(id) }

                DetailScreen(
                    listing = listing,
                    isLoading = isLoading,
                    seller = seller,
                    isOwner = isOwner,
                    onReserve = viewModel::reserve,
                    onMarkSold = viewModel::markSold,
                    onMakeAvailable = viewModel::makeAvailable,
                    onEdit = { navController.navigate(EditRoute(id)) },
                    onDelete = { viewModel.remove { navController.popBackStack() } },
                    onBack = { navController.popBackStack() },
                )
            }

            composable<PostRoute> {
                RequireLogin(currentUser, authViewModel) {
                    val viewModel = viewModel {
                        ListingFormViewModel(AppContainer.listingRepository, AppContainer.authRepository)
                    }
                    val errors by viewModel.errors.collectAsStateWithLifecycle()

                    ListingFormScreen(
                        isEditing = false,
                        initial = null,
                        errors = errors,
                        onSave = { title, description, price, category, photoUrl ->
                            viewModel.save(title, description, price, category, photoUrl) {
                                navController.popBackStack()
                            }
                        },
                        onBack = { navController.popBackStack() },
                    )
                }
            }

            composable<EditRoute> { entry ->
                val id = entry.toRoute<EditRoute>().id

                RequireLogin(currentUser, authViewModel) {
                    val viewModel = viewModel {
                        ListingFormViewModel(AppContainer.listingRepository, AppContainer.authRepository)
                    }
                    val initial by viewModel.initial.collectAsStateWithLifecycle()
                    val errors by viewModel.errors.collectAsStateWithLifecycle()

                    LaunchedEffect(id) { viewModel.load(id) }

                    ListingFormScreen(
                        isEditing = true,
                        initial = initial,
                        errors = errors,
                        onSave = { title, description, price, category, photoUrl ->
                            viewModel.save(title, description, price, category, photoUrl) {
                                navController.popBackStack()
                            }
                        },
                        onBack = { navController.popBackStack() },
                    )
                }
            }
        }
    }
}

@Composable
private fun LoginContent(authViewModel: AuthViewModel) {
    val error by authViewModel.error.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { authViewModel.clearError() }

    LoginScreen(
        error = error,
        onLogin = { email, password -> authViewModel.login(email, password) },
        modifier = Modifier.statusBarsPadding(),
    )
}

@Composable
private fun RequireLogin(
    user: UserAccount?,
    authViewModel: AuthViewModel,
    content: @Composable (UserAccount) -> Unit,
) {
    if (user == null) {
        LoginContent(authViewModel)
    } else {
        content(user)
    }
}
