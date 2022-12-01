package projet.ift604.broomitclient.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData

class ProfileViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Page du profil de l'utilisateur."
    }
    val text: LiveData<String> = _text
}