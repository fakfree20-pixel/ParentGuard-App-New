package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DataUsage
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
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
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.R
import com.example.data.model.DevicePermissionItem
import com.example.ui.theme.NaturalBg
import com.example.ui.theme.NaturalBorder
import com.example.ui.theme.NaturalCardBg
import com.example.ui.theme.NaturalGreen100
import com.example.ui.theme.NaturalGreen700
import com.example.ui.theme.NaturalGreen900
import com.example.ui.theme.NaturalSurfaceVariant
import com.example.ui.theme.NaturalTextPrimary
import com.example.ui.theme.NaturalTextSecondary
import com.example.ui.theme.Terracotta700

/**
 * FlashGet Kids QR Code Visual Mock
 */
@Composable
fun FlashGetQRCodeGraphic(modifier: Modifier = Modifier, code: String = "794 821 305") {
    Box(
        modifier = modifier
            .size(160.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(2.dp, NaturalGreen700, RoundedCornerShape(16.dp))
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            val step = w / 9f

            // Corner squares (standard QR code positioning markers)
            // Top Left
            drawRect(color = Color(0xFF1E293B), topLeft = Offset(0f, 0f), size = Size(step * 3, step * 3))
            drawRect(color = Color.White, topLeft = Offset(step * 0.7f, step * 0.7f), size = Size(step * 1.6f, step * 1.6f))
            drawRect(color = Color(0xFF1E293B), topLeft = Offset(step * 1.1f, step * 1.1f), size = Size(step * 0.8f, step * 0.8f))

            // Top Right
            drawRect(color = Color(0xFF1E293B), topLeft = Offset(w - step * 3, 0f), size = Size(step * 3, step * 3))
            drawRect(color = Color.White, topLeft = Offset(w - step * 2.3f, step * 0.7f), size = Size(step * 1.6f, step * 1.6f))
            drawRect(color = Color(0xFF1E293B), topLeft = Offset(w - step * 1.9f, step * 1.1f), size = Size(step * 0.8f, step * 0.8f))

            // Bottom Left
            drawRect(color = Color(0xFF1E293B), topLeft = Offset(0f, h - step * 3), size = Size(step * 3, step * 3))
            drawRect(color = Color.White, topLeft = Offset(step * 0.7f, h - step * 2.3f), size = Size(step * 1.6f, step * 1.6f))
            drawRect(color = Color(0xFF1E293B), topLeft = Offset(step * 1.1f, h - step * 1.9f), size = Size(step * 0.8f, step * 0.8f))

            // Decorative data dots
            val dotPositions = listOf(
                Pair(4, 1), Pair(4, 2), Pair(4, 4), Pair(4, 5), Pair(4, 7),
                Pair(1, 4), Pair(2, 4), Pair(6, 4), Pair(7, 4),
                Pair(6, 1), Pair(7, 2), Pair(6, 7), Pair(7, 6),
                Pair(1, 6), Pair(2, 7), Pair(3, 5), Pair(5, 3)
            )
            dotPositions.forEach { (col, row) ->
                drawRect(
                    color = Color(0xFF2E7D32),
                    topLeft = Offset(col * step, row * step),
                    size = Size(step * 0.85f, step * 0.85f)
                )
            }
        }

        // Center badge
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(NaturalGreen700),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Shield, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
        }
    }
}

/**
 * Screen on Parent's Phone showing the 9-Digit Binding Code & QR Code
 */
