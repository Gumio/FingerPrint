package com.gumio_inf.fingerprintapp

import android.content.Context
import android.content.Intent
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fingerprintManager = getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager

        // 指紋認証した時のコールバック
        val authenticationCallback = object : FingerprintManager.AuthenticationCallback() {
            //エラー
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(this@MainActivity, "おめぇ、壊れてんぞ", Toast.LENGTH_SHORT).show()
            }
            // 失敗
            override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
                super.onAuthenticationHelp(helpCode, helpString)
                Toast.makeText(this@MainActivity, "認証失敗", Toast.LENGTH_SHORT).show()
            }
            // 成功
            override fun onAuthenticationSucceeded(result: FingerprintManager.AuthenticationResult?) {
                super.onAuthenticationSucceeded(result)
                Toast.makeText(this@MainActivity, "認証成功！", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        try {
            if (fingerprintManager.isHardwareDetected && fingerprintManager.hasEnrolledFingerprints())
                fingerprintManager.authenticate(null, null, 0, authenticationCallback, null)
            else
                Toast.makeText(this@MainActivity, "おめぇ、指紋登録してねえな？", Toast.LENGTH_SHORT).show()
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
}

