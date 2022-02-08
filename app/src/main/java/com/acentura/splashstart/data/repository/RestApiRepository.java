package com.acentura.splashstart.data.repository;

import com.acentura.splashstart.data.interfaces.APIController;
import com.acentura.splashstart.data.network.model.Post;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class RestApiRepository {

    private final APIController apiController;

    @Inject
    public RestApiRepository(APIController apiController) {
        this.apiController = apiController;
    }

    public Single<List<Post>> getPosts() {
        return apiController.getPosts();
    }
}
