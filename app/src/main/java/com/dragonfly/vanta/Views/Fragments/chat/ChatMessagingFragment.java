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

import com.dragonfly.vanta.R;
import com.vantapi.ChatByUserQuery;

import java.util.ArrayList;

public class ChatMessagingFragment extends DialogFragment {

    private final static String TAG = "ChatMessagingDialog";

    String user;
    ListView messageListView;
    Button sendButton;
    EditText messageEditText;
    TextView titleTextView;
    ChatByUserQuery.ChatByUser chat;
    ArrayList<Spanned> chatMsg;

    public ChatMessagingFragment(ChatByUserQuery.ChatByUser chat, String user){
        this.chat = chat;
        this.user = user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat_interface, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        chatMsg = new ArrayList<>();
        for (ChatByUserQuery.Conversation conv : chat.conversation()) {
            Spanned text = formatText(conv.sender(), conv.content());
            chatMsg.add(text);
        }
        ArrayAdapter<Spanned> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, chatMsg);

        titleTextView = view.findViewById(R.id.textViewTitle);
        sendButton = view.findViewById(R.id.buttonSend);
        messageEditText = view.findViewById(R.id.editTextMessage);
        messageListView = view.findViewById(R.id.chat_messages_list);
        messageListView.setAdapter(adapter);

        titleTextView.setText(user);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text_msg = messageEditText.getText().toString();
            }
        });
    }

    private Spanned formatText(String user, String msg){
        SpannableStringBuilder builder = new SpannableStringBuilder();
        StyleSpan boldSpan = new StyleSpan(android.graphics.Typeface.BOLD);
        builder.append(user + ": ", boldSpan, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(msg);

        return builder;
    }
}
