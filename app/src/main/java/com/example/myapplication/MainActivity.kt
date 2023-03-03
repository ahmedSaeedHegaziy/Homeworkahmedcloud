package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        save.setOnClickListener {
            val name = name.text.toString()
            val number = number.text.toString()
            val  address = address.text.toString()

            saveFireStore(name, number,address)

        }
        readFireStoreData()
    }

    fun saveFireStore(name: String, number: String, address: Any) {
        val db = FirebaseFirestore.getInstance()
        val user: MutableMap<String, Any> = HashMap()
        user["name"] = name
        user["number"] = number
        user["address"] = address

        db.collection("users")
            .add(user)
            .addOnSuccessListener {
                Toast.makeText(this@MainActivity, "record added successfully ", Toast.LENGTH_SHORT ).show()
            }
            .addOnFailureListener{
                Toast.makeText(this@MainActivity, "record Failed to add ", Toast.LENGTH_SHORT ).show()
            }
        readFireStoreData()
    }

    fun readFireStoreData() {
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .get()
            .addOnCompleteListener {

                val result: StringBuffer = StringBuffer()

                if (it.isSuccessful) {
                    for (document in it.result!!) {
                        result.append(document.data.getValue("name")).append(" ")
                            .append(document.data.getValue("number")).append("")
                            .append(document.data.getValue("address")).append("\n" + "\n ")
                    }
                    textViewResult.setText(result)
                }
            }

    }}