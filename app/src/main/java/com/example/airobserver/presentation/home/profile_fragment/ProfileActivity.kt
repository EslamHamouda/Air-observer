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
import com.airbnb.lottie.LottieAnimationView
import com.example.airobserver.R
import com.example.airobserver.databinding.ActivityProfileBinding
import com.example.airobserver.di.SharedPref
import com.example.airobserver.domain.model.BaseResponse
import com.example.airobserver.presentation.auth.AuthActivity
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

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getProfileResponse() {
        lifecycleScope.launch {
            viewModel.getProfileResponse.flowWithLifecycle(
                lifecycle,
                Lifecycle.State.STARTED
            )
                .collectLatest {
                   /* when(it) {
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
                    }*/
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
                            if(it1.phone.toString().length!=11){
                                binding.edtPhone.setText("0".plus(it1.phone.toString()))
                            }else{
                                binding.edtPhone.setText(it1.phone.toString())
                            }
                            binding.edtDate.setText(it1.Birthday)
                            binding.autoCompleteGender.setText(it1.gender.toString())
                        })
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
                  /*  when(it) {
                        is ApiResponseStates.Success -> {
                            showSnackbar("User information updated successfully and email was sent.",this@ProfileActivity)
                            binding.btnEdit.visibility = View.INVISIBLE
                            disableEditText(binding.tilFirstname)
                            disableEditText(binding.tilLastname)
                            disableEditText(binding.tilPhone)
                            getProfile()
                        }
                        else -> {}
                    }*/
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
                        })
                }

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
            binding.tilFirstname.error = "Invalid first name"
            return false
        } else if (lastName.length < 3) {
            binding.tilLastname.error = "Invalid last name"
            return false
        }
        else if (!isValidEmail(email)) {
            binding.tilEmail.error = "Enter a valid email"
            return false
        }
        else if (!isValidPhoneNumber(phone)) {
            binding.tilPhone.error = "Phone not correct"
            return false
        }
        else if (!isValidDate(date)) {
            binding.tilDate.error = "Not a valid date: 2000-05-01"
            return false
        }
        return true
    }

    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        val pattern = Regex("^(01)[0-9]{9}$")
        return pattern.matches(phoneNumber)
    }

    @SuppressLint("SimpleDateFormat")
    private fun isValidDate(date: String): Boolean {
        val regex = "^\\d{4}-\\d{2}-\\d{2}$"
        if (!date.matches(regex.toRegex())) {
            return false
        }
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        sdf.isLenient = false
        try {
            sdf.parse(date)
        } catch (e: ParseException) {
            return false
        }
        return true
    }

    private fun isValidEmail(email: String): Boolean {
        val regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$"
        return email.matches(regex.toRegex())
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
            is ApiResponseStates.NetworkError -> {
                progressBar?.let { it1 -> hideProgress(it1) }
                showSnackbar(it.throwable.handling(activity), activity) { tryAgain() }

            }
        }
    }
}