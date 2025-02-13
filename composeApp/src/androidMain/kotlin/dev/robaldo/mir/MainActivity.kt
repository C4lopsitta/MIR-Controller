package dev.robaldo.mir

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

@Composable
actual fun storePreferences(): DataStore<Preferences> {
    val context = LocalContext.current
    return remember {
        instantiatePreferences(
            createPath = {
                context.filesDir.resolve(preferencesFile).absolutePath
            },
        )
    }
}


class MainActivity : ComponentActivity() {
    @Composable
    private fun getSysTheme(context: Context): ColorScheme {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return getMuiYouTheme(context)
        }

        return if(isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
    }

    @Composable
    @RequiresApi(Build.VERSION_CODES.S)
    private fun getMuiYouTheme(context: Context): ColorScheme {
        return if (isSystemInDarkTheme()) {
            dynamicDarkColorScheme(context)
        } else dynamicLightColorScheme(context)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current
            val colorScheme = getSysTheme(context)



            App(colorScheme=colorScheme)
        }
    }
}
