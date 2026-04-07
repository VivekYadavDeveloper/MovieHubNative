package com.example.movieapp.util



import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel as composeHiltViewModel
import androidx.lifecycle.ViewModel

/**
 * Wrapper function for hiltViewModel - Use this in all screens
 */
@Composable
inline fun <reified VM : ViewModel> hiltViewModel(): VM {
    return composeHiltViewModel<VM>()
}