package com.example.apprent.ui.product_page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.apprent.R;
import com.example.apprent.domain.models.ProductItem;
import com.example.apprent.ui.main_activity.MainActivityVM;

import java.util.List;


public class ProductFragment extends Fragment {

    private ProductItem product;
    private MainActivityVM mainActivityVM;
    private int imageNum = 0;
    private ProductFragmentVM vm;

    public ProductFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm = new ViewModelProvider(this).get(ProductFragmentVM.class);
        vm.setContext(getContext());

        product = (ProductItem) getArguments().getSerializable("openProduct"); //todo
        ImageSwitcher productImageSwitcher = view.findViewById(R.id.product_image_switcher);
        //todo?        productImageSwitcher.setImageURI();
        vm.setProductImageSwitcher(productImageSwitcher);
        List<String> imagesList = product.getImagesPath();
        vm.setImage(product.getMainImagePath());
        vm.loadImages(imagesList);
        Animation slideInLeftAnimation = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left);
        Animation slideOutRight = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_out_right);


//        productImageSwitcher.setOutAnimation(slideOutRight);
        productImageSwitcher.setOnClickListener(v -> {
            imageNum++;
            if (imageNum > imagesList.size() - 1) {
                imageNum = 0;
            }
            vm.setImage(imagesList.get(imageNum));
//            productImageSwitcher.setInAnimation(slideInLeftAnimation);
        });


        mainActivityVM = (MainActivityVM) getArguments().getSerializable("MainActivityVM");

        TextView price = view.findViewById(R.id.product_price_fragment);
        price.setText(product.getMinPrice());
        TextView name = view.findViewById(R.id.product_name_fragment);
        name.setText(product.getName());
        TextView description = view.findViewById(R.id.product_description_fragment);
        description.setText(product.getDescription());
//        mainActivityVM.getBottomNavigationView().setVisibility(BottomNavigationView.INVISIBLE);
        mainActivityVM.getBackButtonState().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                mainActivityVM.setBackButtonState(false);
                Bundle bundle = new Bundle();
                bundle.putSerializable("MainActivityVM", getArguments().getSerializable("MainActivityVM"));
                bundle.putString("FullPath", getArguments().getString("FullPath"));
                mainActivityVM.getNavController().navigate(R.id.categoryFragment, bundle);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        mainActivityVM.getBottomNavigationView().setVisibility(BottomNavigationView.VISIBLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product, container, false);
    }


}