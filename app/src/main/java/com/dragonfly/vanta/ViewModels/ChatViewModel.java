package com.dragonfly.vanta.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dragonfly.vanta.Model.Repository.RepositoryChat;
import com.vantapi.ChatByIdQuery;
import com.vantapi.ChatByUserQuery;
import com.vantapi.SendMessageMutation;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ChatViewModel extends ViewModel {
    private RepositoryChat repositoryChat;

    private MutableLiveData<ChatByUserQuery.Data> chatsData = new MutableLiveData<>();
    private MutableLiveData<ChatByIdQuery.Data> chatData = new MutableLiveData<>();
    private MutableLiveData<SendMessageMutation.Data> msgData = new MutableLiveData<>();
    private MutableLiveData<String> toastObserver = new MutableLiveData();


    public LiveData<String> getToastObserver(){
        return toastObserver;
    }

    public MutableLiveData<ChatByUserQuery.Data> getChatsData() { return chatsData; }
    public MutableLiveData<ChatByIdQuery.Data> getChatData() { return chatData; }
    public MutableLiveData<SendMessageMutation.Data> getMsgData() { return msgData; }

    public ChatViewModel() { this.repositoryChat = new RepositoryChat();}

    public void getChatByUser(String mail){
        CompletableFuture<ChatByUserQuery.Data> res = repositoryChat.gqlChatByUser(mail);
        try{
            ChatByUserQuery.Data promData = res.get(2L, TimeUnit.SECONDS);
            chatsData.postValue(promData);
        }catch(Error | ExecutionException | InterruptedException | TimeoutException e){
            toastObserver.setValue("No se pudo comunicar con el servidor");
        }
    }

    public void getChatById(String mail, String chatId){
        CompletableFuture<ChatByIdQuery.Data> res = repositoryChat.gqlChatById(mail, chatId);
        try{
            ChatByIdQuery.Data promData = res.get(2L, TimeUnit.SECONDS);
            chatData.postValue(promData);
        }catch(Error | ExecutionException | InterruptedException | TimeoutException e){
            toastObserver.setValue("No se pudo comunicar con el servidor");
        }
    }

    public void sendMessage(String mail, String chatId, String content){
        CompletableFuture<SendMessageMutation.Data> res = repositoryChat.gqlSendMessage(mail, chatId, content);
        try{
            SendMessageMutation.Data promData = res.get(2L, TimeUnit.SECONDS);
            msgData.postValue(promData);
        }catch(Error | ExecutionException | InterruptedException | TimeoutException e){
            toastObserver.setValue("No se pudo comunicar con el servidor");
        }
    }
}
