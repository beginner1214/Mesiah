package com.example.mesiah.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.TextPart
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonNull.content

@Composable
fun AccountScreen() {
    // State for user input, chat messages, loading, errors, and model initialization
    var userInput by remember { mutableStateOf(TextFieldValue("")) }
    val messages = remember { mutableStateListOf<Pair<String, Boolean>>() } // Pair<message, isUser>
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    // State to hold the GenerativeModel and initialize it safely
    var generativeModel by remember { mutableStateOf<GenerativeModel?>(null) }

    // Use LaunchedEffect to initialize the model safely outside of remember
    LaunchedEffect(Unit) {
        try {
            generativeModel = GenerativeModel(
                modelName = "gemini-1.5-flash",
                apiKey = "AIzaSyBFK6980BgOu9PYOeEDwm0aUhb_WBzu5jc"
            )
        } catch (e: Exception) {
            errorMessage = "Failed to initialize Gemini: ${e.message}"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Error message display
        errorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Chat display area
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages) { message ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = if (message.second) Arrangement.End else Arrangement.Start
                ) {
                    Card(
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (message.second) MaterialTheme.colorScheme.primaryContainer
                            else MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Text(
                            text = message.first,
                            modifier = Modifier.padding(8.dp),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }

        // Loading indicator
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp)
            )
        }

        // Input area
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = userInput,
                onValueChange = { userInput = it },
                modifier = Modifier
                    .weight(1f)
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant,
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(12.dp),
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                enabled = !isLoading
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (userInput.text.isNotBlank() && generativeModel != null) {
                        // Add user message to chat
                        messages.add(Pair(userInput.text, true))
                        val currentInput = userInput.text
                        userInput = TextFieldValue("")
                        isLoading = true
                        errorMessage = null

                        // Call Gemini API with proper content structure
                        coroutineScope.launch {
                            try {
                                // --- CORRECTION START ---
                                // Use the content builder DSL
                                val content = content { // <--- Use the builder function
                                    role = "user"      // <--- Set role inside
                                    text(currentInput) // <--- Use text() to add the text part
                                }
                                // --- CORRECTION END ---

                                val response =
                                    generativeModel!!.generateContent(content) // Send the correctly built content
                                messages.add(Pair(response.text ?: "No response", false))
                            } catch (e: Exception) {
                                errorMessage = "Error: ${e.message}"
                                // Optionally add the error message to the chat as well, as you were doing
                                messages.add(Pair("Error generating response: ${e.message}", false))
                            } finally {
                                isLoading = false
                            }
                        }
                    } else if (generativeModel == null) {
                        errorMessage =
                            "Gemini model not initialized. Check API key or initialization."
                    } else if (userInput.text.isBlank()) {
                        errorMessage = "Please enter a message." // Handle blank input
                    }
                },
                modifier = Modifier.height(48.dp),
                enabled = !isLoading && generativeModel != null // Also disable button if model isn't ready
            ) {
                Text("Send")
            }
        }
    }
}