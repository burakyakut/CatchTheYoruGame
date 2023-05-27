package com.example.catchthevalorantagent
import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import androidx.core.os.postDelayed
import com.example.catchthevalorantagent.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var yoruList: ArrayList<ImageView>
    var runnable:Runnable= Runnable {  }
    var handler:Handler= Handler(Looper.getMainLooper())
    var score=0
    var highScore=0
    private lateinit var sharedPref: SharedPreferences
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        sharedPref=getSharedPreferences("com.example.catchthevalorantagent", MODE_PRIVATE)
        val playerName=sharedPref.getString("name","")
        binding.scoreTextView.text="$playerName Score:0"

        yoruList=ArrayList<ImageView>()
        yoruList.add(binding.yoru1)
        yoruList.add(binding.yoru2)
        yoruList.add(binding.yoru3)
        yoruList.add(binding.yoru4)
        yoruList.add(binding.yoru5)
        yoruList.add(binding.yoru6)
        yoruList.add(binding.yoru7)
        yoruList.add(binding.yoru8)
        yoruList.add(binding.yoru9)

        for (yoru in yoruList){
            yoru.visibility=View.INVISIBLE
        }

       start()
       yoruClick()

    }


    @SuppressLint("SetTextI18n")
    private fun yoruClick(){
        val playerName=sharedPref.getString("name","")
        binding.scoreTextView.text="$playerName Score:0"
        for (yoru in yoruList){
            yoru.setOnClickListener {
                score++
                binding.scoreTextView.text="$playerName Score:${score}"
            }
        }
    }

    private fun start() {
        binding.startButton.setOnClickListener {
            time()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun time(){
        val playerName=sharedPref.getString("name","")
        binding.scoreTextView.text="$playerName Score:0"
        object : CountDownTimer(5000,1000) {
            override fun onTick(p0: Long) {
                runnable()
                binding.timeTextView.text="${p0/1000}"
                binding.startButton.isEnabled=false
                binding.radioButtons.visibility=View.INVISIBLE
            }
            override fun onFinish() {
                binding.radioButtons.visibility=View.VISIBLE
                binding.timeTextView.text="5"
                binding.startButton.isEnabled=true
                handler.removeCallbacks(runnable)
                for (yoru in yoruList){
                    yoru.visibility=View.INVISIBLE
                }
                if (score>highScore){
                    highScore=score
                    binding.highScoreTextView.text="High Score:${highScore}"
                }
                score=0
                binding.scoreTextView.text="$playerName Score:0"
            }
        }
            .start()
    }

    private fun runnable(){
        runnable= Runnable {
            yoruVisible()
            if (binding.radioEasy.isChecked){
                handler.postDelayed(runnable,1000)
            }else{
                handler.postDelayed(runnable,500)
            }
        }
        handler.post(runnable)
    }

    private fun yoruVisible(){
        for(yoru in yoruList){
            yoru.visibility=View.INVISIBLE
        }
        val random=Random
        val randomIndex=random.nextInt(9)
        yoruList[randomIndex].visibility=View.VISIBLE
    }

}