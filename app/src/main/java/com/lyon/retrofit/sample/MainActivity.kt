package com.lyon.retrofit.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    val TAG ="MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiService = RetrofitManager.client.create(ApiService::class.java)
        var options : MutableMap<String, String> = mutableMapOf<String, String>()
        //ex_ch=tse_3673.tw&json=1&delay=0
        options["ex_ch"]="tse_3673.tw|tse_2002.tw"
        options["json"]="1"
        options["delay"]="0"
        apiService.getStockData(options).enqueue(object : Callback<StockInfoJson> {
            override fun onResponse(call: Call<StockInfoJson>, response: Response<StockInfoJson>) {
                val sysDate = response.body()!!.queryTime.sysDate
                val sysTime = response.body()!!.queryTime.sysTime
                val list :List<StockInfo> = response.body()!!.msgArray
                val sb = StringBuffer()
                for(stockInfo in list){
                    sb.append(stockInfo.stockName+" ,"+stockInfo.stockId+" ,"+stockInfo.stockValue+",a="+stockInfo.stockA)
                    sb.append("\n")
                    sb.append("---------------------\n")
                }
                Log.d(TAG,"onResponse():\n"+sysDate+"/"+sysTime+" \n"+sb)
                info.text = sysDate+"/"+sysTime+" \n"+sb
            }

            override fun onFailure(call: Call<StockInfoJson>, t: Throwable) {
                Log.d(TAG,"onFailure response:"+t)
            }
        })
    }
}
