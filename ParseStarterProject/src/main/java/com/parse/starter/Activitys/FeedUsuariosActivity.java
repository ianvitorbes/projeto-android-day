package com.parse.starter.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.Adapter.HomeAdapter;
import com.parse.starter.R;

import java.util.ArrayList;
import java.util.List;

public class FeedUsuariosActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;
    private String userName;
    private ArrayAdapter<ParseObject> adapter;
    private ArrayList<ParseObject> postagens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_usuarios);

        //Recuperar os dadados da intent

        Intent intent = getIntent();
        userName = intent.getStringExtra("username");


        //--//

        toolbar = (Toolbar) findViewById(R.id.toolbar_feed_usuario);
        toolbar.setTitle(userName);
        toolbar.setTitleTextColor(R.color.Preta);
        setSupportActionBar(toolbar);

        //Config list view e adapter
        postagens = new ArrayList<>();
        listView = (ListView) findViewById(R.id.list_feed_usuario);
        adapter = new HomeAdapter( getApplicationContext(), postagens );
        listView.setAdapter(adapter);

        //recup dados
        getPostagens();

    }

    private void getPostagens(){
        //Recupera as postagens

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Fotos");
        query.whereEqualTo("username", userName);
        query.orderByAscending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if(e==null){//certo
                    if(objects.size() > 0){

                        postagens.clear();
                        for (ParseObject parseObject : objects){
                            postagens.add(parseObject);
                        }
                        adapter.notifyDataSetChanged();

                    }

                }else{//erro
                    Toast.makeText(getApplicationContext(),
                            "Erro ao recuperar as postagens desse usuario",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

}
