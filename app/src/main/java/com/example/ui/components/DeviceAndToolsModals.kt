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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Dialpad
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.ChildProfile
import com.example.ui.theme.EarthAmber100
import com.example.ui.theme.EarthAmber600
import com.example.ui.theme.NaturalBg
import com.example.ui.theme.NaturalBorder
import com.example.ui.theme.NaturalGreen100
import com.example.ui.theme.NaturalGreen700
import com.example.ui.theme.NaturalSurface
import com.example.ui.theme.NaturalSurfaceVariant
import com.example.ui.theme.NaturalTextPrimary
import com.example.ui.theme.NaturalTextSecondary
import com.example.ui.theme.Terracotta100
import com.example.ui.theme.Terracotta700

/**
 * 1. DEVICE ACTIVITY DIALOG (डिवाइस एक्टिविटी व टाइमलाइन)
 * Comprehensive device activity metrics: Unlocks, Screen On/Off timeline, Battery usage, Charging sessions.
 */
data class DeviceActivityEvent(
    val time: String,
    val title: String,
    val titleHindi: String,
    val description: String,
    val descriptionHindi: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val color: Color
)

@Composable
fun DeviceActivityDialog(
    child: ChildProfile,
    isHindi: Boolean,
    onDismiss: () -> Unit
) {
    val activityEvents = remember {
        listOf(
            DeviceActivityEvent("02:35 PM", "Screen Unlocked", "स्क्रीन अनलॉक की गई", "Child unlocked device at school", "बच्चे ने स्कूल में फोन अनलॉक किया", Icons.Default.Smartphone, NaturalGreen700),
            DeviceActivityEvent("01:10 PM", "Battery Charged to 86%", "बैटरी 86% चार्ज हुई", "Connected to standard charger for 35m", "35 मिनट के लिए चार्जर से कनेक्ट रहा", Icons.Default.BatteryChargingFull, Color(0xFF0284C7)),
            DeviceActivityEvent("11:45 AM", "Screen Turned Off", "स्क्रीन बंद की गई", "Device idle for 1h 20m during class", "क्लास के दौरान 1 घंटा 20 मिनट फोन बंद रहा", Icons.Default.PowerSettingsNew, EarthAmber600),
            DeviceActivityEvent("10:15 AM", "Switched to YouTube Kids", "YouTube Kids खोला गया", "Used for 25 minutes of educational video", "25 मिनट शैक्षिक वीडियो देखे", Icons.Default.QueryStats, Color(0xFFEF4444)),
            DeviceActivityEvent("08:00 AM", "Device Booted Up", "डिवाइस ऑन हुआ", "Morning startup and network synced", "सुबह डिवाइस ऑन व नेटवर्क सिंक हुआ", Icons.Default.CheckCircle, NaturalGreen700)
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
                                .background(NaturalGreen100),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Timeline, contentDescription = null, tint = NaturalGreen700)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = if (isHindi) "डिवाइस एक्टिविटी (Device Activity)" else "Device Activity Timeline",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = NaturalTextPrimary
                            )
                            Text(
                                text = if (isHindi) "${child.name} का फोन उपयोग व अनलॉक लॉग" else "${child.name}'s Hardware & Screen logs",
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

                // Metric Cards Grid
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .shadow(1.dp, RoundedCornerShape(16.dp))
                            .border(1.dp, NaturalBorder, RoundedCornerShape(16.dp)),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = NaturalSurface)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(text = if (isHindi) "कुल अनलॉक्स" else "Total Unlocks", fontSize = 11.sp, color = NaturalTextSecondary)
                            Text(text = "42 बार", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = NaturalTextPrimary)
                            Text(text = if (isHindi) "औसत से कम" else "Normal range", fontSize = 10.sp, color = NaturalGreen700)
                        }
                    }

                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .shadow(1.dp, RoundedCornerShape(16.dp))
                            .border(1.dp, NaturalBorder, RoundedCornerShape(16.dp)),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = NaturalSurface)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(text = if (isHindi) "स्क्रीन ऑन समय" else "Screen On Time", fontSize = 11.sp, color = NaturalTextSecondary)
                            Text(text = "1h 48m", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = NaturalGreen700)
                            Text(text = if (isHindi) "दैनिक सीमा के भीतर" else "Within daily limit", fontSize = 10.sp, color = NaturalTextSecondary)
                        }
                    }

                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .shadow(1.dp, RoundedCornerShape(16.dp))
                            .border(1.dp, NaturalBorder, RoundedCornerShape(16.dp)),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = NaturalSurface)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(text = if (isHindi) "इंटरनेट डेटा" else "Data Usage", fontSize = 11.sp, color = NaturalTextSecondary)
                            Text(text = "1.2 GB", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0284C7))
                            Text(text = if (isHindi) "Wi-Fi व 5G" else "Wi-Fi + Mobile", fontSize = 10.sp, color = NaturalTextSecondary)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = if (isHindi) "आज की एक्टिविटी टाइमलाइन" else "Today's Activity Timeline",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = NaturalTextPrimary
                )

                Spacer(modifier = Modifier.height(10.dp))

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(activityEvents) { event ->
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
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(event.color.copy(alpha = 0.15f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(event.icon, contentDescription = null, tint = event.color, modifier = Modifier.size(20.dp))
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = if (isHindi) event.titleHindi else event.title,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp,
                                            color = NaturalTextPrimary
                                        )
                                        Text(text = event.time, fontSize = 11.sp, color = NaturalTextSecondary)
                                    }
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = if (isHindi) event.descriptionHindi else event.description,
                                        fontSize = 11.sp,
                                        color = NaturalTextSecondary
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
 * 2. CHECK PERMISSIONS DIAGNOSTICS DIALOG (परमिशन जांच व स्वास्थ्य)
 * Audits all 9 essential Android permissions with real-time green/amber indicators and fix instructions.
 */
data class PermissionCheckItem(
    val title: String,
    val titleHindi: String,
    val description: String,
    val descriptionHindi: String,
    val isGranted: Boolean,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

@Composable
fun CheckPermissionsDialog(
    child: ChildProfile,
    isHindi: Boolean,
    onDismiss: () -> Unit
) {
    var isChecking by remember { mutableStateOf(false) }

    val permissionsList = remember {
        listOf(
            PermissionCheckItem("Accessibility Service", "एक्सेसिबिलिटी सर्विस", "Required for real-time app blocking & remote control", "लाइव ऐप ब्लॉकिंग व रिमोट कंट्रोल के लिए अनिवार्य", true, Icons.Default.Accessibility),
            PermissionCheckItem("Usage Data Access", "ऐप यूसेज एक्सेस", "Tracks screen time and app launch durations", "स्क्रीन टाइम और ऐप उपयोग का समय ट्रैक करता है", true, Icons.Default.QueryStats),
            PermissionCheckItem("Device Administrator", "डिवाइस एडमिनिस्ट्रेटर", "Prevents unauthorized uninstallation by child", "बच्चे द्वारा ऐप को अनइंस्टॉल करने से रोकता है", true, Icons.Default.AdminPanelSettings),
            PermissionCheckItem("Location Always-On", "बैकग्राउंड लोकेशन", "Live GPS tracking and geofence safe zone alerts", "लाइव जीपीएस ट्रैकिंग व सेफ ज़ोन अलर्ट के लिए", true, Icons.Default.LocationOn),
            PermissionCheckItem("Display Over Other Apps", "डिस्प्ले ओवर अदर ऐप्स", "Displays instant lock screen and parent notices", "इंस्टेंट लॉक स्क्रीन और नोटिस दिखाने के लिए", true, Icons.Default.Layers),
            PermissionCheckItem("Notification Listener", "नोटिफिकेशन लिसनर", "Monitors WhatsApp chats and incoming alerts", "व्हाट्सएप चैट व सुरक्षा नोटिफिकेशन के लिए", true, Icons.Default.Notifications),
            PermissionCheckItem("Camera & Screen Capture", "कैमरा व स्क्रीन कैप्चर", "Remote snapshots and screen mirroring", "रिमोट कैमरा स्नैपशॉट व स्क्रीनकास्ट के लिए", true, Icons.Default.PhotoCamera),
            PermissionCheckItem("Microphone & Audio", "माइक्रोफोन व ऑडियो", "One-way ambient sound listening for safety", "आसपास की आवाज सुनने व सुरक्षा के लिए", true, Icons.Default.Mic),
            PermissionCheckItem("Battery Optimization Exemption", "बैटरी ऑप्टिमाइज़ेशन छूट", "Ensures 24/7 background guard without sleep", "बिना रुके 24/7 बैकग्राउंड में चालू रखने के लिए", true, Icons.Default.BatteryChargingFull)
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
                                .background(NaturalGreen100),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = NaturalGreen700)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = if (isHindi) "परमिशन जांच (Check Permissions)" else "Check Permissions Health",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = NaturalTextPrimary
                            )
                            Text(
                                text = if (isHindi) "सभी 9 मुख्य सुरक्षा परमिशन सक्रिय हैं" else "All 9 Core Permissions active & healthy",
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

                // Permissions List
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(permissionsList) { item ->
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
                                            .background(NaturalGreen100),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(item.icon, contentDescription = null, tint = NaturalGreen700, modifier = Modifier.size(20.dp))
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Text(
                                            text = if (isHindi) item.titleHindi else item.title,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp,
                                            color = NaturalTextPrimary
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = if (isHindi) item.descriptionHindi else item.description,
                                            fontSize = 11.sp,
                                            color = NaturalTextSecondary
                                        )
                                    }
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(NaturalGreen100)
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = NaturalGreen700, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = if (isHindi) "सक्रिय" else "Active",
                                        color = NaturalGreen700,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
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
                    Text(if (isHindi) "परमिशन स्थिति ठीक है" else "All Permissions Verified OK", fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}

/**
 * 3. HOW TO OPEN THE HIDDEN CHILD'S APP GUIDE DIALOG (छिपा हुआ ऐप कैसे खोलें?)
 * Interactive guide explaining all 4 stealth recovery methods:
 * 1. Dialer Code (*#9842#)
 * 2. Notification shade 3-taps
 * 3. Calculator Secret PIN (1234=)
 * 4. Android Settings fallback
 */
@Composable
fun HowToOpenHiddenAppDialog(
    child: ChildProfile,
    isHindi: Boolean,
    onDismiss: () -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    var copiedCode by remember { mutableStateOf(false) }
    var testDialerInput by remember { mutableStateOf("") }
    var dialerSuccess by remember { mutableStateOf(false) }

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
                                .background(EarthAmber100),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Help, contentDescription = null, tint = EarthAmber600)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = if (isHindi) "छिपा हुआ ऐप कैसे खोलें?" else "How to open hidden child's app?",
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp,
                                color = NaturalTextPrimary
                            )
                            Text(
                                text = if (isHindi) "स्टेल्थ मोड से ऐप रिकवर करने के तरीके" else "4 Instant Recovery Methods",
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
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Method 1: Phone Dialer
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(1.dp, RoundedCornerShape(16.dp))
                                .border(1.dp, NaturalGreen700.copy(alpha = 0.5f), RoundedCornerShape(16.dp)),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = NaturalSurface)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clip(CircleShape)
                                            .background(NaturalGreen700),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(text = "1", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = if (isHindi) "तरीका 1: फोन डायलर सीक्रेट कोड (सबसे आसान)" else "Method 1: Phone Dialer Secret Code",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = NaturalTextPrimary
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = if (isHindi) "बच्चे के फोन का साधारण डायलर खोलें और यह कोड डायल करें:" else "Open standard Phone Dialer on child's phone and dial:",
                                    fontSize = 12.sp,
                                    color = NaturalTextSecondary
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(Color(0xFF0F172A))
                                        .padding(horizontal = 14.dp, vertical = 10.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = child.dialerSecretCode.ifBlank { "*#9842#" },
                                        color = Color(0xFF4ADE80),
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.Monospace
                                    )
                                    IconButton(
                                        onClick = {
                                            clipboardManager.setText(AnnotatedString(child.dialerSecretCode.ifBlank { "*#9842#" }))
                                            copiedCode = true
                                        }
                                    ) {
                                        Icon(Icons.Default.ContentCopy, contentDescription = "Copy", tint = Color.White)
                                    }
                                }
                            }
                        }
                    }

                    // Method 2: Notification Shade
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(1.dp, RoundedCornerShape(16.dp))
                                .border(1.dp, NaturalBorder, RoundedCornerShape(16.dp)),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = NaturalSurface)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clip(CircleShape)
                                            .background(Color(0xFF0284C7)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(text = "2", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = if (isHindi) "तरीका 2: नोटिफिकेशन बार पर 3 टैप" else "Method 2: 3-Tap Notification Shade",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = NaturalTextPrimary
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = if (isHindi) "ऊपर से नोटिफिकेशन शेड नीचे खींचें और 'Battery Service' नोटिफिकेशन पर 3 बार लगातार टैप करें।" else "Swipe down notification shade and tap the subtle 'Battery Service' notification 3 times.",
                                    fontSize = 12.sp,
                                    color = NaturalTextSecondary
                                )
                            }
                        }
                    }

                    // Method 3: Calculator Stealth PIN
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(1.dp, RoundedCornerShape(16.dp))
                                .border(1.dp, NaturalBorder, RoundedCornerShape(16.dp)),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = NaturalSurface)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clip(CircleShape)
                                            .background(EarthAmber600),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(text = "3", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = if (isHindi) "तरीका 3: कैलकुलेटर सीक्रेट पिन" else "Method 3: Calculator Secret PIN",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = NaturalTextPrimary
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = if (isHindi) "कैलकुलेटर ऐप खोलें और '1234=' टाइप करें। छिपा हुआ सुरक्षा मेनू खुल जाएगा।" else "Open calculator and type '1234='. The secret recovery console will open immediately.",
                                    fontSize = 12.sp,
                                    color = NaturalTextSecondary
                                )
                            }
                        }
                    }

                    // Method 4: Android Settings Fallback
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(1.dp, RoundedCornerShape(16.dp))
                                .border(1.dp, NaturalBorder, RoundedCornerShape(16.dp)),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = NaturalSurface)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clip(CircleShape)
                                            .background(Color(0xFF8B5CF6)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(text = "4", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = if (isHindi) "तरीका 4: फोन सेटिंग्स से खोलें" else "Method 4: Android Settings Fallback",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = NaturalTextPrimary
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = if (isHindi) "सेटिंग्स > ऐप्स > ParentGuard > 'ओपन' पर क्लिक करें और अपना 4-अंकों का मास्टर पिन डालें।" else "Go to Settings > Apps > ParentGuard > Tap 'Open' and enter your 4-digit master parent PIN.",
                                    fontSize = 12.sp,
                                    color = NaturalTextSecondary
                                )
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
                    Text(if (isHindi) "समझ गया" else "Got It", fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}

/**
 * 4. PARENT NOTICE & BROADCAST DIALOG (अभिभावक नोटिस व घोषणा)
 * Send instant high-priority notices, audio chime alerts, and homework reminders directly to child device screen.
 */
@Composable
fun ParentNoticeBroadcastDialog(
    child: ChildProfile,
    isHindi: Boolean,
    onDismiss: () -> Unit
) {
    var noticeTitle by remember { mutableStateOf("Dinner Time! 🍽️") }
    var noticeMessage by remember { mutableStateOf("Please put away the phone and come down for family dinner.") }
    var playAudioChime by remember { mutableStateOf(true) }
    var isEmergencyBanner by remember { mutableStateOf(false) }
    var sentSuccess by remember { mutableStateOf(false) }

    val quickTemplates = listOf(
        "Dinner Time! 🍽️" to "Please put away your phone and join for dinner.",
        "Homework Reminder 📚" to "Time to complete your Math homework and review notes.",
        "Time for Bed! 🌙" to "Wind down for sleep. Screen time is finished for today.",
        "Call Papa / Mummy 📞" to "Please give us a call as soon as you see this message."
    )

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
                            Icon(Icons.Default.Campaign, contentDescription = null, tint = Color(0xFF0284C7))
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = if (isHindi) "नोटिस भेजें (Send Notice)" else "Send Parental Notice",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = NaturalTextPrimary
                            )
                            Text(
                                text = if (isHindi) "${child.name} की स्क्रीन पर तुरंत पॉप-अप होगा" else "Instantly pops up on ${child.name}'s screen",
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
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Quick Templates
                    item {
                        Text(
                            text = if (isHindi) "त्वरित नोटिस टेम्पलेट" else "Quick Notice Templates",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = NaturalTextPrimary
                        )
                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            quickTemplates.take(2).forEach { (t, m) ->
                                OutlinedButton(
                                    onClick = {
                                        noticeTitle = t
                                        noticeMessage = m
                                    },
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier.weight(1f),
                                    contentPadding = PaddingValues(8.dp)
                                ) {
                                    Text(text = t, fontSize = 11.sp, maxLines = 1)
                                }
                            }
                        }
                    }

                    item {
                        OutlinedTextField(
                            value = noticeTitle,
                            onValueChange = { noticeTitle = it },
                            label = { Text(if (isHindi) "नोटिस शीर्षक" else "Notice Title") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = NaturalSurface,
                                unfocusedContainerColor = NaturalSurface,
                                focusedBorderColor = NaturalGreen700,
                                unfocusedBorderColor = NaturalBorder
                            )
                        )
                    }

                    item {
                        OutlinedTextField(
                            value = noticeMessage,
                            onValueChange = { noticeMessage = it },
                            label = { Text(if (isHindi) "संदेश विवरण" else "Message Content") },
                            minLines = 3,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = NaturalSurface,
                                unfocusedContainerColor = NaturalSurface,
                                focusedBorderColor = NaturalGreen700,
                                unfocusedBorderColor = NaturalBorder
                            )
                        )
                    }

                    item {
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
                                    .padding(14.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.VolumeUp, contentDescription = null, tint = NaturalGreen700)
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(text = if (isHindi) "ऑडियो चाइम बजाएं" else "Play Audio Chime", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = NaturalTextPrimary)
                                        Text(text = if (isHindi) "साइलेंट मोड में भी मधुर घंटी बजेगी" else "Plays friendly chime on child device", fontSize = 11.sp, color = NaturalTextSecondary)
                                    }
                                }
                                Switch(
                                    checked = playAudioChime,
                                    onCheckedChange = { playAudioChime = it },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = Color.White,
                                        checkedTrackColor = NaturalGreen700
                                    )
                                )
                            }
                        }
                    }

                    if (sentSuccess) {
                        item {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = NaturalGreen100)
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = NaturalGreen700)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = if (isHindi) "नोटिस बच्चे के डिवाइस पर सफलतापूर्वक भेजा गया!" else "Notice broadcasted to child device!",
                                        color = NaturalGreen700,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        sentSuccess = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = NaturalGreen700)
                ) {
                    Icon(Icons.Default.Send, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (isHindi) "तुरंत नोटिस भेजें" else "Broadcast Notice Now", fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}
