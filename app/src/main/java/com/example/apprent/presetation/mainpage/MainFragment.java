package com.example.apprent.presetation.mainpage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apprent.R;

public class MainFragment extends Fragment {
    Boolean signIn = false;
    private static final String Tag = "MyApp";
    private SharedPreferences sharedPreferences;
    private RecyclerView recyclerView;

    private MainFragmentVM vm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(Tag, "onCreate [MainFragment]");
        Context context = getActivity();
        if (context != null) {
            sharedPreferences = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(Tag, "onViewCreated [MainFragment]");
        vm = new ViewModelProvider(this).get(MainFragmentVM.class);
        recyclerView = view.findViewById(R.id.productList);//todo? где выполнять поиск по id
        vm.getCategoryItemArrayList().observe(getViewLifecycleOwner(), categoryItemArrayList -> {
            Log.e("HELP", "OK");
            CategoryAdapter adapter = new CategoryAdapter(categoryItemArrayList, getContext());
            recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
            recyclerView.setAdapter(adapter);
        });
        signIn = sharedPreferences.getBoolean(getString(R.string.saved_log_in_key), false);
        vm.getCategoryList();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

}