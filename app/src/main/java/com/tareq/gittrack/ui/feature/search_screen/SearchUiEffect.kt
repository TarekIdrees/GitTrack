package com.tareq.gittrack.ui.feature.search_screen

import com.tareq.gittrack.ui.base.BaseUiEffect

sealed class SearchUiEffect : BaseUiEffect {
    object ShowToastEffect : SearchUiEffect()
}