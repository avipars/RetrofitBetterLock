package com.aviparshan.betterlock.datamodel;


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
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("icon")
    @Expose
    private String icon;

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
}
