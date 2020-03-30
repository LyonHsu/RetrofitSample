package com.lyon.retrofit.sample

import com.google.gson.annotations.SerializedName

class StockTime{
    @SerializedName("sysDate")
    var sysDate :String = ""
    @SerializedName("sysTime")
    var sysTime :String = ""
}