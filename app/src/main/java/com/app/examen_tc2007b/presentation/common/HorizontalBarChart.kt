package com.app.examen_tc2007b.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.app.examen_tc2007b.domain.model.CovidStats

@Composable
fun HorizontalBarChart(data: List<CovidStats>) {
    val maxCases = data.maxOfOrNull { it.newCases } ?: 1

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(data) { stats ->
            BarChartItem(stats = stats, maxCases = maxCases)
        }
    }
}

@Composable
fun BarChartItem(stats: CovidStats, maxCases: Int) {
    val percentage = (stats.newCases.toFloat() / maxCases.toFloat()).coerceIn(0f, 1f)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stats.country,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.width(100.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        
        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .height(24.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(percentage)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(4.dp))
                    .background(MaterialTheme.colorScheme.primary)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = stats.newCases.toString(),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.width(50.dp)
        )
    }
}
