package ulmon.task.com.topplaces;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private LayoutInflater layoutInflater;
    private TopImage images;


    public  CardAdapter(Context context,TopImage imagesObj){
        layoutInflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getInstance();
        imageLoader = volleySingleton.getImageLoader();
        images = new TopImage();
        images = imagesObj;


    }


    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View cardLayoutView = layoutInflater.inflate(R.layout.card_view, parent, false);


        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(cardLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CardAdapter.ViewHolder holder, int position) {

        String urlThumbnail = images.urlPreview;

        imageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                holder.thumbnail.setImageBitmap(response.getBitmap());
            }
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

         ImageView thumbnail;
        public ViewHolder(View itemView){
            super(itemView);
            thumbnail = (ImageView) itemView.findViewById(R.id.topimg);
        }
    }


    @Override
    public int getItemCount() {
        return 1;
    }
}
