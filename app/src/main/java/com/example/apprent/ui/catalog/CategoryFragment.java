package com.example.apprent.ui.catalog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.apprent.R;
import com.example.apprent.ui.catalog.adapters.CategoryAdapter;
import com.example.apprent.ui.catalog.adapters.ProductAdapter;
import com.example.apprent.ui.main_activity.MainActivity;
import com.example.apprent.ui.main_activity.MainActivityVM;

public class CategoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private CategoryFragmentVM vm;
    private MainActivityVM mainActivityVM;
    private Bundle arguments;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    //todo реализовать полное сохранение всех данных фрагмента, сейчас сохраняется только путь? и адаптер


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm = new ViewModelProvider(this).get(CategoryFragmentVM.class);
        ProgressBar progressBar = view.findViewById(R.id.progressBarCategory);
        recyclerView = view.findViewById(R.id.productList);//todo где выполнять поиск по id
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        mainActivityVM = ((MainActivity) getActivity()).getVM();//todo   | ? |
        arguments = getArguments();
        vm.setMainVM(mainActivityVM);
        vm.getShowProgressBar().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                progressBar.setVisibility(ProgressBar.VISIBLE);
            } else {
                progressBar.setVisibility(ProgressBar.GONE);
            }
        });
        NavBackStackEntry backStackEntry = mainActivityVM.getNavController().getPreviousBackStackEntry();
        if (backStackEntry == null) {
            Log.e("MyApp", "backStack is null");
        } else {
            if (backStackEntry.getDestination().getId() != R.id.productFragment) {
                vm.getCategoryList(vm.getFragmentPath());
            } else {
                mainActivityVM.getBottomNavigationView().setVisibility(View.VISIBLE);
                vm.setPath(arguments.getString("FullPath"));
                vm.getProductList(vm.getFragmentPath());
            }
        }
        vm.getCategoryItemArrayList().observe(getViewLifecycleOwner(), categoryItemArrayList -> {
            CategoryAdapter adapter = new CategoryAdapter(categoryItemArrayList, getContext(), vm);
            vm.setAdapter(adapter);
        });
        vm.getProductItemArrayList().observe(getViewLifecycleOwner(), productItems -> {
            ProductAdapter adapter = new ProductAdapter(productItems, getContext(), vm, getChildFragmentManager());
            vm.setAdapter(adapter);
        });
        vm.getOpenProduct().observe(getViewLifecycleOwner(), productItem -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("openProduct", productItem);
            bundle.putString("FullPath", vm.getFragmentPath());
            mainActivityVM.getNavController().navigate(R.id.productFragment, bundle);
        });
        mainActivityVM.getBackButtonState().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
//                mainActivityVM.setTitleOfTopBar("");
                mainActivityVM.setBackButtonState(false);
                String fullPath = vm.getFragmentPath();
                Log.e("MyApp", fullPath);
                fullPath = fullPath.substring(0, fullPath.lastIndexOf('/'));
                vm.getCategoryList(fullPath);//todo set title
            }
        });
        vm.getAdapter().observe(getViewLifecycleOwner(), adapter -> {
            recyclerView.setAdapter(adapter);//todo skeleton
        });
        mainActivityVM.getSearchResultsForCategoryFragment().observe(getViewLifecycleOwner(), productItems -> {
            Log.e("OkK", "OkK");
            ProductAdapter adapter = new ProductAdapter(productItems, getContext(), vm, getChildFragmentManager());
            vm.setAdapter(adapter);
        });
        vm.getTitle().observe(getViewLifecycleOwner(), s -> mainActivityVM.setTitleOfTopBar(s));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }
}