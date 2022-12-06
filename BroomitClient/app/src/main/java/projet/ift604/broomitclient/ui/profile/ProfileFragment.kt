package projet.ift604.broomitclient.ui.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.Button
import android.view.ViewGroup
import android.widget.TextView
import android.view.LayoutInflater
import projet.ift604.broomitclient.R
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import projet.ift604.broomitclient.models.User
import projet.ift604.broomitclient.ApplicationState
import projet.ift604.broomitclient.api.UserService
import projet.ift604.broomitclient.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private var user: User = ApplicationState.getInstance().user

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val circleView: TextView = binding.title
        // Use first letter from username in 'circleView'
        circleView.text = user.username[0].uppercaseChar().toString()

        // Fill fields 'Username' & 'Email'
        binding.username.setText(user.username)
        binding.email.setText(user.email)

        val saveButton: Button = binding.save
        saveButton.setOnClickListener {
            val username: String = binding.username.text.toString().trim()
            val email: String = binding.email.text.toString().trim()
            val newPassword: String = binding.newPassword.text.toString().trim()
            val confirmPassword: String = binding.confirmPassword.text.toString().trim()

            if (validateFields(username, email, newPassword, confirmPassword)) {
                val handler = CoroutineExceptionHandler { _, err -> err.printStackTrace() }
                lifecycleScope.launch(Dispatchers.IO + handler) {
                    val state = ApplicationState.getInstance()
                    try {
                        state.user = User(user.id, username, newPassword, email, user.locations)
                        state.updateUser()

                        // Update first letter from username in 'circleView'
                        val circleView: TextView = binding.title
                        circleView.text = user.username[0].uppercaseChar().toString()
                    } catch (e: ApplicationState.HttpException) {
                        // TODO: show error message on UI
                        activity?.runOnUiThread {
                            Toast.makeText(
                                activity?.applicationContext,
                                "ERROR: " + e.code,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                    activity?.runOnUiThread {
                        Toast.makeText(
                            activity?.applicationContext,
                            requireContext().getString(R.string.save_changes_success),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

        return root
    }

    // Returns 'false' as long as all conditions are not met.
    fun validateFields(username: String, email: String, newPassword: String, confirmPassword: String): Boolean {

        if (username.isNullOrEmpty()) {
            activity?.runOnUiThread {
                Toast.makeText(activity?.applicationContext, requireContext().getString(R.string.no_empty_username), Toast.LENGTH_LONG)
                    .show()
            }
            return false
        }
        if (email.isNullOrEmpty()) {
            activity?.runOnUiThread {
                Toast.makeText(activity?.applicationContext, requireContext().getString(R.string.no_empty_email), Toast.LENGTH_LONG)
                    .show()
            }
            return false
        }
        if (!email.contains("@")) {
            activity?.runOnUiThread {
                Toast.makeText(activity?.applicationContext, requireContext().getString(R.string.missing_at_email), Toast.LENGTH_LONG)
                    .show()
            }
            return false
        }
        if (confirmPassword != newPassword) {
            activity?.runOnUiThread {
                Toast.makeText(activity?.applicationContext, requireContext().getString(R.string.passwords_do_not_match), Toast.LENGTH_LONG)
                    .show()
            }
            return false
        }
        else {
            return true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}