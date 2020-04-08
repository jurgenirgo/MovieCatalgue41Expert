package jurgen.example.MovieCatalogue41.network;

import jurgen.example.MovieCatalogue41.model.movie.Movie;
import jurgen.example.MovieCatalogue41.model.movie.MovieGenresResponse;
import jurgen.example.MovieCatalogue41.model.movie.MoviesResponse;
import jurgen.example.MovieCatalogue41.model.tv.Tv;
import jurgen.example.MovieCatalogue41.model.tv.TvGenresResponse;
import jurgen.example.MovieCatalogue41.model.tv.TvsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("discover/movie")
    Call<MoviesResponse> getMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("discover/tv")
    Call<TvsResponse> getTvs(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );


    @GET("genre/movie/list")
    Call<MovieGenresResponse> getMovieGenres(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("genre/tv/list")
    Call<TvGenresResponse> getTvGenres(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("movie/{movie_id}")
    Call<Movie> getMovie(
            @Path("movie_id") int id,
            @Query("api_key") String apiKEy,
            @Query("language") String language
    );

    @GET("tv/{tv_id}")
    Call<Tv> getTv(
            @Path("tv_id") int id,
            @Query("api_key") String apiKEy,
            @Query("language") String language
    );
}
