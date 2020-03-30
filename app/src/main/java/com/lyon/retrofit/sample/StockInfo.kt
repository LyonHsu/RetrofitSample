package com.lyon.retrofit.sample

import com.google.gson.annotations.SerializedName

class StockInfo {
    @SerializedName("n")
    var stockName:String=""
    @SerializedName("a")
    var stockA:String=""
    @SerializedName("b")
    var stockB:String=""
    @SerializedName("ch")
    var stockCh:String=""
    @SerializedName("c")
    var stockId:String=""
    @SerializedName("y")
    lateinit var stockYesterday:String
    @SerializedName("z")
    var stockValue:String=""
}