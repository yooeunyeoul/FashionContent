package com.example.stylefeed.ui.common
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.stylefeed.domain.model.Product

@Composable
fun ProductCard(product: Product) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .aspectRatio(0.7f)  // 너비 대비 높이를 0.7 비율로 설정 (예: 너비 100dp일 때 높이는 70dp)
            .clickable { /* 상품 클릭 */ },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = product.thumbnailUrl,
            contentDescription = product.brandName,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = product.brandName,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = "${product.price}원",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold
        )

        if (product.saleRate > 0) {
            Text(
                text = "${product.saleRate}% 할인",
                style = MaterialTheme.typography.labelSmall,
                color = Color.Red
            )
        }

        if (product.hasCoupon) {
            Box(
                modifier = Modifier
                    .padding(top = 2.dp)
                    .background(Color(0xFFFEF2E9), RoundedCornerShape(4.dp))
                    .padding(horizontal = 4.dp, vertical = 2.dp)
            ) {
                Text(
                    text = "쿠폰",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFFF76A24)
                )
            }
        }
    }
}