package ie.wit.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ie.wit.R
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.startActivityForResult

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //when button clicked starts activity MovieListActivity
        managerBtn.setOnClickListener{
            startActivityForResult<Home>(0)
        }

        //when button clicked starts activity MovieListActivity
        playerBtn.setOnClickListener{
            startActivityForResult<Home>(0)
        }
    }
}
