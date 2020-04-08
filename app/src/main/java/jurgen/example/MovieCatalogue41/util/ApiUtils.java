package jurgen.example.MovieCatalogue41.util;

import jurgen.example.MovieCatalogue41.network.ApiClient;
import jurgen.example.MovieCatalogue41.network.ApiInterface;

public class ApiUtils {

    public static final String IMAGE_URL = "https://image.tmdb.org/t/p/w500";
    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    public static ApiInterface getApi() {
        return ApiClient.getClient(BASE_URL).create(ApiInterface.class);
    }
}