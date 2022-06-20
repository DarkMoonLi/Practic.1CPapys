package com.example.newsblock.scenes.news

import NetworkStatus
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.newsblock.R
import com.example.newsblock.databinding.FragmentNewsBinding
import com.example.newsblock.scenes.newsBlock.NewsAdapter


class News : Fragment() {
    private var _binding: FragmentNewsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var webView: WebView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNewsBinding.inflate(inflater, container, false)

        binding.title.text = arguments?.getString("title")

        binding.annotationValue.text = arguments?.getString("annotation")

        binding.dataValue.text = arguments?.getString("date")

        if (arguments?.getString("type") == "0") {
            binding.typeValue.text = "Новости ЖКХ"
        } else {
            binding.typeValue.text = "Новости компании"
        }

        val imgLink = arguments?.getString("img")

        val mobileUrl = arguments?.getString("mobileUrl")

        webView = binding.image
        webView!!.setBackgroundColor(0)

        if (imgLink != null) {
            webView?.loadUrl(imgLink)
        }

        fun openNewTabWindow(urls: String, context : Context) {
            val uris = Uri.parse(urls)
            val intents = Intent(Intent.ACTION_VIEW, uris)
            val b = Bundle()
            b.putBoolean("new_window", true)
            intents.putExtras(b)
            context.startActivity(intents)
        }

        binding.open.setOnClickListener() {
            if (mobileUrl != null) {
                context?.let { it1 -> openNewTabWindow(mobileUrl, it1) }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.back.setOnClickListener() {
            findNavController().navigate(R.id.action_news_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}