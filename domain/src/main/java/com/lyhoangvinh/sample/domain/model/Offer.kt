package com.lyhoangvinh.sample.domain.model

data class Offer(
    val title: String,
    val subtitle: String,
    val amount: String, // e.g. "$32.50"
    val fee: String, // e.g. "$0.00"
    val total: String, // e.g. "$32.50"
    val highlights: List<String> = emptyList(),
    val cta: String = "Choose"
)