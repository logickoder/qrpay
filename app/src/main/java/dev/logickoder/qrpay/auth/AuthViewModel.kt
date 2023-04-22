package dev.logickoder.qrpay.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.logickoder.qrpay.R
import dev.logickoder.qrpay.app.data.remote.ResultWrapper
import dev.logickoder.qrpay.app.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: UserRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(AuthScreenState())
    val state = _state.map { state ->
        val usernameError = if (state.username.isNotBlank()) {
            null
        } else R.string.username_required
        val passwordError = if (state.password.isNotBlank()) {
            null
        } else R.string.password_required
        val firstnameError = when (state.type) {
            AuthScreenType.Register -> null
            else -> if (state.firstname.isNotBlank()) {
                null
            } else R.string.firstname_required
        }
        val lastnameError = when (state.type) {
            AuthScreenType.Register -> null
            else -> if (state.lastname.isNotBlank()) {
                null
            } else R.string.lastname_required
        }

        state.copy(
            usernameError = usernameError,
            passwordError = passwordError,
            firstnameError = firstnameError,
            lastnameError = lastnameError,
            enabled = usernameError == null && passwordError == null && firstnameError == null && lastnameError == null,
        )
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), AuthScreenState()
    )

    fun update(state: AuthScreenState) {
        _state.update { state }
    }

    fun submit() {
        viewModelScope.launch {
            _state.update {
                it.copy(loading = true)
            }
            val result = when {
                // clear the error if the user clicks the back button on an error screen
                _state.value.error != null -> {
                    _state.update {
                        it.copy(error = null)
                    }
                    ResultWrapper.Loading
                }

                else -> when (_state.value.type) {
                    AuthScreenType.Login -> repository.login(
                        username = _state.value.username,
                        password = _state.value.password,
                    )

                    AuthScreenType.Register -> repository.register(
                        firstname = _state.value.firstname,
                        lastname = _state.value.lastname,
                        username = _state.value.username,
                        password = _state.value.password,
                    )
                }
            }
            if (result is ResultWrapper.Failure) {
                _state.update {
                    it.copy(error = result.error.message)
                }
            }
            _state.update {
                it.copy(loading = false)
            }
        }
    }
}
