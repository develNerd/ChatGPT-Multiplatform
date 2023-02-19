package org.flepper.chatgpt.android.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.flepper.chatgpt.android.BuildConfig
import org.flepper.chatgpt.data.datastore.AppDataStore
import org.flepper.chatgpt.data.model.ConversationsResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.*
import java.util.concurrent.TimeUnit

interface ConversationsApi {



    @GET("https://chat.openai.com/backend-api/conversations?offset=0&limit=20")
    suspend fun getPurchaseOrders(): ConversationsResponse

    companion object {



        operator fun invoke(
        ): ConversationsApi {
            var baseUrl = "https://chat.openai.com"

            val builder = OkHttpClient.Builder()
            builder
                .connectTimeout(60L, TimeUnit.SECONDS)
                .readTimeout(60L, TimeUnit.SECONDS)
                .writeTimeout(60L, TimeUnit.SECONDS)
                .addInterceptor { chain ->
                    val bearerToken = ""
                    val cookie = ""
                    val original = chain.request()
                    val reqBuilder = original.newBuilder()
                        .addHeader("Authorization", "$bearerToken")
                        .addHeader("Accept", "application/json")
                        .addHeader("user-agent","org.flepper.chatgpt.android")
                        .addHeader("cookie", "__Host-next-auth.csrf-token=12b304d2bc80c434fc38c96e1363ad738a3f77db4d7c2d5e5c320076a38f271e%7C691fc5671befb6e763412d5b3278e6d85d3deb7f3ddddb627292406244f248d8; __Secure-next-auth.callback-url=https%3A%2F%2Fchat.openai.com%2F; _cfuvid=y1yEAQUmC6bxSXkFk81IrBaRnZKGVVz244jjessng8g-1676296191062-0-604800000; cf_clearance=UafSdOeG0dPIK32GcxTN2_DjmO9.qlxUn91m.Smtvao-1676475762-0-1-39683532.d3e0f6be.988e2d25-250; __cf_bm=vNIynj.GmUovpi.MPZqEdYsJn8xJMdPQA9wZarC8lGU-1676475762-0-Ac4d8yb0xbYw7CBf4gnlXS5RHK0AVYbuDPfqOW1Da+IvCG7qERoPj33Toe91AxokE3YhSQbriDfSGq+Kd+yyBBM=; __Secure-next-auth.session-token=eyJhbGciOiJkaXIiLCJlbmMiOiJBMjU2R0NNIn0..S40Ln-z4TMf2mIxE.8M4Z7I5FUeGKpPibY-AMPNJRFKcCaGgC6F2wT3z_ObzBFceXw5GVKLjgneRgFkefugmksB2CWxpm3lwVkh0IRa7MEj26Lju50Une7q8B1u3ObMUtOSzRabDxLVTCiDC_GgjXEylCDJhGAbR1R5AXiSm87qo51PiZ85wqAjnWbGl9PHaTwIgflnLcnD__yr5FzNaAcx4ncGKykOdnLTegZnMWScdt5w26Pi7orMMm9RRKiVSquXlpEQ89Cd_QDV8J9nUtRpte1DTFMcf5U1JdGMS2BDewPxZXgNR7XwtcVHDV0xAgMazbTOf6wnccX-yatlA1Vn3m90Kzz-1hTsbEvr0_LI6njemywcpIfKAGeP-w7HO1FZc0bywl7T_dl7MzjQjGcT8Fx5_Fpb1wjUuD7P0r-le4-5ElH5BUzpsMyL376z0kKXrvaR1GMypih5dud2oBhg1VLTRjodsMhIPhXF-iTpI6D04C5uf-kXdAzQR1RxcihK6gemaL3GlnSn8lAI-g1sZK7-pgQpuSYrLnPa4Zlpcn3-0RE9lGTg66o-by-ZVRoRS-KIzdDy7Hkx-66lro_BrYXvo9FmfiWag-zcqQlFD0wyNHAYcA8khiLp-EyBF7gMgeRjvz_FN_9OuMU3zXCYMjh9xMaDcepT0pGNln3FVGutPPrAD0sIGOw_guaZO3CfOGD6awNj3TQn9pvLjYY3LEfT2eE0RoGxytBnUdqDdaw4wIYad0A7NvcbwsjtmYh-O8oNjcFmC4EJDgYhHlbyTfYE1tQZDc4mQvtTE3Cm4K-yfPYP8egwDHcoabZZm6mFTZnOzySLjAlwOWw66GjKCF2HDhGtEqeSynoZHazm1epN4yBQOuMD3vgj-ZagzMIX_N0iXgu5COPBRzReKV5yCMSJtL0lmHRADpgxD2iS_lhg0e9ASnT3MKL9HkyXon7z35R-YxLMeUTldyds-lAdHOdewolZzwMMtt2deMnnwHt8S7dp3CxBo8DbQ2CGhH_1CruMIHJtSdtFWZxzgo4iSXdhkjZZhYALV8MDJ-34sG3U_JkIjQ0-GwrQLWXYSh0gPB5zRb9fPTGdBdTRUWV1E3T85NJ9OqcIrw2csZXEdKyjUED98biZ1BbyWy7PFNSE_j2C934K4nVrF7TmpFajRY4i2J0DxyP_foVl-IC1qxe2okZKKxmb4CqwBQ9LnzBdsmroTqndpHeVWSk0qV6jWzDUCp7dUfRFAM9LGs89H3AQl6qyY1hZLn1uxvtnR6V72rWH77x5-KmZa2BnztDnE8F595XRFy76z-FJySWxAOXfw1R4Q6T0o300iGD_-7vluLKIe6Ilqh4Oy0hlEqlwOiEiURc2pmUO0tcWFsOKasBrIMz8rDuwhaF-ldfaMdRFS8Y9PolPJhBO4GinLnGxkpyVTpJhq7RxcHQ_XSNTtQmyCzLrk8rOBDGWdnxQQtgCHvObHto9cnJXbXbqOKMzrFD15MsZaSgviRu6eBdoDj5Vv9JE54lNzmf-62iLP3Hf_0Ytg6ymtY4DfVRbmkg71tgmVEjJf6wFgwG2SzQIS88t26glramBiaOycfW2wJf9LXdy7PR5lopP9vyKoflgmDEUWifLQa6URoKPbQjKGbGEp264pH9p3S3UsGHEYek9fgwyfIHq--jKbx9do3dnucTEYNZlnIDjHPnwGpScu9bck_0y6QsEGYu3JHwcoGfPEKotk92-ra6cPmWO26ryQzG986dfRjtJlLIC8H7VoAzz28qSZ5H-Q6k-LMqbrw04J9brTPqmJc70dZQe6ifniw2Unv2QoGTAy8fqfiNAIDWqfn1hLyGgU2wh7vRNBoqpMpqp5AhRVlEXNCkwZ3uPxPywxqryCzaVkd5hkfRJGSB3yJeP26PKbBpro_gSct9mO8dtFGx0DMnAhychP4TgSksOhDm5LTaxsw43HW_pK2guAduhEmQJn1oGOMFzQGOx-XVAHydO0qFsiRsRHEOV7i6oJ1cZ6c5txN1hw0M5l2DsRRuXcgxXs1grosV52bHozSu3kmbCOFQun_T_JSUXeMpYduwcYGh_FuLG7xmNzDbVRoCCtMCbLO-IOMqijETTgm3jmWVaAExtWZqiQC6IZkK6kK9cMZzFmLbgOSkIRwJ2Bg1CDtCL9w_23pPjs646QiFXATkloImt5rCQnrSR38zo9MpR91pJouCsYFpAbOlJKKXqi-GZLsrtlV6plO4J8DVx8Zir_lq9MvMQ6sojv1KtH28daK209xyMglypqsORdfYNYYJ1tH_j1O8vqRMqjl_9SAuNqxwuLC2_JifMVHY_vTPSVCpF_q7cbUxrdgUDVacXf88eEDOkwcLsbVD9Zy1FDewmIyBnnttQyVIH9r_hGPUnKx1ig2Nv-z_nEVzJMAaGMqpWXcoD4JJl_pQ-taJ-J1QG_DAEc7BLtcIuTX7I4nlw0x5HN0f-KQDoEMa-MV-x7Qy4aok5r7GvzvyFyD_sXeQQWzZSp0tYzgDWHLL0zPJHDjGujxsYMdFQ.QkqfrjUJ4YJXoz2xiffcdw")
                    chain.proceed(reqBuilder.build())
                }

            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.apply {
                    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                }

                builder.addInterceptor(loggingInterceptor)
            }

            val gsonBuilder = GsonBuilder()

            return Retrofit.Builder()
                .client(builder.build())
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                .build()
                .create(ConversationsApi::class.java)
        }
    }

}