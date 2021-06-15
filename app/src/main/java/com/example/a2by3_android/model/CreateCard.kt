package com.example.a2by3_android.model
data class CreateCard(
        var  seller_id: Int ?= null,
        var  category_id: Int ?= null,
        var  title: String ?= null,
        var  description: String ?= null,
        var  listing_type: String ?= null,
        var  bid_minimum: String ?= null,
        var  bid_expiry: String ?= null,
        var  fix_price: String ?= null,
        var  additional_fields: String ?= null,
        var  shipping_method: Int ?= null,
        var  image: String ?= null)