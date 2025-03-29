package com.example.stylefeed.ui.screens.product.previewdata

import com.example.stylefeed.domain.model.*
import java.util.UUID

object PreviewData {

    private val bannerSection = Section(
        header = Header("오늘의 배너", null, null), content = Content.BannerContent(
            banners = listOf(
                Banner(
                    linkUrl = "https://www.musinsa.com/app/plan/views/22278",
                    thumbnailUrl = "https://image.msscdn.net/images/event_banner/2022062311154900000044053.jpg",
                    title = "하이드아웃 S/S 시즌오프",
                    description = "최대 30% 할인",
                    keyword = "세일"
                )
            )
        ), footer = null
    ).previewState(visibleItemCount = 1, totalItemCount = 1)

    private val gridSection = Section(
        header = Header("클리어런스", null, null), content = Content.GridContent(
            products = listOf(
                Product(
                    linkUrl = "https://www.musinsa.com/app/goods/2281818",
                    thumbnailUrl = "https://image.msscdn.net/images/goods_img/20211224/2281818/2281818_1_320.jpg",
                    brandName = "아스트랄 프로젝션",
                    formattedPrice = "39,900원",
                    saleRate = 50,
                    hasCoupon = true
                )
            )
        ), footer = Footer(type = FooterType.MORE, title = "더보기", iconUrl = null)
    ).previewState(visibleItemCount = 1, totalItemCount = 5)

    private val scrollSection = Section(
        header = Header(
            title = "디스커버리 익스페디션 인기 스니커즈",
            iconUrl = "https://image.msscdn.net/icons/mobile/clock.png",
            linkUrl = "https://www.musinsa.com/brands/discoveryexpedition"
        ), content = Content.ScrollContent(
            products = listOf(
                Product(
                    linkUrl = "https://www.musinsa.com/app/goods/1727824",
                    thumbnailUrl = "https://image.msscdn.net/images/goods_img/20201221/1727824/1727824_4_320.jpg",
                    brandName = "디스커버리 익스페디션",
                    formattedPrice = "59,500원",
                    saleRate = 50,
                    hasCoupon = true
                )
            )
        ), footer = Footer(type = FooterType.REFRESH, title = "새로운 추천", iconUrl = null)
    ).previewState(visibleItemCount = 1, totalItemCount = 10)

    private val styleSection = Section(
        header = Header("무신사 추천 코디", null, null), content = Content.StyleContent(
            styles = listOf(
                Style(
                    linkUrl = "https://www.musinsa.com/app/styles/views/27417",
                    thumbnailUrl = "https://image.musinsa.com/images/style/list/2022062214302100000008217.jpg"
                )
            )
        ), footer = Footer(FooterType.MORE, "더보기", null)
    ).previewState(visibleItemCount = 1, totalItemCount = 20)

    val allSections = listOf(bannerSection, gridSection, scrollSection, styleSection)
}

private fun Section.previewState(
    visibleItemCount: Int, totalItemCount: Int, id: String = UUID.randomUUID().toString()
) = SectionState(
    id = id, section = this, visibleItemCount = visibleItemCount, totalItemCount = totalItemCount
)
