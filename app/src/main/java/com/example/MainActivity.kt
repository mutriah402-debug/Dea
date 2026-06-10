package com.example

import android.graphics.Color
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        Scaffold(
          modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
        ) { innerPadding ->
          QuizWebView(
            modifier = Modifier
              .fillMaxSize()
              .padding(innerPadding)
          )
        }
      }
    }
  }
}

@Composable
fun QuizWebView(modifier: Modifier = Modifier) {
  AndroidView(
    factory = { context ->
      WebView(context).apply {
        // Set Slate-900 color mapping to avoid white loading flickers
        setBackgroundColor(Color.parseColor("#0f172a"))
        settings.apply {
          javaScriptEnabled = true
          domStorageEnabled = true
          allowFileAccess = true
          allowContentAccess = true
          mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
          useWideViewPort = true
          loadWithOverviewMode = true
          setSupportZoom(false)
        }
        webViewClient = object : WebViewClient() {
          override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            return false // Let WebView handle navigations inside itself
          }
        }
        loadUrl("file:///android_asset/index.html")
      }
    },
    modifier = modifier
  )
}

