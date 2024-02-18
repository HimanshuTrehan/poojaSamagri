package com.slyked.admin.configuration.model

data class PromoCode(
    val code: String="",
    val createdAt: String?=null,
    val description: String="",
    val discount_type: String="fixed",
    val discount_value: String="0",
    val end_date: String,
    val id: Int?=null,
    val max_discount_value: String="0",
    val min_discount_value: String="0",
    val min_order_value: String="0",
    val offer_per_user: Boolean=false,
    val offer_status: Boolean=false,
    val offer_type: String="discount",
    val start_date: String,
    val updatedAt: String?=null
)