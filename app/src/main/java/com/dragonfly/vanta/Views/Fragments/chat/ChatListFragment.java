package com.dragonfly.vanta.Views.Fragments.chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.dragonfly.vanta.R;
import com.vantapi.ChatByUserQuery;

import java.util.ArrayList;
import java.util.List;

public class ChatListFragment extends DialogFragment {

    private final static String TAG = "ChatListDialog";

    ListView listView;
    String chatUser;
    ChatByUserQuery.Data chatData;
    ArrayList<String> chatNames;

    public ChatListFragment(ChatByUserQuery.Data data, String user) {
        this.chatData = data;
        this.chatUser = user;
    }


    // TODO: Rename and change types and number of parameters
    public static ChatListFragment newInstance(ChatByUserQuery.Data data, String user) {
        return new ChatListFragment(data, user);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        chatNames = new ArrayList<>();

        //Fill array with names of conversations and present it in list view
        if(chatData.chatByUser().isEmpty()){
            chatNames.add("Usted no tiene ningun chat");
        }

        for(ChatByUserQuery.ChatByUser chat : chatData.chatByUser()){
            if(chatUser.equals(chat.user1())){
                chatNames.add(chat.user2());
            }else if(chatUser.equals(chat.user2())){
                chatNames.add(chat.user1());
            }
        }

        //Put all Chat info in the array into the List View
        listView = view.findViewById(R.id.chatList);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, chatNames);
        listView.setAdapter(adapter);

        //Return info of the selected chat
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!chatNames.get(position).equals("Usted no tiene ningun chat")){
                    ChatByUserQuery.ChatByUser chat = chatData.chatByUser().get(position);
                    ChatMessagingFragment chatMessagingFragment = new ChatMessagingFragment(chat._id() , chatUser, chatNames.get(position));
                    chatMessagingFragment.show(getParentFragmentManager(), "ChatMessagingDialog");
                }
            }
        });
    }
}