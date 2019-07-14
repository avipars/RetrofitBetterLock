package com.androtechlayup.retrofitsample;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.androtechlayup.retrofitsample.datamodel.TVMazeDataModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private Context context;
    private List<TVMazeDataModel> tvMazeDataModels;
    private TVMazeDataModel tvMazeDataModel;
    public RecyclerAdapter(Context context, List<TVMazeDataModel> tvMazeDataModels) {
        this.context=context;
        this.tvMazeDataModels=tvMazeDataModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        tvMazeDataModel=tvMazeDataModels.get(i);
        viewHolder.tv_actorname.setText(tvMazeDataModel.getPerson().getName());
        viewHolder.tv_actorcountry.setText(tvMazeDataModel.getPerson().getCountry().getName());
        viewHolder.tv_actordateofbirth.setText(tvMazeDataModel.getPerson().getBirthday());
        viewHolder.tv_actorgender.setText(tvMazeDataModel.getPerson().getGender());
        Picasso.with(context)
                .load(tvMazeDataModel.getPerson().getImage().getOriginal())
                .resize(100, 100)
                .into(viewHolder.img_actor);

    }

    @Override
    public int getItemCount() {
        return tvMazeDataModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private AppCompatImageView img_actor;
        private AppCompatTextView tv_actorname,tv_actorcountry,tv_actordateofbirth,tv_actorgender;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_actor=(AppCompatImageView) itemView.findViewById(R.id.img_actorimage);
            tv_actorname=(AppCompatTextView)itemView.findViewById(R.id.tv_actorname);
            tv_actorcountry=(AppCompatTextView)itemView.findViewById(R.id.tv_actorcountry);
            tv_actordateofbirth=(AppCompatTextView)itemView.findViewById(R.id.tv_actordob);
            tv_actorgender=(AppCompatTextView)itemView.findViewById(R.id.tv_actorgender);
        }
    }
}
