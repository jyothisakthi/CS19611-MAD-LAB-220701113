package com.example.exp10_telephony_113

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.TelephonyManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var operatorName: EditText
    private lateinit var phoneType: EditText
    private lateinit var networkCountryISO: EditText
    private lateinit var simCountryISO: EditText
    private lateinit var softwareVersion: EditText
    private lateinit var getInfoButton: Button

    private val PERMISSION_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        operatorName = findViewById(R.id.operatorName)
        phoneType = findViewById(R.id.phoneType)
        networkCountryISO = findViewById(R.id.networkCountryISO)
        simCountryISO = findViewById(R.id.simCountryISO)
        softwareVersion = findViewById(R.id.softwareVersion)
        getInfoButton = findViewById(R.id.getInfoButton)

        getInfoButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_PHONE_STATE),
                    PERMISSION_REQUEST_CODE
                )
            } else {
                loadTelephonyInfo()
            }
        }
    }

    private fun loadTelephonyInfo() {
        val telephonyManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager

        operatorName.setText(telephonyManager.networkOperatorName)
        phoneType.setText(getPhoneTypeString(telephonyManager.phoneType))
        networkCountryISO.setText(telephonyManager.networkCountryIso)
        simCountryISO.setText(telephonyManager.simCountryIso)
        softwareVersion.setText(telephonyManager.deviceSoftwareVersion ?: "-----")
    }

    private fun getPhoneTypeString(type: Int): String {
        return when (type) {
            TelephonyManager.PHONE_TYPE_GSM -> "GSM"
            TelephonyManager.PHONE_TYPE_CDMA -> "CDMA"
            TelephonyManager.PHONE_TYPE_SIP -> "SIP"
            TelephonyManager.PHONE_TYPE_NONE -> "None"
            else -> "Unknown"
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_CODE &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            loadTelephonyInfo()
        }
    }
}
