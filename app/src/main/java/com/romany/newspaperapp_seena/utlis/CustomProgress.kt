package com.romany.newspaperapp_seena.utlis

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.AnimationDrawable
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.romany.newspaperapp_seena.R


class CustomProgress  : Dialog {
    constructor(context: Context) : super(context)
    constructor(context: Context, themeResId: Int) : super(context, themeResId)

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        val imageView = findViewById<View>(R.id.spinnerImageView) as ImageView
        val spinner = imageView.background as AnimationDrawable
        spinner.start()
    }

    fun setMessage(message: CharSequence?) {
        if (message != null && message.isNotEmpty()) {
            findViewById<View>(R.id.message).setVisibility(View.VISIBLE)
            val txt = findViewById<View>(R.id.message) as TextView
            txt.text = message
            txt.invalidate()
        }
    }

    companion object {
        @SuppressLint("CutPasteId")
        fun show(
            context: Context,
            message: CharSequence?,
            cancelable: Boolean,
            cancelListener: DialogInterface.OnCancelListener?
        ): CustomProgress {
            val dialog = CustomProgress(context, R.style.Custom_Progress)
            dialog.setTitle("")
            dialog.setContentView(R.layout.progress_custom)
            if (message == null || message.isEmpty()) {
                dialog.findViewById<View>(R.id.message).visibility = View.GONE
            } else {
                val txt = dialog.findViewById<View>(R.id.message) as TextView
                txt.text = message
            }
            dialog.setCancelable(true)
            dialog.setOnCancelListener(cancelListener)
            dialog.window!!.attributes.gravity = Gravity.CENTER
            val lp = dialog.window!!.attributes
            lp.dimAmount = 0.2f
            dialog.window!!.attributes = lp
            dialog.show()
            return dialog
        }
    }
}