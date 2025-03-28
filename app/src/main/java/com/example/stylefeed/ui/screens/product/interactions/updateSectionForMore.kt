package com.example.stylefeed.ui.screens.product.interactions

import com.example.stylefeed.domain.model.SectionState
import com.example.stylefeed.domain.model.getItemIds

fun updateSectionForMore(
    currentSections: List<SectionState>,
    sectionIndex: Int
): Pair<List<SectionState>, Set<String>> {
    val updatedSections = currentSections.toMutableList()
    val sectionState = updatedSections[sectionIndex]

    val oldIds = sectionState.visibleContent.getItemIds()
    val newVisibleCount = (sectionState.visibleItemCount + 3)
        .coerceAtMost(sectionState.totalItemCount)

    val updatedSectionState = sectionState.copy(visibleItemCount = newVisibleCount)
    val newIds = updatedSectionState.visibleContent.getItemIds()
    updatedSections[sectionIndex] = updatedSectionState

    return updatedSections to (newIds - oldIds)
}