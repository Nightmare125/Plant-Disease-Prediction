package com.example.farmaid.pages

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.graphics.Bitmap
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel

class MLPredictor(private val context: Context) {

    private val interpreter: Interpreter
    private val inputShape: IntArray
    private val outputShape: IntArray

    init {
        val model = loadModelFile("plant_disease_model.tflite")
        interpreter = Interpreter(model)
        inputShape = interpreter.getInputTensor(0).shape()
        outputShape = interpreter.getOutputTensor(0).shape()
    }

    private fun loadModelFile(filename: String): ByteBuffer {
        val assetFileDescriptor = context.assets.openFd(filename)
        val inputStream = FileInputStream(assetFileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = assetFileDescriptor.startOffset
        val declaredLength = assetFileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    fun predict(image: Bitmap): String {
        val inputBuffer = preprocessImage(image)
        val outputBuffer = ByteBuffer.allocateDirect(outputShape.reduce { acc, i -> acc * i } * 4).order(ByteOrder.nativeOrder())

        interpreter.run(inputBuffer, outputBuffer)

        return processOutput(outputBuffer)
    }

    private fun preprocessImage(image: Bitmap): ByteBuffer {
        val scaledImage = Bitmap.createScaledBitmap(image, inputShape[1], inputShape[2], true)
        val inputBuffer = ByteBuffer.allocateDirect(inputShape.reduce { acc, i -> acc * i } * 4).order(ByteOrder.nativeOrder())

        for (y in 0 until inputShape[1]) {
            for (x in 0 until inputShape[2]) {
                val pixel = scaledImage.getPixel(x, y)
                inputBuffer.putFloat(((pixel shr 16 and 0xFF) - 127.5f) / 127.5f)
                inputBuffer.putFloat(((pixel shr 8 and 0xFF) - 127.5f) / 127.5f)
                inputBuffer.putFloat(((pixel and 0xFF) - 127.5f) / 127.5f)
            }
        }
        return inputBuffer
    }

    private fun processOutput(outputBuffer: ByteBuffer): String {
        outputBuffer.rewind()
        val results = FloatArray(outputShape[1])
        for (i in results.indices) {
            results[i] = outputBuffer.float
        }
        val maxIndex = results.indices.maxByOrNull { results[it] } ?: -1
        return "Predicted class: $maxIndex"
    }
}