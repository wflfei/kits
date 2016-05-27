package com.wfl.kits.widget.tagsview;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by wfl on 15/12/8.
 */
public class TagItem implements Parcelable {
    public static final int TYPE_KEYWORLD = 1;
    public static final int TYPE_POI = 2;
    public static final int TYPE_BRAND = 3;
    public int row;
    public int type = TYPE_KEYWORLD;
    public String mText;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.row);
        dest.writeInt(this.type);
        dest.writeString(this.mText);
    }

    public TagItem() {
    }

    protected TagItem(Parcel in) {
        this.row = in.readInt();
        this.type = in.readInt();
        this.mText = in.readString();
    }

}