@Composable
fun ParentDeviceBindingDialog(
    bindingCode: String,
    isHindi: Boolean,
    onDismiss: () -> Unit,
    onAddChildDirectly: (name: String, age: Int) -> Unit
) {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    var isCopied by remember { mutableStateOf(false) }
    var childName by remember { mutableStateOf("") }
    var childAge by remember { mutableStateOf("9") }
    var showManualAdd by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(NaturalGreen100),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.QrCodeScanner, contentDescription = null, tint = NaturalGreen700, modifier = Modifier.size(20.dp))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = if (isHindi) "बच्चे का डिवाइस बाइंड करें" else "Bind Child's Device",
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = NaturalTextPrimary
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (isHindi)
                        "बच्चे के फ़ोन पर FlashGet Kids खोलें और यह 9-अंकीय कोड डालें या QR कोड स्कैन करें:"
                    else
                        "Open FlashGet Kids on your child's phone and enter this 9-digit code or scan the QR code:",
                    fontSize = 13.sp,
                    color = NaturalTextSecondary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(14.dp))

                // QR Code Display
                FlashGetQRCodeGraphic(code = bindingCode)

                Spacer(modifier = Modifier.height(14.dp))

                // 9-Digit Pairing Code Box with Copy Button
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
                                text = if (isHindi) "9-अंकीय बाइंडिंग कोड" else "9-Digit Binding Code",
                                fontSize = 10.sp,
                                color = NaturalTextSecondary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = bindingCode,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                fontFamily = FontFamily.Monospace,
                                color = NaturalGreen900,
                                letterSpacing = 2.sp
                            )
                        }

                        IconButton(
                            onClick = {
                                clipboardManager.setText(AnnotatedString(bindingCode.replace(" ", "")))
                                isCopied = true
                            },
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(if (isCopied) NaturalGreen700 else NaturalGreen100)
                                .size(38.dp)
                        ) {
                            Icon(
                                if (isCopied) Icons.Default.Check else Icons.Default.ContentCopy,
                                contentDescription = "Copy code",
                                tint = if (isCopied) Color.White else NaturalGreen700,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Steps list
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(NaturalCardBg)
                        .border(1.dp, NaturalBorder, RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    Text(
                        text = if (isHindi) "त्वरित बाइंडिंग चरण:" else "Binding Steps:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = NaturalGreen700
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (isHindi)
                            "1. बच्चे के फोन पर FlashGet Kids खोलें\n2. 'बच्चे का फ़ोन (Kids Device)' चुनें\n3. यह 9-अंकीय कोड डालें और अनुमतियां दें"
                        else
                            "1. Open FlashGet Kids on child's device\n2. Select 'Child's Phone'\n3. Enter this 9-digit code and allow permissions",
                        fontSize = 11.sp,
                        color = NaturalTextSecondary,
                        lineHeight = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Toggle manual add
                TextButton(onClick = { showManualAdd = !showManualAdd }) {
                    Text(
                        text = if (showManualAdd)
                            (if (isHindi) "फॉर्म छुपाएं" else "Hide Form")
                        else
                            (if (isHindi) "+ तुरंत बच्चे की प्रोफ़ाइल जोड़ें" else "+ Directly Add Child Profile"),
                        fontSize = 12.sp,
                        color = NaturalGreen700,
                        fontWeight = FontWeight.Bold
                    )
                }

                AnimatedVisibility(visible = showManualAdd) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = childName,
                            onValueChange = { childName = it },
                            placeholder = { Text(if (isHindi) "बच्चे का नाम (उदा. आरव)" else "Child Name (e.g. Aarav)") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = NaturalGreen700)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = childAge,
                            onValueChange = { childAge = it },
                            placeholder = { Text(if (isHindi) "उम्र (वर्ष)" else "Age (Years)") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = NaturalGreen700)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                if (childName.isNotBlank()) {
                                    onAddChildDirectly(childName, childAge.toIntOrNull() ?: 8)
                                    onDismiss()
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = NaturalGreen700)
                        ) {
                            Text(if (isHindi) "प्रोफ़ाइल जोड़ें" else "Add Profile")
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
                Text(if (isHindi) "पूर्ण (Done)" else "Close")
            }
        }
    )
}

/**
 * Screen on Child's Phone to Enter 9-Digit Code and Bind
 */
