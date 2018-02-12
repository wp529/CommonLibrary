package com.wp.sharelogin.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 分享的实体
 * 只能通过构造函数来构建
 * 所以不同的构造代表不同的分享类型
 * Created by WangPing on 2018/2/12.
 */

public class ShareInfo implements Parcelable{
    private String content;

    private String imageUrl;
    private String imageThumb;

    private String url;
    private String title;
    private String thumb;
    private String des;

    //分享文字就用这个构造
    public ShareInfo(String content) {
        this.content = content;
    }

    //分享图片就用这个构造
    public ShareInfo(String imageUrl, String imageThumb) {
        this.imageUrl = imageUrl;
        this.imageThumb = imageThumb;
    }

    //分享链接就这个构造
    public ShareInfo(String url, String title, String thumb, String des) {
        this.url = url;
        this.title = title;
        this.thumb = thumb;
        this.des = des;
    }

    protected ShareInfo(Parcel in) {
        content = in.readString();
        imageUrl = in.readString();
        imageThumb = in.readString();
        url = in.readString();
        title = in.readString();
        thumb = in.readString();
        des = in.readString();
    }

    public static final Creator<ShareInfo> CREATOR = new Creator<ShareInfo>() {
        @Override
        public ShareInfo createFromParcel(Parcel in) {
            return new ShareInfo(in);
        }

        @Override
        public ShareInfo[] newArray(int size) {
            return new ShareInfo[size];
        }
    };

    public String getContent() {
        return content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getImageThumb() {
        return imageThumb;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getThumb() {
        return thumb;
    }

    public String getDes() {
        return des;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(content);
        dest.writeString(imageUrl);
        dest.writeString(imageThumb);
        dest.writeString(url);
        dest.writeString(title);
        dest.writeString(thumb);
        dest.writeString(des);
    }
}
