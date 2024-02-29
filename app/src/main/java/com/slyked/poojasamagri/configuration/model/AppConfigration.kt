package com.slyked.admin.configuration.model

data class AppConfigration(
    val createdAt: String? =null,
    val delivery_charge: String,
    val delivery_time: String,
    val id: Int?=null,
    val min_order_amount: String,
    val phone_number: String,
    val store_status: Boolean,
    val updatedAt: String? =null,
    val whatsapp_number: String
)