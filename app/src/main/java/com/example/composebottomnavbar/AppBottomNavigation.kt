package com.example.composebottomnavbar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

const val NAV_HOME = "home"
const val NAV_FAV = "fav"
const val NAV_FEED = "feed"
const val NAV_PROFILE = "profile"


sealed class NavItem(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val navRoute: String
) {
    object Home : NavItem(R.string.home, R.drawable.ic_home, NAV_HOME)
    object Fav : NavItem(R.string.fav, R.drawable.ic_fav, NAV_FAV)
    object Feed : NavItem(R.string.feed, R.drawable.ic_feed, NAV_FEED)
    object Profile : NavItem(R.string.profile, R.drawable.ic_profile, NAV_PROFILE)
}

@Composable
fun AppBottomNavigation(navController: NavController) {
    val navItems = listOf(NavItem.Home, NavItem.Fav, NavItem.Feed, NavItem.Profile)

    BottomNavigation(
        backgroundColor = colorResource(id = R.color.teal_700),
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        navItems.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(id = item.icon),
                        contentDescription = stringResource(item.title)
                    )
                },
                label = {
                    Text(text = stringResource(item.title), fontSize = 9.sp)
                },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.5f),
                alwaysShowLabel = false,
                selected = currentRoute == item.navRoute,
                onClick = {
                    navController.navigate(item.navRoute) {
                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }
    }
}