package com.app.examen_tc2007b.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.examen_tc2007b.domain.model.CovidStats

@Composable
fun HeatmapCalendar(
    data: List<CovidStats>,
    onDateClick: (CovidStats) -> Unit
) {
    val maxCases = data.maxOfOrNull { it.newCases } ?: 1

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 40.dp),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(data) { stats ->
            HeatmapItem(
                stats = stats, 
                maxCases = maxCases,
                onDateClick = onDateClick
            )
        }
    }
}

@Composable
fun HeatmapItem(
    stats: CovidStats, 
    maxCases: Int,
    onDateClick: (CovidStats) -> Unit
) {
    // Calculate color intensity based on new cases
    val intensity = (stats.newCases.toFloat() / maxCases.toFloat()).coerceIn(0f, 1f)
    val color = Color.Red.copy(alpha = 0.1f + (intensity * 0.9f))

    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(color)
            .clickable { onDateClick(stats) }
            .padding(2.dp),
        contentAlignment = Alignment.Center
    ) {
        // Optional: Show date or number on tap, or just a tooltip
        // For now, minimal representation
    }
}
