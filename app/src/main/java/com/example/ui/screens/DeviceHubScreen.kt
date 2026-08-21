package com.example.ui.screens

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.NetworkPing
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.RingVolume
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ChildProfile
import com.example.ui.components.CheckPermissionsDialog
import com.example.ui.components.DeviceActivityDialog
import com.example.ui.components.HowToOpenHiddenAppDialog
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
import com.example.ui.theme.NaturalTextTertiary
import com.example.ui.theme.Terracotta100
import com.example.ui.theme.Terracotta700
import kotlinx.coroutines.delay

@Composable
fun DeviceHubScreen(
    child: ChildProfile,
    isHindi: Boolean,
    onOpenPermissionsDialog: () -> Unit = {},
    onOpenHowToOpenDialog: () -> Unit = {},
    onOpenDeviceActivityDialog: () -> Unit = {}
) {
    var showPermissionsDialog by remember { mutableStateOf(false) }
    var showHowToOpenDialog by remember { mutableStateOf(false) }
    var showActivityDialog by remember { mutableStateOf(false) }

    var isPinging by remember { mutableStateOf(false) }
    var pingResultMs by remember { mutableStateOf<Int?>(34) }
    var isRinging by remember { mutableStateOf(false) }
    var ringFeedback by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(isPinging) {
        if (isPinging) {
            delay(1200)
            pingResultMs = (28..45).random()
            isPinging = false
        }
    }

    LaunchedEffect(isRinging) {
        if (isRinging) {
            ringFeedback = if (isHindi) "🔊 बच्चे के फोन पर लाउड रिंगर बज रहा है!" else "🔊 Loud siren ringing on child's phone!"
            delay(4000)
            isRinging = false
            ringFeedback = null
        }
    }

    if (showPermissionsDialog) {
        CheckPermissionsDialog(child = child, isHindi = isHindi, onDismiss = { showPermissionsDialog = false })
    }
    if (showHowToOpenDialog) {
        HowToOpenHiddenAppDialog(child = child, isHindi = isHindi, onDismiss = { showHowToOpenDialog = false })
    }
    if (showActivityDialog) {
        DeviceActivityDialog(child = child, isHindi = isHindi, onDismiss = { showActivityDialog = false })
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(NaturalBg)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 32.dp)
    ) {
        // Top Device Overview Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(1.dp, RoundedCornerShape(20.dp))
                    .border(1.dp, NaturalBorder, RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = NaturalSurface)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(CircleShape)
                                    .background(NaturalGreen100),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.PhoneAndroid, contentDescription = null, tint = NaturalGreen700, modifier = Modifier.size(24.dp))
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = child.deviceModel,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp,
                                    color = NaturalTextPrimary
                                )
                                Text(
                                    text = if (child.isDeviceOnline) (if (isHindi) "ऑनलाइन • Android 14" else "Online • Android 14") else (if (isHindi) "ऑफ़लाइन" else "Offline"),
                                    fontSize = 12.sp,
                                    color = if (child.isDeviceOnline) NaturalGreen700 else NaturalTextSecondary
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(NaturalGreen100)
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "🔋 ${child.batteryPercent}%",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = NaturalGreen700
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Hardware Meters (RAM & Storage)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Storage Card
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(NaturalSurfaceVariant)
                                .padding(12.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Storage, contentDescription = null, tint = NaturalGreen700, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(text = if (isHindi) "स्टोरेज" else "Storage", fontSize = 11.sp, color = NaturalTextSecondary)
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(text = "54 GB / 128 GB", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = NaturalTextPrimary)
                            Spacer(modifier = Modifier.height(4.dp))
                            LinearProgressIndicator(
                                progress = { 0.42f },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(4.dp)
                                    .clip(RoundedCornerShape(2.dp)),
                                color = NaturalGreen700,
                                trackColor = NaturalBorder
                            )
                        }

                        // RAM Card
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(NaturalSurfaceVariant)
                                .padding(12.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Memory, contentDescription = null, tint = Color(0xFF0284C7), modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(text = "RAM Memory", fontSize = 11.sp, color = NaturalTextSecondary)
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(text = "3.8 GB / 8.0 GB", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = NaturalTextPrimary)
                            Spacer(modifier = Modifier.height(4.dp))
                            LinearProgressIndicator(
                                progress = { 0.48f },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(4.dp)
                                    .clip(RoundedCornerShape(2.dp)),
                                color = Color(0xFF0284C7),
                                trackColor = NaturalBorder
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Wi-Fi & IP Information
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(NaturalSurfaceVariant)
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Wifi, contentDescription = null, tint = NaturalGreen700, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Wi-Fi: Home_5G_Fiber", fontSize = 12.sp, color = NaturalTextPrimary)
                        }
                        Text(text = "IP: 192.168.1.45", fontSize = 11.sp, color = NaturalTextSecondary)
                    }
                }
            }
        }

        // Live Remote Commands (Ring Phone & Ping Test)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Find & Ring Child Phone Button
                Button(
                    onClick = { isRinging = true },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = EarthAmber600)
                ) {
                    Icon(Icons.Default.RingVolume, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isHindi) "लाउड रिंग बजाएं" else "Remote Ring",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                // Ping & Sync Test Button
                OutlinedButton(
                    onClick = { isPinging = true },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    if (isPinging) {
                        CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp)
                    } else {
                        Icon(Icons.Default.NetworkPing, contentDescription = null, modifier = Modifier.size(18.dp))
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (pingResultMs != null) "Ping: ${pingResultMs}ms" else "Test Ping",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            if (ringFeedback != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = EarthAmber100)
                ) {
                    Text(
                        text = ringFeedback ?: "",
                        modifier = Modifier.padding(10.dp),
                        fontSize = 12.sp,
                        color = EarthAmber600,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Section: Diagnostics & Security Hub Tiles
        item {
            Text(
                text = if (isHindi) "डिवाइस सुरक्षा व टूल्स" else "Device Diagnostics & Tools",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = NaturalTextPrimary
            )
        }

        // 1. Check Permissions Card
        item {
            DeviceToolActionCard(
                icon = Icons.Default.CheckCircle,
                iconColor = NaturalGreen700,
                title = if (isHindi) "परमिशन स्थिति जांचें (Check Permissions)" else "Check Permissions Health",
                subtitle = if (isHindi) "Accessibility, Admin, Location सहित सभी 9 परमिशन" else "Accessibility, Device Admin, GPS & Background Guard",
                tagText = if (isHindi) "9/9 OK" else "9/9 Healthy",
                tagColor = NaturalGreen700,
                tagBg = NaturalGreen100,
                onClick = { showPermissionsDialog = true }
            )
        }

        // 2. How to open hidden child app Card
        item {
            DeviceToolActionCard(
                icon = Icons.Default.Help,
                iconColor = EarthAmber600,
                title = if (isHindi) "छिपा हुआ ऐप कैसे खोलें?" else "How to open the hidden child's app?",
                subtitle = if (isHindi) "डायलर कोड (*#9842#), नोटिफिकेशन बार व कैलकुलेटर ट्रिक" else "Phone Dialer code, Notification shade & Calculator PIN",
                tagText = if (isHindi) "4 तरीके" else "4 Methods",
                tagColor = EarthAmber600,
                tagBg = EarthAmber100,
                onClick = { showHowToOpenDialog = true }
            )
        }

        // 3. Device Activity Timeline Card
        item {
            DeviceToolActionCard(
                icon = Icons.Default.Timeline,
                iconColor = Color(0xFF0284C7),
                title = if (isHindi) "डिवाइस एक्टिविटी (Device Activity)" else "Device Activity & Unlocks",
                subtitle = if (isHindi) "स्क्रीन ऑन/ऑफ चक्र, बैटरी डिस्चार्ज दर व नेटवर्क डेटा" else "Screen on/off timeline, 42 unlocks today, data usage",
                tagText = if (isHindi) "लाइव" else "Live",
                tagColor = Color(0xFF0284C7),
                tagBg = Color(0xFF0284C7).copy(alpha = 0.15f),
                onClick = { showActivityDialog = true }
            )
        }

        // 4. Anti-Uninstall Tamper Protection Card
        item {
            DeviceToolActionCard(
                icon = Icons.Default.Shield,
                iconColor = Terracotta700,
                title = if (isHindi) "अनइंस्टॉल सुरक्षा व सेटिंग्स लॉक" else "Anti-Uninstall & Tamper Lock",
                subtitle = if (isHindi) "डिवाइस एडमिन सक्रिय • सेटिंग्स व फैक्ट्री रीसेट ब्लॉक्ड" else "Device Admin Active • Settings & Reset Protected",
                tagText = if (isHindi) "सुरक्षित" else "Active",
                tagColor = Terracotta700,
                tagBg = Terracotta100,
                onClick = { showPermissionsDialog = true }
            )
        }
    }
}

@Composable
fun DeviceToolActionCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    title: String,
    subtitle: String,
    tagText: String,
    tagColor: Color,
    tagBg: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(1.dp, RoundedCornerShape(16.dp))
            .border(1.dp, NaturalBorder, RoundedCornerShape(16.dp))
            .clickable { onClick() },
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
                        .size(42.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(iconColor.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(22.dp))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = NaturalTextPrimary)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(text = subtitle, fontSize = 11.sp, color = NaturalTextSecondary)
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(tagBg)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(text = tagText, color = tagColor, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
