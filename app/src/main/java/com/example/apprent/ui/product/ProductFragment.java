package com.example.apprent.ui.product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.apprent.R;
import com.example.apprent.domain.models.ProductItem;
import com.example.apprent.ui.call_dialog.CallDialogFragment;
import com.example.apprent.ui.common.adapters.ImagesPagerAdapter;
import com.example.apprent.ui.main_activity.MainActivity;
import com.example.apprent.ui.main_activity.MainActivityViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class ProductFragment extends Fragment {

    private ProductItem product;
    private MainActivityViewModel mainActivityViewModel;
    private ProductFragmentVM vm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm = new ViewModelProvider(this).get(ProductFragmentVM.class);
        vm.setContext(getContext());
        FloatingActionButton reservation = view.findViewById(R.id.reservation_action_button);
        FloatingActionButton calendar = view.findViewById(R.id.calendar_action_button);
        ViewPager2 imagesPager = view.findViewById(R.id.images_pager);
        TextView price = view.findViewById(R.id.product_price_fragment);
        TextView name = view.findViewById(R.id.product_name_fragment);
        TextView description = view.findViewById(R.id.product_description_fragment);
        product = (ProductItem) getArguments().getSerializable("openProduct"); //todo
        mainActivityViewModel = ((MainActivity) getActivity()).getVM();
        List<String> imagesList = product.getImagesPath();
        ImagesPagerAdapter imagesPagerAdapter = new ImagesPagerAdapter(this, imagesList);
        imagesPager.setAdapter(imagesPagerAdapter);
        vm.loadImages(imagesList);
        price.setText(product.getMinPrice() + " руб/сутки");
        name.setText(product.getName());
        description.setText(product.getDescription());
        mainActivityViewModel.getBottomNavigationView().setVisibility(BottomNavigationView.INVISIBLE);
        mainActivityViewModel.getBackButtonState().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                mainActivityViewModel.setBackButtonState(false);
                Bundle bundle = new Bundle();
                bundle.putString("FullPath", getArguments().getString("FullPath"));
                mainActivityViewModel.getNavController().navigate(R.id.categoryFragment, bundle);
            }
        });
        calendar.setOnClickListener(v -> mainActivityViewModel.selectDate(product));
        reservation.setOnClickListener(v -> {
            CallDialogFragment dialogFragment = new CallDialogFragment();
            dialogFragment.show(getChildFragmentManager(), "dialog");
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        mainActivityVM.getBottomNavigationView().setVisibility(BottomNavigationView.VISIBLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        return view;
    }


}