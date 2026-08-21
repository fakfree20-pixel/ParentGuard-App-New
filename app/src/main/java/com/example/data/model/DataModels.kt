package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "child_profiles")
data class ChildProfile(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val age: Int,
    val avatarIndex: Int = 0,
    val deviceModel: String = "Infinix X6823C",
    val batteryPercent: Int = 86,
    val isDeviceOnline: Boolean = true,
    val blockAllApps: Boolean = false,
    val locationAddress: String = "Green Park Avenue, Block 4",
    val locationCoordinates: String = "28.6139° N, 77.2090° E",
    val geofenceStatus: String = "Inside School Safe Zone",
    val weekdayLimitMinutes: Int = 120, // 2 hours
    val weekendLimitMinutes: Int = 180, // 3 hours
    val isLocked: Boolean = false,
    val lockReason: String = "",
    val lockUntilTimestamp: Long = 0L,
    val bedtimeEnabled: Boolean = true,
    val bedtimeStartHour: Int = 21, // 9:00 PM
    val bedtimeStartMinute: Int = 0,
    val bedtimeEndHour: Int = 7, // 7:00 AM
    val bedtimeEndMinute: Int = 0,
    val schoolModeEnabled: Boolean = false,
    val schoolStartHour: Int = 8, // 8:00 AM
    val schoolEndHour: Int = 15, // 3:00 PM
    val webSafeSearch: Boolean = true,
    val webBlockAdult: Boolean = true,
    val webBlockGaming: Boolean = false,
    val webBlockSocial: Boolean = true,
    val bonusMinutesToday: Int = 0,
    val antiUninstallEnabled: Boolean = true, // Prevents child from uninstalling app
    val deviceAdminActive: Boolean = true, // Device Administrator lock
    val preventSettingsAccess: Boolean = true, // Lock Android Settings app
    val preventFactoryReset: Boolean = true, // Lock Factory Data Reset
    val createdTimestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "app_usage_rules")
data class AppUsageRule(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val childId: Long,
    val packageName: String,
    val appName: String,
    val category: String, // GAMES, SOCIAL, EDUCATION, ENTERTAINMENT, PRODUCTIVITY, UTILITY
    val isBlocked: Boolean = false,
    val isAlwaysAllowed: Boolean = false,
    val dailyLimitMinutes: Int = 0, // 0 = unlimited/inherits child limit
    val usageTodayMinutes: Int = 0,
    val weeklyUsageMinutes: String = "30,45,20,50,40,65,70" // Mon to Sun
)

@Entity(tableName = "reward_tasks")
data class ScreenRewardTask(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val childId: Long,
    val title: String,
    val titleHindi: String = "",
    val rewardMinutes: Int = 15,
    val isCompleted: Boolean = false,
    val isApproved: Boolean = false,
    val completedDate: String = ""
)

@Entity(tableName = "activity_logs")
data class ActivityLogItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val childId: Long,
    val timestamp: Long = System.currentTimeMillis(),
    val type: String, // LIMIT_ALERT, APP_BLOCKED, INSTANT_LOCK, BONUS_TIME, BEDTIME_LOCK, REQUEST_TIME, WEB_FILTER, GEOFENCE, LIVE_MONITOR, NOTIFICATION, CHAT_ALERT, CALL_LOG, ANTI_UNINSTALL
    val title: String,
    val titleHindi: String = "",
    val description: String,
    val descriptionHindi: String = ""
)

@Entity(tableName = "web_filter_rules")
data class WebFilterRule(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val childId: Long,
    val domain: String,
    val isBlocked: Boolean = true,
    val category: String = "Custom"
)

@Entity(tableName = "geofence_zones")
data class GeofenceZone(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val childId: Long,
    val name: String,
    val nameHindi: String = "",
    val address: String,
    val radiusMeters: Int = 200,
    val isSafeZone: Boolean = true
)

@Entity(tableName = "app_notifications")
data class AppNotificationItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val childId: Long,
    val appName: String, // WhatsApp, Instagram, YouTube, Messages, Snapchat
    val packageName: String,
    val senderName: String,
    val messageContent: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isSuspiciousKeyword: Boolean = false,
    val category: String = "CHAT", // CHAT, SOCIAL, SYSTEM, PROMO
    val isRead: Boolean = false
)

@Entity(tableName = "whatsapp_conversations")
data class WhatsAppConversation(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val childId: Long,
    val contactName: String,
    val phoneNumber: String = "",
    val lastMessage: String,
    val lastMessageTime: String,
    val unreadCount: Int = 0,
    val avatarColorIndex: Int = 0,
    val isFlaggedSuspicious: Boolean = false,
    val totalMessagesToday: Int = 12,
    val messagesJson: String = "" // formatted message history
)

@Entity(tableName = "call_logs")
data class CallLogItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val childId: Long,
    val contactName: String,
    val phoneNumber: String,
    val callType: String, // INCOMING, OUTGOING, MISSED, REJECTED
    val durationSeconds: Int = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val isSuspicious: Boolean = false,
    val hasRecording: Boolean = false
)

data class UserAccount(
    val email: String = "parent.guardian@gmail.com",
    val fullName: String = "Parent Guardian",
    val isLoggedIn: Boolean = true,
    val accountType: String = "Pro Premium",
    val masterBindingCode: String = "794 821 305",
    val boundDeviceCount: Int = 2
)

data class DevicePermissionItem(
    val id: String,
    val title: String,
    val titleHindi: String,
    val description: String,
    val descriptionHindi: String,
    val iconName: String,
    val isGranted: Boolean,
    val isRequired: Boolean = true
)

