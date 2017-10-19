package com.adam.gankarch;

import com.adam.gankarch.data.api.GankApi;
import com.adam.gankarch.data.bean.GankEntity;
import com.adam.gankarch.data.support.BaseResponse;
import com.adam.gankarch.data.support.RetrofitHelper;

import org.reactivestreams.Publisher;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * @author yu
 *         Create on 2017/10/19.
 */

public class Ass {
    public Ass(){
        RetrofitHelper.Companion.getInstance()
                .createService(GankApi.class)
                .getRandomGirl()
                .flatMap(new Function<BaseResponse<List<GankEntity>>, Publisher<String>>() {
                    @Override
                    public Publisher<String> apply(@NonNull BaseResponse<List<GankEntity>> listBaseResponse) throws Exception {
                        return null;
                    }
                });
    }
}
