package com.parse.starter.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.Adapter.HomeAdapter;
import com.parse.starter.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private ListView listView;
    private ArrayList<ParseObject> postagens;
    private ArrayAdapter<ParseObject> adapter;
    private ParseQuery<ParseObject> query;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //Criação do listView junto do adapter//

        postagens = new ArrayList<>();
        listView = (ListView) view.findViewById(R.id.list_postagens_home);
        adapter = new HomeAdapter( getActivity(), postagens  );
        listView.setAdapter( adapter );

        //Pegar as postagens

        getPostagens ();



        return view;
    }

    private void getPostagens(){

        //Recuperar as imagens

        query = ParseQuery.getQuery("Fotos");
        query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername() );
        query.orderByDescending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if( e==null ){//deu certo

                    if(objects.size() > 0){
                        postagens.clear();
                        for (ParseObject parseObject : objects){
                            postagens.add( parseObject );
                        }
                        adapter.notifyDataSetChanged();

                    }


                }else{//erro
                    e.printStackTrace();

                }


            }
        });


    }

    public void atualizaPostagens(){
        getPostagens();
    }

}
