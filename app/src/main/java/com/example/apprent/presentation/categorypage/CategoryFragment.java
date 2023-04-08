package com.example.apprent.presentation.categorypage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apprent.R;

public class CategoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private CategoryFragmentVM vm;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vm = new ViewModelProvider(this).get(CategoryFragmentVM.class);
        recyclerView = view.findViewById(R.id.productList);//todo? где выполнять поиск по id
        vm.getCategoryItemArrayList().observe(getViewLifecycleOwner(), categoryItemArrayList -> {
            Log.e("HELP", "OK");
            CategoryAdapter adapter = new CategoryAdapter(categoryItemArrayList, getContext());
            recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
            recyclerView.setAdapter(adapter);
        });
        vm.getCategoryList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }
}