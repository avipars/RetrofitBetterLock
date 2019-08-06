package com.aviparshan.betterlock;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.aviparshan.betterlock.datamodel.AppDataModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private Context context;
    private List<AppDataModel> appDataModels;
    private AppDataModel appDataModel;

    private onItemClickListener listener;
    private onItemLongClickListener listenerLong;

    public interface onItemClickListener {
        void itemDetailClick(AppDataModel conversion);
    }

    public interface onItemLongClickListener {
        void itemDetailLongClick(AppDataModel conversion);
    }

    public RecyclerAdapter(Context context, List<AppDataModel> appDataModels, onItemClickListener listener, onItemLongClickListener listenerLong) {
        this.context = context;
        this.appDataModels = appDataModels;
        this.listener = listener;
        this.listenerLong = listenerLong;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
//        View view = LayoutInflater.from(context).inflate(R.layout.grid_list_item, viewGroup, false);

        return new ViewHolder(view);
    }

//    public void modifyItem(final int position, final Model model) {
//        mainModel.set(position, model);
//        notifyItemChanged(position);
//    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        appDataModel = appDataModels.get(i);
        viewHolder.tv_actorname.setText(appDataModel.getTitle());
        viewHolder.tv_actorcountry.setText(appDataModel.getDescription());

        viewHolder.tv_actorgender.setText(appDataModel.getVersion());

        viewHolder.tv_actordateofbirth.setText(appDataModel.getPackageName());

        String url = appDataModel.getIcon();

        final int position = i;

        Picasso.with(context)
                .load(url)
//                .placeholder(R.drawable.ic_android_black_24dp)
                .error(R.drawable.ic_android_black_24dp)
//                .priority(Picasso.Priority.HIGH)
//                .centerCrop()
                .resize(200, 200)
                .into(viewHolder.img_actor);

        viewHolder.setIsRecyclable(false);

        viewHolder.main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.itemDetailClick(appDataModels.get(position));
            }
        });

        viewHolder.main.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.itemDetailClick(appDataModels.get(position));
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return appDataModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        private AppCompatImageView img_actor;
        private AppCompatTextView tv_actorname, tv_actorcountry, tv_actordateofbirth, tv_actorgender;
        private LinearLayout main;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_actor = itemView.findViewById(R.id.img_actorimage);
            tv_actorname = itemView.findViewById(R.id.tv_actorname);
            tv_actorcountry = itemView.findViewById(R.id.tv_actorcountry);
            tv_actordateofbirth = itemView.findViewById(R.id.tv_actordob);
            tv_actorgender = itemView.findViewById(R.id.tv_actorgender);

            main = itemView.findViewById(R.id.main);

            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select The Action");
            menu.add(0, v.getId(), 0, "Call");//groupId, itemId, order, title
            menu.add(0, v.getId(), 0, "SMS");

        }
    }
}
