package com.example.bingewatch.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.bingewatch.R
import com.example.bingewatch.ui.LoginActivity
//import com.example.newsprojectpractice.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.core.View

class ProfileFragment : Fragment() {

    private lateinit var textViewUsername: TextView
    private lateinit var buttonSignOut: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): android.view.View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

       //// textViewUsername = view.findViewById(R.id.textViewUsername)
        buttonSignOut = view.findViewById(R.id.buttonSignOut)

        auth = FirebaseAuth.getInstance()


        buttonSignOut.setOnClickListener {
            // Sign out the user
            auth.signOut()

            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        return view
    }
}
