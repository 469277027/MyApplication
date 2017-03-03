package com.longcai.imageuploadmaster.internet;


import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * Created by Angel on 2016/12/16.
 */

public class FileRequestBody<T> extends RequestBody {

    private static final String TAG = "FileRequestBody";
    private RequestBody requestBody;
    private T t;
    private BufferedSink mBufferedSink;
    private OnUploadListener onUploadListener;

    public FileRequestBody(RequestBody requestBody, T t, OnUploadListener onUploadListener) {
        this.requestBody = requestBody;
        Log.d(TAG, "--> requestBody = " + requestBody.toString());
        this.t = t;
        this.onUploadListener = onUploadListener;
    }

    @Override
    public MediaType contentType() {
        MediaType mediaType = requestBody.contentType();
        Log.d(TAG, "mediaType = " + mediaType.toString());
        return mediaType;
    }

    @Override
    public void writeTo(BufferedSink bufferedSink) throws IOException {
        Log.d(TAG, "--> writeTo");
        if (mBufferedSink == null) {
            mBufferedSink = Okio.buffer(sink(bufferedSink));
        }
        requestBody.writeTo(mBufferedSink);
        mBufferedSink.flush();
    }

    private Sink sink(Sink sink) {
        return new ForwardingSink(sink) {

            long bytesWritten = 0L;
            long contentLength = 0L;




            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
//                Log.d(TAG + "--> contentLength = " + contentLength + ",bytesWritten = " + bytesWritten);

                if (contentLength == 0) {
                    contentLength = contentLength();
                }

                bytesWritten += byteCount;
                onUploadListener.onUpload(contentLength, bytesWritten);
            }
        };
    }

    @Override
    public long contentLength() throws IOException {
        return requestBody.contentLength();
    }

    public interface OnUploadListener {
        void onSuccess(String message);

        void onUpload(long contentLength, long bytesWritten);

        void onError(String message);
    }

}
