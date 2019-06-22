package project.miran.com.firebasechatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    val ANONYMOUS ="anonymous"
    val DEFAULT_MSG_LENGTH_LIMIT =100
    private lateinit var mUserName:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var friendlyMessageList:MutableList<FriendlyMessage> = arrayListOf()
        friendlyMessageList.add(FriendlyMessage("heyy","name","image url"))
        friendlyMessageList.add(FriendlyMessage("heyy","name","image url"))
        friendlyMessageList.add(FriendlyMessage("heyy","name","image url"))
        friendlyMessageList.add(FriendlyMessage("heyy","name","image url"))

        var myStrings:StringBuilder= StringBuilder()

        for (item in friendlyMessageList){
                myStrings.append("${item.name} \n")
            Log.d(TAG, item.name)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater= menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)
    }
}
