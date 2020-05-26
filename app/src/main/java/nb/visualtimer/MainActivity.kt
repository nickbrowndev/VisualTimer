package nb.visualtimer

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.time.Duration
import nb.visualtimer.view.SquareView

class MainActivity : AppCompatActivity() {

    private var squareView: SquareView? = null
    var START_MILLI_SECONDS = 60000L
    val interval = 100L
    lateinit var countdown_timer: CountDownTimer
    var isRunning: Boolean = false;
    var time_in_milli_seconds = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        squareView = findViewById(R.id.squareView)


        button.setOnClickListener {
            if (isRunning) {
                pauseTimer()
            } else {
                val time = time_edit_text.text.toString()
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
        countdown_timer.cancel()
        isRunning = false
        reset.visibility = View.VISIBLE
    }

    private fun startTimer(durationMillis: Long) {
        countdown_timer = object : CountDownTimer(durationMillis, interval) {
            override fun onFinish() {
                onComplete()
            }

            override fun onTick(completionMillis : Long) {
                val percentComplete = (completionMillis * 100F / durationMillis)
                updateTimeDisplay(durationMillis - completionMillis)
                updatePercentDisplay(percentComplete)

                updateCanvasUi(percentComplete)
            }
        }

        countdown_timer.start();

        isRunning = true
        button.text = "Pause"
        reset.visibility = View.INVISIBLE
    }

    private fun resetTimer() {
        time_in_milli_seconds = START_MILLI_SECONDS
        updateTimeDisplay(time_in_milli_seconds)
        updatePercentDisplay(0F)
        reset.visibility = View.INVISIBLE
    }

    private fun updateTimeDisplay(timeRemainingMillis: Long) {
        val elapsedTime = Duration.ofMillis(timeRemainingMillis)
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
    private fun updatePercentDisplay(percentComplete: Float) {
        val formattedPercentComplete = String.format("%.2f", percentComplete) ;
        percent.text = formattedPercentComplete + "%"
    }

    private fun updateCanvasUi(percentComplete : Float) {
        squareView?.setPercentProgress(percentComplete)
    }

    private fun onComplete() {
       timer.text = "Finished"
    }
}
