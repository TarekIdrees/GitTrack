package com.tareq.gittrack.ui.feature.search_screen


sealed class SearchUiEffect {
    data class ShowToastEffect(val message: String) : SearchUiEffect()
    data class OpenCardInBrowserEffect(val url: String) : SearchUiEffect()
}