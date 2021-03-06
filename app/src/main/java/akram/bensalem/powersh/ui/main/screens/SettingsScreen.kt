package akram.bensalem.powersh.ui.main.screens

import akram.bensalem.powersh.LocalStrings
import akram.bensalem.powersh.ui.components.CollapsingToolbarBase
import akram.bensalem.powersh.ui.components.SettingEntrySheet
import akram.bensalem.powersh.ui.components.SettingsEntry
import akram.bensalem.powersh.ui.components.SettingsEntryLanguage
import akram.bensalem.powersh.ui.theme.Dimens
import akram.bensalem.powersh.ui.theme.PowerSHTheme
import akram.bensalem.powersh.ui.theme.Theme
import akram.bensalem.powersh.utils.Constants
import akram.bensalem.powersh.utils.Language
import akram.bensalem.powersh.utils.Sort
import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    onLanguageOptionClicked: (Int) -> Unit = { },
    onThemeOptionClicked: (Int) -> Unit = { },
    onSortOptionClicked: (Int) -> Unit = { },
    onBackButtonPressed: () -> Unit = { },
    currentTheme: Int,
    currentSortOption: Int,
    currentLanguageOption : Int,
) {
    val toolbarHeight = 250.dp
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }
    val toolbarOffsetHeightPx = remember { mutableStateOf(0f) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx.value + delta
                toolbarOffsetHeightPx.value = newOffset.coerceIn(-toolbarHeightPx, 0f)
                return Offset.Zero
            }
        }
    }

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val settingsEntryName = rememberSaveable {
        mutableStateOf("")
    }

    ModalBottomSheetLayout(
        sheetContent = {
            SettingEntrySheet(
                sheetState = sheetState,
                scope = scope,
                settingsEntryName = settingsEntryName.value,
                currentSortOption = currentSortOption,
                currentTheme = currentTheme,
                currentLanguageOption = currentLanguageOption,
                onSortOptionClicked = {
                    onSortOptionClicked(it)
                },
                onThemeOptionClicked = {
                    onThemeOptionClicked(it)
                    scope.launch {
                        sheetState.hide()
                    }
                },
                onLanguageOptionClicked = {
                    onLanguageOptionClicked(it)
                    scope.launch {
                        sheetState.hide()
                    }
                }
            )

        },
        sheetState = sheetState,
        sheetElevation = 0.dp,
        sheetBackgroundColor = Color.Transparent
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = {
                CollapsingToolbarBase(
                    toolbarHeading = LocalStrings.current.settings,
                    onBackButtonPressed = onBackButtonPressed,
                    toolbarHeight = 250.dp,
                    toolbarOffset = toolbarOffsetHeightPx.value,
                    content = {
                        Text(
                            text = LocalStrings.current.settings,
                            color = MaterialTheme.colors.onSurface,
                            style = MaterialTheme.typography.h3,
                            modifier = Modifier
                                .padding(horizontal = Dimens.SmallPadding.size)
                                .animateContentSize(
                                    animationSpec = tween(
                                        300,
                                        easing = LinearOutSlowInEasing
                                    )
                                )
                        )
                    }
                )
            }
        ) {
            LazyColumn(
                modifier = modifier
                    .nestedScroll(nestedScrollConnection),
                state = listState,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    val language = when (currentLanguageOption) {
                        1 -> Language.ARABIC
                        2 -> Language.FRENCH
                        3 -> Language.ENGLISH
                        else -> Language.FOLLOW_SYSTEM
                    }
                    SettingsEntryLanguage(
                        modifier = Modifier.padding(
                            vertical = Dimens.SmallPadding.size,
                            horizontal = Dimens.MediumPadding.size
                        ),
                        settingsEntryName = LocalStrings.current.languageOption,
                        currentSettingValue =if (language.languageName != Language.FOLLOW_SYSTEM.languageName) language.languageName else LocalStrings.current.followSystemMode,
                        currentSettingIcon = language.icon,
                        onSettingsEntryClick = {
                            settingsEntryName.value = it
                            scope.launch {
                                sheetState.show()
                            }
                        }
                    )
                }
                item {
                    val theme = when (currentTheme) {
                        1 -> Theme.LIGHT_THEME
                        2 -> Theme.DARK_THEME
                        else -> Theme.FOLLOW_SYSTEM
                    }
                    SettingsEntry(
                        modifier = Modifier.padding(
                            vertical = Dimens.SmallPadding.size,
                            horizontal = Dimens.MediumPadding.size
                        ),
                        settingsEntryName = LocalStrings.current.themeOptions,
                        currentSettingValue = when(theme.themeName){
                            Theme.LIGHT_THEME.themeName -> LocalStrings.current.lightTheme
                            Theme.DARK_THEME.themeName -> LocalStrings.current.darkTheme
                            else -> LocalStrings.current.followSystemMode
                                },
                        currentSettingIcon = theme.icon,
                        onSettingsEntryClick = {
                            settingsEntryName.value = it
                            scope.launch {
                                sheetState.show()
                            }
                        }
                    )
                }

                item {
                    val sort = when (currentSortOption) {
                        1 -> Sort.NEW_RELEASE_FIRST
                        2 -> Sort.OLD_RELEASE_FIRST
                        3 -> Sort.LOW_PRICE_FIRST
                        4 -> Sort.HIGH_PRICE_FIRST
                        else -> Sort.ALPHABETICAL_ASC
                    }
                    SettingsEntry(
                        modifier = Modifier.padding(
                            vertical = Dimens.SmallPadding.size,
                            horizontal = Dimens.MediumPadding.size
                        ),
                        settingsEntryName =LocalStrings.current.sortOptions,
                        currentSettingValue = when(sort.type){
                            Sort.ALPHABETICAL_ASC.type -> LocalStrings.current.alphabeticASC
                            Sort.HIGH_PRICE_FIRST.type -> LocalStrings.current.heightPrice
                            Sort.LOW_PRICE_FIRST.type -> LocalStrings.current.lowPrice
                            Sort.NEW_RELEASE_FIRST.type -> LocalStrings.current.firstRelease
                            Sort.OLD_RELEASE_FIRST.type -> LocalStrings.current.lastRelease
                            else -> LocalStrings.current.alphabeticASC
                             },
                        currentSettingIcon = sort.icon,
                        onSettingsEntryClick = {
                            settingsEntryName.value = it
                            scope.launch {
                                sheetState.show()
                            }
                        }
                    )
                }




            }
        }

    }
}

@ExperimentalMaterialApi
@Preview(
    uiMode = Configuration.UI_MODE_TYPE_NORMAL,
    device = Devices.PIXEL_4
)
@Composable
private fun SettingsScreenPrev() {
    PowerSHTheme {
        SettingsScreen(currentTheme = 2, currentSortOption = 1, currentLanguageOption = 1)
    }
}