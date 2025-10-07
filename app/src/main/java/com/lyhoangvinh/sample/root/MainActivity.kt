package com.lyhoangvinh.sample.root

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import com.google.accompanist.insets.ProvideWindowInsets
import com.lyhoangvinh.sample.domain.usecases.GetCategoriesAvg
import com.lyhoangvinh.sample.navigator.NavigatorHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigatorHelper: NavigatorHelper

    @Inject
    lateinit var getCategoriesAvg: GetCategoriesAvg

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen()
        setContent {
            val navController = rememberNavController()

            LaunchedEffect(Unit) {
                navigatorHelper.handleNavigationCommands(navController)
            }

            MaterialTheme {
                ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
                    NavHost(
                        navController = navController,
                        startDestination = "home_screen",
                    ) {
                        composable(route = "home_screen") {
                            CompareDemoScreen(navController)
                        }
                    }
                }
            }

        }
    }
}
