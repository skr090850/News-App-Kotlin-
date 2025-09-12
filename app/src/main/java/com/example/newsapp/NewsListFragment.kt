package com.example.newsapp.ui

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.NewsApplication
import com.example.newsapp.R
import com.example.newsapp.util.Resource

class NewsListFragment : Fragment(R.layout.fragment_news_list) {

    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newsRepository = (requireActivity().application as NewsApplication).newsRepository
        val viewModelFactory = NewsViewModelFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(NewsViewModel::class.java)

        setupRecyclerView()

        newsAdapter.setOnItemClickListener { article ->
            val bundle = Bundle().apply {
                putString("article_url", article.webUrl)
            }
            val detailFragment = NewsDetailFragment().apply {
                arguments = bundle
            }

            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_container, detailFragment)
                addToBackStack(null)
                commit()
            }
        }

        val progressBar: ProgressBar = view.findViewById(R.id.progress_bar)
        val errorTextView: TextView = view.findViewById(R.id.error_text_view)

        viewModel.articles.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    errorTextView.visibility = View.GONE
                }
                is Resource.Success -> {
                    progressBar.visibility = View.GONE
                    errorTextView.visibility = View.GONE
                    newsAdapter.submitList(resource.data)
                }
                is Resource.Error -> {
                    progressBar.visibility = View.GONE
                    errorTextView.visibility = View.VISIBLE
                    errorTextView.text = resource.message
                }
            }
        }
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        val recyclerView: RecyclerView = requireView().findViewById(R.id.news_recycler_view)
        recyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}

