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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import com.example.data.model.ChildProfile
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

data class SentNoticeHistoryItem(
    val title: String,
    val message: String,
    val time: String,
    val status: String,
    val hadChime: Boolean
)

@Composable
fun NoticeScreen(
    child: ChildProfile,
    isHindi: Boolean
) {
    var noticeTitle by remember { mutableStateOf("Dinner Time! 🍽️") }
    var noticeMessage by remember { mutableStateOf("Please put away your phone and join for family dinner.") }
    var playAudioChime by remember { mutableStateOf(true) }
    var sentSuccessMessage by remember { mutableStateOf<String?>(null) }

    val quickTemplates = listOf(
        "Dinner Time! 🍽️" to "Please put away your phone and join for dinner.",
        "Homework Reminder 📚" to "Time to complete your Math homework and review notes.",
        "Time for Bed! 🌙" to "Wind down for sleep. Screen time is finished for today.",
        "Call Papa / Mummy 📞" to "Please give us a call as soon as you see this message.",
        "Come Home Safely 🏡" to "Time to head back home safely after school / tuition."
    )

    val historyNotices = remember {
        mutableStateListOf(
            SentNoticeHistoryItem("Homework Reminder 📚", "Finish Chapter 4 Science exercise", "Today, 03:30 PM", "Seen on Screen", true),
            SentNoticeHistoryItem("Dinner Time! 🍽️", "Come down for family dinner", "Yesterday, 08:15 PM", "Seen on Screen", true),
            SentNoticeHistoryItem("Call Papa 📞", "Call papa when tuition finishes", "2 days ago", "Seen on Screen", false)
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
        // Composer Card
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
                                    .size(42.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color(0xFF0284C7).copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Campaign, contentDescription = null, tint = Color(0xFF0284C7), modifier = Modifier.size(24.dp))
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = if (isHindi) "अभिभावक नोटिस (Notice Broadcast)" else "Broadcast Parental Notice",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = NaturalTextPrimary
                                )
                                Text(
                                    text = if (isHindi) "${child.name} के फोन स्क्रीन पर तुरंत दिखेगा" else "Pops up instantly on ${child.name}'s phone",
                                    fontSize = 12.sp,
                                    color = NaturalTextSecondary
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Quick Templates Row
                    Text(
                        text = if (isHindi) "त्वरित नोटिस टेम्पलेट्स:" else "Quick Templates:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = NaturalTextSecondary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(quickTemplates) { (t, m) ->
                            OutlinedButton(
                                onClick = {
                                    noticeTitle = t
                                    noticeMessage = m
                                },
                                shape = RoundedCornerShape(10.dp),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(text = t, fontSize = 11.sp, color = NaturalTextPrimary)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Title input
                    OutlinedTextField(
                        value = noticeTitle,
                        onValueChange = { noticeTitle = it },
                        label = { Text(if (isHindi) "नोटिस का शीर्षक" else "Notice Title") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = NaturalSurfaceVariant,
                            unfocusedContainerColor = NaturalSurfaceVariant,
                            focusedBorderColor = NaturalGreen700,
                            unfocusedBorderColor = NaturalBorder
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Message input
                    OutlinedTextField(
                        value = noticeMessage,
                        onValueChange = { noticeMessage = it },
                        label = { Text(if (isHindi) "संदेश विवरण" else "Notice Message") },
                        minLines = 3,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = NaturalSurfaceVariant,
                            unfocusedContainerColor = NaturalSurfaceVariant,
                            focusedBorderColor = NaturalGreen700,
                            unfocusedBorderColor = NaturalBorder
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Audio chime toggle
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
                            Icon(Icons.Default.VolumeUp, contentDescription = null, tint = NaturalGreen700)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isHindi) "ऑडियो चाइम बजाएं" else "Play Audio Chime",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = NaturalTextPrimary
                            )
                        }
                        Switch(
                            checked = playAudioChime,
                            onCheckedChange = { playAudioChime = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = NaturalGreen700
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Send Button
                    Button(
                        onClick = {
                            if (noticeTitle.isNotBlank() && noticeMessage.isNotBlank()) {
                                historyNotices.add(
                                    0,
                                    SentNoticeHistoryItem(
                                        title = noticeTitle,
                                        message = noticeMessage,
                                        time = "Just now",
                                        status = "Delivered to Screen",
                                        hadChime = playAudioChime
                                    )
                                )
                                sentSuccessMessage = if (isHindi) "✅ नोटिस ${child.name} के डिवाइस पर भेजा गया!" else "✅ Notice broadcasted to ${child.name}'s device!"
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = NaturalGreen700)
                    ) {
                        Icon(Icons.Default.Send, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isHindi) "तुरंत नोटिस प्रसारित करें" else "Broadcast Notice Now",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    if (sentSuccessMessage != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = sentSuccessMessage ?: "",
                            fontSize = 12.sp,
                            color = NaturalGreen700,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Section: Sent Notices History
        item {
            Text(
                text = if (isHindi) "हाल में भेजे गए नोटिस (${historyNotices.size})" else "Sent Notices Feed (${historyNotices.size})",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = NaturalTextPrimary
            )
        }

        items(historyNotices) { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(1.dp, RoundedCornerShape(16.dp))
                    .border(1.dp, NaturalBorder, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = NaturalSurface)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Campaign, contentDescription = null, tint = Color(0xFF0284C7), modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = item.title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = NaturalTextPrimary)
                        }
                        Text(text = item.time, fontSize = 11.sp, color = NaturalTextSecondary)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = item.message, fontSize = 12.sp, color = NaturalTextSecondary)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = NaturalGreen700, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = item.status, fontSize = 11.sp, color = NaturalGreen700, fontWeight = FontWeight.Bold)
                        }
                        if (item.hadChime) {
                            Text(text = "🔔 Chime Played", fontSize = 10.sp, color = NaturalTextTertiary)
                        }
                    }
                }
            }
        }
    }
}
