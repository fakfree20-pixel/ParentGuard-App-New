package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.FamilyRestroom
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.R
import com.example.ui.theme.NaturalBg
import com.example.ui.theme.NaturalBorder
import com.example.ui.theme.NaturalCardBg
import com.example.ui.theme.NaturalGreen100
import com.example.ui.theme.NaturalGreen700
import com.example.ui.theme.NaturalGreen900
import com.example.ui.theme.NaturalSurfaceVariant
import com.example.ui.theme.NaturalTextPrimary
import com.example.ui.theme.NaturalTextSecondary

@Composable
fun WelcomeModeScreen(
    isHindi: Boolean,
    onToggleLanguage: () -> Unit,
    onSelectParentMode: () -> Unit,
    onSelectChildMode: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(NaturalBg)
            .windowInsetsPadding(WindowInsets.statusBars)
            .windowInsetsPadding(WindowInsets.navigationBars),
        color = NaturalBg
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header with Language Toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.user_profile_logo_1787259898289),
                        contentDescription = "ParentGuard Logo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .border(1.5.dp, NaturalGreen700.copy(alpha = 0.5f), RoundedCornerShape(14.dp))
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "FlashGet Kids",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 18.sp,
                            color = NaturalGreen900
                        )
                        Text(
                            text = "Parental Control & Safety",
                            fontSize = 11.sp,
                            color = NaturalTextSecondary
                        )
                    }
                }

                // Language switch button
                IconButton(
                    onClick = onToggleLanguage,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(NaturalSurfaceVariant)
                        .testTag("welcome_lang_toggle")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Language,
                            contentDescription = "Language",
                            tint = NaturalGreen700,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isHindi) "EN" else "हिं",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = NaturalTextPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Main Illustration / Title
            Text(
                text = if (isHindi) "यह कौन सा डिवाइस है?" else "Which device is this?",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = NaturalTextPrimary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (isHindi)
                    "फ़्लैशगेट किड्स सुरक्षित रूप से माता-पिता और बच्चों के फोन को जोड़ता है।"
                else
                    "Choose this device's role to configure the parental monitoring link.",
                fontSize = 14.sp,
                color = NaturalTextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            Spacer(modifier = Modifier.height(28.dp))

            // OPTION 1: Parent's Device
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .border(1.5.dp, NaturalGreen700.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
                    .clickable { onSelectParentMode() }
                    .testTag("choose_parent_mode_card"),
                colors = CardDefaults.cardColors(containerColor = NaturalCardBg),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(52.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(NaturalGreen100),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.FamilyRestroom,
                                contentDescription = null,
                                tint = NaturalGreen700,
                                modifier = Modifier.size(30.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = if (isHindi) "माता-पिता का फ़ोन (Parent)" else "Parent's Phone",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = NaturalTextPrimary
                            )
                            Text(
                                text = if (isHindi) "बच्चे की निगरानी और नियंत्रण हेतु" else "To supervise & manage child's device",
                                fontSize = 12.sp,
                                color = NaturalGreen700,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Icon(
                            Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = NaturalGreen700
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Feature points
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocationOn, contentDescription = null, tint = NaturalGreen700, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isHindi) "लाइव GPS लोकेशन और जियोफ़ेंस अलर्ट" else "Live GPS location & Geofence alerts",
                            fontSize = 12.sp,
                            color = NaturalTextSecondary
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Timer, contentDescription = null, tint = NaturalGreen700, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isHindi) "स्क्रीन टाइम लिमिट और ऐप ब्लॉकर" else "Screen time limits & App blocker",
                            fontSize = 12.sp,
                            color = NaturalTextSecondary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // OPTION 2: Child's Device
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .border(1.dp, NaturalBorder, RoundedCornerShape(20.dp))
                    .clickable { onSelectChildMode() }
                    .testTag("choose_child_mode_card"),
                colors = CardDefaults.cardColors(containerColor = NaturalCardBg),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(52.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(NaturalSurfaceVariant),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.ChildCare,
                                contentDescription = null,
                                tint = NaturalGreen900,
                                modifier = Modifier.size(30.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = if (isHindi) "बच्चे का फ़ोन (Kids Device)" else "Child's Phone",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = NaturalTextPrimary
                            )
                            Text(
                                text = if (isHindi) "FlashGet Kids for Child ऐप" else "FlashGet Kids for Child",
                                fontSize = 12.sp,
                                color = NaturalTextSecondary,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Icon(
                            Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = NaturalTextSecondary
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.QrCodeScanner, contentDescription = null, tint = NaturalGreen700, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isHindi) "9-अंकीय कोड या QR कोड से तुरंत बाइंड करें" else "Pair instantly via 9-digit code or QR",
                            fontSize = 12.sp,
                            color = NaturalTextSecondary
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Security, contentDescription = null, tint = NaturalGreen700, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isHindi) "एंटी-अनइंस्टॉल और माता-पिता सुरक्षा लॉक" else "Anti-uninstall & tamper protection",
                            fontSize = 12.sp,
                            color = NaturalTextSecondary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(24.dp))

            // Footer note
            Text(
                text = if (isHindi)
                    "🔒 FlashGet Kids एंड-टू-एंड एन्क्रिप्टेड और 100% सुरक्षित है।"
                else
                    "🔒 FlashGet Kids uses end-to-end encryption for privacy & security.",
                fontSize = 11.sp,
                color = NaturalTextSecondary,
                textAlign = TextAlign.Center
            )
        }
    }
}
