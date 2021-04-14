package uz.bdmgroup.onlineshop.screen.sign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_login.*
import uz.bdmgroup.onlineshop.R
import uz.bdmgroup.onlineshop.screen.MainActivity
import uz.bdmgroup.onlineshop.screen.MainViewModel
import uz.bdmgroup.onlineshop.utils.PrefUtils

enum class LoginState{
    CHECK_PHONE,
    LOGIN,
    REGISTRATION,
    CONFIRM,
}

class LoginActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel

    var state = LoginState.CHECK_PHONE
    var phone = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        viewModel.progress.observe(this, Observer {
            flProgress.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.checkPhoneData.observe(this, Observer {
            if (it.is_reg){
                state = LoginState.LOGIN
            }else{
                state = LoginState.REGISTRATION
            }
            initViews()
        })

        viewModel.loginData.observe(this, Observer {
            PrefUtils.setToken(it.token)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        })

        viewModel.confirmData.observe(this, Observer {
            PrefUtils.setToken(it.token)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        })

        viewModel.registrationData.observe(this, Observer {
            state = LoginState.CONFIRM
            initViews()
        })

        cardViewBack.setOnClickListener {
            finish()
        }

        btnNext.setOnClickListener {
            when(state){
                LoginState.CHECK_PHONE ->{
                    phone = edPhone.text.toString().replace(" ", "")
                    viewModel.checkPhone(phone)
                }
                LoginState.LOGIN->{
                    viewModel.login(phone, edPassword.text.toString())
                }
                LoginState.REGISTRATION->{
                    val fullname = edFullname.text.toString()
                    val password = edPassword.text.toString()
                    val repassword = edRePassword.text.toString()
                    if (fullname.isNullOrEmpty()){
                        Toast.makeText(this, "Please input full name!", Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }
                    if (password.isNullOrEmpty()){
                        Toast.makeText(this, "Please input password!", Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }
                    if (password != repassword){
                        Toast.makeText(this, "Please input correct password!", Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }
                    viewModel.registrationData(fullname, phone, password)
                }
                LoginState.CONFIRM->{
                    viewModel.confirmUser(phone, edCode.text.toString())
                }
            }
        }

        initViews()
    }

    fun initViews(){
        lyFullname.visibility = View.GONE
        lySMSCode.visibility = View.GONE
        lyPassword.visibility = View.GONE
        lyRePassword.visibility = View.GONE

        when(state){
            LoginState.CHECK_PHONE ->{
                tvTitle.text = "LOGIN"
                edPhone.isEnabled = true
            }
            LoginState.LOGIN ->{
                tvTitle.text = "LOGIN"
                lyPassword.visibility = View.VISIBLE
                edPhone.isEnabled = false
            }
            LoginState.REGISTRATION ->{
                tvTitle.text = "REGISTRATION"
                lyFullname.visibility = View.VISIBLE
                lyPassword.visibility = View.VISIBLE
                lyRePassword.visibility = View.VISIBLE
                edPhone.isEnabled = false
            }
            LoginState.CONFIRM ->{
                tvTitle.text = "REGISTRATION"
                lySMSCode.visibility = View.VISIBLE
                edPhone.isEnabled = false
            }
        }
    }
}