package com.example.airobserver.presentation.home.learn_fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.airobserver.R
import com.example.airobserver.databinding.BottomSheetNewsBinding
import com.example.airobserver.domain.model.response.Articles
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetNews : BottomSheetDialogFragment(){
    private lateinit var binding: BottomSheetNewsBinding
    private var args:Articles? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetNewsBinding.inflate(inflater, container, false)
        args = arguments?.getParcelable("ARG")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(binding.ivNews.context)
            .load(args?.urlToImage)
            .fitCenter()
            .placeholder(R.drawable.ic_news)
            .error(R.drawable.ic_news)
            .into(binding.ivNews)
        binding.tvNews.text=args?.title
        binding.tvDetails.text=args?.description
        binding.btnDetails.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(args?.url)
            startActivity(intent)
        }

    }

    companion object {
        fun newInstance(argument: Articles): BottomSheetNews {
            val args = Bundle()
            args.putParcelable("ARG", argument)
            val fragment = BottomSheetNews()
            fragment.arguments = args
            return fragment
        }
    }
}