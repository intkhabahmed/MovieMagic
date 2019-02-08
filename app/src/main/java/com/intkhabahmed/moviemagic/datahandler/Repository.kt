import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.intkhabahmed.moviemagic.models.Result
import com.intkhabahmed.moviemagic.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository(val apiService: ApiService) {
    fun searchMovies(apikey: String, keyword: String, page: Int): LiveData<Result>? {
        val resultSet: MutableLiveData<Result> = MutableLiveData()
        apiService.searchMovies(apikey, keyword, page).enqueue(object : Callback<Result> {
            override fun onFailure(call: Call<Result>, t: Throwable) {
                Log.v(Repository::class.java.simpleName, t.message)
            }

            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                resultSet.value = response.body()
            }
        })
        return resultSet
    }
}