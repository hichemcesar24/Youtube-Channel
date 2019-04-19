package com.hichemromdhane.youtubechannel.Retrofit;

import android.support.annotation.NonNull;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RequestBodyPart {
    @NonNull
    public static RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(MediaType.parse("text/plain"), descriptionString);
    }

    @NonNull
    public static MultipartBody.Part prepareFilePart(String partName, String filePath) {
        File file = new File(filePath);
        RequestBody requestFile =RequestBody.create(MediaType.parse("application/binary"),file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }
}
