package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.filled.PhotoAlbum
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.AppUsageRule
import com.example.data.model.ChildProfile
import com.example.data.model.WebFilterRule
import com.example.ui.theme.EarthAmber100
import com.example.ui.theme.EarthAmber600
import com.example.ui.theme.MossGreen100
import com.example.ui.theme.MossGreen600
import com.example.ui.theme.NaturalBg
import com.example.ui.theme.NaturalBorder
import com.example.ui.theme.NaturalCardBg
import com.example.ui.theme.NaturalGreen100
import com.example.ui.theme.NaturalGreen700
import com.example.ui.theme.NaturalGreen900
import com.example.ui.theme.NaturalSurface
import com.example.ui.theme.NaturalSurfaceVariant
import com.example.ui.theme.NaturalTextPrimary
import com.example.ui.theme.NaturalTextSecondary
import com.example.ui.theme.NaturalTextTertiary
import com.example.ui.theme.Terracotta100
import com.example.ui.theme.Terracotta600
import com.example.ui.theme.Terracotta700

/**
 * 1. USAGE SAFETY DIALOG (यूसेज सेफ्टी व डिजिटल वेलबीइंग)
 * Analyzes night-time binge usage, screen distance from eyes, rapid unlocks, and eye-protection blue light.
 */
@Composable
fun UsageSafetyDialog(
    child: ChildProfile,
    isHindi: Boolean,
    onDismiss: () -> Unit
) {
    var lateNightAlert by remember { mutableStateOf(true) }
    var eyeDistanceAlert by remember { mutableStateOf(true) }
    var postureAlert by remember { mutableStateOf(true) }
    var blueLightFilter by remember { mutableStateOf(true) }
    var bingeWarningLimitMinutes by remember { mutableIntStateOf(45) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(NaturalBg),
            color = NaturalBg
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
                                .background(NaturalGreen100),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.HealthAndSafety, contentDescription = null, tint = NaturalGreen700)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = if (isHindi) "यूसेज सेफ्टी (Usage Safety)" else "Usage Safety & Health",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = NaturalTextPrimary
                            )
                            Text(
                                text = if (isHindi) "आंखों की सुरक्षा व डिजिटल स्वास्थ्य" else "Eye Health, Posture & Screen Safety",
                                fontSize = 12.sp,
                                color = NaturalTextSecondary
                            )
                        }
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = NaturalTextPrimary)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Safety Health Score Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(1.dp, RoundedCornerShape(18.dp))
                        .border(1.dp, NaturalBorder, RoundedCornerShape(18.dp)),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = NaturalSurface)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = if (isHindi) "डिजिटल स्वास्थ्य स्कोर" else "Safety & Health Score",
                                fontSize = 13.sp,
                                color = NaturalTextSecondary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (isHindi) "94/100 (सुरक्षित व स्वस्थ)" else "94/100 (Safe & Healthy)",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = NaturalGreen700
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (isHindi) "रात 9 बजे के बाद कोई अत्यधिक उपयोग नहीं" else "No late-night binge detected today",
                                fontSize = 11.sp,
                                color = NaturalTextTertiary
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(54.dp)
                                .clip(CircleShape)
                                .background(NaturalGreen100),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "94%",
                                fontWeight = FontWeight.Bold,
                                color = NaturalGreen700,
                                fontSize = 16.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Safety Rules List
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        SafetyToggleCard(
                            icon = Icons.Default.Nightlight,
                            iconColor = Color(0xFF6366F1),
                            title = if (isHindi) "देर रात उपयोग चेतावनी" else "Late-Night Usage Alert",
                            subtitle = if (isHindi) "रात 10 बजे के बाद स्क्रीन चालू होने पर तुरंत सूचना भेजें" else "Alert parent if screen is used after 10:00 PM",
                            checked = lateNightAlert,
                            onCheckedChange = { lateNightAlert = it }
                        )
                    }

                    item {
                        SafetyToggleCard(
                            icon = Icons.Default.RemoveRedEye,
                            iconColor = Color(0xFF0284C7),
                            title = if (isHindi) "स्क्रीन दूरी सुरक्षा (Eye Distance Alert)" else "Eye Distance & Strain Alert",
                            subtitle = if (isHindi) "यदि स्क्रीन आंखों से 25 सेमी से कम दूरी पर है तो चेतावनी दें" else "Prompt child if screen is held closer than 25cm",
                            checked = eyeDistanceAlert,
                            onCheckedChange = { eyeDistanceAlert = it }
                        )
                    }

                    item {
                        SafetyToggleCard(
                            icon = Icons.Default.Timer,
                            iconColor = EarthAmber600,
                            title = if (isHindi) "लगातार उपयोग ब्रेक (Binge Alert)" else "Continuous Screen Break Alert",
                            subtitle = if (isHindi) "हर 45 मिनट के लगातार उपयोग के बाद 5 मिनट का ब्रेक लें" else "Prompts a 5-minute break every 45 mins of continuous play",
                            checked = postureAlert,
                            onCheckedChange = { postureAlert = it }
                        )
                    }

                    item {
                        SafetyToggleCard(
                            icon = Icons.Default.HealthAndSafety,
                            iconColor = NaturalGreen700,
                            title = if (isHindi) "स्मार्ट ब्लू लाइट प्रोटेक्शन" else "Smart Blue Light Eye Shield",
                            subtitle = if (isHindi) "शाम 7 बजे के बाद स्वतः वार्म डिस्प्ले कलर सक्रिय करें" else "Automatically activate warm screen filter after 7:00 PM",
                            checked = blueLightFilter,
                            onCheckedChange = { blueLightFilter = it }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = NaturalGreen700)
                ) {
                    Text(if (isHindi) "सेटिंग्स सुरक्षित करें" else "Save Safety Settings", fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}

/**
 * 2. SOCIAL APP DETECTION & SCANNER DIALOG (सोशल ऐप डिटेक्शन)
 * Detects all installed social apps (WhatsApp, Instagram, Snapchat, TikTok, etc.) with risk score and 1-tap lock.
 */
data class SocialAppItem(
    val name: String,
    val packageName: String,
    val category: String,
    val riskLevel: String, // LOW, MEDIUM, HIGH
    val usageTodayMinutes: Int,
    val flaggedWordsCount: Int,
    val isBlocked: Boolean,
    val iconColor: Color
)

@Composable
fun SocialAppDetectionDialog(
    child: ChildProfile,
    isHindi: Boolean,
    onDismiss: () -> Unit
) {
    val socialApps = remember {
        mutableStateListOf(
            SocialAppItem("WhatsApp", "com.whatsapp", "Chat & Call", "LOW", 25, 1, false, Color(0xFF25D366)),
            SocialAppItem("Instagram", "com.instagram.android", "Photo & Video", "MEDIUM", 0, 1, true, Color(0xFFE1306C)),
            SocialAppItem("YouTube Kids", "com.google.android.apps.youtube.kids", "Video & Learning", "LOW", 45, 0, false, Color(0xFFFF0000)),
            SocialAppItem("Snapchat", "com.snapchat.android", "Ephemeral Chat", "HIGH", 0, 0, true, Color(0xFFFFFC00)),
            SocialAppItem("Discord", "com.discord", "Gaming Community", "MEDIUM", 0, 0, true, Color(0xFF5865F2)),
            SocialAppItem("Roblox Social", "com.roblox.client", "Gaming & Friends", "LOW", 28, 0, false, Color(0xFF00A2FF)),
            SocialAppItem("Telegram", "org.telegram.messenger", "Messenger", "HIGH", 0, 0, true, Color(0xFF0088CC))
        )
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(NaturalBg),
            color = NaturalBg
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
                                .background(Color(0xFFE1306C).copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Security, contentDescription = null, tint = Color(0xFFE1306C))
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = if (isHindi) "सोशल ऐप डिटेक्शन" else "Social App Detection",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = NaturalTextPrimary
                            )
                            Text(
                                text = if (isHindi) "${socialApps.size} सोशल ऐप्स मॉनिटर किए जा रहे हैं" else "${socialApps.size} Social Apps detected & monitored",
                                fontSize = 12.sp,
                                color = NaturalTextSecondary
                            )
                        }
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = NaturalTextPrimary)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Summary Stats
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(1.dp, RoundedCornerShape(16.dp))
                        .border(1.dp, NaturalBorder, RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = NaturalSurface)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "7", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = NaturalTextPrimary)
                            Text(text = if (isHindi) "स्कैन किए ऐप्स" else "Detected", fontSize = 11.sp, color = NaturalTextSecondary)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "4", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Terracotta700)
                            Text(text = if (isHindi) "ब्लॉक किए गए" else "Blocked", fontSize = 11.sp, color = NaturalTextSecondary)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "98m", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = NaturalGreen700)
                            Text(text = if (isHindi) "आज का समय" else "Screen Time", fontSize = 11.sp, color = NaturalTextSecondary)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Social Apps List
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(socialApps) { item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(1.dp, RoundedCornerShape(16.dp))
                                .border(1.dp, NaturalBorder, RoundedCornerShape(16.dp)),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = NaturalSurface)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(44.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(item.iconColor.copy(alpha = 0.2f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = item.name.take(2).uppercase(),
                                            fontWeight = FontWeight.Bold,
                                            color = item.iconColor,
                                            fontSize = 15.sp
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = item.name,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 15.sp,
                                                color = NaturalTextPrimary
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Box(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(6.dp))
                                                    .background(
                                                        when (item.riskLevel) {
                                                            "HIGH" -> Terracotta100
                                                            "MEDIUM" -> EarthAmber100
                                                            else -> NaturalGreen100
                                                        }
                                                    )
                                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                                            ) {
                                                Text(
                                                    text = item.riskLevel,
                                                    fontSize = 9.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = when (item.riskLevel) {
                                                        "HIGH" -> Terracotta700
                                                        "MEDIUM" -> EarthAmber600
                                                        else -> NaturalGreen700
                                                    }
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = "${item.category} • ${if (isHindi) "आज" else "Today"}: ${item.usageTodayMinutes} min",
                                            fontSize = 12.sp,
                                            color = NaturalTextSecondary
                                        )
                                    }
                                }

                                // Block / Unblock Toggle Button
                                val index = socialApps.indexOf(item)
                                Button(
                                    onClick = {
                                        if (index >= 0) {
                                            socialApps[index] = item.copy(isBlocked = !item.isBlocked)
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (item.isBlocked) Terracotta700 else NaturalGreen700
                                    ),
                                    shape = RoundedCornerShape(10.dp),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Icon(
                                        if (item.isBlocked) Icons.Default.Lock else Icons.Default.LockOpen,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = if (item.isBlocked) (if (isHindi) "ब्लॉक है" else "Blocked") else (if (isHindi) "अनुमति" else "Allowed"),
                                        fontSize = 11.sp,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * 3. ALBUMS SAFETY & MEDIA SCANNER DIALOG (एल्बम व फोटो सुरक्षा)
 * Scans child gallery/albums for sensitive content, screenshots, downloads with blur protection.
 */
data class AlbumSafetyItem(
    val title: String,
    val count: Int,
    val flaggedCount: Int,
    val lastScanTime: String,
    val isProtected: Boolean
)

@Composable
fun AlbumsSafetyDialog(
    child: ChildProfile,
    isHindi: Boolean,
    onDismiss: () -> Unit
) {
    var aiSensitiveFilter by remember { mutableStateOf(true) }
    var blurExplicitImages by remember { mutableStateOf(true) }
    var screenshotDetection by remember { mutableStateOf(true) }
    var albumVaultLock by remember { mutableStateOf(false) }

    val albumsList = remember {
        listOf(
            AlbumSafetyItem("Camera Photos", 142, 0, "10 mins ago", true),
            AlbumSafetyItem("Screenshots", 38, 1, "Just now", true),
            AlbumSafetyItem("WhatsApp Media", 89, 0, "1 hour ago", true),
            AlbumSafetyItem("Downloads Folder", 24, 0, "Yesterday", true)
        )
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(NaturalBg),
            color = NaturalBg
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
                                .background(Color(0xFF8B5CF6).copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.PhotoAlbum, contentDescription = null, tint = Color(0xFF8B5CF6))
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = if (isHindi) "एल्बम व फोटो सुरक्षा (Albums Safety)" else "Albums & Media Safety",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = NaturalTextPrimary
                            )
                            Text(
                                text = if (isHindi) "गैलरी व मीडिया फाइलों की AI सुरक्षा" else "AI Photo & Screenshot Protection",
                                fontSize = 12.sp,
                                color = NaturalTextSecondary
                            )
                        }
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = NaturalTextPrimary)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Protection Toggles
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        SafetyToggleCard(
                            icon = Icons.Default.Shield,
                            iconColor = NaturalGreen700,
                            title = if (isHindi) "AI संवेदनशील फोटो डिटेक्शन" else "AI Sensitive Image Detection",
                            subtitle = if (isHindi) "अनुपयुक्त या संवेदनशील फोटो स्वतः पहचानें व अलर्ट करें" else "Auto-scan and flag sensitive or adult photos on device",
                            checked = aiSensitiveFilter,
                            onCheckedChange = { aiSensitiveFilter = it }
                        )
                    }

                    item {
                        SafetyToggleCard(
                            icon = Icons.Default.VisibilityOff,
                            iconColor = Color(0xFF8B5CF6),
                            title = if (isHindi) "संदिग्ध मीडिया ब्लर करें" else "Blur Inappropriate Media",
                            subtitle = if (isHindi) "गैलरी में संदिग्ध तस्वीरों को स्वतः धुंधला (ब्लर) रखें" else "Keep unapproved photos blurred on child's device",
                            checked = blurExplicitImages,
                            onCheckedChange = { blurExplicitImages = it }
                        )
                    }

                    item {
                        SafetyToggleCard(
                            icon = Icons.Default.PhotoCamera,
                            iconColor = Color(0xFF0284C7),
                            title = if (isHindi) "स्क्रीनशॉट एक्टिविटी मॉनिटर" else "Screenshot Activity Monitor",
                            subtitle = if (isHindi) "बच्चे द्वारा स्क्रीनशॉट लिए जाने पर सूचना दें" else "Notify parent whenever child captures a screenshot",
                            checked = screenshotDetection,
                            onCheckedChange = { screenshotDetection = it }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if (isHindi) "स्कैन किए गए एल्बम फोल्डर" else "Scanned Album Folders",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = NaturalTextPrimary
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                    }

                    items(albumsList) { album ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(1.dp, RoundedCornerShape(14.dp))
                                .border(1.dp, NaturalBorder, RoundedCornerShape(14.dp)),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = NaturalSurface)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.PhotoAlbum, contentDescription = null, tint = NaturalTextSecondary, modifier = Modifier.size(20.dp))
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(text = album.title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = NaturalTextPrimary)
                                        Text(text = "${album.count} ${if (isHindi) "फोटो" else "items"} • ${album.lastScanTime}", fontSize = 11.sp, color = NaturalTextSecondary)
                                    }
                                }
                                if (album.flaggedCount > 0) {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(Terracotta100)
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(text = "${album.flaggedCount} Flagged", color = Terracotta700, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                    }
                                } else {
                                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = NaturalGreen700, modifier = Modifier.size(18.dp))
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = NaturalGreen700)
                ) {
                    Text(if (isHindi) "सुरक्षा सक्रिय है" else "Safety Active", fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}

/**
 * 4. BROWSER SAFETY & WEB FILTER DIALOG (ब्राउज़र व वेब सुरक्षा)
 * Full SafeSearch and domain filtering (Adult, Gambling, Malware, Phishing).
 */
@Composable
fun BrowserSafetyDialog(
    child: ChildProfile,
    isHindi: Boolean,
    onDismiss: () -> Unit
) {
    var safeSearchActive by remember { mutableStateOf(child.webSafeSearch) }
    var blockAdultWeb by remember { mutableStateOf(child.webBlockAdult) }
    var blockGamblingWeb by remember { mutableStateOf(true) }
    var blockSocialWeb by remember { mutableStateOf(child.webBlockSocial) }
    var customBlockedUrl by remember { mutableStateOf("") }

    val blockedSitesList = remember {
        mutableStateListOf("tiktok.com", "twitch.tv", "discord.com", "adult-content.xyz", "betting-games.com")
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(NaturalBg),
            color = NaturalBg
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
                                .background(Color(0xFF0284C7).copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Public, contentDescription = null, tint = Color(0xFF0284C7))
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = if (isHindi) "ब्राउज़र व वेब सुरक्षा" else "Browser & Web Safety",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = NaturalTextPrimary
                            )
                            Text(
                                text = if (isHindi) "SafeSearch व अनुपयुक्त वेबसाइट ब्लॉकर" else "SafeSearch & Malicious Web Filter",
                                fontSize = 12.sp,
                                color = NaturalTextSecondary
                            )
                        }
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = NaturalTextPrimary)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        SafetyToggleCard(
                            icon = Icons.Default.Search,
                            iconColor = Color(0xFF0284C7),
                            title = if (isHindi) "Google SafeSearch अनिवार्य करें" else "Enforce Google SafeSearch",
                            subtitle = if (isHindi) "सभी सर्च इंजनों पर अनुपयुक्त सामग्री स्वतः ब्लॉक करें" else "Enforces strict SafeSearch on Google, Bing & Yahoo",
                            checked = safeSearchActive,
                            onCheckedChange = { safeSearchActive = it }
                        )
                    }

                    item {
                        SafetyToggleCard(
                            icon = Icons.Default.Shield,
                            iconColor = Terracotta700,
                            title = if (isHindi) "वयस्क व 18+ वेबसाइट्स ब्लॉक करें" else "Block 18+ & Adult Websites",
                            subtitle = if (isHindi) "लाखों अनुपयुक्त वेबसाइट्स का डेटाबेस ब्लॉक रहता है" else "Real-time AI database blocking 10M+ unsafe websites",
                            checked = blockAdultWeb,
                            onCheckedChange = { blockAdultWeb = it }
                        )
                    }

                    item {
                        SafetyToggleCard(
                            icon = Icons.Default.Block,
                            iconColor = EarthAmber600,
                            title = if (isHindi) "जुआ व बेटिंग साइट्स ब्लॉक करें" else "Block Gambling & Betting Sites",
                            subtitle = if (isHindi) "रमी, कसीनो, बेटिंग प्लेटफॉर्म्स को ब्लॉक करें" else "Blocks casino, online gaming & real money betting",
                            checked = blockGamblingWeb,
                            onCheckedChange = { blockGamblingWeb = it }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if (isHindi) "ब्लॉक की गई कस्टम वेबसाइट्स" else "Custom Blocked Websites",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = NaturalTextPrimary
                        )
                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = customBlockedUrl,
                                onValueChange = { customBlockedUrl = it },
                                placeholder = { Text(if (isHindi) "उदा. badsite.com" else "e.g. badsite.com", fontSize = 12.sp) },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(50.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = NaturalSurface,
                                    unfocusedContainerColor = NaturalSurface,
                                    focusedBorderColor = NaturalGreen700,
                                    unfocusedBorderColor = NaturalBorder
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = {
                                    if (customBlockedUrl.isNotBlank()) {
                                        blockedSitesList.add(0, customBlockedUrl.trim().lowercase())
                                        customBlockedUrl = ""
                                    }
                                },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Terracotta700),
                                modifier = Modifier.height(50.dp)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
                            }
                        }
                    }

                    items(blockedSitesList) { domain ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(1.dp, RoundedCornerShape(12.dp))
                                .border(1.dp, NaturalBorder, RoundedCornerShape(12.dp)),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = NaturalSurface)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Block, contentDescription = null, tint = Terracotta700, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(text = domain, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = NaturalTextPrimary)
                                }
                                IconButton(
                                    onClick = { blockedSitesList.remove(domain) },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Terracotta700, modifier = Modifier.size(16.dp))
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = NaturalGreen700)
                ) {
                    Text(if (isHindi) "वेब फिल्टर लागू करें" else "Apply Web Filter", fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}

/**
 * 5. CALL & SMS SAFETY DIALOG (कॉल व एसएमएस सुरक्षा)
 */
@Composable
fun CallAndSmsSafetyDialog(
    child: ChildProfile,
    isHindi: Boolean,
    onDismiss: () -> Unit
) {
    var blockUnknownCallers by remember { mutableStateOf(false) }
    var blockSpamRobocalls by remember { mutableStateOf(true) }
    var smsFraudKeywordFilter by remember { mutableStateOf(true) }
    var emergencyWhitelistOnly by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(NaturalBg),
            color = NaturalBg
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
                                .background(NaturalGreen100),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Call, contentDescription = null, tint = NaturalGreen700)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = if (isHindi) "कॉल व एसएमएस सुरक्षा" else "Call & SMS Safety Shield",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = NaturalTextPrimary
                            )
                            Text(
                                text = if (isHindi) "अज्ञात कॉलर व फ्रॉड एसएमएस ब्लॉकर" else "Spam Caller & SMS Fraud Protection",
                                fontSize = 12.sp,
                                color = NaturalTextSecondary
                            )
                        }
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = NaturalTextPrimary)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        SafetyToggleCard(
                            icon = Icons.Default.Shield,
                            iconColor = Terracotta700,
                            title = if (isHindi) "अज्ञात कॉल्स ब्लॉक करें" else "Block Unknown Callers",
                            subtitle = if (isHindi) "केवल सेव किए गए फोन संपर्कों से ही कॉल आने दें" else "Allow calls only from saved contacts in phonebook",
                            checked = blockUnknownCallers,
                            onCheckedChange = { blockUnknownCallers = it }
                        )
                    }

                    item {
                        SafetyToggleCard(
                            icon = Icons.Default.Call,
                            iconColor = Color(0xFF0284C7),
                            title = if (isHindi) "स्पैम व टेलीमार्केटर फिल्टर" else "Spam & Robocall Blocker",
                            subtitle = if (isHindi) "पहचाने गए स्पैम नंबरों को स्वतः साइलेंट या रिजेक्ट करें" else "Auto-reject identified fraud and telemarketing numbers",
                            checked = blockSpamRobocalls,
                            onCheckedChange = { blockSpamRobocalls = it }
                        )
                    }

                    item {
                        SafetyToggleCard(
                            icon = Icons.Default.Message,
                            iconColor = EarthAmber600,
                            title = if (isHindi) "एसएमएस फ्रॉड व OTP सेफ्टी" else "SMS Fraud & OTP Protection",
                            subtitle = if (isHindi) "संदिग्ध बैंक, लॉटरी या पासवर्ड वाले एसएमएस पर अलर्ट करें" else "Detect suspicious phishing, banking or OTP request SMS",
                            checked = smsFraudKeywordFilter,
                            onCheckedChange = { smsFraudKeywordFilter = it }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = NaturalGreen700)
                ) {
                    Text(if (isHindi) "सुरक्षा लागू करें" else "Save Safety Settings", fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}

@Composable
fun SafetyToggleCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(1.dp, RoundedCornerShape(16.dp))
            .border(1.dp, NaturalBorder, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = NaturalSurface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(iconColor.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(20.dp))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = NaturalTextPrimary)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(text = subtitle, fontSize = 11.sp, color = NaturalTextSecondary)
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = NaturalGreen700,
                    uncheckedThumbColor = NaturalTextSecondary,
                    uncheckedTrackColor = NaturalBorder
                )
            )
        }
    }
}
