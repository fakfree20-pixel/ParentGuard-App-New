package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.ParentalRepository
import com.example.data.model.ActivityLogItem
import com.example.data.model.AppNotificationItem
import com.example.data.model.AppUsageRule
import com.example.data.model.CallLogItem
import com.example.data.model.ChildProfile
import com.example.data.model.DevicePermissionItem
import com.example.data.model.GeofenceZone
import com.example.data.model.ScreenRewardTask
import com.example.data.model.UserAccount
import com.example.data.model.WebFilterRule
import com.example.data.model.WhatsAppConversation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

enum class AppMode {
    WELCOME_MODE_SELECTION,
    PARENT_AUTH,
    PARENT_MAIN,
    CHILD_BINDING,
    CHILD_PERMISSIONS,
    CHILD_PROTECTED
}

class ParentalControlViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ParentalRepository = ParentalRepository(AppDatabase.getDatabase(application).parentalControlDao())
    private val prefs = application.getSharedPreferences("parent_guard_prefs", android.content.Context.MODE_PRIVATE)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.ensureDataPopulated()
        }
    }

    // User Authentication State (ParentGuard Gmail & 10-Digit Code)
    private val _currentUser = MutableStateFlow<UserAccount?>(
        UserAccount(
            email = prefs.getString("user_email", "musahidraza78600@gmail.com") ?: "musahidraza78600@gmail.com",
            fullName = prefs.getString("user_name", "Parent Guardian") ?: "Parent Guardian",
            isLoggedIn = prefs.getBoolean("is_logged_in", true),
            accountType = "Pro Premium Lifetime",
            masterBindingCode = "9842 761 530", // 10-Digit Master Code
            inviteLink = "https://parentguard.app/download?code=9842761530",
            boundDeviceCount = 2
        )
    )
    val currentUser: StateFlow<UserAccount?> = _currentUser.asStateFlow()

    // Current App Mode Navigation
    private val _currentAppMode = MutableStateFlow(
        if (prefs.getBoolean("is_logged_in", true)) AppMode.PARENT_MAIN else AppMode.WELCOME_MODE_SELECTION
    )
    val currentAppMode: StateFlow<AppMode> = _currentAppMode.asStateFlow()

    fun setAppMode(mode: AppMode) {
        _currentAppMode.value = mode
    }

    // Google Sign-In with Gmail
    fun loginWithGoogle(gmail: String = "musahidraza78600@gmail.com", name: String = "Musahid Raza"): Boolean {
        val code = generateBindingCode()
        val cleanDigits = code.replace(" ", "")
        val user = UserAccount(
            email = gmail.trim().ifBlank { "parent.guardian@gmail.com" },
            fullName = name.trim().ifBlank { "Parent Guardian" },
            isLoggedIn = true,
            accountType = "Pro Premium Google Verified",
            masterBindingCode = code,
            inviteLink = "https://parentguard.app/download?code=$cleanDigits",
            boundDeviceCount = 2
        )
        _currentUser.value = user
        prefs.edit()
            .putString("user_email", user.email)
            .putString("user_name", user.fullName)
            .putBoolean("is_logged_in", true)
            .apply()
        _currentAppMode.value = AppMode.PARENT_MAIN
        return true
    }

    // Auth actions
    fun login(email: String, pass: String, rememberMe: Boolean = true): Boolean {
        if (email.isNotBlank() && pass.length >= 4) {
            val code = generateBindingCode()
            val cleanDigits = code.replace(" ", "")
            val user = UserAccount(
                email = email.trim(),
                fullName = email.substringBefore("@").replace(".", " ").capitalizeWords(),
                isLoggedIn = true,
                accountType = "Pro Premium Lifetime",
                masterBindingCode = code,
                inviteLink = "https://parentguard.app/download?code=$cleanDigits",
                boundDeviceCount = 2
            )
            _currentUser.value = user
            prefs.edit()
                .putString("user_email", user.email)
                .putString("user_name", user.fullName)
                .putBoolean("is_logged_in", true)
                .apply()
            _currentAppMode.value = AppMode.PARENT_MAIN
            return true
        }
        return false
    }

    fun register(name: String, email: String, pass: String): Boolean {
        if (name.isNotBlank() && email.isNotBlank() && pass.length >= 4) {
            val code = generateBindingCode()
            val cleanDigits = code.replace(" ", "")
            val user = UserAccount(
                email = email.trim(),
                fullName = name.trim(),
                isLoggedIn = true,
                accountType = "Pro Premium Lifetime",
                masterBindingCode = code,
                inviteLink = "https://parentguard.app/download?code=$cleanDigits",
                boundDeviceCount = 0
            )
            _currentUser.value = user
            prefs.edit()
                .putString("user_email", user.email)
                .putString("user_name", user.fullName)
                .putBoolean("is_logged_in", true)
                .apply()
            _currentAppMode.value = AppMode.PARENT_MAIN
            return true
        }
        return false
    }

    fun logout() {
        _currentUser.value = null
        prefs.edit().putBoolean("is_logged_in", false).apply()
        _currentAppMode.value = AppMode.WELCOME_MODE_SELECTION
    }

    // 10-Digit Code Generation (e.g. 9842 761 530)
    private fun generateBindingCode(): String {
        val part1 = (1000..9999).random() // 4 digits
        val part2 = (100..999).random()   // 3 digits
        val part3 = (100..999).random()   // 3 digits
        return "$part1 $part2 $part3"
    }

    fun getInviteLink(): String {
        val code = _currentUser.value?.masterBindingCode?.replace(" ", "") ?: "9842761530"
        return "https://parentguard.app/download?code=$code"
    }

    fun getShareableInviteText(isHindi: Boolean): String {
        val user = _currentUser.value
        val code = user?.masterBindingCode ?: "9842 761 530"
        val link = getInviteLink()
        val email = user?.email ?: "musahidraza78600@gmail.com"

        return if (isHindi) {
            "👨‍👩‍👧‍👦 *ParentGuard पेरेंटल कंट्रोल इन्वाइट*\n\nनमस्ते! आपके अभिभावक ($email) ने आपको ParentGuard से जुड़ने के लिए आमंत्रित किया है।\n\n📲 *1. ऐप डाउनलोड लिंक:* $link\n🔑 *2. आपका 10-अंकीय बाइंडिंग कोड:* *$code*\n\n*सेटअप कैसे करें:*\n1. ऊपर दिए लिंक से ऐप डाउनलोड करके इंस्टॉल करें।\n2. 'Child Device / बच्चे का फ़ोन' विकल्प चुनें।\n3. यह 10-अंकीय कोड डालें और सुरक्षा सक्रिय करें।"
        } else {
            "👨‍👩‍👧‍👦 *ParentGuard Parental Control Invite*\n\nHello! Your parent ($email) has invited you to connect via ParentGuard.\n\n📲 *1. App Download Link:* $link\n🔑 *2. Your 10-Digit Binding Code:* *$code*\n\n*Setup Instructions:*\n1. Download and install the app from the link above.\n2. Select 'Child's Phone' mode.\n3. Enter this 10-digit code to enable parental safety."
        }
    }

    private fun String.capitalizeWords(): String = split(" ").joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } }

    // Child Mode Setup & Permissions Checklist
    private val _devicePermissions = MutableStateFlow(
        listOf(
            DevicePermissionItem(
                id = "accessibility",
                title = "Accessibility Service",
                titleHindi = "एक्सेसिबिलिटी सेवा",
                description = "Required to monitor app usage, screen time limits, and content filter in real-time.",
                descriptionHindi = "ऐप उपयोग, स्क्रीन समय सीमा और सामग्री फ़िल्टर की वास्तविक समय निगरानी के लिए आवश्यक।",
                iconName = "accessibility",
                isGranted = true,
                isRequired = true
            ),
            DevicePermissionItem(
                id = "usage_access",
                title = "Usage Data Access",
                titleHindi = "उपयोग डेटा एक्सेस",
                description = "Allows tracking daily application usage hours, statistics, and launch frequency.",
                descriptionHindi = "दैनिक ऐप उपयोग के घंटे, आंकड़े और ऐप खोलने की आवृत्ति ट्रैक करने की अनुमति देता है।",
                iconName = "data_usage",
                isGranted = true,
                isRequired = true
            ),
            DevicePermissionItem(
                id = "notification_listener",
                title = "Notification Listener",
                titleHindi = "नोटिफिकेशन लिसनर",
                description = "Synchronizes suspicious chat messages, social alerts, and spam calls to parent device.",
                descriptionHindi = "संदेहास्पद चैट संदेश, सोशल अलर्ट और स्पैम कॉल को माता-पिता के फ़ोन पर सिंक करता है।",
                iconName = "notifications",
                isGranted = true,
                isRequired = true
            ),
            DevicePermissionItem(
                id = "device_admin",
                title = "Anti-Uninstall Device Admin",
                titleHindi = "एंटी-अनइंस्टॉल डिवाइस एडमिन",
                description = "Prevents child from deleting ParentGuard without entering parent security PIN.",
                descriptionHindi = "माता-पिता के सुरक्षा पिन के बिना बच्चे को ऐप हटाने से रोकता है।",
                iconName = "security",
                isGranted = true,
                isRequired = true
            ),
            DevicePermissionItem(
                id = "location",
                title = "Live Location & GPS",
                titleHindi = "लाइव लोकेशन और जीपीएस",
                description = "Enables geofencing safe zones and live tracking of child's physical coordinates.",
                descriptionHindi = "जियोफ़ेंस सुरक्षित क्षेत्र और बच्चे के वास्तविक स्थान की लाइव ट्रैकिंग सक्षम करता है।",
                iconName = "location_on",
                isGranted = true,
                isRequired = false
            ),
            DevicePermissionItem(
                id = "battery_optimization",
                title = "Ignore Battery Optimization",
                titleHindi = "बैटरी ऑप्टिमाइज़ेशन अनदेखा करें",
                description = "Keeps background safety protection continuously running without being killed by Android OS.",
                descriptionHindi = "एंड्रॉइड ओएस द्वारा बंद किए बिना बैकग्राउंड सुरक्षा को लगातार चालू रखता है।",
                iconName = "battery_charging_full",
                isGranted = true,
                isRequired = false
            )
        )
    )
    val devicePermissions: StateFlow<List<DevicePermissionItem>> = _devicePermissions.asStateFlow()

    fun togglePermission(permissionId: String) {
        _devicePermissions.value = _devicePermissions.value.map {
            if (it.id == permissionId) it.copy(isGranted = !it.isGranted) else it
        }
    }

    fun bindChildWithCode(code: String, childName: String = "My Kid's Device", childAge: Int = 10): Boolean {
        // Any 9 digit code is accepted or matches master
        val cleanCode = code.replace(" ", "").replace("-", "")
        if (cleanCode.length >= 6) {
            viewModelScope.launch {
                val newProfile = ChildProfile(
                    name = childName.ifBlank { "Child Device" },
                    age = childAge,
                    avatarIndex = (0..5).random(),
                    deviceModel = "Infinix X6823C",
                    weekdayLimitMinutes = 120,
                    weekendLimitMinutes = 180
                )
                val newId = repository.insertChildProfile(newProfile)
                _selectedChildId.value = newId
            }
            _currentAppMode.value = AppMode.CHILD_PERMISSIONS
            return true
        }
        return false
    }

    // Language setting ("hi" = Hindi, "en" = English)
    private val _currentLanguage = MutableStateFlow("hi")
    val currentLanguage: StateFlow<String> = _currentLanguage.asStateFlow()

    fun toggleLanguage() {
        _currentLanguage.value = if (_currentLanguage.value == "hi") "en" else "hi"
    }

    // Active Child selection
    private val _selectedChildId = MutableStateFlow<Long?>(null)
    val selectedChildId: StateFlow<Long?> = _selectedChildId.asStateFlow()

    val allChildProfiles: StateFlow<List<ChildProfile>> = repository.allChildProfiles
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val activeChildProfile: StateFlow<ChildProfile?> = combine(allChildProfiles, _selectedChildId) { profiles, selectedId ->
        if (profiles.isEmpty()) {
            null
        } else {
            val child = profiles.find { it.id == selectedId } ?: profiles.first()
            if (_selectedChildId.value != child.id) {
                _selectedChildId.value = child.id
            }
            child
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun selectChild(childId: Long) {
        _selectedChildId.value = childId
    }

    // Reactive data based on selected child
    val appUsageRules: StateFlow<List<AppUsageRule>> = _selectedChildId.flatMapLatest { id ->
        if (id != null) repository.getAppUsageRules(id) else flowOf(emptyList())
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val rewardTasks: StateFlow<List<ScreenRewardTask>> = _selectedChildId.flatMapLatest { id ->
        if (id != null) repository.getRewardTasks(id) else flowOf(emptyList())
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val activityLogs: StateFlow<List<ActivityLogItem>> = _selectedChildId.flatMapLatest { id ->
        if (id != null) repository.getActivityLogs(id) else flowOf(emptyList())
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val webFilterRules: StateFlow<List<WebFilterRule>> = _selectedChildId.flatMapLatest { id ->
        if (id != null) repository.getWebFilterRules(id) else flowOf(emptyList())
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val geofenceZones: StateFlow<List<GeofenceZone>> = _selectedChildId.flatMapLatest { id ->
        if (id != null) repository.getGeofenceZones(id) else flowOf(emptyList())
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val appNotifications: StateFlow<List<AppNotificationItem>> = _selectedChildId.flatMapLatest { id ->
        if (id != null) repository.getAppNotifications(id) else flowOf(emptyList())
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val whatsAppConversations: StateFlow<List<WhatsAppConversation>> = _selectedChildId.flatMapLatest { id ->
        if (id != null) repository.getWhatsAppConversations(id) else flowOf(emptyList())
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val callLogs: StateFlow<List<CallLogItem>> = _selectedChildId.flatMapLatest { id ->
        if (id != null) repository.getCallLogs(id) else flowOf(emptyList())
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Security & PIN
    private val _masterPin = MutableStateFlow("1234")
    val masterPin: StateFlow<String> = _masterPin.asStateFlow()

    private val _isParentPinUnlocked = MutableStateFlow(true)
    val isParentPinUnlocked: StateFlow<Boolean> = _isParentPinUnlocked.asStateFlow()

    fun verifyPin(pin: String): Boolean {
        val success = pin == _masterPin.value
        if (success) {
            _isParentPinUnlocked.value = true
        }
        return success
    }

    fun changePin(newPin: String) {
        if (newPin.length == 4 && newPin.all { it.isDigit() }) {
            _masterPin.value = newPin
        }
    }

    // App Mode: false = Parent Mode, true = Child Device Mode
    private val _isChildModeActive = MutableStateFlow(false)
    val isChildModeActive: StateFlow<Boolean> = _isChildModeActive.asStateFlow()

    fun enterChildMode() {
        _isChildModeActive.value = true
    }

    fun exitChildMode() {
        _isChildModeActive.value = false
    }

    fun setChildMode(active: Boolean) {
        _isChildModeActive.value = active
    }

    // Real-time Background Usage Simulation
    private var simulationJob: Job? = null

    init {
        startSimulation()
    }

    private fun startSimulation() {
        simulationJob?.cancel()
        simulationJob = viewModelScope.launch {
            while (isActive) {
                delay(30000) // update every 30 seconds
                val child = activeChildProfile.value
                val apps = appUsageRules.value
                if (child != null && !child.isLocked && !child.blockAllApps && apps.isNotEmpty()) {
                    val activeApp = apps.filter { !it.isBlocked }.shuffled().firstOrNull()
                    if (activeApp != null) {
                        repository.addAppUsageMinutes(activeApp.id, 1)
                    }
                }
            }
        }
    }

    // User Actions
    fun setInstantLock(isLocked: Boolean, reason: String = "Parent Lock", durationMinutes: Int = 0) {
        val childId = _selectedChildId.value ?: return
        viewModelScope.launch {
            repository.setInstantLock(childId, isLocked, reason, durationMinutes)
        }
    }

    fun toggleBlockAllApps(blockAll: Boolean) {
        val childId = _selectedChildId.value ?: return
        viewModelScope.launch {
            repository.setBlockAllApps(childId, blockAll)
        }
    }

    fun addBonusMinutes(minutes: Int, reason: String = "Bonus Screen Time") {
        val childId = _selectedChildId.value ?: return
        viewModelScope.launch {
            repository.addBonusMinutes(childId, minutes, reason)
        }
    }

    fun toggleAppBlock(rule: AppUsageRule) {
        val childId = _selectedChildId.value ?: return
        viewModelScope.launch {
            repository.toggleAppBlock(rule.id, childId, rule.appName, !rule.isBlocked)
        }
    }

    fun setAppLimit(ruleId: Long, limitMinutes: Int) {
        viewModelScope.launch {
            repository.setAppLimit(ruleId, limitMinutes)
        }
    }

    fun toggleAlwaysAllowed(ruleId: Long, isAlwaysAllowed: Boolean) {
        viewModelScope.launch {
            repository.toggleAlwaysAllowed(ruleId, isAlwaysAllowed)
        }
    }

    fun updateChildSettings(profile: ChildProfile) {
        viewModelScope.launch {
            repository.updateChildProfile(profile)
        }
    }

    fun addChildProfile(name: String, age: Int, avatarIndex: Int = 0, dailyLimitMinutes: Int = 120, deviceModel: String = "Infinix X6823C") {
        viewModelScope.launch {
            val newProfile = ChildProfile(
                name = name,
                age = age,
                avatarIndex = avatarIndex,
                deviceModel = deviceModel,
                weekdayLimitMinutes = dailyLimitMinutes,
                weekendLimitMinutes = (dailyLimitMinutes * 1.5).toInt()
            )
            val newId = repository.insertChildProfile(newProfile)
            _selectedChildId.value = newId
        }
    }

    fun addRewardTask(title: String, titleHindi: String, rewardMinutes: Int) {
        val childId = _selectedChildId.value ?: return
        viewModelScope.launch {
            repository.insertRewardTask(
                ScreenRewardTask(
                    childId = childId,
                    title = title,
                    titleHindi = titleHindi,
                    rewardMinutes = rewardMinutes
                )
            )
        }
    }

    fun completeTaskByChild(task: ScreenRewardTask) {
        viewModelScope.launch {
            repository.completeRewardTaskByChild(task)
        }
    }

    fun approveTaskReward(task: ScreenRewardTask) {
        viewModelScope.launch {
            repository.approveRewardTask(task)
        }
    }

    fun deleteTask(taskId: Long) {
        viewModelScope.launch {
            repository.deleteRewardTask(taskId)
        }
    }

    fun addWebFilter(domain: String, category: String) {
        val childId = _selectedChildId.value ?: return
        viewModelScope.launch {
            repository.insertWebFilterRule(
                WebFilterRule(
                    childId = childId,
                    domain = domain.trim().lowercase(),
                    isBlocked = true,
                    category = category
                )
            )
        }
    }

    fun removeWebFilter(ruleId: Long) {
        viewModelScope.launch {
            repository.deleteWebFilterRule(ruleId)
        }
    }

    fun addGeofenceZone(name: String, nameHindi: String, address: String, radius: Int) {
        val childId = _selectedChildId.value ?: return
        viewModelScope.launch {
            repository.insertGeofenceZone(
                GeofenceZone(
                    childId = childId,
                    name = name,
                    nameHindi = nameHindi,
                    address = address,
                    radiusMeters = radius,
                    isSafeZone = true
                )
            )
        }
    }

    fun deleteGeofenceZone(zoneId: Long) {
        viewModelScope.launch {
            repository.deleteGeofenceZone(zoneId)
        }
    }

    fun clearNotifications() {
        val childId = _selectedChildId.value ?: return
        viewModelScope.launch {
            repository.clearAppNotifications(childId)
        }
    }

    fun deleteWhatsAppChat(convoId: Long) {
        viewModelScope.launch {
            repository.deleteWhatsAppConversation(convoId)
        }
    }

    fun logLiveMonitoringEvent(type: String, title: String, titleHi: String, desc: String, descHi: String) {
        val childId = _selectedChildId.value ?: return
        viewModelScope.launch {
            repository.addActivityLog(
                ActivityLogItem(
                    childId = childId,
                    type = type,
                    title = title,
                    titleHindi = titleHi,
                    description = desc,
                    descriptionHindi = descHi
                )
            )
        }
    }

    fun requestExtraTimeByChild(minutes: Int) {
        val child = activeChildProfile.value ?: return
        viewModelScope.launch {
            repository.addActivityLog(
                ActivityLogItem(
                    childId = child.id,
                    type = "REQUEST_TIME",
                    title = "Child requested +$minutes mins extra time",
                    titleHindi = "बच्चे ने +$minutes मिनट अतिरिक्त समय की मांग की",
                    description = "${child.name} is asking for extra screen time",
                    descriptionHindi = "${child.name} ने स्क्रीन समय बढ़ाने का अनुरोध भेजा है"
                )
            )
        }
    }

    fun toggleAntiUninstallProtection(enabled: Boolean, preventSettings: Boolean = true, preventReset: Boolean = true) {
        val childId = _selectedChildId.value ?: return
        viewModelScope.launch {
            repository.setAntiUninstallProtection(childId, enabled, preventSettings, preventReset)
        }
    }

    fun clearCallLogs() {
        val childId = _selectedChildId.value ?: return
        viewModelScope.launch {
            repository.clearCallLogs(childId)
        }
    }

    fun deleteCallLog(callLogId: Long) {
        viewModelScope.launch {
            repository.deleteCallLog(callLogId)
        }
    }

    // App Hide / Stealth Mode & Remote App Launcher
    private val _activeRemoteApp = MutableStateFlow<Pair<String, String>?>(null) // Pair(packageName, appName)
    val activeRemoteApp: StateFlow<Pair<String, String>?> = _activeRemoteApp.asStateFlow()

    private val _remoteNavLastEvent = MutableStateFlow<String?>(null)
    val remoteNavLastEvent: StateFlow<String?> = _remoteNavLastEvent.asStateFlow()

    fun toggleAppHidden(isHidden: Boolean) {
        val childId = _selectedChildId.value ?: return
        viewModelScope.launch {
            repository.setAppHidden(childId, isHidden)
        }
    }

    fun launchRemoteApp(packageName: String, appName: String) {
        val childId = _selectedChildId.value ?: return
        _activeRemoteApp.value = Pair(packageName, appName)
        viewModelScope.launch {
            repository.setRemoteActiveApp(childId, packageName, appName)
        }
    }

    fun stopRemoteApp() {
        val childId = _selectedChildId.value ?: return
        val current = _activeRemoteApp.value
        _activeRemoteApp.value = null
        viewModelScope.launch {
            repository.setRemoteActiveApp(childId, "", "")
            if (current != null) {
                repository.addActivityLog(
                    ActivityLogItem(
                        childId = childId,
                        type = "REMOTE_LAUNCH",
                        title = "Closed Remote ${current.second}",
                        titleHindi = "रिमोट ऐप ${current.second} बंद किया",
                        description = "Parent closed remote app session from parent dashboard",
                        descriptionHindi = "माता-पिता ने रिमोट ऐप सत्र समाप्त किया"
                    )
                )
            }
        }
    }

    fun sendRemoteNavigationCommand(command: String) {
        _remoteNavLastEvent.value = command
        val childId = _selectedChildId.value ?: return
        val appName = _activeRemoteApp.value?.second ?: "Child App"
        viewModelScope.launch {
            val (title, titleHi) = when (command) {
                "BACK" -> "Remote Back Pressed" to "रिमोट बैक बटन दबाया"
                "HOME" -> "Remote Home Pressed" to "रिमोट होम बटन दबाया"
                "RECENTS" -> "Remote Recents Opened" to "रिमोट रीसेंट्स खोला"
                "SCROLL_UP" -> "Remote Scrolled Up" to "रिमोट ऊपर स्क्रॉल किया"
                "SCROLL_DOWN" -> "Remote Scrolled Down" to "रिमोट नीचे स्क्रॉल किया"
                "VOLUME_UP" -> "Remote Volume Increased" to "रिमोट वॉल्यूम बढ़ाया"
                "VOLUME_DOWN" -> "Remote Volume Decreased" to "रिमोट वॉल्यूम घटाया"
                else -> "Remote Command: $command" to "रिमोट कमांड: $command"
            }
            repository.addActivityLog(
                ActivityLogItem(
                    childId = childId,
                    type = "REMOTE_LAUNCH",
                    title = "$title ($appName)",
                    titleHindi = "$titleHi ($appName)",
                    description = "Parent executed remote gesture $command on child device",
                    descriptionHindi = "माता-पिता ने बच्चे के फोन पर रिमोट कमांड $command भेजी"
                )
            )
        }
    }

    fun clearLogs() {
        val childId = _selectedChildId.value ?: return
        viewModelScope.launch {
            repository.clearLogs(childId)
        }
    }
}
