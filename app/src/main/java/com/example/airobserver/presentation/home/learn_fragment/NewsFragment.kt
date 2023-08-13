package com.example.airobserver.presentation.home.learn_fragment

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.LottieAnimationView
import com.example.airobserver.R
import com.example.airobserver.databinding.FragmentNewsBinding
import com.example.airobserver.domain.model.response.Articles
import com.example.airobserver.domain.model.response.NewsResponse
import com.example.airobserver.presentation.viewmodel.NewsViewModel
import com.example.airobserver.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding
    private val viewModel: NewsViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNewsBinding.inflate(inflater)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        viewModel.getNews()
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getNews()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getNews() {
        lifecycleScope.launch {
            viewModel.newsResponse.flowWithLifecycle(
                lifecycle,
                Lifecycle.State.STARTED
            )
                .collectLatest {
                    dataResponseHandling(this@NewsFragment.requireActivity(),
                        it,
                        binding.progressBar.progressBar,
                        {
                            viewModel.getNews()
                        },
                        { it1 ->
                            if (it1.isEmpty()) {
                                binding.progressBar.progressBar.let { it1 -> hideProgress(it1) }
                                binding.rvNews.adapter = null
                                //binding.emptyLayout.tvEmpty.visibility = View.VISIBLE
                            } else {
                                //binding.emptyLayout.tvEmpty.visibility = View.GONE
                                binding.rvNews.adapter = NewsAdapter(it1,object:NewsAdapter.OnItemClickListener{
                                    override fun onItemClick(item: Articles) {
                                        val bottomSheet = BottomSheetNews.newInstance(item)
                                        bottomSheet.show(requireActivity().supportFragmentManager, "BottomSheetDialog")
                                    }
                                    override fun onItemClickShare(item: Articles) {
                                        val intent = Intent(Intent.ACTION_SEND)
                                        intent.type = "text/plain"
                                        val message = "Check out this article: ${item.url}"
                                        intent.putExtra(Intent.EXTRA_TEXT, message)
                                        startActivity(Intent.createChooser(intent,
                                            getString(R.string.share_article_with)))
                                    }
                                })
                            }
                        })
                }

        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun dataResponseHandling(
        activity: Activity,
        it: ApiResponseStates<NewsResponse>,
        progressBar: LottieAnimationView?,
        tryAgain: () -> Unit,
        successFunc: (ArrayList<Articles>) -> Unit,
        errorCode: Int? = 0, handleError: (() -> Unit)? = null,
    ) {
        when (it) {
            is ApiResponseStates.Success -> {
                progressBar?.let { it1 -> hideProgress(it1) }
                it.value?.let {
                    (it).handle(activity, successFunc)
                }
            }
            is ApiResponseStates.Loading -> {
                progressBar?.let { it1 -> showProgress(it1) }
            }
            is ApiResponseStates.Failure.Network -> {
                progressBar?.let { it1 -> hideProgress(it1) }
                showSnackbar(it.throwable.handling(activity), activity) { tryAgain() }

            }

            else -> {}
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun NewsResponse.handle(activity: Activity, successFunc: (ArrayList<Articles>) -> Unit) {
        when (status) {
            "ok" -> {
                articles.let {
                    successFunc.invoke(it)
                }
            }
            else -> status?.let {
                showSnackbar(it, activity)
            }
        }
    }
}