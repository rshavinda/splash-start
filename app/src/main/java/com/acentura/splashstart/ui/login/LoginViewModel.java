package com.acentura.splashstart.ui.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.acentura.splashstart.data.network.model.Post;
import com.acentura.splashstart.data.repository.RestApiRepository;
import com.acentura.splashstart.util.reponse.NetResource;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class LoginViewModel extends ViewModel {

    private RestApiRepository repository;
    private final CompositeDisposable disposables;
    private final MutableLiveData<NetResource<List<Post>>> responseLiveData = new MutableLiveData<>();

    @Inject
    public LoginViewModel(RestApiRepository repository) {
        this.repository = repository;
        this.disposables= new CompositeDisposable();
    }

    public String getData(){
        return "Hi";
    }


    public void getPosts() {
        Timber.d("getApplicantsList");
        responseLiveData.setValue(NetResource.loading());
        disposables.add(repository.getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Post>>() {
                    @Override
                    public void onSuccess(@NotNull List<Post> postList) {
                        Timber.d("getPosts %s", new Gson().toJson(postList));
                        responseLiveData.postValue(NetResource.success("", postList));
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        Timber.d("getPosts Error %s", e.getMessage());
                        Timber.v(" Exception %s", new Gson().toJson(e));
                        responseLiveData.postValue(NetResource.error(e));
                    }
                }));
    }

    public MutableLiveData<NetResource<List<Post>>> getResponseLiveData() {
        return responseLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
