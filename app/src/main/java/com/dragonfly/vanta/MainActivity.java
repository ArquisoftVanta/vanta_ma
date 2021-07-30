package com.dragonfly.vanta;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.NavigationUI;

import com.dragonfly.vanta.ViewModels.ChatViewModel;
import com.dragonfly.vanta.Views.Fragments.chat.ChatListFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.vantapi.ChatByUserQuery;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    ChatViewModel chatViewModel;
    DrawerLayout drawerLayout;
    TextView mailText;
    Toolbar toolbar;
    NavigationView navigationView;
    NavController navController;
    ActionBarDrawerToggle drawerToggle;
    MenuItem logoutItem;

    FloatingActionButton floatChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
            logoutItem = navigationView.getMenu().findItem(R.id.logOut);
            mailText = navigationView.getHeaderView(0).findViewById(R.id.textViewMail);
        toolbar = findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);


        //Setup automatic fragment navigation from the xml settings
        navController = Navigation.findNavController(this, R.id.nav_fragment);
        NavigationUI.setupWithNavController(navigationView, navController);
        final String mail = getMail();
        mailText.setText(mail);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.abrir, R.string.cerrar);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        //Add extra actions for navigation items
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {            }
        });

        //Add actions for non-navigation items
        logoutItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                logOut();
                return true;
            }
        });

        //Chat configuration
        floatChat = findViewById(R.id.floatingActionButton);
        chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);

        floatChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatViewModel.getChatByUser(mail);
            }
        });
        chatViewModel.getChatsData().observe(this, new Observer<ChatByUserQuery.Data>() {
            @Override
            public void onChanged(ChatByUserQuery.Data data) {
                ChatListFragment chatList = new ChatListFragment(data, mail);
                chatList.show(getSupportFragmentManager(), "ChatListDialog");
            }
        });
    }

    //Borrar el token y salirse a la actividad le logeo
    private void logOut() {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("jwt", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("token", "");
        editor.commit();

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public String getMail(){
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("jwt", Context.MODE_PRIVATE);
        String mail = sharedPref.getString("email", "example@mail.com");
        return mail;
    }
    public String getToken(){
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("jwt", Context.MODE_PRIVATE);
        String token = sharedPref.getString("token", "example@mail.com");
        return token;
    }

    //Setup for date picker used in Request and Service creation
    public void getDateDialog(final EditText dateEdit){
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String mString = String.valueOf(month);
                String dString = String.valueOf(dayOfMonth);
                if(month < 9){
                    mString = "0"+ mString;
                }
                if(dayOfMonth < 9){
                    dString = "0" + dString;
                }
                String dateString = String.valueOf(year) + "-" + mString + "-" + dString;
                dateEdit.setText(dateString);
            }
        };
        DatePickerDialog datePicker = new DatePickerDialog(this, R.style.DialogTheme , dateSetListener,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePicker.show();
    }
}