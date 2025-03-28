package com.example.stylefeed.ui.screens.product.interactions

import com.example.stylefeed.domain.model.SectionState
import com.example.stylefeed.domain.model.shuffleContent

fun updateSectionForRefresh(
    currentSections: List<SectionState>,
    sectionIndex: Int
): List<SectionState> {
    val updatedSections = currentSections.toMutableList()
    val sectionState = updatedSections[sectionIndex]

    val shuffledContent = sectionState.section.content.shuffleContent()
    updatedSections[sectionIndex] = sectionState.copy(
        section = sectionState.section.copy(content = shuffledContent)
    )
    return updatedSections
}