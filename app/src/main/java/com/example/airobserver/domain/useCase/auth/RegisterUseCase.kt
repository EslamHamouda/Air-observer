package com.example.airobserver.domain.useCase.auth

import com.example.airobserver.R
import com.example.airobserver.domain.model.BaseResponse
import com.example.airobserver.domain.model.response.LoginResponse
import com.example.airobserver.domain.repo.AuthRepository
import com.example.airobserver.utils.ApiResponseStates
import com.example.airobserver.utils.isValidDate
import com.example.airobserver.utils.isValidEmail
import com.example.airobserver.utils.isValidName
import com.example.airobserver.utils.isValidPassword
import com.example.airobserver.utils.isValidPhoneNumber
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(fname:String,
                                lname:String,
                                email:String,
                                phone:String,
                                password:String,
                                birthdate: String,
                                gender:String): ApiResponseStates<BaseResponse<String>> {
        val validationFailure = mutableMapOf<String, String>()
        return try {
            if (!isValidName(fname)) {
                validationFailure["isValidFname"]=R.string.invalid_first_name.toString()
                ApiResponseStates.ValidationFailure(validationFailure)
                //ApiResponseStates.ValidationFailure(R.string.invalid_first_name.toString())
            }

            else if (!isValidName(lname)) {
                validationFailure["isValidLname"]=R.string.invalid_last_name.toString()
                ApiResponseStates.ValidationFailure(validationFailure)
                //ApiResponseStates.ValidationFailure(R.string.invalid_last_name.toString())
            }

            else if (!isValidEmail(email)) {
                validationFailure["isValidEmail"]=R.string.enter_a_valid_email.toString()
                ApiResponseStates.ValidationFailure(validationFailure)
                //ApiResponseStates.ValidationFailure(R.string.enter_a_valid_email.toString())
            }
            else if (!isValidPhoneNumber(phone)) {
                validationFailure["isValidPhone"]=R.string.phone_not_correct.toString()
                ApiResponseStates.ValidationFailure(validationFailure)
                //ApiResponseStates.ValidationFailure(R.string.phone_not_correct.toString())
            }

            else if (!isValidDate(birthdate)) {
                validationFailure["isValidBirthdate"]=R.string.not_a_valid_date_2000_05_01.toString()
                ApiResponseStates.ValidationFailure(validationFailure)
                //ApiResponseStates.ValidationFailure(R.string.not_a_valid_date_2000_05_01.toString())
            }

            else if (gender == "Gender") {
                validationFailure["isValidGender"]=R.string.please_choose_your_gender.toString()
                ApiResponseStates.ValidationFailure(validationFailure)
                //ApiResponseStates.ValidationFailure(R.string.please_choose_your_gender.toString())
            }

            else if (!isValidPassword(password)) {
                validationFailure["isValidPassword"]=R.string.password_should_be_8_or_more.toString()
                ApiResponseStates.ValidationFailure(validationFailure)
                //ApiResponseStates.ValidationFailure(R.string.password_should_be_8_or_more.toString())
            }
            else{
                ApiResponseStates.Success(repository.register(fname, lname, email, phone, password, birthdate, gender))
            }
        }catch (throwable:Throwable){
            ApiResponseStates.Failure(throwable)
        }
    }
}