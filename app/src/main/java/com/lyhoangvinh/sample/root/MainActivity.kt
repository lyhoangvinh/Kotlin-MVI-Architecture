package com.lyhoangvinh.sample.root

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.lyhoangvinh.sample.domain.model.CategoryAvgModel
import com.lyhoangvinh.sample.domain.usecases.GetCategoriesAvg
import com.lyhoangvinh.sample.navigator.NavigatorHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigatorHelper: NavigatorHelper

    @Inject
    lateinit var getCategoriesAvg: GetCategoriesAvg

    private val categoriesState = mutableStateListOf<CategoryAvgModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen()
        lifecycleScope.launch(Dispatchers.IO) {
            val categories = getCategoriesAvg()
            withContext(Dispatchers.Main) {
                categories?.let {
                    categoriesState.clear()
                    categoriesState.addAll(it)
                }
            }
        }
        setContent {
//      val navController = rememberNavController()
//
//      LaunchedEffect(Unit) {
//        navigatorHelper.handleNavigationCommands(navController)
//      }
//
//      ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
//        NavHost(
//          navController = navController,
//          startDestination = MyRoute.OnBoarding.name,
//        ) {
//
//        }
//      }
            CharacterContent(categoriesState)
        }
    }
}

@Composable
fun CharacterContent(
    categories: List<CategoryAvgModel>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 4.dp),
    ) {
        items(count = categories.size) { index ->
            CharacterRow(
                dto = categories[index]
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CharacterRow(
    dto: CategoryAvgModel
) {
    Card(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(
                vertical = 4.dp,
                horizontal = 8.dp
            ),
        elevation = 8.dp
    ) {
        Row {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(dto.coverUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(size = 8.dp))
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 12.dp, start = 4.dp, bottom = 4.dp)
            ) {
                Text(
                    text = dto.name.orEmpty(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 2.dp),
                    style = JetRortyTypography.subtitle1
                )
                Text(
                    text = dto.shortname.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 2.dp),
                    style = JetRortyTypography.subtitle1
                )
            }
        }
    }
}

val JetRortyTypography: Typography
    @Composable get() = MaterialTheme.typography
