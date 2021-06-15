package com.example.a2by3_android.ui.privacypolicy

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import com.example.a2by3_android.base.BaseFragment
import com.example.a2by3_android.databinding.FragmentWebViewBinding
import com.example.a2by3_android.extensions.hide
import com.example.a2by3_android.repository.EmptyRepository
import com.example.a2by3_android.util.Constant.WEBVIEW_URL
import kotlinx.android.synthetic.main.fragment_web_view.ivBack
import kotlinx.android.synthetic.main.fragment_web_view.progressBar
import kotlinx.android.synthetic.main.fragment_web_view.webView

class WebViewFragment : BaseFragment<FragmentWebViewBinding , EmptyRepository>() {
  override fun getFragmentBinding(
    inflater: LayoutInflater,
    container: ViewGroup?
  ): FragmentWebViewBinding {
    return FragmentWebViewBinding.inflate(inflater, container, false)
  }

  override fun getRepository(): EmptyRepository {
    return EmptyRepository()
  }

  override fun onPostInit() {
    webView.settings.javaScriptEnabled = true

    webView.webViewClient = object : WebViewClient() {
      override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        view?.loadUrl(url!!)
        return true
      }
    }

    val url = arguments?.getString(WEBVIEW_URL)
    url?.let {
      webView.loadUrl(it)
    }

    webView.webViewClient = object : WebViewClient() {
      override fun onPageFinished(view: WebView, url: String) {
        // do your stuff here
        progressBar.hide()
      }
    }

    ivBack.setOnClickListener {
      findNavController().navigateUp()
    }
  }

  override fun onOptionsSelected(item: MenuItem) {
  }

  override fun onActivityCreation() {
  }
}