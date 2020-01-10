package adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagram.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import model.PostModel;
import strictmode.StrictMode;
import url.Url;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeAdapterViewHolder> {

    Context mContext;
    public List<PostModel> homelist;

    public HomeAdapter(Context mContext, List<PostModel> homelist) {
        this.mContext = mContext;
        this.homelist = homelist;
    }

    @NonNull
    @Override
    public HomeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_view,parent, false);
        return new HomeAdapterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapterViewHolder holder, int position) {
        final PostModel postModel = homelist.get(position);
        Toast.makeText(mContext, postModel.getCaption(),Toast.LENGTH_LONG).show();
        final String posterImagePath = Url.BASE_URL + "/uploads/" + postModel.getPostImage();

        StrictMode();

        try {
            URL url = new URL(posterImagePath);
            holder.photo.setImageBitmap(BitmapFactory.decodeStream((InputStream)url.getContent()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.caption.setText(postModel.getCaption());


    }

    @Override
    public int getItemCount() {
        return homelist.size();
    }

    public class HomeAdapterViewHolder extends RecyclerView.ViewHolder {
        public ImageView photo;
        public CircleImageView posterimage;
        public TextView uploadername, caption;

        public HomeAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            uploadername=itemView.findViewById(R.id.tvUsernameDisplay);
            photo=itemView.findViewById(R.id.imageView);
            caption=itemView.findViewById(R.id.tvDisplayCaption);
            posterimage=itemView.findViewById(R.id.cvUserImage);

        }
    }
    public static void StrictMode() {

        android.os.StrictMode.ThreadPolicy policy = new android.os.StrictMode.ThreadPolicy.Builder().permitAll().build();
        android.os.StrictMode.setThreadPolicy(policy);
    }
}

