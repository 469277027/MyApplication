package com.longcai.imageuploadmaster.internet;

import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by 丛龙宇 on 17-3-2.
 */

public interface UploadAPI {

    @POST("upload?action=uploadimage")
    Observable<Object> upload();
}
