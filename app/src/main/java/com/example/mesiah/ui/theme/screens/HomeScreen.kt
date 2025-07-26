package com.example.mesiah.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Define custom colors for a vibrant, welcoming theme
val PrimaryGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF6200EA), // Deep purple
        Color(0xFF03DAC5) // Teal
    )
)
val CardColorCamera = Color(0xFFBBDEFB) // Light blue for camera card
val CardColorPdf = Color(0xFFF8BBD0) // Soft pink for PDF card
val CardColorChatbot = Color(0xFFB2DFDB) // Mint green for chatbot card
val AccentColor = Color(0xFFFFCA28) // Warm yellow for highlights

// App content strings
const val WELCOME_MESSAGE = "Welcome, Learner!"
const val CAMERA_TITLE = "Ask with Camera"
const val CAMERA_DESC = "Point at a question and get answers"
const val PDF_TITLE = "Chat with PDF"
const val PDF_DESC = "Upload a PDF and ask questions about it"
const val CHATBOT_TITLE = "AI Chatbot"
const val CHATBOT_DESC = "Ask anything, get help with subjects"
const val QUOTE = "“Education is the most powerful weapon which you can use to change the world.”"
const val QUOTE_AUTHOR = "– Nelson Mandela"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onCameraClick: () -> Unit = {},
    onPdfClick: () -> Unit = {},
    onChatbotClick: () -> Unit = {}
) {
    Scaffold(

        modifier = Modifier.background(PrimaryGradient) // Apply gradient background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Welcome Section
            Text(
                text = WELCOME_MESSAGE,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                ),
                textAlign = TextAlign.Center,
                color = Color.Black
            )


            // Inspirational Quote
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White.copy(alpha = 0.1f))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = QUOTE,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        color = Color.Blue,
                        fontSize = 20.sp
                    ),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = QUOTE_AUTHOR,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Magenta,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // Feature Cards
            FeatureCard(
                title = CAMERA_TITLE,
                description = CAMERA_DESC,
                icon = Icons.Filled.CameraAlt,
                cardColor = CardColorCamera,
                onClick = onCameraClick
            )

            FeatureCard(
                title = PDF_TITLE,
                description = PDF_DESC,
                icon = Icons.Filled.PictureAsPdf,
                cardColor = CardColorPdf,
                onClick = onPdfClick
            )

            FeatureCard(
                title = CHATBOT_TITLE,
                description = CHATBOT_DESC,
                icon = Icons.Filled.Chat,
                cardColor = CardColorChatbot,
                onClick = onChatbotClick
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeatureCard(
    title: String,
    description: String,
    icon: ImageVector,
    cardColor: Color,
    onClick: () -> Unit
) {
    ElevatedCard(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(48.dp),
                tint = Color(0xFF6200EA) // Deep purple for icons
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    ),
                    color = Color(0xFF1A237E) // Dark navy for contrast
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                    color = Color(0xFF424242) // Dark gray for readability
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen()
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreviewDark() {
    MaterialTheme {
        HomeScreen()
    }
}