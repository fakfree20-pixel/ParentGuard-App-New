package com.example.ui.screens

import androidx.compose.animation.Crossfade
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.HourglassBottom
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PhotoAlbum
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppUsageRule
import com.example.data.model.ChildProfile
import com.example.ui.components.AlbumsSafetyDialog
import com.example.ui.components.BrowserSafetyDialog
import com.example.ui.components.CallAndSmsSafetyDialog
import com.example.ui.components.SocialAppDetectionDialog
import com.example.ui.components.UsageSafetyDialog
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

@Composable
fun SafetyHubScreen(
    child: ChildProfile,
    appsList: List<AppUsageRule>,
    isHindi: Boolean,
    onToggleAppBlock: (AppUsageRule) -> Unit,
    onSetAppLimit: (Long, Int) -> Unit,
    onUpdateChildSettings: (ChildProfile) -> Unit
) {
    var selectedSafetyCategory by remember { mutableIntStateOf(0) }
    // 0: All Tools, 1: Screen Time & Limits, 2: Social Apps, 3: Browser, 4: Albums, 5: Call & SMS, 6: Usage Health

    var showUsageSafetyModal by remember { mutableStateOf(false) }
    var showSocialModal by remember { mutableStateOf(false) }
    var showAlbumsModal by remember { mutableStateOf(false) }
    var showBrowserModal by remember { mutableStateOf(false) }
    var showCallSmsModal by remember { mutableStateOf(false) }

    if (showUsageSafetyModal) {
        UsageSafetyDialog(child = child, isHindi = isHindi, onDismiss = { showUsageSafetyModal = false })
    }
    if (showSocialModal) {
        SocialAppDetectionDialog(child = child, isHindi = isHindi, onDismiss = { showSocialModal = false })
    }
    if (showAlbumsModal) {
        AlbumsSafetyDialog(child = child, isHindi = isHindi, onDismiss = { showAlbumsModal = false })
    }
    if (showBrowserModal) {
        BrowserSafetyDialog(child = child, isHindi = isHindi, onDismiss = { showBrowserModal = false })
    }
    if (showCallSmsModal) {
        CallAndSmsSafetyDialog(child = child, isHindi = isHindi, onDismiss = { showCallSmsModal = false })
    }

    val categories = listOf(
        if (isHindi) "सभी सुरक्षा टूल्स" else "All Tools",
        if (isHindi) "स्क्रीन टाइम लिमिट्स" else "Screen Limits",
        if (isHindi) "ऐप रूल्स व लिमिट्स" else "App Rules",
        if (isHindi) "सोशल ऐप्स" else "Social Apps",
        if (isHindi) "ब्राउज़र सेफ्टी" else "Browser Safety",
        if (isHindi) "एल्बम सेफ्टी" else "Albums Safety",
        if (isHindi) "कॉल व SMS" else "Call & SMS",
        if (isHindi) "यूसेज सेफ्टी" else "Usage Health"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(NaturalBg)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 32.dp)
    ) {
        // Top Categories Filter Chips
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(categories.size) { idx ->
                    val isSelected = selectedSafetyCategory == idx
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedSafetyCategory = idx },
                        label = { Text(text = categories[idx], fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = NaturalGreen700,
                            selectedLabelColor = Color.White,
                            containerColor = NaturalSurface,
                            labelColor = NaturalTextSecondary
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = isSelected,
                            borderColor = if (isSelected) NaturalGreen700 else NaturalBorder
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }
        }

        // Safety Status Banner
        item {
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
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(NaturalGreen100),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Shield, contentDescription = null, tint = NaturalGreen700, modifier = Modifier.size(24.dp))
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = if (isHindi) "सुरक्षा कवच सक्रिय" else "Full Safety Shield Active",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = NaturalTextPrimary
                            )
                            Text(
                                text = if (isHindi) "सभी 7 सुरक्षा फिल्टर बच्चे के डिवाइस पर लागू हैं" else "7 Safety Modules active on ${child.name}'s phone",
                                fontSize = 12.sp,
                                color = NaturalTextSecondary
                            )
                        }
                    }
                }
            }
        }

        // Feature 1: Screen Time Limits Card
        if (selectedSafetyCategory == 0 || selectedSafetyCategory == 1) {
            item {
                SafetyFeatureCard(
                    icon = Icons.Default.Timer,
                    iconColor = NaturalGreen700,
                    title = if (isHindi) "स्क्रीन टाइम लिमिट्स (Screen Time Limits)" else "Screen Time Limits",
                    subtitle = if (isHindi) "सोम-शुक्र: ${child.weekdayLimitMinutes / 60}h ${child.weekdayLimitMinutes % 60}m • शनि-रवि: ${child.weekendLimitMinutes / 60}h" else "Weekday: ${child.weekdayLimitMinutes / 60}h ${child.weekdayLimitMinutes % 60}m • Weekend: ${child.weekendLimitMinutes / 60}h",
                    tag = if (isHindi) "सक्रिय" else "Active",
                    onClick = {
                        // Quick increment
                        val next = if (child.weekdayLimitMinutes >= 180) 60 else child.weekdayLimitMinutes + 30
                        onUpdateChildSettings(child.copy(weekdayLimitMinutes = next))
                    }
                )
            }
        }

        // Feature 2: App Time Limits & App Rules
        if (selectedSafetyCategory == 0 || selectedSafetyCategory == 2) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(1.dp, RoundedCornerShape(18.dp))
                        .border(1.dp, NaturalBorder, RoundedCornerShape(18.dp)),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = NaturalSurface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(Color(0xFF0284C7).copy(alpha = 0.15f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.Apps, contentDescription = null, tint = Color(0xFF0284C7))
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = if (isHindi) "ऐप टाइम लिमिट्स व ऐप रूल्स" else "App Time Limits & Rules",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = NaturalTextPrimary
                                    )
                                    Text(
                                        text = if (isHindi) "${appsList.size} ऐप्स मॉनिटर किए जा रहे हैं" else "${appsList.size} Apps Configured",
                                        fontSize = 11.sp,
                                        color = NaturalTextSecondary
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        appsList.take(3).forEach { rule ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = rule.appName, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = NaturalTextPrimary)
                                    Text(
                                        text = if (rule.isBlocked) (if (isHindi) "ब्लॉक है" else "Blocked") else "${if (isHindi) "दैनिक सीमा" else "Limit"}: ${if (rule.dailyLimitMinutes > 0) "${rule.dailyLimitMinutes} min" else if (isHindi) "अनलिमिटेड" else "Unlimited"}",
                                        fontSize = 11.sp,
                                        color = if (rule.isBlocked) Terracotta700 else NaturalTextSecondary
                                    )
                                }

                                Button(
                                    onClick = { onToggleAppBlock(rule) },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (rule.isBlocked) Terracotta700 else NaturalGreen700
                                    ),
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = if (rule.isBlocked) (if (isHindi) "खोलें" else "Unblock") else (if (isHindi) "ब्लॉक" else "Block"),
                                        fontSize = 10.sp,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Feature 3: Social App Detection Card
        if (selectedSafetyCategory == 0 || selectedSafetyCategory == 3) {
            item {
                SafetyFeatureCard(
                    icon = Icons.Default.Security,
                    iconColor = Color(0xFFE1306C),
                    title = if (isHindi) "सोशल ऐप डिटेक्शन (Social App Detection)" else "Social App Detection & Scanner",
                    subtitle = if (isHindi) "WhatsApp, Instagram, Snapchat, TikTok की सुरक्षा व जोखिम मीटर" else "Scan WhatsApp, Instagram, Snapchat, TikTok for safety risks",
                    tag = if (isHindi) "7 ऐप्स स्कैन" else "7 Apps Scanned",
                    onClick = { showSocialModal = true }
                )
            }
        }

        // Feature 4: Browser Safety Card
        if (selectedSafetyCategory == 0 || selectedSafetyCategory == 4) {
            item {
                SafetyFeatureCard(
                    icon = Icons.Default.Public,
                    iconColor = Color(0xFF0284C7),
                    title = if (isHindi) "ब्राउज़र सुरक्षा (Browser Safety)" else "Browser Safety & Web Filter",
                    subtitle = if (isHindi) "Google SafeSearch, 18+ वेबसाइट ब्लॉकर व कस्टम ब्लैकलिस्ट" else "SafeSearch enforcement, Adult/Gambling URL blockers",
                    tag = if (isHindi) "सक्रिय" else "SafeSearch ON",
                    onClick = { showBrowserModal = true }
                )
            }
        }

        // Feature 5: Albums Safety Card
        if (selectedSafetyCategory == 0 || selectedSafetyCategory == 5) {
            item {
                SafetyFeatureCard(
                    icon = Icons.Default.PhotoAlbum,
                    iconColor = Color(0xFF8B5CF6),
                    title = if (isHindi) "एल्बम व फोटो सुरक्षा (Albums Safety)" else "Albums & Photos Safety",
                    subtitle = if (isHindi) "AI संवेदनशील फोटो पहचान, स्क्रीनशॉट डिटेक्टर व ब्लर शील्ड" else "AI Sensitive Image Scanner & Inappropriate Media Blur",
                    tag = if (isHindi) "AI शील्ड" else "AI Shield",
                    onClick = { showAlbumsModal = true }
                )
            }
        }

        // Feature 6: Call & SMS Safety Card
        if (selectedSafetyCategory == 0 || selectedSafetyCategory == 6) {
            item {
                SafetyFeatureCard(
                    icon = Icons.Default.Call,
                    iconColor = NaturalGreen700,
                    title = if (isHindi) "कॉल व एसएमएस सुरक्षा (Call & SMS Safety)" else "Call & SMS Safety Shield",
                    subtitle = if (isHindi) "अज्ञात कॉलर ब्लॉकर, स्पैम रोबोकॉल व फ्रॉड एसएमएस प्रोटेक्शन" else "Spam Call Filter, Unknown Callers & Fraud SMS Shield",
                    tag = if (isHindi) "सुरक्षित" else "Protected",
                    onClick = { showCallSmsModal = true }
                )
            }
        }

        // Feature 7: Usage Safety & Health Card
        if (selectedSafetyCategory == 0 || selectedSafetyCategory == 7) {
            item {
                SafetyFeatureCard(
                    icon = Icons.Default.HealthAndSafety,
                    iconColor = EarthAmber600,
                    title = if (isHindi) "यूसेज सेफ्टी (Usage Safety & Eye Health)" else "Usage Safety & Digital Health",
                    subtitle = if (isHindi) "देर रात उपयोग चेतावनी, आंखों की दूरी अलर्ट व ब्लू लाइट फिल्टर" else "Late-night screen alert, Eye distance & Blue Light Shield",
                    tag = if (isHindi) "94/100" else "Score 94",
                    onClick = { showUsageSafetyModal = true }
                )
            }
        }
    }
}

@Composable
fun SafetyFeatureCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    title: String,
    subtitle: String,
    tag: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(1.dp, RoundedCornerShape(18.dp))
            .border(1.dp, NaturalBorder, RoundedCornerShape(18.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = NaturalSurface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
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
                    .background(NaturalGreen100)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(text = tag, color = NaturalGreen700, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
