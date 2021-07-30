package com.dragonfly.vanta.Views.Fragments.chat;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.dragonfly.vanta.R;
import com.dragonfly.vanta.ViewModels.ChatViewModel;
import com.vantapi.ChatByUserQuery;
import com.vantapi.SendMessageMutation;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class ChatMessagingFragment extends DialogFragment {

    private final static String TAG = "ChatMessagingDialog";

    String user;
    String user2;
    String chatId;

    ChatByUserQuery.ChatByUser chat;
    ArrayList<Spanned> chatMsg;
    ArrayAdapter<Spanned> adapter;

    ChatViewModel chatViewModel;


    ListView messageListView;
    Button sendButton;
    EditText messageEditText;
    TextView titleTextView;

    public ChatMessagingFragment(ChatByUserQuery.ChatByUser chat, String user, String user2){
        this.chat = chat;
        this.user = user;
        this.user2 = user2;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat_interface, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titleTextView = view.findViewById(R.id.textViewTitle);
        sendButton = view.findViewById(R.id.buttonSend);
        messageEditText = view.findViewById(R.id.editTextMessage);
        messageListView = view.findViewById(R.id.chat_messages_list);

        //Put all formated messages into a single list as SpannableStrings
        chatId = chat._id();
        chatMsg = new ArrayList<>();
        for (ChatByUserQuery.Conversation conv : chat.conversation()) {
            Spanned text = formatText(conv.sender(), conv.content());
            chatMsg.add(text);
        }
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, chatMsg);


        messageListView.setAdapter(adapter);
        messageListView.smoothScrollToPosition(adapter.getCount() -1);
        titleTextView.setText(user2);

        //Action for when the message is sent
        chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text_msg = messageEditText.getText().toString();
                if (!text_msg.isEmpty()) {
                    sendMessage(text_msg);
                }
            }
        });



    }


    // Add the User in Bold with message appended
    private Spanned formatText(String user, String msg){
        SpannableStringBuilder builder = new SpannableStringBuilder();
        StyleSpan boldSpan = new StyleSpan(android.graphics.Typeface.BOLD);
        builder.append(user + ": ", boldSpan, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(msg);

        return builder;
    }


    // Take message and put into the screen, the database and the send it trough the socket
    private void sendMessage(final String msg){
        chatViewModel.sendMessage(user, chatId, msg);
        chatViewModel.getMsgData().observe(this, new Observer<SendMessageMutation.Data>() {
            @Override
            public void onChanged(SendMessageMutation.Data data) {
                Spanned textInternal = formatText(user, msg);
                chatMsg.add(textInternal);
                messageListView.smoothScrollToPosition(adapter.getCount() -1);
                messageEditText.setText("");
                System.out.println(chatMsg.size());
            }
        });
    }
}
