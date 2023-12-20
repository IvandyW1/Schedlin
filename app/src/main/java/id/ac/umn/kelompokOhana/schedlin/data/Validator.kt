package id.ac.umn.kelompokOhana.schedlin.data

//Digunakan untuk menentukan ketentuan dan aturan dari informasi user
//Hal ini dicek pada saat register dan login
object Validator {
    //Menyimpan ketentuan dan melakukan validasi untuk first name
    fun validateFirstName(fName: String): ValidationResult {
        return ValidationResult(
            (!fName.isNullOrEmpty() && fName.length >= 2)
        )

    }

    //Menyimpan ketentuan dan melakukan validasi untuk last name
    fun validateLastName(lName: String): ValidationResult {
        return ValidationResult(
            (!lName.isNullOrEmpty() && lName.length >= 2)
        )
    }

    //Menyimpan ketentuan dan melakukan validasi untuk email
    fun validateEmail(email: String): ValidationResult {
        return ValidationResult(
            (!email.isNullOrEmpty())
        )
    }

    //Menyimpan ketentuan dan melakukan validasi untuk password
    fun validatePassword(password: String): ValidationResult {
        return ValidationResult(
            (!password.isNullOrEmpty() && password.length >= 4)
        )
    }

    //Menyimpan hasil validasi secara keseluruhan
    data class ValidationResult(
        val status: Boolean = false
    )
}