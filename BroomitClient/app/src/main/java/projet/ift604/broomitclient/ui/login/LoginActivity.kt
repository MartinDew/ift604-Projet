package projet.ift604.broomitclient.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import projet.ift604.broomitclient.ApplicationState
import projet.ift604.broomitclient.MainActivity
import projet.ift604.broomitclient.R
import projet.ift604.broomitclient.api.UserService
import projet.ift604.broomitclient.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val signInText = applicationContext.getString(R.string.action_sign_in)
        val registerText = applicationContext.getString(R.string.action_register)

        binding.toggleLoginType.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.login.text = registerText
                binding.email.visibility = View.VISIBLE
            } else {
                binding.login.text = signInText
                binding.email.visibility = View.GONE
            }
        }

        binding.login.setOnClickListener {
            val usr = binding.username.text.toString()
            val pass = binding.password.text.toString()

            val handler = CoroutineExceptionHandler { _, err -> err.printStackTrace() }
            lifecycleScope.launch(Dispatchers.IO + handler) {
                val state = ApplicationState.instance
                try {
                    if (binding.toggleLoginType.isChecked) {
                        val email = binding.email.text.toString()

                        state.create(UserService.CreateRequest(usr, pass, email))
                    } else {
                        state.login(UserService.LoginRequest(usr, pass))
                    }
                } catch(e: ApplicationState.HttpException) {
                    // TODO: show error message on UI
                    runOnUiThread {
                        Toast.makeText(applicationContext, "ERROR: " + e.code, Toast.LENGTH_LONG)
                            .show()
                    }
                }

                if (state.loggedIn) {
                    val myIntent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(myIntent)
                    // TODO: Prevent 'Back' button to Login from MainActivity (?)
                }
            }
        }
    }
}