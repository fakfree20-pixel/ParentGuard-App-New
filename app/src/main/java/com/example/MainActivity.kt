package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.animation.Crossfade
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.ChildProfile
import com.example.ui.components.AddChildDialog
import com.example.ui.components.ChildAvatarCircle
import com.example.ui.components.PinKeypadDialog
import com.example.ui.screens.ActivityLogScreen
import com.example.ui.screens.AppsControlScreen
import com.example.ui.screens.AuthScreen
import com.example.ui.screens.ChildDevicePairingScreen
import com.example.ui.screens.ChildModeScreen
import com.example.ui.screens.ChildPermissionsScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.ParentDeviceBindingDialog
import com.example.ui.screens.RewardsScreen
import com.example.ui.screens.ScheduleRulesScreen
import com.example.ui.screens.WelcomeModeScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.NaturalBg
import com.example.ui.theme.NaturalBorder
import com.example.ui.theme.NaturalCardBg
import com.example.ui.theme.NaturalGreen100
import com.example.ui.theme.NaturalGreen700
import com.example.ui.theme.NaturalGreen900
import com.example.ui.theme.NaturalNavBg
import com.example.ui.theme.NaturalSurface
import com.example.ui.theme.NaturalSurfaceVariant
import com.example.ui.theme.NaturalTextPrimary
import com.example.ui.theme.NaturalTextSecondary
import com.example.ui.theme.Terracotta700
import com.example.ui.viewmodel.AppMode
import com.example.ui.viewmodel.ParentalControlViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: ParentalControlViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                ParentGuardMainApp(viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParentGuardMainApp(viewModel: ParentalControlViewModel) {
    val appMode by viewModel.currentAppMode.collectAsStateWithLifecycle()
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
    val activeChild by viewModel.activeChildProfile.collectAsStateWithLifecycle()
    val allChildren by viewModel.allChildProfiles.collectAsStateWithLifecycle()
    val apps by viewModel.appUsageRules.collectAsStateWithLifecycle()
    val tasks by viewModel.rewardTasks.collectAsStateWithLifecycle()
    val logs by viewModel.activityLogs.collectAsStateWithLifecycle()
    val webRules by viewModel.webFilterRules.collectAsStateWithLifecycle()
    val geofenceZones by viewModel.geofenceZones.collectAsStateWithLifecycle()
    val whatsAppConversations by viewModel.whatsAppConversations.collectAsStateWithLifecycle()
    val appNotifications by viewModel.appNotifications.collectAsStateWithLifecycle()
    val callLogs by viewModel.callLogs.collectAsStateWithLifecycle()
    val currentLang by viewModel.currentLanguage.collectAsStateWithLifecycle()
    val devicePermissions by viewModel.devicePermissions.collectAsStateWithLifecycle()

    val isHindi = currentLang == "hi"

    var selectedNavTab by remember { mutableIntStateOf(0) }
    var showAddChildDialog by remember { mutableStateOf(false) }
    var showChildDropdown by remember { mutableStateOf(false) }
    var showParentAccountMenu by remember { mutableStateOf(false) }
    var showParentBindingDialog by remember { mutableStateOf(false) }
    var showPinDialogForChildMode by remember { mutableStateOf(false) }

    // Dialog: Parent Binding QR & 9-digit code
    if (showParentBindingDialog) {
        ParentDeviceBindingDialog(
            bindingCode = currentUser?.masterBindingCode ?: "794 821 305",
            isHindi = isHindi,
            onDismiss = { showParentBindingDialog = false },
            onAddChildDirectly = { name, age ->
                viewModel.addChildProfile(name, age, (0..5).random(), 120)
            }
        )
    }

    if (showAddChildDialog) {
        AddChildDialog(
            onAddChild = { name, age, avatarIndex, dailyLimit ->
                viewModel.addChildProfile(name, age, avatarIndex, dailyLimit)
                showAddChildDialog = false
            },
            onDismiss = { showAddChildDialog = false },
            isHindi = isHindi
        )
    }

    if (showPinDialogForChildMode) {
        PinKeypadDialog(
            title = if (isHindi) "पैरेंट सुरक्षा पिन" else "Parent Security PIN",
            subtitle = if (isHindi) "पैरेंट डैशबोर्ड में जाने के लिए पिन दर्ज करें (डिफ़ॉल्ट: 1234)" else "Enter 4-digit PIN (Default: 1234)",
            onPinEntered = { pin ->
                val ok = viewModel.verifyPin(pin)
                if (ok) {
                    showPinDialogForChildMode = false
                    viewModel.setAppMode(AppMode.PARENT_MAIN)
                }
                ok
            },
            onDismiss = { showPinDialogForChildMode = false },
            isHindi = isHindi
        )
    }

    // Route based on AppMode
    when (appMode) {
        AppMode.WELCOME_MODE_SELECTION -> {
            WelcomeModeScreen(
                isHindi = isHindi,
                onToggleLanguage = { viewModel.toggleLanguage() },
                onSelectParentMode = {
                    if (currentUser?.isLoggedIn == true) {
                        viewModel.setAppMode(AppMode.PARENT_MAIN)
                    } else {
                        viewModel.setAppMode(AppMode.PARENT_AUTH)
                    }
                },
                onSelectChildMode = {
                    viewModel.setAppMode(AppMode.CHILD_BINDING)
                }
            )
            return
        }

        AppMode.PARENT_AUTH -> {
            AuthScreen(
                isHindi = isHindi,
                onToggleLanguage = { viewModel.toggleLanguage() },
                onLogin = { email, pass, rememberMe ->
                    viewModel.login(email, pass, rememberMe)
                },
                onRegister = { name, email, pass ->
                    viewModel.register(name, email, pass)
                },
                onBackToModeSelect = {
                    viewModel.setAppMode(AppMode.WELCOME_MODE_SELECTION)
                },
                onSwitchToChildMode = {
                    viewModel.setAppMode(AppMode.CHILD_BINDING)
                }
            )
            return
        }

        AppMode.CHILD_BINDING -> {
            ChildDevicePairingScreen(
                isHindi = isHindi,
                onToggleLanguage = { viewModel.toggleLanguage() },
                onPairWithCode = { code, name, age ->
                    viewModel.bindChildWithCode(code, name, age)
                },
                onBack = {
                    viewModel.setAppMode(AppMode.WELCOME_MODE_SELECTION)
                }
            )
            return
        }

        AppMode.CHILD_PERMISSIONS -> {
            ChildPermissionsScreen(
                permissions = devicePermissions,
                isHindi = isHindi,
                onTogglePermission = { id -> viewModel.togglePermission(id) },
                onCompleteSetup = {
                    viewModel.setAppMode(AppMode.CHILD_PROTECTED)
                }
            )
            return
        }

        AppMode.CHILD_PROTECTED -> {
            val child = activeChild ?: ChildProfile(name = "Child", age = 9)
            ChildModeScreen(
                child = child,
                apps = apps,
                tasks = tasks,
                isHindi = isHindi,
                onExitChildMode = { showPinDialogForChildMode = true },
                onRequestExtraTime = { mins -> viewModel.requestExtraTimeByChild(mins) },
                onCompleteTask = { task -> viewModel.completeTaskByChild(task) },
                onVerifyPin = { pin -> viewModel.verifyPin(pin) }
            )
            return
        }

        AppMode.PARENT_MAIN -> {
            // Parent Dashboard Scaffold
        }
    }

    if (activeChild == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(NaturalBg),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.user_profile_logo_1787259898289),
                    contentDescription = "ParentGuard Logo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(68.dp)
                        .clip(RoundedCornerShape(18.dp))
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = if (isHindi) "ParentGuard लोड हो रहा है..." else "Loading ParentGuard...",
                    fontWeight = FontWeight.Bold,
                    color = NaturalGreen700
                )
            }
        }
        return
    }

    val child = activeChild!!

    // Parent Mode Layout
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(NaturalBg),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { showChildDropdown = true }
                            .padding(horizontal = 4.dp, vertical = 4.dp)
                    ) {
                        ChildAvatarCircle(
                            avatarIndex = child.avatarIndex,
                            name = child.name,
                            size = 40.dp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = child.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp,
                                    color = NaturalTextPrimary
                                )
                                Icon(
                                    Icons.Default.ArrowDropDown,
                                    contentDescription = "Switch child",
                                    tint = NaturalTextSecondary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Text(
                                text = if (isHindi) "डिवाइस: ${child.deviceModel} • 🔋 ${child.batteryPercent}%" else "Device: ${child.deviceModel} • 🔋 ${child.batteryPercent}%",
                                fontSize = 11.sp,
                                color = NaturalTextSecondary
                            )
                        }

                        // Child Profile Dropdown Menu
                        DropdownMenu(
                            expanded = showChildDropdown,
                            onDismissRequest = { showChildDropdown = false }
                        ) {
                            allChildren.forEach { childProfile ->
                                DropdownMenuItem(
                                    text = {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            ChildAvatarCircle(
                                                avatarIndex = childProfile.avatarIndex,
                                                name = childProfile.name,
                                                size = 30.dp
                                            )
                                            Spacer(modifier = Modifier.width(10.dp))
                                            Column {
                                                Text(childProfile.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                                Text("${childProfile.deviceModel} • ${childProfile.age} yrs", fontSize = 11.sp, color = NaturalTextSecondary)
                                            }
                                        }
                                    },
                                    onClick = {
                                        viewModel.selectChild(childProfile.id)
                                        showChildDropdown = false
                                    }
                                )
                            }

                            Divider()

                            DropdownMenuItem(
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.QrCodeScanner, contentDescription = null, tint = NaturalGreen700, modifier = Modifier.size(20.dp))
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(if (isHindi) "+ बच्चे का फ़ोन बाइंड करें" else "+ Bind Child Device (QR/Code)", fontWeight = FontWeight.Bold, color = NaturalGreen700)
                                    }
                                },
                                onClick = {
                                    showChildDropdown = false
                                    showParentBindingDialog = true
                                }
                            )

                            DropdownMenuItem(
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.PersonAdd, contentDescription = null, tint = NaturalGreen700, modifier = Modifier.size(20.dp))
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(if (isHindi) "+ प्रोफ़ाइल जोड़ें" else "+ Add Profile Manually", color = NaturalTextPrimary)
                                    }
                                },
                                onClick = {
                                    showChildDropdown = false
                                    showAddChildDialog = true
                                }
                            )
                        }
                    }
                },
                actions = {
                    // FlashGet 9-Digit Binding Code Button
                    IconButton(
                        onClick = { showParentBindingDialog = true },
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(NaturalGreen100)
                            .testTag("parent_qr_binding_button")
                    ) {
                        Icon(
                            Icons.Default.QrCodeScanner,
                            contentDescription = "Bind Device",
                            tint = NaturalGreen700,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    // Language Switcher Toggle
                    IconButton(
                        onClick = { viewModel.toggleLanguage() },
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(NaturalSurfaceVariant)
                            .testTag("toggle_language_button")
                    ) {
                        Text(
                            text = if (isHindi) "EN" else "हिं",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = NaturalTextPrimary
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    // Parent Account / Profile Menu
                    Box {
                        IconButton(
                            onClick = { showParentAccountMenu = true },
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(NaturalSurfaceVariant)
                                .testTag("parent_account_menu_button")
                        ) {
                            Icon(
                                Icons.Default.AccountCircle,
                                contentDescription = "Account Settings",
                                tint = NaturalGreen700,
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        DropdownMenu(
                            expanded = showParentAccountMenu,
                            onDismissRequest = { showParentAccountMenu = false }
                        ) {
                            // User Info Header
                            DropdownMenuItem(
                                text = {
                                    Column {
                                        Text(
                                            text = currentUser?.fullName ?: "Parent Admin",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp,
                                            color = NaturalTextPrimary
                                        )
                                        Text(
                                            text = currentUser?.email ?: "parent.guardian@gmail.com",
                                            fontSize = 11.sp,
                                            color = NaturalTextSecondary
                                        )
                                        Text(
                                            text = "Plan: ${currentUser?.accountType ?: "FlashGet Pro"}",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = NaturalGreen700
                                        )
                                    }
                                },
                                onClick = {}
                            )

                            Divider()

                            DropdownMenuItem(
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.QrCodeScanner, contentDescription = null, tint = NaturalGreen700, modifier = Modifier.size(18.dp))
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(if (isHindi) "बाइंडिंग कोड & QR" else "Pairing Code & QR")
                                    }
                                },
                                onClick = {
                                    showParentAccountMenu = false
                                    showParentBindingDialog = true
                                }
                            )

                            DropdownMenuItem(
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.ChildCare, contentDescription = null, tint = NaturalTextPrimary, modifier = Modifier.size(18.dp))
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(if (isHindi) "बच्चे के मोड में जाएं" else "Switch to Kids Device")
                                    }
                                },
                                onClick = {
                                    showParentAccountMenu = false
                                    viewModel.setAppMode(AppMode.CHILD_PROTECTED)
                                }
                            )

                            Divider()

                            DropdownMenuItem(
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.Logout, contentDescription = null, tint = Terracotta700, modifier = Modifier.size(18.dp))
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(if (isHindi) "लॉग आउट करें (Logout)" else "Sign Out / Logout", color = Terracotta700, fontWeight = FontWeight.Bold)
                                    }
                                },
                                onClick = {
                                    showParentAccountMenu = false
                                    viewModel.logout()
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = NaturalBg
                )
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(NaturalNavBg)
                    .border(width = 1.dp, color = NaturalBorder)
            ) {
                NavigationBar(
                    containerColor = NaturalNavBg,
                    tonalElevation = 0.dp,
                    modifier = Modifier
                        .windowInsetsPadding(WindowInsets.navigationBars)
                        .testTag("parent_bottom_nav")
                ) {
                    val navItems = listOf(
                        Triple(0, Icons.Default.Dashboard, if (isHindi) "होम" else "Home"),
                        Triple(1, Icons.Default.Apps, if (isHindi) "ऐप्स" else "Apps"),
                        Triple(2, Icons.Default.Schedule, if (isHindi) "शेड्यूल" else "Schedule"),
                        Triple(3, Icons.Default.EmojiEvents, if (isHindi) "इनाम" else "Rewards"),
                        Triple(4, Icons.Default.History, if (isHindi) "रिपोर्ट्स" else "Reports")
                    )

                    navItems.forEach { (index, icon, label) ->
                        val isSelected = selectedNavTab == index
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { selectedNavTab = index },
                            icon = { Icon(icon, contentDescription = label) },
                            label = {
                                Text(
                                    text = label,
                                    fontSize = 10.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = NaturalGreen700,
                                selectedTextColor = NaturalGreen700,
                                indicatorColor = NaturalGreen100,
                                unselectedIconColor = NaturalTextSecondary,
                                unselectedTextColor = NaturalTextSecondary
                            ),
                            modifier = Modifier.testTag("nav_item_$index")
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Crossfade(
            targetState = selectedNavTab,
            label = "tab_crossfade",
            modifier = Modifier.padding(paddingValues)
        ) { tab ->
            when (tab) {
                0 -> DashboardScreen(
                    child = child,
                    allProfiles = allChildren,
                    apps = apps,
                    logs = logs,
                    geofenceZones = geofenceZones,
                    whatsAppConversations = whatsAppConversations,
                    appNotifications = appNotifications,
                    callLogs = callLogs,
                    isHindi = isHindi,
                    onSelectChild = { viewModel.selectChild(it) },
                    onInstantLockToggle = { isLock, reason, duration ->
                        viewModel.setInstantLock(isLock, reason, duration)
                    },
                    onToggleBlockAllApps = { viewModel.toggleBlockAllApps(it) },
                    onAddBonusMinutes = { mins, reason ->
                        viewModel.addBonusMinutes(mins, reason)
                    },
                    onAddGeofence = { name, address, radius ->
                        viewModel.addGeofenceZone(name, name, address, radius)
                    },
                    onDeleteGeofence = { zoneId ->
                        viewModel.deleteGeofenceZone(zoneId)
                    },
                    onClearNotifications = { viewModel.clearNotifications() },
                    onToggleAntiUninstall = { enabled, preventSettings, preventReset ->
                        viewModel.toggleAntiUninstallProtection(enabled, preventSettings, preventReset)
                    },
                    onDeleteCallLog = { callLogId ->
                        viewModel.deleteCallLog(callLogId)
                    },
                    onClearCallLogs = { viewModel.clearCallLogs() }
                )
                1 -> AppsControlScreen(
                    apps = apps,
                    isHindi = isHindi,
                    onToggleBlock = { app -> viewModel.toggleAppBlock(app) },
                    onSetAppLimit = { ruleId, limit -> viewModel.setAppLimit(ruleId, limit) },
                    onToggleAlwaysAllowed = { ruleId, isAllowed -> viewModel.toggleAlwaysAllowed(ruleId, isAllowed) }
                )
                2 -> ScheduleRulesScreen(
                    child = child,
                    webRules = webRules,
                    isHindi = isHindi,
                    onUpdateChild = { updated -> viewModel.updateChildSettings(updated) },
                    onAddWebRule = { domain, category -> viewModel.addWebFilter(domain, category) },
                    onDeleteWebRule = { ruleId -> viewModel.removeWebFilter(ruleId) }
                )
                3 -> RewardsScreen(
                    tasks = tasks,
                    childName = child.name,
                    bonusEarnedToday = child.bonusMinutesToday,
                    isHindi = isHindi,
                    onAddTask = { title, titleHi, mins -> viewModel.addRewardTask(title, titleHi, mins) },
                    onApproveTask = { task -> viewModel.approveTaskReward(task) },
                    onDeleteTask = { taskId -> viewModel.deleteTask(taskId) }
                )
                4 -> ActivityLogScreen(
                    logs = logs,
                    isHindi = isHindi,
                    onClearLogs = { viewModel.clearLogs() }
                )
            }
        }
    }
}
