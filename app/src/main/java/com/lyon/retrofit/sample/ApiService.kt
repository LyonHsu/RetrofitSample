package com.lyon.retrofit.sample


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Ref.
 * https://medium.com/@givemepass/retrofit-%E6%93%8D%E4%BD%9C%E6%95%99%E5%AD%B8-26c7851ec154
 */
interface ApiService {
    @GET("getStockInfo.jsp")//ex_ch=tse_3673.tw&json=1&delay=0
    fun getStockData(@QueryMap options : Map<String, String>): Call<StockInfoJson>
}