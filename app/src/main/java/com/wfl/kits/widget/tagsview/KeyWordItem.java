package com.wfl.kits.widget.tagsview;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by wfl on 15/12/15.
 */
public class KeyWordItem extends TagItem {
    public String nameEn;
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.nameEn);
    }

    public KeyWordItem() {
        type = TYPE_KEYWORLD;
    }

    protected KeyWordItem(Parcel in) {
        super(in);
        this.nameEn = in.readString();
    }

    public static final Parcelable.Creator<KeyWordItem> CREATOR = new Parcelable.Creator<KeyWordItem>() {
        public KeyWordItem createFromParcel(Parcel source) {
            return new KeyWordItem(source);
        }

        public KeyWordItem[] newArray(int size) {
            return new KeyWordItem[size];
        }
    };
}
