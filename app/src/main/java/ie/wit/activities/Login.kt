package ie.wit.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.wajahatkarim3.easyvalidation.core.view_ktx.textNotEqualTo
import ie.wit.R
import ie.wit.main.MainApp
import ie.wit.models.FixtureModel
import ie.wit.models.UserModel
import ie.wit.utils.createLoader
import ie.wit.utils.hideLoader
import ie.wit.utils.showLoader
import kotlinx.android.synthetic.main.fragment_fixture.*
import kotlinx.android.synthetic.main.login.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.util.HashMap

class Login : AppCompatActivity(), AnkoLogger, View.OnClickListener {

    lateinit var app: MainApp
    lateinit var loader : AlertDialog

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        app = application as MainApp
        // Buttons
        emailSignInButton.setOnClickListener(this)
        emailCreateAccountButton.setOnClickListener(this)
        signOutButton.setOnClickListener(this)
        verifyEmailButton.setOnClickListener(this)
        sign_in_button.setOnClickListener(this)


        //sign_in_button.setSize(SignInButton.SIZE_WIDE)
        //sign_in_button.setColorScheme(0)

        app.auth = FirebaseAuth.getInstance()

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        app.googleSignInClient = GoogleSignIn.getClient(this, gso)

        loader = createLoader(this)


    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = app.auth.currentUser
        updateUI(currentUser)
        //startActivity<Login>()

    }

    private fun createAccount() {

        val email = fieldEmail.text.toString()
        val password = fieldPassword.text.toString()
        val userType = fieldUserType.text.toString()

        Log.d(TAG, "createAccount:$email")
        if (!validateForm()) {
            return
        }

        showLoader(loader, "Creating Account...")
        app.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = app.auth.currentUser
                    writeNewUser(UserModel(userType = userType, email = email))
                    updateUI(user)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
                hideLoader(loader)
            }

    }

    private fun signIn(email: String, password: String) {
        Log.d(TAG, "signIn:$email")
        if (!validateForm()) {
            return
        }
        showLoader(loader, "Logging In...")
        app.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = app.auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }

                if (!task.isSuccessful) {
                    status.setText(R.string.auth_failed)
                }
                hideLoader(loader)

            }

    }

    private fun signOut() {
        app.auth.signOut()
        app.googleSignInClient.signOut()
        updateUI(null)
    }

    private fun sendEmailVerification() {
        // Disable button
        verifyEmailButton.isEnabled = false

        // Send verification email
        // [START send_email_verification]
        val user = app.auth.currentUser
        user?.sendEmailVerification()
            ?.addOnCompleteListener(this) { task ->
                // [START_EXCLUDE]
                // Re-enable button
                verifyEmailButton.isEnabled = true

                if (task.isSuccessful) {
                    Toast.makeText(baseContext,
                        "Verification email sent to ${user.email} ",
                        Toast.LENGTH_SHORT).show()
                } else {
                    Log.e(TAG, "sendEmailVerification", task.exception)
                    Toast.makeText(baseContext,
                        "Failed to send verification email.",
                        Toast.LENGTH_SHORT).show()
                }
                // [END_EXCLUDE]
            }
        // [END send_email_verification]
    }

    private fun validateForm(): Boolean {
        var valid = true

        val email = fieldEmail.text.toString()
        if (TextUtils.isEmpty(email)) {
            fieldEmail.error = "Required."
            valid = false
        } else {
            fieldEmail.error = null
        }

        val password = fieldPassword.text.toString()
        if (TextUtils.isEmpty(password)) {
            fieldPassword.error = "Required."
            valid = false
        } else {
            fieldPassword.error = null
        }

        val userType = fieldUserType.text.toString()
        if (TextUtils.isEmpty(userType)) {
            fieldUserType.error = "Required."
            valid = false
        } else {
            fieldUserType.error = null
        }

        //If user is not either manager, player or supporter
        //invalid user will appear and asked to add a valid user
        val userTypeValidation = fieldUserType.text.toString()
        if (userTypeValidation == "Manager" || userTypeValidation == "manager" ||
            userTypeValidation == "Admin" || userTypeValidation == "admin" ||
            userTypeValidation == "Player" || userTypeValidation == "player"
        ) {
            return valid
        } else {
            fieldUserType.error = "Invalid User Type, Please Enter Manager, Player or Supporter"
            valid = false
        }

        return valid
    }


    private fun updateUI(user: FirebaseUser?) {
        hideLoader(loader)

        if (user != null) {

                status.text = getString(
                    R.string.emailpassword_status_fmt,
                    user.email, user.isEmailVerified
                )
                detail.text = getString(R.string.firebase_status_fmt, user.uid)

                emailPasswordButtons.visibility = View.GONE
                emailPasswordFields.visibility = View.GONE
                googleSignInButton.visibility = View.INVISIBLE
                signedInButtons.visibility = View.VISIBLE
                skipSignIn.visibility = View.INVISIBLE

                verifyEmailButton.isEnabled = !user.isEmailVerified
                app.database = FirebaseDatabase.getInstance().reference
                app.storage = FirebaseStorage.getInstance().reference


            //val checkUserType = app.database.child("userType")
            val userType = fieldUserType.text.toString()

            showLoader(loader, " Loading Screen")

            if (userType == "Admin" || userType == "admin") {
                //showLoader(loader, " Loading Supporter Screen")
                startActivity<AdminHome>()
            }

            else if (userType == "Manager" || userType == "manager") {
                //showLoader(loader, " Loading Manager Screen")
                startActivity<ManagerHome>()
            }
            else if (userType == "Player" || userType == "player") {
                //showLoader(loader, " Loading Player Screen")
                startActivity<PlayerHome>()
            }
            else if (userType.isEmpty()) {
                //showLoader(loader, " Loading Player Screen")
                app.auth.signOut()
                app.googleSignInClient.signOut()
                updateUI(null)
                //startActivity<SupporterHome>()
            }

            hideLoader(loader)

        } else {
            status.setText(R.string.signed_out)
            detail.text = null

            emailPasswordButtons.visibility = View.VISIBLE
            emailPasswordFields.visibility = View.VISIBLE
            googleSignInButton.visibility = View.VISIBLE
            signedInButtons.visibility = View.GONE
            skipSignIn.visibility = View.VISIBLE
        }
    }

    override fun onClick(v: View) {
        val i = v.id
        when (i) {
            R.id.emailCreateAccountButton -> createAccount()
            R.id.emailSignInButton -> signIn(fieldEmail.text.toString(), fieldPassword.text.toString())
            R.id.signOutButton -> signOut()
            R.id.verifyEmailButton -> sendEmailVerification()
            R.id.sign_in_button -> googleSignIn()

        }
    }

    companion object {
        private const val TAG = "EmailPassword"
        private const val RC_SIGN_IN = 9001
    }



    fun writeNewUser(user: UserModel) {

        // Create new fixture at /fixtures & /fixtures/$uid
        showLoader(loader, "Adding User to Firebase")
        info("Firebase DB Reference : $app.database")
        val key = app.database.child("users").push().key
        if (key == null) {
            info("Firebase Error : Key Empty")
            return
        }
        user.uid = key
        val userValues = user.toMap()

        val childUpdates = HashMap<String, Any>()
        childUpdates["/users/$key"] = userValues

        app.database.updateChildren(childUpdates)
        hideLoader(loader)

    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // [START_EXCLUDE]
                updateUI(null)
                // [END_EXCLUDE]
            }
        }
    }

    private fun googleSignIn() {
        val signInIntent = app.googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)
        // [START_EXCLUDE silent]
        showLoader(loader, "Logging In with Google...")
        // [END_EXCLUDE]

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        app.auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = app.auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Snackbar.make(main_layout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
                    updateUI(null)
                }

                // [START_EXCLUDE]
                hideLoader(loader)
                // [END_EXCLUDE]
            }
    }

    }

