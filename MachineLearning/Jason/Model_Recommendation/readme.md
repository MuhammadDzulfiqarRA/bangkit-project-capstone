# TensorFlow Lite Project
Proyek ini menunjukkan cara mengintegrasikan model TensorFlow Lite ke dalam aplikasi Android Studio.

## Langkah 1: Persiapkan Proyek Android Studio

  **Tambahkan TensorFlow Lite ke Proyek**

   - Buka `build.gradle` (Module: app) dan tambahkan dependensi TensorFlow Lite:
     ```gradle
     dependencies {
         implementation 'org.tensorflow:tensorflow-lite:2.10.0'  // Atau versi terbaru
     }
     ```

   - Sinkronkan proyek untuk mengunduh dependensi.

## Langkah 2: Tambahkan Model TensorFlow Lite ke Direktori Aset

1. **Tambahkan Model TensorFlow Lite**

   - Buat direktori `assets` di dalam direktori `app` jika belum ada.
   - Salin file `cosine_sim_model.tflite` ke dalam direktori `assets`.

## Langkah 3: Muat dan Gunakan Model di Aplikasi Android

1. **Buat Kelas untuk Model TensorFlow Lite**

   Buat kelas baru di `app/src/main/java/com/example/yourproject` misalnya `CosineSimilarityModel.kt`:

   ```kotlin
   package com.example.yourproject

   import android.content.Context
   import org.tensorflow.lite.Interpreter
   import java.nio.MappedByteBuffer
   import java.nio.channels.FileChannel
   import java.io.FileInputStream
   import java.io.IOException

   class CosineSimilarityModel(context: Context) {
       private val interpreter: Interpreter

       init {
           val model = loadModelFile(context, "cosine_sim_model.tflite")
           interpreter = Interpreter(model)
       }

       @Throws(IOException::class)
       private fun loadModelFile(context: Context, modelName: String): MappedByteBuffer {
           val fileDescriptor = context.assets.openFd(modelName)
           val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
           val fileChannel = inputStream.channel
           val startOffset = fileDescriptor.startOffset
           val declaredLength = fileDescriptor.declaredLength
           return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
       }

       fun predict(input: Array<FloatArray>): Array<FloatArray> {
           val output = Array(1) { FloatArray(input[0].size) }
           interpreter.run(input, output)
           return output
       }
   }
    ```

## Langkah 4: Integrasikan Model dengan UI di MainActivity

1. Buka `MainActivity.kt` dan modifikasi seperti berikut:
    ```kotlin
    package com.example.yourproject

    import android.os.Bundle
    import android.widget.TextView
    import androidx.appcompat.app.AppCompatActivity

    class MainActivity : AppCompatActivity() {
        private lateinit var model: CosineSimilarityModel

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            // Inisialisasi model
            model = CosineSimilarityModel(this)

            // Contoh input untuk prediksi
            val input = arrayOf(floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f, 5.0f))

            // Gunakan model untuk membuat prediksi
            val output = model.predict(input)

            // Tampilkan output di TextView
            val textView: TextView = findViewById(R.id.textView)
            textView.text = "Output: ${output.contentDeepToString()}"
        }
    }
    ```

2. Tambahkan `TextView` ke Layout
    Buka `res/layout/activity_main.xml` dan tambahkan `TextView` untuk menampilkan hasil prediksi:
    ```xml
    <?xml version="1.0" encoding="utf-8"?>
    <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".MainActivity">

        <TextView
            android:id="@+id/textView"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Hello World!"
            android:layout_centerInParent="true"/>
    </RelativeLayout>
    ```