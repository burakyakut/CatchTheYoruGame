package com.example.catchthevalorantagent
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.catchthevalorantagent.databinding.ActivityLoginBinding
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPref=getSharedPreferences("com.example.catchthevalorantagent", MODE_PRIVATE)
        //val playerName=sharedPref.getString("name","")
        //binding.textInputEditText.setText(playerName)

        binding.loginButton.setOnClickListener {
            val name=binding.textInputEditText.text.toString()
            val intent=Intent(this,MainActivity::class.java)
            if (name.isNotEmpty()){
                sharedPref.edit().putString("name",name).apply()
            }
            startActivity(intent)
        }
    }
}