@Composable
fun ChildDevicePairingScreen(
    isHindi: Boolean,
    onToggleLanguage: () -> Unit,
    onPairWithCode: (code: String, name: String, age: Int) -> Boolean,
    onBack: () -> Unit
) {
    var pairingCodeInput by remember { mutableStateOf("794821305") }
    var childName by remember { mutableStateOf("Aarav's Infinix") }
    var childAge by remember { mutableStateOf("9") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isScanningMock by remember { mutableStateOf(false) }

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
                .padding(horizontal = 24.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(NaturalSurfaceVariant)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = NaturalTextPrimary)
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.user_profile_logo_1787259898289),
                        contentDescription = "FlashGet Kids Logo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "FlashGet Kids",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = NaturalGreen900
                    )
                }

                IconButton(
                    onClick = onToggleLanguage,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(NaturalSurfaceVariant)
                ) {
                    Text(text = if (isHindi) "EN" else "हिं", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = NaturalTextPrimary)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Title
            Text(
                text = if (isHindi) "माता-पिता के फ़ोन से बाइंड करें" else "Bind to Parent's Phone",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = NaturalTextPrimary,
                textAlign = TextAlign.Center
            )

            Text(
                text = if (isHindi)
                    "माता-पिता के FlashGet Kids ऐप में दिख रहा 9-अंकीय कोड दर्ज करें या QR स्कैन करें।"
                else
                    "Enter the 9-digit pairing code shown on your parent's phone to connect this device.",
                fontSize = 13.sp,
                color = NaturalTextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Main Input Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, NaturalBorder, RoundedCornerShape(20.dp)),
                colors = CardDefaults.cardColors(containerColor = NaturalCardBg),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = if (isHindi) "9-अंकीय बाइंडिंग कोड (9-Digit Code)" else "9-Digit Binding Code",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = NaturalTextPrimary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = pairingCodeInput,
                        onValueChange = {
                            pairingCodeInput = it
                            errorMessage = null
                        },
                        placeholder = { Text("794 821 305") },
                        leadingIcon = {
                            Icon(Icons.Default.QrCode, contentDescription = null, tint = NaturalGreen700)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_pairing_code"),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NaturalGreen700,
                            focusedLabelColor = NaturalGreen700
                        )
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = if (isHindi) "डिवाइस का नाम (Device Name)" else "Device Name",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = NaturalTextPrimary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = childName,
                        onValueChange = { childName = it },
                        placeholder = { Text("Infinix X6823C") },
                        leadingIcon = {
                            Icon(Icons.Default.PhoneAndroid, contentDescription = null, tint = NaturalGreen700)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NaturalGreen700,
                            focusedLabelColor = NaturalGreen700
                        )
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = if (isHindi) "बच्चे की उम्र (Child's Age)" else "Child's Age",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = NaturalTextPrimary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = childAge,
                        onValueChange = { childAge = it },
                        placeholder = { Text("9") },
                        leadingIcon = {
                            Icon(Icons.Default.Person, contentDescription = null, tint = NaturalGreen700)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NaturalGreen700,
                            focusedLabelColor = NaturalGreen700
                        )
                    )

                    if (errorMessage != null) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(Terracotta700.copy(alpha = 0.1f))
                                .padding(8.dp)
                        ) {
                            Icon(Icons.Default.Info, contentDescription = null, tint = Terracotta700, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = errorMessage!!, color = Terracotta700, fontSize = 12.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Connect & Next Button
                    Button(
                        onClick = {
                            if (pairingCodeInput.isBlank() || pairingCodeInput.length < 6) {
                                errorMessage = if (isHindi) "कृपया वैध 9-अंकीय कोड दर्ज करें।" else "Please enter a valid 9-digit code."
                            } else {
                                val ok = onPairWithCode(pairingCodeInput, childName, childAge.toIntOrNull() ?: 9)
                                if (!ok) {
                                    errorMessage = if (isHindi) "कोड अमान्य है। पुनः जांचें।" else "Invalid code. Please re-check."
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("submit_pair_button"),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = NaturalGreen700)
                    ) {
                        Text(
                            text = if (isHindi) "डिवाइस बाइंड करें (Next: Permissions)" else "Pair & Proceed to Permissions",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Scan QR code shortcut
            Button(
                onClick = {
                    pairingCodeInput = "794 821 305"
                    val ok = onPairWithCode("794821305", childName, childAge.toIntOrNull() ?: 9)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .testTag("scan_qr_simulate_button"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = NaturalGreen100)
            ) {
                Icon(Icons.Default.QrCodeScanner, contentDescription = null, tint = NaturalGreen700, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isHindi) "📷 माता-पिता का QR कोड स्कैन करें (Auto-Fill)" else "📷 Scan Parent's QR Code (Auto-Fill)",
                    color = NaturalGreen900,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }
        }
    }
}

/**
 * Child Permissions Setup Checklist Wizard
 */
@Composable
fun ChildPermissionsScreen(
    permissions: List<DevicePermissionItem>,
    isHindi: Boolean,
    onTogglePermission: (String) -> Unit,
    onCompleteSetup: () -> Unit
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
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.user_profile_logo_1787259898289),
                    contentDescription = "FlashGet Logo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = if (isHindi) "अनुमति सेटअप विज़ार्ड" else "Required Permissions Wizard",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp,
                        color = NaturalTextPrimary
                    )
                    Text(
                        text = if (isHindi) "फ़्लैशगेट किड्स सुरक्षा अनुमतियां" else "FlashGet Kids Shield Setup",
                        fontSize = 12.sp,
                        color = NaturalGreen700
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = if (isHindi)
                    "फ़्लैशगेट किड्स को स्क्रीन टाइम, ऐप ब्लॉकिंग और एंटी-अनइंस्टॉल सुरक्षा ठीक से चलाने के लिए इन अनुमतियों की आवश्यकता है।"
                else
                    "To enable live monitoring, app limits, geofencing, and tamper prevention, enable each permission below:",
                fontSize = 13.sp,
                color = NaturalTextSecondary
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Permissions list
            permissions.forEach { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .border(1.dp, NaturalBorder, RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(containerColor = NaturalCardBg),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (item.isGranted) NaturalGreen100 else NaturalSurfaceVariant),
                            contentAlignment = Alignment.Center
                        ) {
                            val icon = when (item.id) {
                                "accessibility" -> Icons.Default.Accessibility
                                "usage_access" -> Icons.Default.DataUsage
                                "notification_listener" -> Icons.Default.Notifications
                                "device_admin" -> Icons.Default.Security
                                "location" -> Icons.Default.LocationOn
                                else -> Icons.Default.BatteryChargingFull
                            }
                            Icon(
                                icon,
                                contentDescription = null,
                                tint = if (item.isGranted) NaturalGreen700 else NaturalTextSecondary,
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = if (isHindi) item.titleHindi else item.title,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = NaturalTextPrimary
                                )
                                if (item.isRequired) {
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(Terracotta700.copy(alpha = 0.15f))
                                            .padding(horizontal = 4.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = if (isHindi) "आवश्यक" else "Required",
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Terracotta700
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = if (isHindi) item.descriptionHindi else item.description,
                                fontSize = 11.sp,
                                color = NaturalTextSecondary,
                                lineHeight = 15.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Switch(
                            checked = item.isGranted,
                            onCheckedChange = { onTogglePermission(item.id) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = NaturalGreen700
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Finish Setup Button
            Button(
                onClick = onCompleteSetup,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("complete_permissions_setup_button"),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = NaturalGreen700)
            ) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isHindi) "सेटअप पूर्ण करें और सुरक्षा चालू करें" else "Finish Setup & Start Protection",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
        }
    }
}
