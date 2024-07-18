package com.example.farmaid
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth


class AuthViewModel: ViewModel() {

    private val auth : FirebaseAuth = FirebaseAuth.getInstance()

    private val _authstate = MutableLiveData<Authstate>()
    val authstate: LiveData<Authstate> = _authstate

    init {
        checkAuthState()
    }

    fun checkAuthState(){
        if(auth.currentUser == null){
            _authstate.value = Authstate.Unauthenticated
        }else{
            _authstate.value = Authstate.Authenticated
        }
    }

    fun login(email : String, password : String){

        if(email.isEmpty() || password.isEmpty()){
            _authstate.value = Authstate.Error("Please fill all fields")
            return
        }
        _authstate.value = Authstate.Loading
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{task ->
                if(task.isSuccessful){
                    _authstate.value = Authstate.Authenticated
                }else{
                    _authstate.value = Authstate.Error(task.exception?.message?:"Something went wrong")
                }
            }
    }

    fun signup(email : String, password : String){

        if(email.isEmpty() || password.isEmpty()){
            _authstate.value = Authstate.Error("Please fill all fields")
            return
        }
        _authstate.value = Authstate.Loading
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{task ->
                if(task.isSuccessful){
                    _authstate.value = Authstate.Authenticated
                }else{
                    _authstate.value = Authstate.Error(task.exception?.message?:"Something went wrong")
                }
            }
    }

    fun logout(){
        auth.signOut()
        _authstate.value = Authstate.Unauthenticated
    }


}
sealed class Authstate{
    object Authenticated: Authstate()
    object Unauthenticated: Authstate()
    object Loading: Authstate()
    data class Error(val message: String): Authstate()
}
