package com.aviparshan.betterlock.datamodel;


import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * RetrofitBetterLock
 * Created by Avi Parshan on 7/27/2019 on com.aviparshan.betterlock.datamodel
 */
public class AppDataModel {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("package")
    @Expose
    private String packageName;
    @SerializedName("activity")
    @Expose
    private String activity;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("icon")
    @Expose
    private String icon;
    private boolean installed;
    private Bitmap photo;

    public AppDataModel() {
        super();
    }

    public AppDataModel(String title, String packageName, String activity, String version, String description, String icon) {
        this.title = title;
        this.packageName = packageName;
        this.activity = activity;
        this.version = version;
        this.description = description;
        this.icon = icon;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public boolean getInstalled() {
        return installed;
    }

    public void setInstalled(boolean installed) {
        this.installed = installed;
    }
}
