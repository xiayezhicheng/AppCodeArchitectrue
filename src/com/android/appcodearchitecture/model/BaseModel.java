package com.android.appcodearchitecture.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;


public class BaseModel implements Parcelable {

	public String toJson(){
		return new Gson().toJson(this);
	}
	
	@Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public BaseModel() {
    }

    private BaseModel(Parcel in) {
    }

    public static final Parcelable.Creator<BaseModel> CREATOR = new Parcelable.Creator<BaseModel>() {
        public BaseModel createFromParcel(Parcel source) {
            return new BaseModel(source);
        }

        public BaseModel[] newArray(int size) {
            return new BaseModel[size];
        }
    };
}
