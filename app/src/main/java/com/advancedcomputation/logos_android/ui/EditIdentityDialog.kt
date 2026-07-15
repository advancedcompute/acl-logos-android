package com.advancedcomputation.logos_android.ui

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.advancedcomputation.logos_android.R
import com.advancedcomputation.logos_android.db.Identity
import com.google.android.material.textfield.TextInputEditText

class EditIdentityDialog(private val context: Context, private val identity: Identity, private val onSave: (String, String) -> Unit)
{
    fun show() {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.dialog_edit_identity, null)

        val nameInput = view.findViewById<TextInputEditText>(R.id.nameInput)
        val emailInput = view.findViewById<TextInputEditText>(R.id.emailInput)

        nameInput.setText(identity.name)
        emailInput.setText(identity.email)

        val dialog = AlertDialog.Builder(context)
            .setTitle("Edit Identity")
            .setView(view)
            .setPositiveButton("Save", null)
            .setNegativeButton("Cancel", null)
            .create()

        dialog.setOnShowListener {

            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener {

                    val name = nameInput.text.toString().trim()

                    if (name.isBlank()) {
                        nameInput.error = "Name required"
                        return@setOnClickListener
                    }

                    onSave(
                        name,
                        emailInput.text.toString().trim()
                    )

                    dialog.dismiss()
                }
        }

        dialog.show()
    }
}