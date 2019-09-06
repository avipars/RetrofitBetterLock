package com.aviparshan.betterlock;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.lang.reflect.Field;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private Context context;
    private List<AppDataModel> appDataModels;
    private AppDataModel appDataModel;

    private onItemClickListener listener;
    private onItemLongClickListener listenerLong;

    boolean imageSet = false;
    Bitmap b;
    private Target mTarget;

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
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        appDataModel = appDataModels.get(i);
        viewHolder.tv_actorname.setText(appDataModel.getTitle());
        viewHolder.tv_actorcountry.setText(appDataModel.getDescription());
//        viewHolder.tv_actorgender.setText(Main.versionChecker(appDataModel));
//        viewHolder.tv_actordateofbirth.setText(Main.packageInstalled(context, appDataModel));
        viewHolder.tv_actordateofbirth.setText(Main.versionChecker(appDataModel));
        viewHolder.tv_actordateofbirth.setTextColor(Main.versionCheckerColor(appDataModel));
        String url = appDataModel.getIcon();

//        Picasso.with(context)
//                .load(url)
////                .placeholder(R.drawable.ic_android_black_24dp)
//                .error(R.drawable.ic_android_black_24dp)
////                .priority(Picasso.Priority.HIGH)
////                .centerCrop()
////Picasso.get().load("file:///android_asset/DvpvklR.png").into(imageView2);
////                .resize(200, 200)
//                .into(viewHolder.img_actor);

        mTarget = new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                b = bitmap;
                appDataModel.setPhoto(bitmap);
                viewHolder.img_actor.setImageBitmap(bitmap);
                imageSet = true;

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                b = ((BitmapDrawable) errorDrawable).getBitmap();
                appDataModel.setPhoto(b);
                imageSet = false;
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
//                b = ((BitmapDrawable) placeHolderDrawable).getBitmap();
//                appDataModel.setPhoto(b);
                imageSet = false;
            }
        };

        Picasso.with(context)
                .load(url)
                .error(R.drawable.ic_android_black_24dp)
                .noPlaceholder()
//                .header("Cache-Control", String.format("max-age=%d", 50000))
                .into(mTarget);

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

    private boolean pop(View v, final int position) {
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
                        if (imageSet) {
                            Main.pinShortcut(appDataModels.get(position));
                        } else {
                            Main.createToast("Image Not Yet Set", Toast.LENGTH_SHORT);
                        }
                        return true;
                    default:
                        return false;
                }
            }
        });

        popup.getMenu().add("Version: " + appDataModel.getVersion());
        popup.show();
        // here you can inflate your menu
        popup.inflate(R.menu.context_menu);
        popup.setGravity(Gravity.START);
        // if you want icon with menu items then write this try-catch block.
        try {
            Field mFieldPopup = popup.getClass().getDeclaredField("mPopup");
            mFieldPopup.setAccessible(true);
            MenuPopupHelper mPopup = (MenuPopupHelper) mFieldPopup.get(popup);

//            Menu menu = popup.getMenu();
//            MenuItem menuItem = menu.findItem(R.id.gp1);
//            menuItem.setTitle("Avi Change");
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
//            tv_actorgender = itemView.findViewById(R.id.tv_actorgender);
            main = itemView.findViewById(R.id.main);
        }
    }
}
