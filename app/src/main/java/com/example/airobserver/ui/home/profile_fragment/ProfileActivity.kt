package com.example.airobserver.ui.home.profile_fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.airobserver.databinding.ActivityHomeBinding
import com.example.airobserver.databinding.ActivityProfileBinding
import com.example.airobserver.di.SharedPref
import com.example.airobserver.domain.model.BaseResponse
import com.example.airobserver.ui.viewmodel.AuthViewModel
import com.example.airobserver.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding
    private val viewModel: AuthViewModel by viewModels()
    @Inject
    lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.materialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStart() {
        super.onStart()
        pref.getString("EMAIL","")?.let { viewModel.getProfile(it) }
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
                    when(it) {
                        is ApiResponseStates.Success -> {
                            showSnackbar("Authorization has been accepted for this request.",this@ProfileActivity)
                            binding.edtFirstname.setText(it.value?.data?.firstname.toString())
                            binding.edtLastname.setText(it.value?.data?.lastname.toString())
                            binding.edtEmail.setText(it.value?.data?.email.toString())
                            binding.edtPhone.setText(it.value?.data?.phone.toString())
                            binding.edtDate.setText(it.value?.data?.Birthday)
                            binding.autoCompleteGender.setText(it.value?.data?.gender.toString())
                        }
                        else -> {}
                    }
                    dataResponseHandling(this@ProfileActivity,
                        it,
                        binding.progressBar.progressBar,
                        {
                            try {
                                it as ApiResponseStates.Success
                                it.value?.message?.let { it1 ->
                                    showSnackbar(
                                        it1,this@ProfileActivity
                                    ) {
                                        viewModel.getProfile(
                                            pref.getString(SharedPref.EMAIL, "").toString()
                                        )
                                    }
                                }
                            }catch (e:Exception){
                                Log.d("mm",e.message.toString())
                            }
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

    @RequiresApi(Build.VERSION_CODES.M)
    private fun <B : BaseResponse<T>, T> dataResponseHandling(
        activity: Activity,
        it: ApiResponseStates<B>,
        progressBar: ProgressBar?,
        tryAgain: () -> Unit,
        successFunc: (T) -> Unit,
        errorCode: Int? = 0, handleError: (() -> Unit)? = null,
    ) {
        when (it) {
            is ApiResponseStates.Success -> {
                progressBar?.let { it1 -> hideProgress(it1) }
                it.value?.let {
                    (it as BaseResponse<T>).handle(activity, successFunc)
                }
            }
            is ApiResponseStates.Loading -> {
                progressBar?.let { it1 -> showProgress(it1) }
            }
            is ApiResponseStates.NetworkError -> {
                progressBar?.let { it1 -> hideProgress(it1) }
                showSnackbar(it.throwable.handling(activity), activity) { tryAgain() }

            }
        }
    }
}