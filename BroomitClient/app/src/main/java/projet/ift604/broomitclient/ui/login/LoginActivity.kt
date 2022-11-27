package projet.ift604.broomitclient.ui.login

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import projet.ift604.broomitclient.ApplicationState
import projet.ift604.broomitclient.R
import projet.ift604.broomitclient.api.UserService
import projet.ift604.broomitclient.databinding.ActivityLoginBinding
import java.lang.Exception
import java.security.MessageDigest

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

            val digest = MessageDigest.getInstance("SHA-256")
            val hash = digest.digest(pass.toByteArray()).toString()

            lifecycleScope.launch(Dispatchers.IO) {
                val state = ApplicationState.getInstance()
                try {
                    if (binding.toggleLoginType.isChecked) {
                        val email = binding.email.text.toString()

                        state.create(UserService.CreateRequest(usr, hash, email))
                    } else {
                        state.login(UserService.LoginRequest(usr, hash))
                    }
                } catch(e: ApplicationState.HttpException) {
                    //@TODO: show error message on UI
                    // handle error in UI
                    runOnUiThread {
                        Toast.makeText(applicationContext, "ERROR: " + e.code, Toast.LENGTH_LONG)
                            .show()
                    }
                }

                if (state.loggedIn) {
                    // @TODO: Redirect to main activity
                }
            }
        }
    }
}