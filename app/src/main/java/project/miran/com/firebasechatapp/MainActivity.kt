package project.miran.com.firebasechatapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    val ANONYMOUS = "anonymous"
    val DEFAULT_MSG_LENGTH_LIMIT = 100
    private lateinit var mUserName: String

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var childEventListener: ChildEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("messages")
        var friendlyMessageList: MutableList<FriendlyMessage> = arrayListOf()
        val adapter = MessageAdapter(friendlyMessageList)

        mUserName = ANONYMOUS
        progressBar.visibility = ProgressBar.INVISIBLE
        photoPickerButton.setOnClickListener {

        }
        messageEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                sendButton.isEnabled = charSequence.toString().trim { it <= ' ' }.isNotEmpty()
            }

            override fun afterTextChanged(editable: Editable) {}
        })


        sendButton.setOnClickListener {
            val friendlyMessage = FriendlyMessage(messageEditText.text.toString().trim(), mUserName, "")
            databaseReference.push().setValue(friendlyMessage)
            messageEditText.setText("")
        }

        childEventListener = object : ChildEventListener {

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val f0 =p0.getValue(FriendlyMessage::class.java)
                friendlyMessageList.add(f0!!)
                adapter.notifyDataSetChanged()
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }


        }
        databaseReference.addChildEventListener(childEventListener)


        messageListView.setHasFixedSize(false)
        messageListView.layoutManager = LinearLayoutManager(this)
        messageListView.adapter = adapter


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

}
