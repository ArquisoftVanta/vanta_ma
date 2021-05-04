package com.dragonfly.vanta.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dragonfly.vanta.Model.Repository.RepositoryChat;
import com.vantapi.ChatByUserQuery;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ChatViewModel extends ViewModel {
    private RepositoryChat repositoryChat;

    private MutableLiveData<ChatByUserQuery.Data> chatData = new MutableLiveData<>();
    private MutableLiveData<String> toastObserver = new MutableLiveData();


    public LiveData<String> getToastObserver(){
        return toastObserver;
    }

    public MutableLiveData<ChatByUserQuery.Data> getChatData() { return chatData; }

    public ChatViewModel() { this.repositoryChat = new RepositoryChat();}

    public void getChatByUser(String mail){
        CompletableFuture<ChatByUserQuery.Data> res = repositoryChat.gqlChatByUser(mail);
        try{
            ChatByUserQuery.Data promData = res.get(2L, TimeUnit.SECONDS);
            chatData.postValue(promData);
        }catch(Error | ExecutionException | InterruptedException | TimeoutException e){
            toastObserver.setValue("No se pudo comunicar con el servidor");
        }
    }
}
