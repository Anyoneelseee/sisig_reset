package com.example.sisig

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.DialogFragment
import androidx.core.content.ContextCompat
import com.example.sisig.databinding.DialogBiometricBinding

class BiometricDialog : DialogFragment() {

    interface BiometricAuthCallback {
        fun onAuthenticationSuccess()
    }

    private var _binding: DialogBiometricBinding? = null
    private val binding get() = _binding!!

    var authCallback: BiometricAuthCallback? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogBiometricBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = arguments?.getString("dialogTitle") ?: "Default Title"
        // You can set the title in your TextView if needed
        binding.dialogTitle = title

        binding.buttonBiometricLogin.setOnClickListener {
            authenticateUser()
        }
    }

    private fun authenticateUser() {
        val biometricPrompt = BiometricPrompt(this, ContextCompat.getMainExecutor(requireContext()),
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    authCallback?.onAuthenticationSuccess()
                    dismiss()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    // Handle error (optional)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    // Handle failed authentication (optional)
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Authenticate using your biometric credential")
            .setNegativeButtonText("Cancel")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}