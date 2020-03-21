package ie.wit.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ie.wit.R
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivityForResult

class UserSelection : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        managerBtn.setOnClickListener{
            startActivityForResult<Home>(0)
        }

        playerBtn.setOnClickListener{
            startActivityForResult<Home>(0)

        }
    }
}
