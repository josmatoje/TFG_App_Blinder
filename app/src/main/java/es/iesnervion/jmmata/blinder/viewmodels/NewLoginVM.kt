package es.iesnervion.jmmata.blinder.viewmodels

import android.content.Context
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.common.base.Objects
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.QuerySnapshot
import es.iesnervion.jmmata.blinder.BlinderApplication
import es.iesnervion.jmmata.blinder.R
import es.iesnervion.jmmata.blinder.businessObject.UserBO
import es.iesnervion.jmmata.blinder.dataaccess.local.room.datasourceImplementation.UserLocalDataSourceImpl
import es.iesnervion.jmmata.blinder.dataaccess.remote.datasourceImplementation.UserRemoteDataSourceImpl
import es.iesnervion.jmmata.blinder.dataaccess.repository.UsersRepository
import es.iesnervion.jmmata.blinder.dataaccess.useCase.CreateUserAuthUseCase
import es.iesnervion.jmmata.blinder.dataaccess.useCase.InsertUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NewLoginVM : ViewModel() {


    //Repositoy
    private val userRepository = UsersRepository(
        UserRemoteDataSourceImpl(),
        UserLocalDataSourceImpl(BlinderApplication.instance)
    )

    //Usecases
    private val createUserAuthUseCase = CreateUserAuthUseCase(userRepository)
    private val insertUserUseCase = InsertUserUseCase(userRepository)

    //public functions
    fun createUserAuth(email: String, password: String, context: Context, newUser: UserBO){
        viewModelScope.launch(Dispatchers.IO) {
            createUserAuthUseCase.invoke(email, password){
                if (it.isSuccessful) {
                    GlobalScope.launch(Dispatchers.IO) {
                        insertUserUseCase.invoke(newUser)
                        //Toast.makeText(context, "Usuario registrado", Toast.LENGTH_LONG).show()
                    }
                } else {
                    if (it.exception is FirebaseAuthUserCollisionException){
                        //Toast.makeText(context, "El usuario ya está registrado con este correo.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }


}