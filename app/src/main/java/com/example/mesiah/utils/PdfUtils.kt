package com.example.mesiah.utils

import android.content.Context
import android.net.Uri
import android.util.Log
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.text.PDFTextStripper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

private const val TAG = "PdfUtils"

object PdfUtils {
    // Initialize PDFBox (call this in your Application class)
    fun initPdfBox(context: Context) {
        PDFBoxResourceLoader.init(context)
    }

    suspend fun extractTextFromPdf(context: Context, pdfUri: Uri): String = withContext(Dispatchers.IO) {
        try {
            // Create a temporary file from the URI
            val tempFile = File(context.cacheDir, "temp_pdf.pdf")
            context.contentResolver.openInputStream(pdfUri)?.use { input ->
                FileOutputStream(tempFile).use { output ->
                    input.copyTo(output)
                }
            }

            // Load the PDF document
            val document = PDDocument.load(tempFile)

            // Create PDF text stripper
            val stripper = PDFTextStripper()

            // Extract text
            val text = stripper.getText(document)

            // Close the document
            document.close()

            // Delete the temporary file
            tempFile.delete()

            return@withContext text
        } catch (e: Exception) {
            Log.e(TAG, "Error extracting text from PDF", e)
            return@withContext "Error extracting text: ${e.message}"
        }
    }

    // Function to create chunks from the extracted text
    fun createChunks(text: String, chunkSize: Int = 1000, overlap: Int = 200): List<String> {
        val chunks = mutableListOf<String>()
        var i = 0
        while (i < text.length) {
            val end = minOf(i + chunkSize, text.length)
            val chunk = text.substring(i, end)
            if (chunk.length >= 100) { // Ensure chunk is meaningful
                chunks.add(chunk)
            }
            i += (chunkSize - overlap)
        }
        return chunks
    }

    // This is a simple implementation for finding relevant chunks
    // For a production app, you might want to use a vector database or embeddings
    fun findRelevantChunks(chunks: List<String>, question: String, topK: Int = 3): List<String> {
        // Simple keyword matching to find relevant chunks
        // This is a basic implementation - for production, use embeddings/semantic search
        val questionWords = question.lowercase().split(" ")
            .filter { it.length > 3 } // Filter out short words

        // Score each chunk by counting occurrences of question words
        val scoredChunks = chunks.map { chunk ->
            val score = questionWords.sumOf { word ->
                val regex = "\\b$word\\b".toRegex(RegexOption.IGNORE_CASE)
                regex.findAll(chunk).count()
            }
            Pair(chunk, score)
        }

        // Sort by score (descending) and take top K
        return scoredChunks.sortedByDescending { it.second }
            .take(topK)
            .map { it.first }
    }
}