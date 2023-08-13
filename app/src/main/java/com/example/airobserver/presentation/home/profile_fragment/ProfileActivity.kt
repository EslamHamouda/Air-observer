package com.example.airobserver.presentation.home.profile_fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.example.airobserver.R
import com.example.airobserver.databinding.ActivityProfileBinding
import com.example.airobserver.di.SharedPref
import com.example.airobserver.domain.model.BaseResponse
import com.example.airobserver.presentation.auth.AuthActivity
import com.example.airobserver.presentation.auth.RegisterFragmentDirections
import com.example.airobserver.presentation.viewmodel.AuthViewModel
import com.example.airobserver.utils.*
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import javax.inject.Inject


@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding
    private val viewModel: AuthViewModel by viewModels()
    @Inject
    lateinit var pref: SharedPreferences

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.materialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.swipeRefresh.setOnRefreshListener {
            // update data
            getProfile()
            // stop refreshing when task is completed
            binding.swipeRefresh.isRefreshing = false
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStart() {
        super.onStart()

        getProfile()
        getProfileResponse()

        binding.btnEdit.setOnClickListener {
                //if (checkValidation()) {
                    viewModel.updateProfile(
                        binding.edtFirstname.text.toString(),
                        binding.edtLastname.text.toString(),
                        binding.edtEmail.text.toString(),
                        binding.edtPhone.text.toString(),
                        binding.autoCompleteGender.text.toString(),
                        binding.edtDate.text.toString(),
                    )
                    getUpdateProfileResponse()
                //}
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_profile, menu)
        return true
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.edit ->{
                    binding.btnEdit.visibility = View.VISIBLE
                    enableEditText(binding.tilFirstname)
                    enableEditText(binding.tilLastname)
                    enableEditText(binding.tilPhone)
                    binding.btnEdit.setText("Save").toString()
                true
            }
            R.id.logout -> {
               showLogoutDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getProfileResponse() {
        lifecycleScope.launch {
            viewModel.getProfileResponse.flowWithLifecycle(
                lifecycle,
                Lifecycle.State.STARTED
            )
                .collectLatest {

                    when (it) {
                        is ApiResponseStates.Loading -> binding.progressBar.progressBar.showProgressBar()
                        is ApiResponseStates.Success -> {
                            setValidationErrorsToEmpty()
                            binding.progressBar.progressBar.hideProgressBar()
                            binding.edtFirstname.setText(it.value?.data?.firstname.toString())
                            binding.edtLastname.setText(it.value?.data?.lastname.toString())
                            binding.edtEmail.setText(it.value?.data?.email.toString())
                            if(it.value?.data?.phone.toString().length!=11){
                                binding.edtPhone.setText("0".plus(it.value?.data?.phone.toString()))
                            }else{
                                binding.edtPhone.setText(it.value?.data?.phone.toString())
                            }
                            binding.edtDate.setText(it.value?.data?.Birthday)
                            binding.autoCompleteGender.setText(it.value?.data?.gender.toString())
                        }
                        is ApiResponseStates.Failure.Validation -> {
                            setValidationErrorsToEmpty()
                            binding.progressBar.progressBar.hideProgressBar()
                            setValidationErrors(it.message.toMutableMap())
                            //showSnackbar(getString(it.message.toInt()), requireActivity())
                        }
                        is ApiResponseStates.Failure.Network -> {
                            setValidationErrorsToEmpty()
                            binding.progressBar.progressBar.hideProgressBar()
                            showSnackbar(it.throwable.message.toString(), this@ProfileActivity)
                        }
                    }

                    /*dataResponseHandling(this@ProfileActivity,
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
                            if(it1.phone.toString().length!=11){
                                binding.edtPhone.setText("0".plus(it1.phone.toString()))
                            }else{
                                binding.edtPhone.setText(it1.phone.toString())
                            }
                            binding.edtDate.setText(it1.Birthday)
                            binding.autoCompleteGender.setText(it1.gender.toString())
                        })*/
                }

        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getUpdateProfileResponse() {
        lifecycleScope.launch {
            viewModel.updateProfileResponse.flowWithLifecycle(
                lifecycle,
                Lifecycle.State.STARTED
            )
                .collectLatest {

                    when (it) {
                        is ApiResponseStates.Loading -> binding.progressBar.progressBar.showProgressBar()
                        is ApiResponseStates.Success -> {
                            setValidationErrorsToEmpty()
                            binding.progressBar.progressBar.hideProgressBar()
                            showSnackbar(it.value?.message.toString(),this@ProfileActivity)
                            binding.btnEdit.visibility = View.INVISIBLE
                            disableEditText(binding.tilFirstname)
                            disableEditText(binding.tilLastname)
                            disableEditText(binding.tilPhone)
                            getProfile()
                        }
                        is ApiResponseStates.Failure.Validation -> {
                            setValidationErrorsToEmpty()
                            binding.progressBar.progressBar.hideProgressBar()
                            setValidationErrors(it.message.toMutableMap())
                            //showSnackbar(getString(it.message.toInt()), requireActivity())
                        }
                        is ApiResponseStates.Failure.Network -> {
                            setValidationErrorsToEmpty()
                            binding.progressBar.progressBar.hideProgressBar()
                            showSnackbar(it.throwable.message.toString(), this@ProfileActivity)
                        }
                    }

                    /*dataResponseHandling(this@ProfileActivity,
                        it,
                        binding.progressBar.progressBar,
                        {
                            try {
                                it as ApiResponseStates.Success
                                it.value?.message?.let { it1 ->
                                    showSnackbar(
                                        it1,this@ProfileActivity
                                    ) {
                                        if (checkValidation()) {
                                            viewModel.updateProfile(
                                                binding.edtFirstname.text.toString(),
                                                binding.edtLastname.text.toString(),
                                                binding.edtEmail.text.toString(),
                                                binding.edtPhone.text.toString(),
                                                binding.autoCompleteGender.text.toString(),
                                                binding.edtDate.text.toString(),
                                            )
                                            getUpdateProfileResponse()
                                        }
                                    }
                                }
                            }catch (e:Exception){
                               e.message
                            }
                        },
                        { it1 ->
                            it as ApiResponseStates.Success
                            showSnackbar(it.value?.message.toString(),this@ProfileActivity)
                            binding.btnEdit.visibility = View.INVISIBLE
                            disableEditText(binding.tilFirstname)
                            disableEditText(binding.tilLastname)
                            disableEditText(binding.tilPhone)
                            getProfile()
                        })*/
                }

        }
    }

    private fun setValidationErrors(map:MutableMap<String,String>) {
        map["isValidFname"]?.let { getString(it.toInt()) }
            ?.let { binding.tilFirstname.setValidationError(it) }
        map["isValidLname"]?.let { getString(it.toInt()) }
            ?.let { binding.tilLastname.setValidationError(it) }
        map["isValidEmail"]?.let { getString(it.toInt()) }
            ?.let { binding.tilEmail.setValidationError(it) }
        map["isValidPhone"]?.let { getString(it.toInt()) }
            ?.let { binding.tilPhone.setValidationError(it) }
        map["isValidGender"]?.let { getString(it.toInt()) }
            ?.let { binding.tilGender.setValidationError(it) }
        map["isValidBirthdate"]?.let { getString(it.toInt()) }
            ?.let { binding.tilDate.setValidationError(it) }
    }

    private fun setValidationErrorsToEmpty() {
        binding.tilFirstname.error = null
        binding.tilLastname.error = null
        binding.tilEmail.error = null
        binding.tilPhone.error = null
        binding.tilGender.error = null
        binding.tilDate.error = null
    }

    private fun enableEditText(editText: TextInputLayout) {
        editText.isEnabled = true
    }
    private fun disableEditText(editText: TextInputLayout) {
        editText.isEnabled = false
    }
    private fun removeFirstAndLastChar(str: String?): String {
        return if (str?.length!! <= 2) {"" } else {
            str.substring(1, str.length - 1)
        }
    }

    private fun checkValidation(): Boolean {
        binding.tilFirstname.error = null
        binding.tilLastname.error = null
        binding.tilEmail.error = null
        binding.tilPhone.error = null
        binding.tilDate.error = null
        binding.tilGender.error = null
        val firstName = binding.edtFirstname.text.toString()
        val lastName = binding.edtLastname.text.toString()
        val email = binding.edtEmail.text.toString()
        val phone = binding.edtPhone.text.toString()
        val date = binding.edtDate.text.toString()

        if (firstName.length < 3) {
            binding.tilFirstname.error = getString(R.string.invalid_first_name)
            return false
        } else if (lastName.length < 3) {
            binding.tilLastname.error = getString(R.string.invalid_last_name)
            return false
        }
        else if (!isValidEmail(email)) {
            binding.tilEmail.error = getString(R.string.enter_a_valid_email)
            return false
        }
        else if (!isValidPhoneNumber(phone)) {
            binding.tilPhone.error = getString(R.string.phone_not_correct)
            return false
        }
        else if (!isValidDate(date)) {
            binding.tilDate.error = getString(R.string.not_a_valid_date_2000_05_01)
            return false
        }
        return true
    }


    private fun getProfile(){
        pref.getString("EMAIL","")?.let { viewModel.getProfile(removeFirstAndLastChar(pref.getString("EMAIL",""))) }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showLogoutDialog() {
        val alert = AlertDialog.Builder(binding.root.context, R.style.AlertDialog)
            .setTitle(getString(R.string.logout))
            .setPositiveButton("Yes") { _, _ ->
                logout(pref,this)
                startActivity(Intent(this, AuthActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))

            }
            .setNegativeButton("No") { _, _ -> }
            .create()
        alert.show()
        alert.window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun <B : BaseResponse<T>, T> dataResponseHandling(
        activity: Activity,
        it: ApiResponseStates<B>,
        progressBar: LottieAnimationView,
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
            is ApiResponseStates.Failure.Network -> {
                progressBar?.let { it1 -> hideProgress(it1) }
                showSnackbar(it.throwable.handling(activity), activity) { tryAgain() }

            }

            else -> {}
        }
    }
}