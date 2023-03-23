package com.emberstudio.exodus

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.emberstudio.exodus.navigation.NavigationDirections
import com.emberstudio.exodus.ui.theme.ExodusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val scaffoldState = rememberScaffoldState()
            val context = LocalContext.current

            val selectedPage = rememberSaveable { mutableStateOf(0) }

            val navController = rememberNavController()

            Scaffold(
                modifier = Modifier,
                scaffoldState = scaffoldState,
                topBar = {
                    TopAppBar(modifier = Modifier) {
                        Text(text = "Exodus")
                    }
                },
                bottomBar = {
                    BottomNavigation(modifier = Modifier) {
                        navItems.forEachIndexed { index, navItem ->
                            BottomNavigationItem(
                                selected = index == selectedPage.value,
                                selectedContentColor = Color.White,
                                unselectedContentColor = Color.Gray,
                                onClick = {
                                    selectedPage.value = index
                                    navController.navigate(navItem.destination)
                                    Toast.makeText(context, navItem.label, Toast.LENGTH_SHORT).show()
                                },
                                label = { Text(text = stringResource(id = navItem.label)) },
                                icon = { painterResource(id = navItem.icon) }
                            )

                        }
                    }
                }
            ){ paddingValues ->
                ExodusApp(navController)
            }

        }
    }
}
