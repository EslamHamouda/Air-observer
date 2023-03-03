package com.example.airobserver.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.airobserver.R
import com.example.airobserver.databinding.FragmentForgotPasswordBinding
import com.example.airobserver.databinding.FragmentLoginBinding


class ForgotPasswordFragment : Fragment() {
    lateinit var binding: FragmentForgotPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentForgotPasswordBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSubmit.setOnClickListener {
            findNavController().navigate(ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToVerificationFragment("test#gmail.com"))
        }
    }


}