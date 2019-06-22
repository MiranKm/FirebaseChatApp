package project.miran.com.firebasechatapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.Arrays.asList


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    val ANONYMOUS = "anonymous"
    val DEFAULT_MSG_LENGTH_LIMIT = 100
    private lateinit var mUserName: String
    private val RC_SIGN_IN = 123

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var childEventListener: ChildEventListener
    private lateinit var mAuth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener
    private lateinit var friendlyMessageList: MutableList<FriendlyMessage>

    val adapter = MessageAdapter();


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("messages")
        friendlyMessageList = arrayListOf()

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


        authStateListener = FirebaseAuth.AuthStateListener {

            if (it.currentUser != null) {
//                signed in
                onSignedInitialized(it.currentUser!!.displayName)
            } else {
//                not signed in

                onSignoutCleanUp()
                startActivityForResult(
                    AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(
                            asList(
                                AuthUI.IdpConfig.GoogleBuilder().build(),
                                AuthUI.IdpConfig.EmailBuilder().build()
                            )
                        )
                        .build(),
                    RC_SIGN_IN
                )

            }

        }
        adapter.addData(friendlyMessageList)
        messageListView.setHasFixedSize(false)
        messageListView.layoutManager = LinearLayoutManager(this)
        messageListView.adapter = adapter


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "Signed In", Toast.LENGTH_SHORT).show()

            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Signed Cancelled", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun onSignoutCleanUp() {
        mUserName = ANONYMOUS
        adapter.clearAll()

    }

    private fun onSignedInitialized(displayName: String?) {
        mUserName = displayName!!
        attachDataBaseReadListener()

    }


    private fun detachDataBaseReadListener() {
        databaseReference.removeEventListener(childEventListener)
    }

    private fun attachDataBaseReadListener() {
        childEventListener = object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onChildMoved(p0: DataSnapshot, p1: String?) {}
            override fun onChildChanged(p0: DataSnapshot, p1: String?) {}

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val f0 = p0.getValue(FriendlyMessage::class.java)
                adapter.addData(f0!!)
                messageListView.smoothScrollToPosition(RecyclerView.SCROLL_INDICATOR_END)
                adapter.notifyDataSetChanged()
            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }

        }
        databaseReference.addChildEventListener(childEventListener)
    }

    override fun onResume() {
        super.onResume()
        mAuth.addAuthStateListener(authStateListener)
    }

    override fun onPause() {
        super.onPause()
        mAuth.removeAuthStateListener(authStateListener)
        detachDataBaseReadListener()
        adapter.clearAll()

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.sign_out -> {
                AuthUI.getInstance().signOut(this)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

}
