package com.lyon.retrofit.sample

import com.google.gson.annotations.SerializedName

//{"a":"20.05_20.10_20.15_20.20_20.25_","b":"20.00_19.95_19.90_19.85_19.80_","c":"2002","ch":"2002.tw","d":"20200316","ex":"tse","f":"327_521_388_720_288_","g":"2711_1037_1326_662_1392_","h":"20.25","i":"10","ip":"0","it":"12","l":"20.00","mt":"713957","n":"中鋼","nf":"中國鋼鐵股份有限公司","o":"20.20","p":"0","ps":"3040","pz":"20.00","s":"8","t":"13:28:48","tk0":"2002.tw_tse_20200316_B_9998692929","tk1":"2002.tw_tse_20200316_B_9998692430","tlong":"1584336528000","ts":"1","tv":"8","u":"22.20","v":"27706","w":"18.20","y":"20.20","z":"20.05"}
class StockInfoJson {
    @SerializedName("queryTime")
    lateinit var queryTime :StockTime
    @SerializedName("referer")
    var referer:String = ""
    @SerializedName("msgArray")
    lateinit var msgArray:List<StockInfo>

}