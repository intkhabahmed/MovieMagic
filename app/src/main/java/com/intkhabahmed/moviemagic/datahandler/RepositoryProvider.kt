import com.intkhabahmed.moviemagic.network.ApiService

object RepositoryProvider {
    fun provideRepository(): Repository {
        return Repository(ApiService.create())
    }
}