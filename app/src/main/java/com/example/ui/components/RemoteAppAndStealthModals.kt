package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Dialpad
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material.icons.filled.Gamepad
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SmartDisplay
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.VolumeDown
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.AppUsageRule
import com.example.data.model.ChildProfile
import com.example.ui.theme.EarthAmber100
import com.example.ui.theme.EarthAmber500
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
import kotlinx.coroutines.delay

/**
 * 1. REMOTE APP LAUNCHER & LIVE INTERACTIVE CONTROLLER
 * Allows the parent to launch and control ANY app on the child's device remotely in real time.
 */
@Composable
fun RemoteAppControlModal(
    child: ChildProfile,
    appsList: List<AppUsageRule>,
    initialSelectedApp: AppUsageRule? = null,
    isHindi: Boolean,
    onLaunchRemoteApp: (packageName: String, appName: String) -> Unit,
    onStopRemoteApp: () -> Unit,
    onSendNavigationCommand: (command: String) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedApp by remember {
        mutableStateOf(
            initialSelectedApp ?: appsList.firstOrNull() ?: AppUsageRule(
                childId = child.id,
                packageName = "com.whatsapp",
                appName = "WhatsApp",
                category = "SOCIAL"
            )
        )
    }

    var isSessionActive by remember { mutableStateOf(true) }
    var remoteTextToSend by remember { mutableStateOf("") }
    var lastActionFeedback by remember { mutableStateOf<String?>(null) }
    var showQuickAppPicker by remember { mutableStateOf(false) }

    LaunchedEffect(lastActionFeedback) {
        if (lastActionFeedback != null) {
            delay(2200)
            lastActionFeedback = null
        }
    }

    val pulseAnim = rememberInfiniteTransition(label = "pulse")
    val liveGlow by pulseAnim.animateFloat(
        initialValue = 0.4f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    Dialog(
        onDismissRequest = {
            onStopRemoteApp()
            onDismiss()
        },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0F1416))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 36.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
            ) {
                // Top Header Bar
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
                                .background(NaturalGreen700),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.TouchApp, contentDescription = null, tint = Color.White, modifier = Modifier.size(22.dp))
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = if (isHindi) "रिमोट ऐप कंट्रोल" else "Remote App Control",
                                    color = Color.White,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = NaturalGreen700.copy(alpha = liveGlow)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(Icons.Default.FiberManualRecord, contentDescription = null, tint = Color.White, modifier = Modifier.size(8.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(text = "LIVE", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.ExtraBold)
                                    }
                                }
                            }
                            Text(
                                text = if (isHindi) "${child.name} का फोन: ${child.deviceModel}" else "${child.name}'s Device: ${child.deviceModel}",
                                color = Color.LightGray,
                                fontSize = 11.sp
                            )
                        }
                    }

                    IconButton(
                        onClick = {
                            onStopRemoteApp()
                            onDismiss()
                        },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.15f))
                            .size(36.dp)
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Active App Switcher Bar
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showQuickAppPicker = !showQuickAppPicker },
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E282C)),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(NaturalGreen100),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.PlayArrow, contentDescription = null, tint = NaturalGreen700, modifier = Modifier.size(18.dp))
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = if (isHindi) "सक्रिय रिमोट ऐप (चालू है):" else "Running on Child Phone:",
                                    color = Color.Gray,
                                    fontSize = 10.sp
                                )
                                Text(
                                    text = selectedApp.appName,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color.White.copy(alpha = 0.1f)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.SwapHoriz, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = if (isHindi) "ऐप बदलें" else "Switch App",
                                        color = Color.White,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        }
                    }
                }

                // Quick App Selection Drawer (if expanded)
                AnimatedVisibility(visible = showQuickAppPicker) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF182226)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = if (isHindi) "बच्चे के फोन पर चलाने के लिए ऐप चुनें:" else "Select an app to remotely launch on child phone:",
                                color = Color.LightGray,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                contentPadding = PaddingValues(horizontal = 4.dp)
                            ) {
                                items(appsList) { app ->
                                    val isCur = app.packageName == selectedApp.packageName
                                    Surface(
                                        shape = RoundedCornerShape(10.dp),
                                        color = if (isCur) NaturalGreen700 else Color(0xFF243238),
                                        modifier = Modifier.clickable {
                                            selectedApp = app
                                            showQuickAppPicker = false
                                            onLaunchRemoteApp(app.packageName, app.appName)
                                            lastActionFeedback = if (isHindi) "🚀 ${app.appName} बच्चे के फोन पर खुला!" else "🚀 Launched ${app.appName} on child device!"
                                        }
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                imageVector = when (app.category) {
                                                    "GAMES" -> Icons.Default.Gamepad
                                                    "SOCIAL" -> Icons.Default.SmartDisplay
                                                    else -> Icons.Default.PlayArrow
                                                },
                                                contentDescription = null,
                                                tint = Color.White,
                                                modifier = Modifier.size(16.dp)
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(
                                                text = app.appName,
                                                color = Color.White,
                                                fontSize = 12.sp,
                                                fontWeight = if (isCur) FontWeight.Bold else FontWeight.Normal
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Simulated Interactive Child Phone Screen Mirror
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .border(1.5.dp, Color(0xFF2C3E44), RoundedCornerShape(20.dp)),
                    colors = CardDefaults.cardColors(containerColor = Color.Black),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        // Phone Screen Inner Layout
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color(0xFF162329),
                                            Color(0xFF0F171A),
                                            Color(0xFF080D0E)
                                        )
                                    )
                                )
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Top Simulated Android Status Bar
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "10:45 AM • 4G LTE",
                                    color = Color.Gray,
                                    fontSize = 10.sp,
                                    fontFamily = FontFamily.Monospace
                                )
                                Text(
                                    text = "🔋 ${child.batteryPercent}%",
                                    color = Color(0xFFA5D6A7),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            // Center Running App Canvas Simulation
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(24.dp),
                                    color = Color(0xFF1E323A),
                                    modifier = Modifier.size(72.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = when (selectedApp.category) {
                                                "GAMES" -> Icons.Default.Gamepad
                                                "SOCIAL" -> Icons.Default.SmartDisplay
                                                else -> Icons.Default.TouchApp
                                            },
                                            contentDescription = null,
                                            tint = NaturalGreen100,
                                            modifier = Modifier.size(38.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(14.dp))

                                Text(
                                    text = "${selectedApp.appName} is running on child's screen",
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = if (isHindi)
                                        "आप नीचे दिए गए रिमोट बटनों से बच्चे के फोन पर इस ऐप को चला, टाइप और नियंत्रित कर सकते हैं।"
                                    else
                                        "You are actively streaming & controlling this app in real time via Accessibility Bridge.",
                                    color = Color.LightGray,
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 16.sp
                                )

                                if (lastActionFeedback != null) {
                                    Spacer(modifier = Modifier.height(14.dp))
                                    Surface(
                                        shape = RoundedCornerShape(10.dp),
                                        color = NaturalGreen700
                                    ) {
                                        Text(
                                            text = lastActionFeedback!!,
                                            color = Color.White,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                                        )
                                    }
                                }
                            }

                            // Quick Touch Gesture Simulated Triggers
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color.White.copy(alpha = 0.1f),
                                    modifier = Modifier.clickable {
                                        onSendNavigationCommand("SCROLL_UP")
                                        lastActionFeedback = if (isHindi) "⬆️ ऊपर स्क्रॉल किया" else "⬆️ Scrolled Up"
                                    }
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(Icons.Default.ArrowUpward, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(text = if (isHindi) "ऊपर स्क्रॉल" else "Scroll Up", color = Color.White, fontSize = 10.sp)
                                    }
                                }

                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color.White.copy(alpha = 0.1f),
                                    modifier = Modifier.clickable {
                                        onSendNavigationCommand("SCROLL_DOWN")
                                        lastActionFeedback = if (isHindi) "⬇️ नीचे स्क्रॉल किया" else "⬇️ Scrolled Down"
                                    }
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(Icons.Default.ArrowDownward, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(text = if (isHindi) "नीचे स्क्रॉल" else "Scroll Down", color = Color.White, fontSize = 10.sp)
                                    }
                                }

                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color.White.copy(alpha = 0.1f),
                                    modifier = Modifier.clickable {
                                        onSendNavigationCommand("REFRESH")
                                        lastActionFeedback = if (isHindi) "🔄 रिफ्रेश किया" else "🔄 Refreshed Page"
                                    }
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(Icons.Default.Refresh, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(text = if (isHindi) "रिफ्रेश" else "Refresh", color = Color.White, fontSize = 10.sp)
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Remote Typing / Input Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = remoteTextToSend,
                        onValueChange = { remoteTextToSend = it },
                        placeholder = {
                            Text(
                                if (isHindi) "रिमोट टेक्स्ट टाइप करें..." else "Type text to send to child's app...",
                                color = Color.Gray,
                                fontSize = 12.sp
                            )
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Keyboard, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(18.dp))
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                        keyboardActions = KeyboardActions(onSend = {
                            if (remoteTextToSend.isNotBlank()) {
                                onSendNavigationCommand("TYPE: $remoteTextToSend")
                                lastActionFeedback = if (isHindi) "⌨️ '$remoteTextToSend' टाइप किया गया" else "⌨️ Typed '$remoteTextToSend'"
                                remoteTextToSend = ""
                            }
                        }),
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = NaturalGreen700,
                            unfocusedBorderColor = Color(0xFF2C3E44),
                            focusedContainerColor = Color(0xFF182226),
                            unfocusedContainerColor = Color(0xFF182226)
                        )
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            if (remoteTextToSend.isNotBlank()) {
                                onSendNavigationCommand("TYPE: $remoteTextToSend")
                                lastActionFeedback = if (isHindi) "⌨️ '$remoteTextToSend' टाइप किया गया" else "⌨️ Typed '$remoteTextToSend'"
                                remoteTextToSend = ""
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = NaturalGreen700),
                        modifier = Modifier.height(50.dp)
                    ) {
                        Icon(Icons.Default.Send, contentDescription = "Send text", tint = Color.White, modifier = Modifier.size(18.dp))
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Bottom Android Remote Navigation Bar (Back, Home, Recents, Volume, Stop)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF141D20)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // 1. Back Button
                        IconButton(
                            onClick = {
                                onSendNavigationCommand("BACK")
                                lastActionFeedback = if (isHindi) "◀️ रिमोट बैक बटन दबाया" else "◀️ Remote Back Pressed"
                            },
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.08f))
                                .size(44.dp)
                        ) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White, modifier = Modifier.size(20.dp))
                        }

                        // 2. Home Button
                        IconButton(
                            onClick = {
                                onSendNavigationCommand("HOME")
                                lastActionFeedback = if (isHindi) "⭕ रिमोट होम बटन दबाया" else "⭕ Remote Home Pressed"
                            },
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.08f))
                                .size(44.dp)
                        ) {
                            Icon(Icons.Default.Home, contentDescription = "Home", tint = Color.White, modifier = Modifier.size(22.dp))
                        }

                        // 3. Recents / App Switcher Button
                        IconButton(
                            onClick = {
                                onSendNavigationCommand("RECENTS")
                                lastActionFeedback = if (isHindi) "🔲 रीसेंट ऐप्स खोला गया" else "🔲 Recent Apps Triggered"
                            },
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.08f))
                                .size(44.dp)
                        ) {
                            Icon(Icons.Default.OpenInNew, contentDescription = "Recents", tint = Color.White, modifier = Modifier.size(20.dp))
                        }

                        // 4. Volume Up
                        IconButton(
                            onClick = {
                                onSendNavigationCommand("VOLUME_UP")
                                lastActionFeedback = if (isHindi) "🔊 वॉल्यूम बढ़ाया गया" else "🔊 Volume Raised"
                            },
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.08f))
                                .size(44.dp)
                        ) {
                            Icon(Icons.Default.VolumeUp, contentDescription = "Vol+", tint = Color(0xFFA5D6A7), modifier = Modifier.size(20.dp))
                        }

                        // 5. Force Close / Stop Remote App Button
                        Button(
                            onClick = {
                                onStopRemoteApp()
                                onDismiss()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Terracotta700),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            Icon(Icons.Default.Stop, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (isHindi) "ऐप बंद करें" else "Close App",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * 2. APP HIDE & STEALTH MODE SETTINGS DIALOG
 * Configures hiding the ParentGuard app icon from the child's phone launcher,
 * running silently in the background, and recovering it using a secret dialer code.
 */
@Composable
fun AppStealthModeDialog(
    child: ChildProfile,
    isHindi: Boolean,
    onToggleStealth: (Boolean) -> Unit,
    onDismiss: () -> Unit
) {
    var isStealthEnabled by remember { mutableStateOf(child.isAppHidden) }
    var secretCode by remember { mutableStateOf(child.dialerSecretCode.ifBlank { "*#9842#" }) }
    val clipboardManager = LocalClipboardManager.current
    var isCopied by remember { mutableStateOf(false) }
    var showDialerSim by remember { mutableStateOf(false) }
    var simulatedDialInput by remember { mutableStateOf("") }
    var simUnlockedApp by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (isStealthEnabled) Terracotta100 else NaturalGreen100),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isStealthEnabled) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = null,
                        tint = if (isStealthEnabled) Terracotta700 else NaturalGreen700,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = if (isHindi) "ऐप आइकन छुपाएं (स्टील्थ मोड)" else "Hide App Icon (Stealth Mode)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = NaturalTextPrimary
                    )
                    Text(
                        text = if (isHindi) "बच्चे के फोन पर ऐप को अदृश्य करें" else "Make ParentGuard invisible on child's phone",
                        fontSize = 11.sp,
                        color = NaturalTextSecondary
                    )
                }
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                // Switch Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isStealthEnabled) Terracotta700.copy(alpha = 0.08f) else NaturalSurfaceVariant
                    ),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = if (isHindi) "स्टील्थ सुरक्षा सक्रिय करें" else "Enable Stealth Mode",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = if (isStealthEnabled) Terracotta700 else NaturalTextPrimary
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = if (isStealthEnabled) {
                                    if (isHindi) "✅ ऐप आइकन बच्चे के लॉन्चर से छुपा हुआ है" else "✅ App icon is hidden from child launcher"
                                } else {
                                    if (isHindi) "ऐप आइकन सामान्य रूप से दिखाई दे रहा है" else "App icon is visible in app drawer"
                                },
                                fontSize = 11.sp,
                                color = NaturalTextSecondary
                            )
                        }

                        Switch(
                            checked = isStealthEnabled,
                            onCheckedChange = {
                                isStealthEnabled = it
                                onToggleStealth(it)
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Terracotta700
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // How it works section
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, NaturalBorder, RoundedCornerShape(12.dp)),
                    colors = CardDefaults.cardColors(containerColor = NaturalCardBg),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = if (isHindi) "गुप्त रूप से कैसे काम करता है:" else "How Stealth Mode Works:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = NaturalGreen700
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = if (isHindi)
                                "1. चालू करने पर बच्चे के होम स्क्रीन और ऐप ड्रॉअर से ParentGuard का आइकन हट जाता है।\n2. बैकग्राउंड सर्विस पूरी तरह सक्रिय रहती है (लाइव लोकेशन, स्क्रीन लिमिट, कैमरा मॉनिटरिंग चालू रहती है)।\n3. बच्चे के फोन पर ऐप को दोबारा खोलने के लिए फ़ोन के डायलर में सीक्रेट कोड डायल करें।"
                            else
                                "1. When enabled, ParentGuard icon disappears from child's launcher.\n2. Background protection remains 100% active (Location, Limits, Remote Camera).\n3. To open the app on child's phone, dial the secret code into the Phone dialer.",
                            fontSize = 11.sp,
                            color = NaturalTextSecondary,
                            lineHeight = 16.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Secret Dialer Code Box
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = NaturalSurfaceVariant),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = if (isHindi) "डायलर सीक्रेट कोड (गुप्त कोड)" else "Secret Dialer Access Code",
                                fontSize = 10.sp,
                                color = NaturalTextSecondary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = secretCode,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                fontFamily = FontFamily.Monospace,
                                color = NaturalGreen900,
                                letterSpacing = 2.sp
                            )
                        }

                        IconButton(
                            onClick = {
                                clipboardManager.setText(AnnotatedString(secretCode))
                                isCopied = true
                            },
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(if (isCopied) NaturalGreen700 else NaturalGreen100)
                                .size(36.dp)
                        ) {
                            Icon(
                                if (isCopied) Icons.Default.CheckCircle else Icons.Default.ContentCopy,
                                contentDescription = "Copy code",
                                tint = if (isCopied) Color.White else NaturalGreen700,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Test Dialer Simulation Button
                OutlinedButton(
                    onClick = {
                        showDialerSim = !showDialerSim
                        simulatedDialInput = ""
                        simUnlockedApp = false
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.Dialpad, contentDescription = null, tint = NaturalGreen700, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (showDialerSim)
                            (if (isHindi) "डायलर सिम्युलेटर बंद करें" else "Close Dialer Test")
                        else
                            (if (isHindi) "📞 डायलर कोड टेस्ट करें (Simulate)" else "📞 Test Secret Code on Dialer"),
                        fontSize = 12.sp,
                        color = NaturalGreen700,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Interactive Phone Dialer Simulation
                AnimatedVisibility(visible = showDialerSim) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF141C1F)),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = if (isHindi) "बच्चे के फोन का डायलर सिम्युलेटर" else "Child Device Dialer Simulator",
                                color = Color.Gray,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // Dialer Screen
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color.Black,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(40.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = simulatedDialInput.ifBlank { "Dial Code..." },
                                        color = if (simulatedDialInput.isNotBlank()) Color.White else Color.DarkGray,
                                        fontSize = 16.sp,
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Keypad rows
                            val keys = listOf(
                                listOf("*", "#", "9"),
                                listOf("8", "4", "2")
                            )

                            keys.forEach { row ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    row.forEach { k ->
                                        Surface(
                                            shape = CircleShape,
                                            color = Color.White.copy(alpha = 0.12f),
                                            modifier = Modifier
                                                .size(40.dp)
                                                .clickable {
                                                    simulatedDialInput += k
                                                    if (simulatedDialInput == secretCode) {
                                                        simUnlockedApp = true
                                                    }
                                                }
                                        ) {
                                            Box(contentAlignment = Alignment.Center) {
                                                Text(text = k, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                            }
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                            }

                            if (simUnlockedApp) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = NaturalGreen700
                                ) {
                                    Text(
                                        text = if (isHindi) "🎉 सही कोड! ParentGuard ऐप खुल गया!" else "🎉 Secret Code Verified! ParentGuard Unhidden!",
                                        color = Color.White,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = NaturalGreen700)
            ) {
                Text(if (isHindi) "सहेजें (Done)" else "Done")
            }
        }
    )
}
