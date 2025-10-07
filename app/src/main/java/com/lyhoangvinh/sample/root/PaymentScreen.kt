package com.lyhoangvinh.sample.root

import android.net.Uri
import com.lyhoangvinh.sample.domain.model.Offer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun CompareDemoScreen(navController: NavController) {
    val left by remember {
        mutableStateOf(
            Offer(
                title = "Wallet Balance",
                subtitle = "Instant • No extra fee",
                amount = "$32.50",
                fee = "$0.00",
                total = "$32.50",
                highlights = listOf("Fastest", "Buyer protection"),
                cta = "Pay from Balance"
            )
        )
    }
    val right by remember {
        mutableStateOf(
            Offer(
                title = "Visa •••• 4242",
                subtitle = "Card • Standard speed",
                amount = "$32.50",
                fee = "$0.65",
                total = "$33.15",
                highlights = listOf("Rewards eligible"),
                cta = "Pay with Card"
            )
        )
    }

    MaterialTheme(colorScheme = paypalishScheme()) {
        Scaffold(
            topBar = { CompareTopAppBar() }
        ) { inner ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(inner)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item { HeaderSummary(amount = "$32.50", currency = "USD") }
                item {
                    TwoColumnCompare(
                        left = left,
                        right = right,
                        onLeftClick = {
                            navController.navigate("confirm/${Uri.encode(left.title)}")
                        },
                        onRightClick = {
                            navController.navigate("confirm/${Uri.encode(right.title)}")
                        }
                    )
                }
                item { OrDivider() }
                item {
                    InfoNotice(
                        text = "You can change your default funding source anytime in Settings."
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CompareTopAppBar() {
    TopAppBar(
        title = { Text("Compare payment options", fontWeight = FontWeight.SemiBold) }
    )
}

@Composable
fun HeaderSummary(amount: String, currency: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(Modifier.weight(1f)) {
                Text("You're paying", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(6.dp))
                Text("$amount $currency", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            }
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Text("$", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
fun TwoColumnCompare(
    left: Offer,
    right: Offer,
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            OfferCard(offer = left, accent = MaterialTheme.colorScheme.primary, onClick = onLeftClick)
        }
        Column(modifier = Modifier.weight(1f)) {
            OfferCard(offer = right, accent = MaterialTheme.colorScheme.tertiary, onClick = onRightClick)
        }
    }
}

@Composable
fun OfferCard(offer: Offer, accent: Color, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        tonalElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(accent.copy(alpha = 0.14f)),
                    contentAlignment = Alignment.Center
                ) { Text(text = offer.title.firstOrNull()?.toString() ?: "?", color = accent, fontWeight = FontWeight.SemiBold) }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(offer.title, fontWeight = FontWeight.SemiBold)
                    Text(offer.subtitle, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            KeyValueRow("Amount", offer.amount)
            KeyValueRow("Fee", offer.fee)
            Divider()
            KeyValueRowTotal("Total", offer.total)

            if (offer.highlights.isNotEmpty()) {
                FlowRowChips(offer.highlights, accent)
            }

            Button(onClick = onClick, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp)) {
                Text(offer.cta)
            }
        }
    }
}

@Composable
private fun KeyValueRow(label: String, value: String) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun KeyValueRowTotal(label: String, value: String) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(label, fontWeight = FontWeight.SemiBold)
        Text(value, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun FlowRowChips(items: List<String>, accent: Color) {
    // Simple wrap content chips without accompanist
    var currentRowWidth = 0
    Row(modifier = Modifier.fillMaxWidth()) {
        items.forEach { text ->
            AssistChip(text = text, accent = accent)
            Spacer(Modifier.width(8.dp))
        }
    }
}

@Composable
fun AssistChip(text: String, accent: Color) {
    Surface(
        shape = RoundedCornerShape(50),
        color = accent.copy(alpha = 0.10f),
        tonalElevation = 0.dp
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            color = accent,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun OrDivider() {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Divider(modifier = Modifier.weight(1f))
        Surface(shape = RoundedCornerShape(50), tonalElevation = 1.dp) {
            Text("OR", modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), textAlign = TextAlign.Center)
        }
        Divider(modifier = Modifier.weight(1f))
    }
}

@Composable
fun InfoNotice(text: String) {
    Surface(shape = RoundedCornerShape(14.dp), tonalElevation = 0.dp, color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)) {
        Text(text, modifier = Modifier.padding(12.dp), color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

// A light scheme biased toward a PayPal-like primary
@Composable
fun paypalishScheme(): ColorScheme {
    return lightColorScheme(
        primary = Color(0xFF0070E0),       // blue vibe
        onPrimary = Color.White,
        secondary = Color(0xFF111B2B),
        tertiary = Color(0xFF2E77D0),
        surface = Color(0xFFFCFCFD),
        background = Color(0xFFF7F9FC)
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewCompare() {
    val navController = rememberNavController()
    CompareDemoScreen(navController)
}
