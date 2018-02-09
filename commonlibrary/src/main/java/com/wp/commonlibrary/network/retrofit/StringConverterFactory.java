package com.wp.commonlibrary.network.retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Retrofit的StringConverter
 * Created by WangPing on 2018/1/17.
 */

public class StringConverterFactory extends Converter.Factory {

    private static final MediaType TEXT = MediaType.parse("text/plain");

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (String.class.equals(type)) {
            return (Converter<ResponseBody, String>) value -> value.string();
        }
        return null;
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations,
                                                          Annotation[] methodAnnotations, Retrofit retrofit) {
        if (String.class.equals(type)) {
            return (Converter<String, RequestBody>) value -> RequestBody.create(TEXT, value);
        }
        return null;
    }

    static Converter.Factory create() {
        return new StringConverterFactory();
    }
}
