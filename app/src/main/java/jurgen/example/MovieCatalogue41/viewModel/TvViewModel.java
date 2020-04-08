package jurgen.example.MovieCatalogue41.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.Objects;
import jurgen.example.MovieCatalogue41.model.tv.Tv;
import jurgen.example.MovieCatalogue41.model.tv.TvGenre;
import jurgen.example.MovieCatalogue41.model.tv.TvGenresResponse;
import jurgen.example.MovieCatalogue41.model.tv.TvsResponse;
import jurgen.example.MovieCatalogue41.network.ApiInterface;
import jurgen.example.MovieCatalogue41.util.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static jurgen.example.MovieCatalogue41.BuildConfig.API_KEY;

public class TvViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Tv>> lisTvs = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<TvGenre>> listTvGenres = new MutableLiveData<>();
    private final MutableLiveData<Tv> tv = new MutableLiveData<>();
    private  static final ApiInterface apiInterface = new ApiUtils().getApi();

    public LiveData<ArrayList<Tv>> getTvs() {
        return lisTvs;
    }

    public void setTvs(final String language) {
        apiInterface.getTvs(API_KEY, language).enqueue(new Callback<TvsResponse>() {
            @Override
            public void onResponse(Call<TvsResponse> call, Response<TvsResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<Tv> tvs = new ArrayList<>(Objects.requireNonNull(response.body()).getTvs());
                    lisTvs.postValue(tvs);
                    Log.d("MainActivity", "posts loaded from API");
                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<TvsResponse> call, Throwable t) {
                Log.i("MainActivity", "error", t);

            }
        });
    }

    public LiveData<ArrayList<TvGenre>> getGenres() {
        return listTvGenres;
    }

    public LiveData<Tv> getTv() {
        return tv;
    }

    public void setTv(int tv_id, String language) {
        apiInterface.getTv(tv_id, API_KEY, language).enqueue(new Callback<Tv>() {
            @Override
            public void onResponse(Call<Tv> call, Response<Tv> response) {
                if (response.isSuccessful()) {
                    tv.postValue(response.body());
                    Log.d("MainActivity", "posts loaded from API");
                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<Tv> call, Throwable t) {
                Log.d("MainActivity", "error loading from API");

            }
        });
    }

    public void setGenre(String language) {
        apiInterface.getTvGenres(API_KEY, language).enqueue(new Callback<TvGenresResponse>() {
            @Override
            public void onResponse(Call<TvGenresResponse> call, Response<TvGenresResponse> response) {
                if (response.isSuccessful()) {
                    ArrayList<TvGenre> tvGenres = new ArrayList<>(Objects.requireNonNull(response.body()).getGenres());
                    listTvGenres.postValue(tvGenres);
                    Log.d("MainActivity", "posts loaded from API");
                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<TvGenresResponse> call, Throwable t) {
                Log.d("MainActivity", "error loading from API");

            }
        });
    }


}

