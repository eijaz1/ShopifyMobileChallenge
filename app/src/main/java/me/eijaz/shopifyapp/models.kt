package me.eijaz.shopifyapp

import java.math.BigDecimal

class Results(val orders: List<Order>)

class Order (val id: BigDecimal, val created_at: String, val shipping_address: ShippingAddress?, val customer: Customer? = null)

class ShippingAddress(val province_code: String)

class Customer(val first_name: String, val last_name: String)
