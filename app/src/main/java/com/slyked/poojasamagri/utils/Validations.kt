package com.slyked.poojasamagri.utils

class Validations {
    companion object {
        fun phoneNumberValidator(phoneNumber: String): Boolean {
            var result = false;

            if (phoneNumber.isNotBlank() && phoneNumber.length == 10) {
                result = true;
            }
            return result;
        }
    }



}