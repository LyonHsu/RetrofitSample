# RetrofitSample
Retrofit 是一個很方便的網路連結套件，它可以幫你在連結網路的時候做好封裝的效果，操作方式簡單方便迅速，可以跟 OkHttp 以及 RxJava 合併使用。
完整程式碼

GitHub:https://github.com/givemepassxd999/retrofit_demo

情境
如果我們需要連結 API 取得所需的資料，那麼就可以透過 Retrofit 這個套件來輕鬆完成串接 API 的任務了，它的好處在於乾淨的介面，透過宣告類別的方式，就可以把 Request 跟 Response 都設置完成。
程式碼說明
在這邊我們會用到下面這些第三方套件。

    implementation 'com.squareup.retrofit2:retrofit:2.4.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.4.0'
    implementation 'com.squareup.okhttp3:okhttp:3.11.0'
  
要下載網路記得一定要把網路權限打開，否則會不能運作。

    <uses-permission android:name="android.permission.INTERNET" />
    
在使用 Retrofit 的時候，如果你有自己想串接的 Http Client 第三方，也可以透過 Retrofit 來進行串接，Retrofit 本身底層就是用 OkHttp 的 Client，如果沒有特別運用的話，可以不需要自己生成一個 OkHttp 的物件，反之，如果你有想要寫自己的攔截器或者調整一些設定，那麼設置自己的 OkHttp 物件是一個不錯的選擇，透過 AppClientManager 可以產出一個 Retrofit 的實體物件。

    class AppClientManager private constructor() {
        private val retrofit: Retrofit
        private val okHttpClient = OkHttpClient()

        init {
            retrofit = Retrofit.Builder()
                    .baseUrl(Config.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()
        }

        companion object {
            private val manager = AppClientManager()
            val client: Retrofit
                get() = manager.retrofit
        }
    }
  
在上面的範例，我們可以看到有一行程式碼如下。

    .addConverterFactory(GsonConverterFactory.create())
    
這邊透過 Google 出的 Json 處理工具叫做 Gson 來進行轉換。
Gson 是一個很好用的工具，它可以幫你把 JSON 透過物件的方式進行字串跟物件的轉換，可以參考以下 Gson 教學。

  https://medium.com/@givemepass/gson-%E5%9F%BA%E7%A4%8E%E6%95%99%E5%AD%B8-f367ee74e65d
  
首先, 我們來示範怎麼串接一個 RESTful API，但是這個範例不需要自己架設一個 Server 並且寫好 RESTFul API，在網路上搜尋到一個提供測試的 RESTFul API 的網站提供一些簡單的測試 API 來玩看看。
JSONPlaceholder — Fake online REST API for developers
我們拿其中一個 GET 的網址來玩。

https://jsonplaceholder.typicode.com/posts

它會回傳以下的格式資訊。

    [
      {
        "userId": 1,
        "id": 1,
        "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
        "body": "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
      },
      {
        "userId": 1,
        "id": 2,
        "title": "qui est esse",
        "body": "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla"
      },
    //...
     ]
     
因為資料很長，所以我們取前兩筆來展示就好，你可以自行前往上面的網址觀看。
它主要的格式是由四個欄位所組成的，透過 JSON 的方式可以知道它其實非常的單純，所以我們透過 GSON 這個套件來宣告，所以可以看到我們將 Response 宣告成以下的類別。

    class Posts {
        @SerializedName("userId")
        var userId: Int = 0
        @SerializedName("id")
        var id: Int = 0
        @SerializedName("title")
        var title: String? = null
        @SerializedName("body")
        var body: String? = null
    }
  
此時我們可以宣告一個 interface 透過 retrofit 來使用這個 response class，因為回來的資料是一個陣列，因此我們將 Posts 這個類別用 List 裝起來，又因為從 API 回來的資訊是非同步，因此採用 Call 這個方法來處理，你只要當作 retrofit 會對這個行為進行非同步處理即可，如下所述。

    interface ApiService {
        @GET("/posts")
        fun index(): Call<List<Posts>>
    }
如此一來前置作業都設定好了，我們就可以開始操作 retrofit 這個工具了，首先宣告一個按鈕，當按下按鈕以後觸發事件。

    info.setOnClickListener {
       //從server撈資料回來處理 
    }
那麼我們就可以在事件內操作 retrofit，透過它從 server 抓回我們的 json 處理如下所述。

    info.setOnClickListener {
        val apiService = AppClientManager.client.create(ApiService::class.java)
        apiService.index().enqueue(object : Callback<List<Posts>> {
            override fun onResponse(call: Call<List<Posts>>, response: Response<List<Posts>>) {
                val sb = StringBuffer()
                val list = response.body()
                for (p in list!!) {
                    sb.append(p.body)
                    sb.append("\n")
                    sb.append("---------------------\n")
                }
                tv.text = sb.toString()
            }

            override fun onFailure(call: Call<List<Posts>>, t: Throwable) {

            }
        })
    }
當我們把資訊抓回來，就可以透過 Response 這個物件取出我們的 json 了， 這個物件會幫我們把所對應的 JSON 轉成對應的物件 Posts，然後根據我們 interface 所定義的方式轉成 List，這樣一來就可以直接使用這個 List，把 body 這個欄位抓出來顯示在 TextView 上面，如下圖所示。

在前面宣告一個簡單的 GET 方法，其實 API 還有很多變化。
如果你是網址後面有帶入相對應的參數可以直接塞入＠後面。

    @GET("users/list?sort=desc")
那你也可以這樣做。

    @GET("users/list")
    Call<List<User>> groupList(@Query("sort") String sort);
如果你的網址上面是對應的變數就可以這樣做。

    @GET("group/{id}/users")
    Call<List<User>> groupList(@Path("id") int groupId);
如果你帶入的參數非常多，可以考慮用 QueryMap 裝起來。

    @GET("group/{id}/users")
    Call<List<User>> groupList(@Path("id") int groupId, @QueryMap Map<String, String> options);
那如果你的 API 是裝在 Body 的情況，就可以這樣處理。

    @POST("users/new")
    Call<User> createUser(@Body User user);
另外 API 還可以加裝一些攔截器，什麼是攔截器？其實這是 OKHttp 所提供的一個功能。

這是官方網站所提供的一張圖片，裡面可以看到，無論你是從網路層回來的攔截器，或者是從 Request/Response 的部分都可以透過攔截器來進行加工，比如說你需要 Log 那麼就可以透過攔截器來串接所需的部分，那麼就可以透過 Logging Interceptor 這個第三方來幫忙處理。
首先在 Gradle 加入這個第三方套件。

    implementation "com.squareup.okhttp3:logging-interceptor:4.0.1"
    接著在我們原本 AppClientManager 的部分就可以調整成這樣。
    private var logging = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            Log.i("interceptor msg", message)
        }
    })

    private var okHttpClient : OkHttpClient

    init {
        logging.level = HttpLoggingInterceptor.Level.BODY
        okHttpClient = OkHttpClient().newBuilder().addInterceptor(logging).build()
        retrofit = Retrofit.Builder()
                .baseUrl(Config.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
    }
如此一來，就可以在打完 API 以後攔截我們所需要的資訊了，當然攔截器還不止這些功能，譬如說可以攔截起來打包、轉址等等，更多功能可以參考一下官方網站所提供的說明。
這樣就是大致上 Retrofit 的基礎操作了。
