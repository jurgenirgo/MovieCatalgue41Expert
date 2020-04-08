package jurgen.example.MovieCatalogue41.view.fragment;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.stone.vega.library.VegaLayoutManager;
import com.wang.avi.AVLoadingIndicatorView;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import butterknife.BindView;
import butterknife.ButterKnife;
import jurgen.example.MovieCatalogue41.R;
import jurgen.example.MovieCatalogue41.adapter.TvsAdapter;
import jurgen.example.MovieCatalogue41.model.tv.Tv;
import jurgen.example.MovieCatalogue41.model.tv.TvGenre;
import jurgen.example.MovieCatalogue41.view.activity.DetailTvActivity;
import jurgen.example.MovieCatalogue41.viewModel.TvViewModel;

public class TvFragment extends Fragment {


    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    @BindView(R.id.rv_tv)
    RecyclerView rvTv;
    @BindView(R.id.error_layout)
    LinearLayout errorLayout;
    private TvsAdapter mAdapter;
    private final Observer<ArrayList<Tv>> getTvs = new Observer<ArrayList<Tv>>() {
        @Override
        public void onChanged(ArrayList<Tv> items) {
            if (items != null) {
                showLoading(false);
                mAdapter.addTvs(items);
            } else {
                showLoading(false);
                showError();
            }
        }
    };
    private final Observer<ArrayList<TvGenre>> getGenres = new Observer<ArrayList<TvGenre>>() {
        @Override
        public void onChanged(ArrayList<TvGenre> items) {
            if (items != null) {
                mAdapter.addGenres(items);
                showLoading(false);
            } else {
                showLoading(false);
                showError();
            }
        }
    };
    private TvViewModel tvViewModel;

    public TvFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tv, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkConnection();
        setupViewModeL();
        setupData();
        setupView();
    }

    private void checkConnection() {
        ConnectivityManager connMgr = (ConnectivityManager) Objects.requireNonNull(getContext())
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = Objects.requireNonNull(connMgr).getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
        } else {
            showLoading(false);
            showError();
        }
    }

    private void setupView() {
        mAdapter = new TvsAdapter(getContext(), new ArrayList<>(), new ArrayList<>(), id -> {
            Intent intent = new Intent(getContext(), DetailTvActivity.class);
            intent.putExtra(DetailTvActivity.TID, id);
            startActivity(intent);
        });
        rvTv.setLayoutManager(new VegaLayoutManager());
        rvTv.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL);
        rvTv.addItemDecoration(itemDecoration);


    }

    private void setupData() {
        String LANGUAGE = Locale.getDefault().toString();
        if (LANGUAGE.equals("in_ID")) {
            LANGUAGE = "id_ID";
        }
        tvViewModel.setTvs(LANGUAGE);
        tvViewModel.setGenre(LANGUAGE);
    }

    private void setupViewModeL() {
        tvViewModel = ViewModelProviders.of(this).get(TvViewModel.class);
        tvViewModel.getTvs().observe(this, getTvs);
        tvViewModel.getGenres().observe(this, getGenres);

    }

    private void showLoading(Boolean state) {
        if (state) {
            avi.show();
        } else {
            avi.hide();
        }
    }


    private void showError() {
        errorLayout.setVisibility(View.VISIBLE);
    }


}
