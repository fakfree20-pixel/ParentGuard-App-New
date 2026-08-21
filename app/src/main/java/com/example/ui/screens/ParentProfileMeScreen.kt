package com.example.ui.screens

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ChildProfile
import com.example.data.model.UserAccount
import com.example.ui.theme.EarthAmber100
import com.example.ui.theme.EarthAmber600
import com.example.ui.theme.NaturalBg
import com.example.ui.theme.NaturalBorder
import com.example.ui.theme.NaturalGreen100
import com.example.ui.theme.NaturalGreen700
import com.example.ui.theme.NaturalGreen900
import com.example.ui.theme.NaturalSurface
import com.example.ui.theme.NaturalSurfaceVariant
import com.example.ui.theme.NaturalTextPrimary
import com.example.ui.theme.NaturalTextSecondary
import com.example.ui.theme.NaturalTextTertiary
import com.example.ui.theme.Terracotta100
import com.example.ui.theme.Terracotta700

@Composable
fun ParentProfileMeScreen(
    user: UserAccount?,
    allChildren: List<ChildProfile>,
    selectedChildId: Long?,
    isHindi: Boolean,
    onSelectChild: (Long) -> Unit,
    onToggleLanguage: () -> Unit,
    onPinChange: (String) -> Unit,
    onOpenPairingScreen: () -> Unit = {}
) {
    val clipboardManager = LocalClipboardManager.current
    var showPinDialog by remember { mutableStateOf(false) }
    var newPinInput by remember { mutableStateOf("") }
    var pinChangeFeedback by remember { mutableStateOf<String?>(null) }
    var isCodeCopied by remember { mutableStateOf(false) }

    val bindingCode = user?.masterBindingCode ?: "9842 761 530"

    if (showPinDialog) {
        AlertDialog(
            onDismissRequest = { showPinDialog = false },
            title = {
                Text(
                    text = if (isHindi) "मास्टर सिक्योरिटी पिन बदलें" else "Change 4-Digit Master PIN",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            },
            text = {
                Column {
                    Text(
                        text = if (isHindi) "नया 4 अंकों का गुप्त पिन दर्ज करें जिसे बच्चा न जानता हो:" else "Enter a new 4-digit secret PIN for parent protection:",
                        fontSize = 13.sp,
                        color = NaturalTextSecondary
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = newPinInput,
                        onValueChange = { if (it.length <= 4 && it.all { ch -> ch.isDigit() }) newPinInput = it },
                        label = { Text("4-Digit PIN") },
                        placeholder = { Text("1234") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NaturalGreen700,
                            unfocusedBorderColor = NaturalBorder
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newPinInput.length == 4) {
                            onPinChange(newPinInput)
                            showPinDialog = false
                            pinChangeFeedback = if (isHindi) "✅ सुरक्षा पिन सफलतापूर्वक बदल दिया गया!" else "✅ Master PIN updated successfully!"
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = NaturalGreen700),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(if (isHindi) "सुरक्षित करें" else "Save PIN", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showPinDialog = false }) {
                    Text(if (isHindi) "रद्द करें" else "Cancel")
                }
            }
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(NaturalBg)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 32.dp)
    ) {
        // Parent Profile Header Card
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
                                    .size(52.dp)
                                    .clip(CircleShape)
                                    .background(NaturalGreen100),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Person, contentDescription = null, tint = NaturalGreen700, modifier = Modifier.size(28.dp))
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = user?.fullName ?: "Parent Guardian",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp,
                                    color = NaturalTextPrimary
                                )
                                Text(
                                    text = user?.email ?: "musahidraza78600@gmail.com",
                                    fontSize = 12.sp,
                                    color = NaturalTextSecondary
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(NaturalGreen100)
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Star, contentDescription = null, tint = NaturalGreen700, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "PRO LIFETIME",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp,
                                    color = NaturalGreen700
                                )
                            }
                        }
                    }
                }
            }
        }

        // Master Binding Code & QR Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(1.dp, RoundedCornerShape(20.dp))
                    .border(1.dp, NaturalGreen700.copy(alpha = 0.4f), RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = NaturalSurface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.QrCode, contentDescription = null, tint = NaturalGreen700)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isHindi) "10-अंकों का मास्टर बाइंडिंग कोड" else "10-Digit Master Binding Code",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = NaturalTextPrimary
                            )
                        }
                        Text(
                            text = if (isHindi) "बच्चे के फोन से जोड़ें" else "Pair Child Device",
                            fontSize = 11.sp,
                            color = NaturalGreen700,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(Color(0xFF0F172A))
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = bindingCode,
                            color = Color(0xFF4ADE80),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace,
                            letterSpacing = 1.sp
                        )

                        IconButton(
                            onClick = {
                                clipboardManager.setText(AnnotatedString(bindingCode))
                                isCodeCopied = true
                            }
                        ) {
                            Icon(Icons.Default.ContentCopy, contentDescription = "Copy", tint = Color.White)
                        }
                    }

                    if (isCodeCopied) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = if (isHindi) "क्लिपबोर्ड में कॉपी किया गया!" else "Code copied to clipboard!",
                            fontSize = 11.sp,
                            color = NaturalGreen700,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Section: Connected Children Devices
        item {
            Text(
                text = if (isHindi) "जुड़े हुए बच्चे के उपकरण (${allChildren.size})" else "Paired Children Devices (${allChildren.size})",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = NaturalTextPrimary
            )
        }

        items(allChildren.size) { index ->
            val child = allChildren[index]
            val isSelected = child.id == selectedChildId
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(1.dp, RoundedCornerShape(16.dp))
                    .border(
                        width = if (isSelected) 2.dp else 1.dp,
                        color = if (isSelected) NaturalGreen700 else NaturalBorder,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickable { onSelectChild(child.id) },
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(if (isSelected) NaturalGreen100 else NaturalSurfaceVariant),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Smartphone,
                                contentDescription = null,
                                tint = if (isSelected) NaturalGreen700 else NaturalTextSecondary
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "${child.name} (${child.age} yrs)",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = NaturalTextPrimary
                            )
                            Text(
                                text = "${child.deviceModel} • 🔋 ${child.batteryPercent}%",
                                fontSize = 12.sp,
                                color = NaturalTextSecondary
                            )
                        }
                    }

                    if (isSelected) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(NaturalGreen100)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(text = if (isHindi) "सक्रिय" else "Active", color = NaturalGreen700, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Section: Settings & Security Menu
        item {
            Text(
                text = if (isHindi) "अभिभावक सेटिंग्स" else "Parental Preferences",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = NaturalTextPrimary
            )
        }

        // Security PIN item
        item {
            MeMenuCard(
                icon = Icons.Default.Lock,
                iconColor = NaturalGreen700,
                title = if (isHindi) "4-अंकों का मास्टर सुरक्षा पिन" else "4-Digit Master PIN",
                subtitle = if (isHindi) "पिन बदलें (डिफ़ॉल्ट: 1234)" else "Protect parent settings (Default: 1234)",
                onClick = { showPinDialog = true }
            )
        }

        // Language toggle item
        item {
            MeMenuCard(
                icon = Icons.Default.Language,
                iconColor = Color(0xFF0284C7),
                title = if (isHindi) "भाषा (Language)" else "App Language",
                subtitle = if (isHindi) "वर्तमान: हिन्दी (टैप करके English करें)" else "Current: English (Tap to switch Hindi)",
                onClick = onToggleLanguage
            )
        }

        // Cloud sync status item
        item {
            MeMenuCard(
                icon = Icons.Default.CloudDone,
                iconColor = NaturalGreen700,
                title = if (isHindi) "क्लाउड बैकअप व सिंक" else "Cloud Backup & Live Sync",
                subtitle = if (isHindi) "सुरक्षित रूप से सिंक किया गया (रियल-टाइम)" else "Encrypted & synced in real time",
                onClick = {}
            )
        }

        // Help & FAQ item
        item {
            MeMenuCard(
                icon = Icons.Default.HelpOutline,
                iconColor = EarthAmber600,
                title = if (isHindi) "सहायता व अक्सर पूछे जाने वाले प्रश्न" else "Help & Safety FAQ",
                subtitle = if (isHindi) "ParentGuard उपयोग गाइड व सुरक्षा टिप्स" else "User guide, stealth tips & customer support",
                onClick = {}
            )
        }

        // App Version
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "ParentGuard Pro v3.2.0 (Build 42)", fontSize = 11.sp, color = NaturalTextTertiary)
                Text(text = "100% Encrypted • Child Safety Standard", fontSize = 10.sp, color = NaturalTextTertiary)
            }
        }
    }
}

@Composable
fun MeMenuCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    title: String,
    subtitle: String,
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

            Icon(Icons.Default.ArrowForwardIos, contentDescription = null, tint = NaturalTextTertiary, modifier = Modifier.size(14.dp))
        }
    }
}
