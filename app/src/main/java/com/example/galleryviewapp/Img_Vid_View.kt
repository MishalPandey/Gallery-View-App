package com.example.galleryviewapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Img_Vid_View : AppCompatActivity() {

    var imageList = arrayListOf<String>()
    var videoList = arrayListOf<String>()
    var list = arrayListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_img_vid_view)
        retrieveImages(this)
        retrieveVideos(this)
        var rv = findViewById<RecyclerView>(R.id.rv)

        // Combine imageList and videoList into a single list
        if (imageList.size > 0 && videoList.size > 0) {
            // Alternate between adding an image and a video
            for (i in 0 until (imageList.size + videoList.size)) {
                if (i % 2 == 0 && i / 2 < imageList.size) {
                    list.add(imageList[i / 2])
                } else if (i % 2 == 1 && i / 2 < videoList.size) {
                    list.add(videoList[i / 2])
                }
            }
        } else if (imageList.size > 0) {
            list.addAll(imageList)
        } else if (videoList.size > 0) {
            list.addAll(videoList)
        }


        rv.layoutManager = GridLayoutManager(this, 4)
        rv.adapter = Adapter(this, list)




    }

    private fun retrieveImages(context: Context) {

        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATA
        )

        val selection = "${MediaStore.Images.Media.DATA} like?"
        val selectionArgs = arrayOf("%${Environment.DIRECTORY_DCIM}%")

        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        val query = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )

        query?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val pathColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val path = cursor.getString(pathColumn)

                val ImageItem = DataClass(id, name, path)
                imageList.add(ImageItem.path)
                 //list.add(ImageItem.path)
            }

        }
    }
        fun retrieveVideos(context: Context) {


            val projection = arrayOf(
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DATA
            )

            val selection = "${MediaStore.Video.Media.DATA} like?"
            val selectionArgs = arrayOf("%${Environment.DIRECTORY_DCIM}%")

            val sortOrder = "${MediaStore.Video.Media.DATE_ADDED} DESC"

            val query = context.contentResolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                sortOrder
            )

            query?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
                val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
                val pathColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val name = cursor.getString(nameColumn)
                    val path = cursor.getString(pathColumn)

                    val videoItem = DataClass(id, name, path)
                    videoList.add(videoItem.path)
                     //list.add(videoItem.path)
                }




                cursor.close()

            }


        }

    }

