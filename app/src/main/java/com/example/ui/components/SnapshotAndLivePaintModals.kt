package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material.icons.filled.FlashOff
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Hearing
import androidx.compose.material.icons.filled.PhotoAlbum
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ScreenShare
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.ChildProfile
import com.example.ui.theme.EarthAmber100
import com.example.ui.theme.EarthAmber600
import com.example.ui.theme.MossGreen600
import com.example.ui.theme.NaturalBg
import com.example.ui.theme.NaturalBorder
import com.example.ui.theme.NaturalGreen100
import com.example.ui.theme.NaturalGreen700
import com.example.ui.theme.NaturalGreen900
import com.example.ui.theme.NaturalSurface
import com.example.ui.theme.NaturalSurfaceVariant
import com.example.ui.theme.NaturalTextPrimary
import com.example.ui.theme.NaturalTextSecondary
import com.example.ui.theme.Terracotta100
import com.example.ui.theme.Terracotta700
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class SnapshotItem(
    val id: Long,
    val title: String,
    val time: String,
    val type: String, // CAMERA_FRONT, CAMERA_BACK, SCREEN
    val backgroundColors: List<Color>,
    val appOrScene: String
)

/**
 * 1. CAMERA SNAPSHOT DIALOG (रिमोट कैमरा स्नैपशॉट)
 * Remote photo snapshot capture with front/back camera switch, flash, gallery, and timestamp watermark.
 */
