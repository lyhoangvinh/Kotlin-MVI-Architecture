package com.lyhoangvinh.sample.root

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import android.net.Uri
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpendingChartScreen(onBack: () -> Unit) {
    val segments = remember {
        listOf(
            ChartSeg(label = "Shopping", value = 45f, color = Color(0xFF0070E0)),
            ChartSeg(label = "Bills", value = 25f, color = Color(0xFF2E77D0)),
            ChartSeg(label = "Transfers", value = 18f, color = Color(0xFF5BA3F0)),
            ChartSeg(label = "Other", value = 12f, color = Color(0xFF9FC6F7))
        )
    }
    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Your spending") },
            navigationIcon = {
                TextButton(onClick = onBack) { Text("Back") }
            }
        )
    }) { inner ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            DonutChart(segments = segments, size = 220.dp, thickness = 28.dp, centerText = "$32.50")
            Spacer(Modifier.height(16.dp))
            LegendList(segments)
        }
    }
}

data class ChartSeg(val label: String, val value: Float, val color: Color)

@Composable
fun DonutChart(segments: List<ChartSeg>, size: Dp, thickness: Dp, centerText: String) {
    val total = segments.sumOf { it.value.toDouble() }.toFloat().coerceAtLeast(1f)
    Box(modifier = Modifier.size(size), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val sweepBase = -90f
            var start = sweepBase
            val stroke = thickness.toPx()
            segments.forEach { seg ->
                val sweep = 360f * (seg.value / total)
                drawArc(
                    color = seg.color,
                    startAngle = start,
                    sweepAngle = sweep,
                    useCenter = false,
                    style = androidx.compose.ui.graphics.drawscope.Stroke(width = stroke)
                )
                start += sweep
            }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(centerText, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text("This period", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
        }
    }
}

@Composable
fun LegendList(segments: List<ChartSeg>) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
        segments.forEach { seg ->
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.size(12.dp).clip(CircleShape).background(seg.color))
                Spacer(Modifier.width(8.dp))
                Text(seg.label, modifier = Modifier.weight(1f))
                Text(String.format("%.0f%%", seg.value))
            }
        }
    }
}

// --- Next screens ---

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmScreen(source: String, onPay: (success: Boolean) -> Unit, onBack: () -> Unit) {
    Scaffold(topBar = {
        TopAppBar(title = { Text("Confirm payment") })
    }) { inner ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Surface(shape = RoundedCornerShape(16.dp), tonalElevation = 2.dp, modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("Funding source", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(Modifier.height(6.dp))
                    Text(source, fontWeight = FontWeight.SemiBold)
                }
            }
            KeyValueRow("Amount", "$32.50")
            KeyValueRow("Fee", "$0.00")
            Divider()
            KeyValueRowTotal("Total", "$32.50")
            Spacer(Modifier.height(12.dp))
            Button(onClick = { onPay(true) }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp)) {
                Text("Pay now")
            }
            TextButton(onClick = onBack, modifier = Modifier.align(Alignment.CenterHorizontally)) { Text("Back") }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(status: String, onDone: () -> Unit) {
    val isSuccess = status.equals("success", ignoreCase = true)
    Scaffold(topBar = { TopAppBar(title = { Text("Payment ${if (isSuccess) "successful" else "failed"}") }) }) { inner ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(if (isSuccess) "✅ Payment complete" else "❌ Payment failed", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp))
            Button(onClick = onDone, shape = RoundedCornerShape(12.dp)) { Text("Done") }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCompare() {
    SpendingChartScreen {
    }
}
