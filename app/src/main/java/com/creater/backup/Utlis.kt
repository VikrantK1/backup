package com.creater.backup

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import android.webkit.ConsoleMessage
import android.widget.*
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.creater.backup.installedApps.AppData
import com.creater.backup.installedApps.BackUpAdapter
import com.creater.backup.installedApps.Progess_Data
import com.google.android.material.card.MaterialCardView

fun toastmessage(context: Context,message:String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

}
@BindingAdapter("iconsOfApp")
fun iconsImage(imageView: ImageView, data: Drawable) {

        imageView.setImageDrawable(data)

}

    @BindingAdapter("listOfApps")
    fun bindingApps(recyclerView: RecyclerView, data: List<AppData>?) {
        val adapter = recyclerView.adapter as BackUpAdapter
        if (data != null) {
            adapter.appList = data
        }
    }

    @BindingAdapter("container")

    fun setbackground(view: MaterialCardView, value: Boolean) {
        if (value) {
            view.setCardBackgroundColor(Color.BLUE)
        }

    }

    @BindingAdapter("Selected")
    fun selectedApps(btn: Button, noOfSelectedApps: Int) {
        if (noOfSelectedApps == 0) {
            btn.visibility = View.GONE
        } else {
            btn.visibility = View.VISIBLE
            btn.text = "Selected Apps(${noOfSelectedApps})"
        }
    }

@BindingAdapter("progessData")
 fun progess(pr:ProgressBar,data:Int)
{
    pr.progress=data
}