@Composable
fun CameraSnapshotDialog(
    child: ChildProfile,
    isHindi: Boolean,
    onDismiss: () -> Unit
) {
    var isFrontCamera by remember { mutableStateOf(false) }
    var isFlashOn by remember { mutableStateOf(false) }
    var isCapturing by remember { mutableStateOf(false) }
    var selectedResolution by remember { mutableStateOf("1080p FHD") }
    var statusMessage by remember { mutableStateOf<String?>(null) }

    val recentSnapshots = remember {
        mutableStateListOf(
            SnapshotItem(
                id = 1,
                title = "Study Desk Front Snapshot",
                time = "Today, 02:45 PM",
                type = "CAMERA_FRONT",
                backgroundColors = listOf(Color(0xFF1E293B), Color(0xFF334155)),
                appOrScene = "Studying Math at Desk"
            ),
            SnapshotItem(
                id = 2,
                title = "Room Environment Back Snapshot",
                time = "Today, 11:20 AM",
                type = "CAMERA_BACK",
                backgroundColors = listOf(Color(0xFF0F172A), Color(0xFF1E293B)),
                appOrScene = "Living Room / Safe Area"
            ),
            SnapshotItem(
                id = 3,
                title = "Classroom Front Snapshot",
                time = "Yesterday, 01:15 PM",
                type = "CAMERA_FRONT",
                backgroundColors = listOf(Color(0xFF1E3A5F), Color(0xFF2D3748)),
                appOrScene = "School Campus"
            )
        )
    }

    var selectedSnapshotPreview by remember { mutableStateOf<SnapshotItem?>(recentSnapshots.firstOrNull()) }

    LaunchedEffect(isCapturing) {
        if (isCapturing) {
            delay(1600)
            val timeStr = SimpleDateFormat("hh:mm:ss a", Locale.getDefault()).format(Date())
            val newSnap = SnapshotItem(
                id = System.currentTimeMillis(),
                title = if (isFrontCamera) "Live Front Snapshot" else "Live Rear Snapshot",
                time = "Today, $timeStr",
                type = if (isFrontCamera) "CAMERA_FRONT" else "CAMERA_BACK",
                backgroundColors = if (isFrontCamera) listOf(Color(0xFF064E3B), Color(0xFF065F46)) else listOf(Color(0xFF1E3A8A), Color(0xFF1E40AF)),
                appOrScene = if (isFrontCamera) "Child Facing Camera" else "Surroundings View"
            )
            recentSnapshots.add(0, newSnap)
            selectedSnapshotPreview = newSnap
            isCapturing = false
            statusMessage = if (isHindi) "✅ स्नैपशॉट सफलतापूर्वक सुरक्षित किया गया!" else "✅ Snapshot captured and saved securely!"
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0F172A)),
            color = Color(0xFF0F172A)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 40.dp, bottom = 20.dp, start = 16.dp, end = 16.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(NaturalGreen700),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.PhotoCamera,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = if (isHindi) "रिमोट कैमरा स्नैपशॉट" else "Remote Camera Snapshot",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = Color.White
                            )
                            Text(
                                text = if (isHindi) "${child.name} का डिवाइस (${child.deviceModel})" else "${child.name}'s Device (${child.deviceModel})",
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.1f))
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Camera Viewport Box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clip(RoundedCornerShape(20.dp))
                        .background(
                            Brush.verticalGradient(
                                selectedSnapshotPreview?.backgroundColors ?: listOf(Color(0xFF1E293B), Color(0xFF0F172A))
                            )
                        )
                        .border(1.5.dp, Color.White.copy(alpha = 0.15f), RoundedCornerShape(20.dp))
                ) {
                    // Viewport Simulation Graphic
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Top info overlay
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.Black.copy(alpha = 0.6f))
                                    .padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF22C55E))
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (isFrontCamera) "FRONT CAM" else "REAR CAM",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.Black.copy(alpha = 0.6f))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = selectedResolution,
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        // Center Focus Reticle
                        Box(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isCapturing) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    CircularProgressIndicator(
                                        color = NaturalGreen700,
                                        modifier = Modifier.size(48.dp),
                                        strokeWidth = 3.dp
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(
                                        text = if (isHindi) "सुरक्षित फोटो ली जा रही है..." else "Capturing remote snapshot...",
                                        color = Color.White,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            } else {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Box(
                                        modifier = Modifier
                                            .size(100.dp)
                                            .border(1.5.dp, Color.White.copy(alpha = 0.3f), CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            if (isFrontCamera) Icons.Default.PhotoCamera else Icons.Default.AutoAwesome,
                                            contentDescription = null,
                                            tint = Color.White.copy(alpha = 0.7f),
                                            modifier = Modifier.size(36.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = selectedSnapshotPreview?.appOrScene ?: (if (isHindi) "लाइव दृश्य सक्रिय" else "Live Lens Active"),
                                        color = Color.White.copy(alpha = 0.9f),
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = selectedSnapshotPreview?.time ?: "",
                                        color = Color.White.copy(alpha = 0.6f),
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        }

                        // Bottom Watermark
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.Black.copy(alpha = 0.5f))
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "🔒 ParentGuard Encrypted Snapshot",
                                color = Color.White.copy(alpha = 0.7f),
                                fontSize = 10.sp
                            )
                            Text(
                                text = "GPS: 28.6139° N, 77.2090° E",
                                color = Color.White.copy(alpha = 0.7f),
                                fontSize = 10.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Action Controls Bar
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Switch Camera
                        IconButton(
                            onClick = { isFrontCamera = !isFrontCamera },
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.1f))
                        ) {
                            Icon(
                                Icons.Default.Cameraswitch,
                                contentDescription = "Switch Camera",
                                tint = Color.White
                            )
                        }

                        // Main Shutter Button
                        Button(
                            onClick = { isCapturing = true },
                            enabled = !isCapturing,
                            colors = ButtonDefaults.buttonColors(containerColor = NaturalGreen700),
                            shape = CircleShape,
                            modifier = Modifier.size(64.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Icon(
                                Icons.Default.PhotoCamera,
                                contentDescription = "Take Snapshot",
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        // Flash Toggle
                        IconButton(
                            onClick = { isFlashOn = !isFlashOn },
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(if (isFlashOn) EarthAmber600.copy(alpha = 0.3f) else Color.White.copy(alpha = 0.1f))
                        ) {
                            Icon(
                                if (isFlashOn) Icons.Default.FlashOn else Icons.Default.FlashOff,
                                contentDescription = "Flash",
                                tint = if (isFlashOn) Color(0xFFFDE047) else Color.White
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Recent Snapshots Gallery
                Text(
                    text = if (isHindi) "हाल के स्नैपशॉट (${recentSnapshots.size})" else "Recent Snapshots (${recentSnapshots.size})",
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 4.dp, bottom = 6.dp)
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(recentSnapshots) { item ->
                        val isSelected = selectedSnapshotPreview?.id == item.id
                        Card(
                            modifier = Modifier
                                .width(130.dp)
                                .height(80.dp)
                                .clickable { selectedSnapshotPreview = item }
                                .border(
                                    width = if (isSelected) 2.dp else 1.dp,
                                    color = if (isSelected) NaturalGreen700 else Color.White.copy(alpha = 0.15f),
                                    shape = RoundedCornerShape(12.dp)
                                ),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B))
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.verticalGradient(item.backgroundColors)
                                    )
                                    .padding(8.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = if (item.type == "CAMERA_FRONT") "📷 Front" else "📸 Rear",
                                        color = Color.White,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    if (isSelected) {
                                        Icon(
                                            Icons.Default.Check,
                                            contentDescription = null,
                                            tint = NaturalGreen700,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                }
                                Text(
                                    text = item.time,
                                    color = Color.White.copy(alpha = 0.8f),
                                    fontSize = 9.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * 2. SCREEN SNAPSHOT DIALOG (स्क्रीन स्नैपशॉट कैप्चर)
 * Real-time instant screen snapshot viewer with current active app badge, timestamp, and save/download.
 */
@Composable
fun ScreenSnapshotDialog(
    child: ChildProfile,
    isHindi: Boolean,
    onDismiss: () -> Unit
) {
    var isRefreshing by remember { mutableStateOf(false) }
    var activeAppOnScreen by remember { mutableStateOf("YouTube Kids") }
    var snapshotTime by remember { mutableStateOf(SimpleDateFormat("hh:mm:ss a", Locale.getDefault()).format(Date())) }
    var saveSuccessMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            delay(1200)
            snapshotTime = SimpleDateFormat("hh:mm:ss a", Locale.getDefault()).format(Date())
            isRefreshing = false
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0F172A)),
            color = Color(0xFF0F172A)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 40.dp, bottom = 20.dp, start = 16.dp, end = 16.dp)
            ) {
                // Top Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF0284C7)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.ScreenShare,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = if (isHindi) "स्क्रीन स्नैपशॉट (Screenshot)" else "Screen Snapshot",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = Color.White
                            )
                            Text(
                                text = if (isHindi) "स्क्रीन कैप्चर समय: $snapshotTime" else "Captured at: $snapshotTime",
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.1f))
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Simulated Phone Frame with Screen Content
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color(0xFF1E293B))
                        .border(2.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(24.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    listOf(Color(0xFF0F172A), Color(0xFF1E293B), Color(0xFF0B1320))
                                )
                            )
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Phone Status bar
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "02:45", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "5G", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(text = "🔋 ${child.batteryPercent}%", color = Color.White, fontSize = 11.sp)
                            }
                        }

                        // App Content Simulation
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF2D3748))
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFEF4444)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.PhotoCamera, contentDescription = null, tint = Color.White)
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "YouTube Kids - Educational Stream",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Watching: Science for Kids - Solar System Episode 4",
                                    color = Color.White.copy(alpha = 0.7f),
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                LinearProgressIndicator(
                                    progress = { 0.65f },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(4.dp)
                                        .clip(RoundedCornerShape(2.dp)),
                                    color = Color(0xFFEF4444),
                                    trackColor = Color.White.copy(alpha = 0.2f)
                                )
                            }
                        }

                        // Watermark Bar
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.Black.copy(alpha = 0.5f))
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "📱 ${child.deviceModel} • Screen Active",
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 10.sp
                            )
                            Text(
                                text = "ParentGuard Verified",
                                color = NaturalGreen700,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    if (isRefreshing) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.6f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                CircularProgressIndicator(color = Color(0xFF0284C7), strokeWidth = 3.dp)
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = if (isHindi) "नया स्क्रीनशॉट लिया जा रहा है..." else "Refreshing live screen snapshot...",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Buttons Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = { isRefreshing = true },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (isHindi) "रीफ्रेश करें" else "Refresh")
                    }

                    Button(
                        onClick = {
                            saveSuccessMessage = if (isHindi) "स्क्रीनशॉट गैलरी में सेव हुआ!" else "Screenshot saved to parent phone gallery!"
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7))
                    ) {
                        Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(18.dp), tint = Color.White)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (isHindi) "सेव करें" else "Save Photo", color = Color.White)
                    }
                }
            }
        }
    }
}

