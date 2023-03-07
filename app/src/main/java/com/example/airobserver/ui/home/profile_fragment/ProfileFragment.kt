package com.example.airobserver.ui.home.profile_fragment

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.airobserver.R
import com.example.airobserver.databinding.FragmentLoginBinding
import com.example.airobserver.databinding.FragmentProfileBinding
import com.example.airobserver.di.SharedPref
import com.example.airobserver.ui.BaseFragment
import com.example.airobserver.ui.auth.LoginFragmentDirections
import com.example.airobserver.ui.viewmodel.AuthViewModel
import com.example.airobserver.utils.ApiResponseStates
import com.example.airobserver.utils.putData
import com.example.airobserver.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment() {
    lateinit var binding: FragmentProfileBinding
    private val viewModel: AuthViewModel by viewModels()
    @Inject
    lateinit var pref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding=FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getProfile(pref.getString(SharedPref.EMAIL,"").toString())
        getProfileResponse()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getProfileResponse() {
        lifecycleScope.launch {
            viewModel.getProfileResponse.flowWithLifecycle(
                lifecycle,
                Lifecycle.State.STARTED
            )
                .collectLatest {
                    dataResponseHandling(this@ProfileFragment.requireActivity(),
                        it,
                        binding.progressBar.progressBar,
                        {
                            viewModel.getProfile(pref.getString(SharedPref.EMAIL,"").toString())
                        },
                        { it1 ->
                            binding.edtFirstname.setText(it1.firstname.toString())
                            binding.edtLastname.setText(it1.lastname.toString())
                            binding.edtEmail.setText(it1.email.toString())
                            binding.edtPhone.setText(it1.phone.toString())
                            binding.edtDate.setText(it1.Birthday)
                            binding.autoCompleteGender.setText(it1.gender.toString())
                        })
                }

        }
    }


}