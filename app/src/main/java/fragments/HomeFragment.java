package fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.instagram.R;

import java.util.ArrayList;
import java.util.List;

import adapter.HomeAdapter;
import api.Post_api;
import model.PostModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import url.Url;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    List<PostModel> postlist = new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.rcHomeView);

        Post_api post_api = Url.getInstance().create(Post_api.class);
        Call<List<PostModel>> responseCall = post_api.getPost();

        responseCall.enqueue(new Callback<List<PostModel>>() {
            @Override
            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getContext(), String.valueOf(response.code()), Toast.LENGTH_LONG).show();
                }else {
                    postlist= response.body();

                    HomeAdapter homeAdapter = new HomeAdapter(getActivity(),postlist);
                    recyclerView.setAdapter(homeAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                }
            }

            @Override
            public void onFailure(Call<List<PostModel>> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        return view;

    }

}
