package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
import com.example.ui.theme.NaturalSurface
import com.example.ui.theme.NaturalSurfaceVariant
import com.example.ui.theme.NaturalTextPrimary
import com.example.ui.theme.NaturalTextSecondary
import com.example.ui.theme.Terracotta700

@Composable
fun AuthScreen(
    isHindi: Boolean,
    onToggleLanguage: () -> Unit,
    onLogin: (String, String, Boolean) -> Boolean,
    onRegister: (String, String, String) -> Boolean,
    onBackToModeSelect: () -> Unit,
    onSwitchToChildMode: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) } // 0 = Sign In, 1 = Sign Up
    var email by remember { mutableStateOf("parent.guardian@gmail.com") }
    var password by remember { mutableStateOf("123456") }
    var confirmPassword by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showForgotPasswordDialog by remember { mutableStateOf(false) }
    var forgotEmail by remember { mutableStateOf("") }
    var forgotSentSuccess by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    if (showForgotPasswordDialog) {
        AlertDialog(
            onDismissRequest = { showForgotPasswordDialog = false },
            title = {
                Text(
                    text = if (isHindi) "पासवर्ड रीसेट करें" else "Reset Password",
                    fontWeight = FontWeight.Bold,
                    color = NaturalTextPrimary
                )
            },
            text = {
                Column {
                    if (forgotSentSuccess) {
                        Text(
                            text = if (isHindi)
                                "✅ रीसेट लिंक $forgotEmail पर भेज दिया गया है। अपना इनबॉक्स चेक करें।"
                            else
                                "✅ Password reset link has been sent to $forgotEmail. Please check your inbox.",
                            color = NaturalGreen700,
                            fontSize = 14.sp
                        )
                    } else {
                        Text(
                            text = if (isHindi)
                                "अपना पंजीकृत ईमेल दर्ज करें। हम आपको पासवर्ड रीसेट करने का लिंक भेजेंगे।"
                            else
                                "Enter your registered email address to receive a secure password recovery link.",
                            fontSize = 13.sp,
                            color = NaturalTextSecondary
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedTextField(
                            value = forgotEmail,
                            onValueChange = { forgotEmail = it },
                            placeholder = { Text("example@gmail.com") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = NaturalGreen700,
                                focusedLabelColor = NaturalGreen700
                            )
                        )
                    }
                }
            },
            confirmButton = {
                if (!forgotSentSuccess) {
                    Button(
                        onClick = {
                            if (forgotEmail.isNotBlank()) {
                                forgotSentSuccess = true
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = NaturalGreen700)
                    ) {
                        Text(if (isHindi) "लिंक भेजें" else "Send Link")
                    }
                } else {
                    Button(
                        onClick = { showForgotPasswordDialog = false },
                        colors = ButtonDefaults.buttonColors(containerColor = NaturalGreen700)
                    ) {
                        Text(if (isHindi) "ठीक है" else "Done")
                    }
                }
            },
            dismissButton = {
                if (!forgotSentSuccess) {
                    TextButton(onClick = { showForgotPasswordDialog = false }) {
                        Text(if (isHindi) "रद्द करें" else "Cancel", color = NaturalTextSecondary)
                    }
                }
            }
        )
    }

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
            // Top Navigation Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBackToModeSelect,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(NaturalSurfaceVariant)
                        .testTag("auth_back_button")
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = NaturalTextPrimary
                    )
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
                        .testTag("auth_lang_toggle")
                ) {
                    Text(
                        text = if (isHindi) "EN" else "हिं",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = NaturalTextPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Title and Subtitle
            Text(
                text = if (selectedTab == 0) {
                    if (isHindi) "पैरेंट अकाउंट लॉगिन" else "Parent Account Login"
                } else {
                    if (isHindi) "नया पैरेंट अकाउंट बनाएं" else "Create Parent Account"
                },
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = NaturalTextPrimary,
                textAlign = TextAlign.Center
            )

            Text(
                text = if (isHindi)
                    "अपने बच्चे के डिवाइस की सुरक्षा और स्क्रीन समय प्रबंधित करने के लिए साइन इन करें।"
                else
                    "Sign in to supervise your child's activities, locations, and app limits.",
                fontSize = 13.sp,
                color = NaturalTextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Tab Selector: Sign In / Sign Up
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = NaturalSurfaceVariant),
                shape = RoundedCornerShape(14.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (selectedTab == 0) NaturalCardBg else Color.Transparent)
                            .clickable {
                                selectedTab = 0
                                errorMessage = null
                            }
                            .padding(vertical = 10.dp)
                            .testTag("tab_signin"),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (isHindi) "लॉग इन (Sign In)" else "Sign In",
                            fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Medium,
                            color = if (selectedTab == 0) NaturalGreen700 else NaturalTextSecondary,
                            fontSize = 14.sp
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (selectedTab == 1) NaturalCardBg else Color.Transparent)
                            .clickable {
                                selectedTab = 1
                                errorMessage = null
                            }
                            .padding(vertical = 10.dp)
                            .testTag("tab_signup"),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (isHindi) "रजिस्टर (Sign Up)" else "Create Account",
                            fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Medium,
                            color = if (selectedTab == 1) NaturalGreen700 else NaturalTextSecondary,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Main Input Form Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, NaturalBorder, RoundedCornerShape(20.dp)),
                colors = CardDefaults.cardColors(containerColor = NaturalCardBg),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    // Full Name (only in Sign Up)
                    AnimatedVisibility(visible = selectedTab == 1) {
                        Column {
                            Text(
                                text = if (isHindi) "आपका नाम (Full Name)" else "Your Name",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = NaturalTextPrimary
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            OutlinedTextField(
                                value = fullName,
                                onValueChange = {
                                    fullName = it
                                    errorMessage = null
                                },
                                placeholder = { Text(if (isHindi) "माता/पिता का नाम दर्ज करें" else "e.g. Rahul Sharma") },
                                leadingIcon = {
                                    Icon(Icons.Default.Person, contentDescription = null, tint = NaturalGreen700)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("input_fullname"),
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = NaturalGreen700,
                                    focusedLabelColor = NaturalGreen700
                                )
                            )
                            Spacer(modifier = Modifier.height(14.dp))
                        }
                    }

                    // Email Field
                    Text(
                        text = if (isHindi) "ईमेल पता (Email Address)" else "Email Address",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = NaturalTextPrimary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            errorMessage = null
                        },
                        placeholder = { Text("parent.guardian@gmail.com") },
                        leadingIcon = {
                            Icon(Icons.Default.Email, contentDescription = null, tint = NaturalGreen700)
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_email"),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NaturalGreen700,
                            focusedLabelColor = NaturalGreen700
                        )
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Password Field
                    Text(
                        text = if (isHindi) "पासवर्ड (Password)" else "Password",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = NaturalTextPrimary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            errorMessage = null
                        },
                        placeholder = { Text("••••••••") },
                        leadingIcon = {
                            Icon(Icons.Default.Lock, contentDescription = null, tint = NaturalGreen700)
                        },
                        trailingIcon = {
                            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                                Icon(
                                    if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = "Toggle password visibility",
                                    tint = NaturalTextSecondary
                                )
                            }
                        },
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = if (selectedTab == 1) ImeAction.Next else ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_password"),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NaturalGreen700,
                            focusedLabelColor = NaturalGreen700
                        )
                    )

                    // Confirm Password (in Sign Up)
                    AnimatedVisibility(visible = selectedTab == 1) {
                        Column {
                            Spacer(modifier = Modifier.height(14.dp))
                            Text(
                                text = if (isHindi) "पासवर्ड की पुष्टि करें" else "Confirm Password",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = NaturalTextPrimary
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            OutlinedTextField(
                                value = confirmPassword,
                                onValueChange = {
                                    confirmPassword = it
                                    errorMessage = null
                                },
                                placeholder = { Text("••••••••") },
                                leadingIcon = {
                                    Icon(Icons.Default.Lock, contentDescription = null, tint = NaturalGreen700)
                                },
                                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Password,
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("input_confirm_password"),
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = NaturalGreen700,
                                    focusedLabelColor = NaturalGreen700
                                )
                            )
                        }
                    }

                    // Remember Me & Forgot Password
                    if (selectedTab == 0) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable { rememberMe = !rememberMe }
                            ) {
                                Checkbox(
                                    checked = rememberMe,
                                    onCheckedChange = { rememberMe = it },
                                    colors = CheckboxDefaults.colors(checkedColor = NaturalGreen700)
                                )
                                Text(
                                    text = if (isHindi) "लॉगिन याद रखें" else "Remember me",
                                    fontSize = 12.sp,
                                    color = NaturalTextSecondary
                                )
                            }

                            TextButton(onClick = {
                                forgotEmail = email
                                forgotSentSuccess = false
                                showForgotPasswordDialog = true
                            }) {
                                Text(
                                    text = if (isHindi) "पासवर्ड भूल गए?" else "Forgot password?",
                                    fontSize = 12.sp,
                                    color = NaturalGreen700,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    // Error message if any
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

                    Spacer(modifier = Modifier.height(18.dp))

                    // Primary Submit Button
                    Button(
                        onClick = {
                            if (selectedTab == 0) {
                                if (email.isBlank() || password.isBlank()) {
                                    errorMessage = if (isHindi) "कृपया ईमेल और पासवर्ड दर्ज करें।" else "Please enter email and password."
                                } else {
                                    val ok = onLogin(email, password, rememberMe)
                                    if (!ok) {
                                        errorMessage = if (isHindi) "अमान्य क्रेडेंशियल। पुनः प्रयास करें।" else "Invalid credentials. Please try again."
                                    }
                                }
                            } else {
                                if (fullName.isBlank()) {
                                    errorMessage = if (isHindi) "कृपया अपना नाम दर्ज करें।" else "Please enter your full name."
                                } else if (email.isBlank() || password.length < 4) {
                                    errorMessage = if (isHindi) "पासवर्ड कम से कम 4 अक्षरों का होना चाहिए।" else "Password must be at least 4 characters."
                                } else if (password != confirmPassword) {
                                    errorMessage = if (isHindi) "पासवर्ड मेल नहीं खाते।" else "Passwords do not match."
                                } else {
                                    val ok = onRegister(fullName, email, password)
                                    if (!ok) {
                                        errorMessage = if (isHindi) "पंजीकरण में त्रुटि हुई।" else "Error creating account."
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("submit_auth_button"),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = NaturalGreen700)
                    ) {
                        Text(
                            text = if (selectedTab == 0) {
                                if (isHindi) "लॉग इन करें (Sign In)" else "Sign In to ParentGuard"
                            } else {
                                if (isHindi) "खाता बनाएं (Create Account)" else "Create Free Account"
                            },
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Quick One-Tap Test / Demo Login
            Button(
                onClick = {
                    onLogin("musahidraza78600@gmail.com", "123456", true)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .testTag("quick_demo_login_button"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = NaturalGreen100)
            ) {
                Icon(
                    Icons.Default.Shield,
                    contentDescription = null,
                    tint = NaturalGreen700,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isHindi) "⚡ त्वरित पैरेंट लॉगिन (musahidraza78600@gmail.com)" else "⚡ Fast Demo Login (musahidraza78600@gmail.com)",
                    color = NaturalGreen900,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Switch to Child Device Mode
            TextButton(
                onClick = onSwitchToChildMode,
                modifier = Modifier.testTag("switch_to_child_mode_from_auth")
            ) {
                Icon(Icons.Default.ChildCare, contentDescription = null, tint = NaturalTextSecondary, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (isHindi) "यह बच्चे का फोन है? यहाँ टैप करें" else "Setting up child's phone? Tap here",
                    fontSize = 12.sp,
                    color = NaturalTextSecondary,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
