package com.lyhoangvinh.sample.navigator

import androidx.navigation.NamedNavArgument

sealed class MyScreen(
  route: String,
  navArguments: List<NamedNavArgument> = emptyList()
) {
  val name: String = route.appendArguments(navArguments)

}

sealed class MyRoute(val name: String)

private fun String.appendArguments(navArguments: List<NamedNavArgument>): String {
  val mandatoryArguments = navArguments.filter { it.argument.defaultValue == null }
    .takeIf { it.isNotEmpty() }
    ?.joinToString(separator = "/", prefix = "/") { "{${it.name}}" }
    .orEmpty()
  val optionalArguments = navArguments.filter { it.argument.defaultValue != null }
    .takeIf { it.isNotEmpty() }
    ?.joinToString(separator = "&", prefix = "?") { "${it.name}={${it.name}}" }
    .orEmpty()
  return "$this$mandatoryArguments$optionalArguments"
}