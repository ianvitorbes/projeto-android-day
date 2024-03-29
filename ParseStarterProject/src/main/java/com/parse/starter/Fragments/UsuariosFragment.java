package com.parse.starter.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.Activitys.FeedUsuariosActivity;
import com.parse.starter.Adapter.UsuariosAdapter;
import com.parse.starter.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsuariosFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter<ParseUser> adapter;
    private ArrayList<ParseUser> usuarios;
    private ParseQuery<ParseUser> query;


    public UsuariosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_usuarios, container, false);

        //Criação de list view e adapter

        usuarios = new ArrayList<>();
        listView = (ListView) view.findViewById(R.id.list_usuarios);
        adapter = new UsuariosAdapter(getActivity(), usuarios );
        listView.setAdapter( adapter );

        //Recup usuarios
        getUsuarios();

        //Click lista ! pesquisar como fazer esse evento

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Busca os dados a serem passados
                ParseUser parseUser = usuarios.get(position);

                //Retorna os dados
                Intent intent = new Intent(getActivity(), FeedUsuariosActivity.class);
                intent.putExtra("username", parseUser.getUsername() );

                startActivity(intent);

            }
        });


        return view;
    }

    private void getUsuarios(){
        //Recupera todos os usuarios
        query = ParseUser.getQuery();
        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername() );
        query.orderByAscending("username");

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e==null){//deu certo

                    if(objects.size() > 0){
                        usuarios.clear();

                        for (ParseUser parseUser : objects){
                           usuarios.add(parseUser);
                        }
                        adapter.notifyDataSetChanged();

                    }

                }else{//errado
                    e.printStackTrace();
                }
            }
        });



    }

}
