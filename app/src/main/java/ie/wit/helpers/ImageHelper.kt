package ie.wit.helpers

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.provider.MediaStore.Images.Media.getBitmap
import androidx.fragment.app.Fragment
import ie.wit.R
import java.io.IOException


fun showImagePicker(parent: Fragment, id: Int) {
    val intent = Intent()
    intent.type = "image/*"
    intent.action = Intent.ACTION_OPEN_DOCUMENT
    intent.addCategory(Intent.CATEGORY_OPENABLE)
    val chooser = Intent.createChooser(intent, R.string.select_teamA_image.toString())
    parent.startActivityForResult(chooser, id)
}

/*
fun readImage(fragment: Fragment, resultCode: Int, data: Intent?): Bitmap? {
    var bitmap: Bitmap? = null
    if (resultCode == Fragment.RESULT_OK && data != null && data.data != null) {
        try {
            bitmap = getBitmap(getActivity().fragment.contentResolver, data.data)

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    return bitmap
}

 */


