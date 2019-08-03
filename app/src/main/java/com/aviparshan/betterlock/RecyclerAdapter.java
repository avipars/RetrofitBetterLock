package com.aviparshan.betterlock;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aviparshan.betterlock.datamodel.AppDataModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private Context context;
    private List<AppDataModel> appDataModels;
    private AppDataModel appDataModel;

    public RecyclerAdapter(Context context, List<AppDataModel> appDataModels) {
        this.context = context;
        this.appDataModels = appDataModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        appDataModel = appDataModels.get(i);
        viewHolder.tv_actorname.setText(appDataModel.getTitle());
        viewHolder.tv_actorcountry.setText(appDataModel.getDescription());
        viewHolder.tv_actordateofbirth.setText(appDataModel.getVersion());
        viewHolder.tv_actorgender.setText(appDataModel.getPackageName());

        String url = appDataModel.getIcon();

        Picasso.with(context)
                .load(url)
                .resize(100, 100)
                .into(viewHolder.img_actor);
    }

    @Override
    public int getItemCount() {
        return appDataModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView img_actor;
        private AppCompatTextView tv_actorname, tv_actorcountry, tv_actordateofbirth, tv_actorgender;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_actor = (AppCompatImageView) itemView.findViewById(R.id.img_actorimage);
            tv_actorname = (AppCompatTextView) itemView.findViewById(R.id.tv_actorname);
            tv_actorcountry = (AppCompatTextView) itemView.findViewById(R.id.tv_actorcountry);
            tv_actordateofbirth = (AppCompatTextView) itemView.findViewById(R.id.tv_actordob);
            tv_actorgender = (AppCompatTextView) itemView.findViewById(R.id.tv_actorgender);
        }
    }
}
