package com.aviparshan.betterlock;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aviparshan.betterlock.datamodel.AppDataModel;
import com.aviparshan.betterlock.utils.HelperClass;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
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

     RecyclerAdapter(Context context, List<AppDataModel> appDataModels, onItemClickListener listener, onItemLongClickListener listenerLong) {
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

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        appDataModel = appDataModels.get(i);
        viewHolder.tv_actorname.setText(appDataModel.getTitle());
        viewHolder.tv_actorcountry.setText(appDataModel.getDescription());
        viewHolder.tv_actorgender.setText(Main.versionChecker(appDataModel));
        viewHolder.tv_actordateofbirth.setText(Main.packageInstalled(appDataModel));
        String url = appDataModel.getIcon();

        Picasso.with(context)
                .load(url)
//                .placeholder(R.drawable.ic_android_black_24dp)
                .error(R.drawable.ic_android_black_24dp)
//                .priority(Picasso.Priority.HIGH)
//                .centerCrop()
//Picasso.get().load("file:///android_asset/DvpvklR.png").into(imageView2);
                .resize(200, 200)
                .into(viewHolder.img_actor);

        viewHolder.setIsRecyclable(false);

        final int position = i;

        viewHolder.main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.itemDetailClick(appDataModels.get(position));
            }
        });

        viewHolder.main.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listenerLong.itemDetailLongClick(appDataModels.get(position));
                return pop(v, position);
            }
        });
    }

    private boolean pop(View v, final int position)
    {
        PopupMenu popup = new PopupMenu(v.getContext(), v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.unin:
                        Main.uninstallApp(appDataModels.get(position));
                        return true;
                    case R.id.info:
                        Main.appInfo(appDataModels.get(position));
                        return true;
                    case R.id.home:
//                        HelperClass.addShortcut(context, appDataModel);
                        HelperClass.addShortcutToHomeScreen(context, appDataModel);
                        return true;
                    default:
                        return false;
                }
            }
        });
        // here you can inflate your menu
        popup.inflate(R.menu.context_menu);
        popup.setGravity(Gravity.START);

        // if you want icon with menu items then write this try-catch block.
        try {
            Field mFieldPopup=popup.getClass().getDeclaredField("mPopup");
            mFieldPopup.setAccessible(true);
            MenuPopupHelper mPopup = (MenuPopupHelper) mFieldPopup.get(popup);
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
        popup.show();
        return true;
    }

    @Override
    public int getItemCount() {
        return appDataModels.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView img_actor;
        private AppCompatTextView tv_actorname, tv_actorcountry, tv_actordateofbirth, tv_actorgender;
        private LinearLayout main;

         ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_actor = itemView.findViewById(R.id.img_actorimage);
            tv_actorname = itemView.findViewById(R.id.tv_actorname);
            tv_actorcountry = itemView.findViewById(R.id.tv_actorcountry);
            tv_actordateofbirth = itemView.findViewById(R.id.tv_actordob);
            tv_actorgender = itemView.findViewById(R.id.tv_actorgender);

            main = itemView.findViewById(R.id.main);
        }
    }
}
