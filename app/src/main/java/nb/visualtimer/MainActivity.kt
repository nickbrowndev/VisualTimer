package nb.visualtimer

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.time.Duration
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    var START_MILLI_SECONDS = 60000L

    lateinit var countdown_timer: CountDownTimer
    var isRunning: Boolean = false;
    var time_in_milli_seconds = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        button.setOnClickListener {
            if (isRunning) {
                pauseTimer()
            } else {
                val time  = time_edit_text.text.toString()
                time_in_milli_seconds = time.toLong() * 60000L
                startTimer(time_in_milli_seconds)
            }
        }

        reset.setOnClickListener {
            resetTimer()
        }
    }

    private fun pauseTimer() {
        button.text= "Start"
        countdown_timer.cancel();
        isRunning = false
        reset.visibility = View.VISIBLE
    }

    private fun startTimer(time_in_seconds: Long) {
        countdown_timer = object : CountDownTimer(time_in_seconds, 1000) {
            override fun onFinish() {
                onComplete()
            }

            override fun onTick(p0 : Long) {
                time_in_milli_seconds = p0
                updateTextUi()
            }
        }
        countdown_timer.start();

        isRunning = true;
        button.text = "Pause"
        reset.visibility = View.INVISIBLE;
    }

    private fun resetTimer() {
        time_in_milli_seconds = START_MILLI_SECONDS
        updateTextUi()
        reset.visibility = View.INVISIBLE
    }

    private fun updateTextUi() {
        var elapsedTime = Duration.ofMillis(time_in_milli_seconds)
        val seconds: Long = elapsedTime.getSeconds()
        val absSeconds = Math.abs(seconds)
        val positive = String.format(
            "%d:%02d:%02d",
            absSeconds / 3600,
            absSeconds % 3600 / 60,
            absSeconds % 60
        )
        timer.text = if (seconds < 0) "-$positive" else positive
    }

    private fun onComplete() {
       timer.text = "Finished"
    }



}
