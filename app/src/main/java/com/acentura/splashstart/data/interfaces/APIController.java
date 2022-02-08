package com.acentura.splashstart.data.interfaces;

import static com.acentura.splashstart.util.constant.NetConstants.GET_POST;

import com.acentura.splashstart.data.network.model.Post;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface APIController {

    @GET(GET_POST)
    Single<List<Post>> getPosts();
}