/**
 * 3. LIVE PAINTING & COLLABORATIVE WHITEBOARD MODAL (लाइव पेंटिंग व स्क्रीन ड्रॉइंग)
 * Allows parents to draw live annotations, circles, arrows, and brush strokes over child's screen.
 */
data class DrawingStroke(
    val path: Path,
    val color: Color,
    val strokeWidth: Float
)

@Composable
fun LivePaintingDialog(
    child: ChildProfile,
    isHindi: Boolean,
    onDismiss: () -> Unit
) {
    val strokes = remember { mutableStateListOf<DrawingStroke>() }
    var currentPath by remember { mutableStateOf<Path?>(null) }
    var selectedColor by remember { mutableStateOf(Color(0xFFEF4444)) } // Red by default
    var selectedStrokeWidth by remember { mutableFloatStateOf(8f) }
    var isLaserMode by remember { mutableStateOf(false) }
    var laserPoint by remember { mutableStateOf<Offset?>(null) }

    val colorsList = listOf(
        Color(0xFFEF4444), // Red
        Color(0xFFF59E0B), // Amber / Yellow
        Color(0xFF22C55E), // Green
        Color(0xFF06B6D4), // Cyan
        Color(0xFF3B82F6), // Blue
        Color(0xFFA855F7), // Purple
        Color.White        // White
    )

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0B0F19)),
            color = Color(0xFF0B0F19)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 36.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
            ) {
                // Header Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFFA855F7)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Brush, contentDescription = null, tint = Color.White, modifier = Modifier.size(22.dp))
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = if (isHindi) "लाइव स्क्रीन पेंटिंग (Live Painting)" else "Live Screen Painting",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = Color.White
                            )
                            Text(
                                text = if (isHindi) "बच्चे की स्क्रीन पर लाइव ड्रॉइंग करें" else "Draw instructions on ${child.name}'s screen in real time",
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.1f))
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Canvas Screen Viewport
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFF1E293B))
                        .border(2.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(20.dp))
                ) {
                    // Background Simulation of Child's Active Screen
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Math Homework • Chapter 4", color = Color.White.copy(alpha = 0.5f), fontSize = 12.sp)
                            Text(text = "🔴 LIVE SYNC ACTIVE", color = Color(0xFFEF4444), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF334155).copy(alpha = 0.4f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (isHindi) "यहाँ अपनी उंगली से ड्रॉ करें\nबच्चे की स्क्रीन पर तुरंत दिखेगा" else "Draw or highlight with your finger\nSyncs to child screen instantly",
                                color = Color.White.copy(alpha = 0.35f),
                                textAlign = TextAlign.Center,
                                fontSize = 14.sp
                            )
                        }

                        Text(
                            text = "💡 Tip: Circle the correct answer or point an arrow to guide your child",
                            color = Color.White.copy(alpha = 0.4f),
                            fontSize = 11.sp
                        )
                    }

                    // Interactive Drawing Canvas
                    Canvas(
                        modifier = Modifier
                            .fillMaxSize()
                            .pointerInput(selectedColor, selectedStrokeWidth, isLaserMode) {
                                detectDragGestures(
                                    onDragStart = { offset ->
                                        if (isLaserMode) {
                                            laserPoint = offset
                                        } else {
                                            val newPath = Path().apply { moveTo(offset.x, offset.y) }
                                            currentPath = newPath
                                        }
                                    },
                                    onDrag = { change, _ ->
                                        if (isLaserMode) {
                                            laserPoint = change.position
                                        } else {
                                            currentPath?.lineTo(change.position.x, change.position.y)
                                        }
                                    },
                                    onDragEnd = {
                                        if (isLaserMode) {
                                            laserPoint = null
                                        } else {
                                            currentPath?.let {
                                                strokes.add(DrawingStroke(it, selectedColor, selectedStrokeWidth))
                                            }
                                            currentPath = null
                                        }
                                    }
                                )
                            }
                    ) {
                        // Draw finalized strokes
                        strokes.forEach { stroke ->
                            drawPath(
                                path = stroke.path,
                                color = stroke.color,
                                style = Stroke(
                                    width = stroke.strokeWidth,
                                    cap = StrokeCap.Round,
                                    join = StrokeJoin.Round
                                )
                            )
                        }

                        // Draw current active stroke
                        currentPath?.let { path ->
                            drawPath(
                                path = path,
                                color = selectedColor,
                                style = Stroke(
                                    width = selectedStrokeWidth,
                                    cap = StrokeCap.Round,
                                    join = StrokeJoin.Round
                                )
                            )
                        }

                        // Draw laser pointer
                        laserPoint?.let { pos ->
                            drawCircle(color = Color(0xFFEF4444).copy(alpha = 0.4f), radius = 24f, center = pos)
                            drawCircle(color = Color(0xFFEF4444), radius = 10f, center = pos)
                            drawCircle(color = Color.White, radius = 4f, center = pos)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Palette and Tools Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        // Colors Selection Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            colorsList.forEach { color ->
                                val isSelected = selectedColor == color && !isLaserMode
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(color)
                                        .border(
                                            width = if (isSelected) 3.dp else 1.dp,
                                            color = if (isSelected) Color.White else Color.Transparent,
                                            shape = CircleShape
                                        )
                                        .clickable {
                                            selectedColor = color
                                            isLaserMode = false
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (isSelected) {
                                        Icon(
                                            Icons.Default.Check,
                                            contentDescription = null,
                                            tint = if (color == Color.White) Color.Black else Color.White,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Controls Row (Laser, Stroke size, Undo, Clear)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Laser Mode Toggle
                            OutlinedButton(
                                onClick = { isLaserMode = !isLaserMode },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = if (isLaserMode) Color(0xFFEF4444).copy(alpha = 0.25f) else Color.Transparent,
                                    contentColor = Color.White
                                ),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Icon(Icons.Default.FiberManualRecord, contentDescription = null, tint = Color(0xFFEF4444), modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(if (isHindi) "लेज़र पॉइंटर" else "Laser Pointer", fontSize = 11.sp)
                            }

                            // Undo Button
                            IconButton(
                                onClick = {
                                    if (strokes.isNotEmpty()) {
                                        strokes.removeAt(strokes.lastIndex)
                                    }
                                },
                                enabled = strokes.isNotEmpty()
                            ) {
                                Icon(Icons.Default.Undo, contentDescription = "Undo", tint = if (strokes.isNotEmpty()) Color.White else Color.White.copy(alpha = 0.3f))
                            }

                            // Clear All Button
                            IconButton(
                                onClick = { strokes.clear() },
                                enabled = strokes.isNotEmpty()
                            ) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear", tint = if (strokes.isNotEmpty()) Color(0xFFEF4444) else Color.White.copy(alpha = 0.3f))
                            }
                        }
                    }
                }
            }
        }
    }
}
