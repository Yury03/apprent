package com.example.apprent.ui.ordering;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.apprent.R;
import com.example.apprent.ui.main_activity.MainActivity;
import com.example.apprent.ui.main_activity.MainActivityViewModel;


public class OrderingFragment extends Fragment {
    private NavController navController;
    private OrderingViewModel vm;

    @SuppressLint("UseCompatLoadingForDrawables")//TODO()
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm = new ViewModelProvider(this).get(OrderingViewModel.class);
        Bundle arguments = getArguments();
        if (arguments != null) {
            vm.setProductList(arguments.getParcelableArrayList("CartProductsList"));
        }
        MainActivityViewModel mainActivityViewModel = ((MainActivity) getActivity()).getVM();
        mainActivityViewModel.getBottomNavigationView().setVisibility(View.GONE);
        mainActivityViewModel.setTitleOfTopBar(getString(R.string.title_top_bar_ordering));
        mainActivityViewModel.showBackButton();
        Button continueButton = view.findViewById(R.id.continue_button);
        ImageView imageView = view.findViewById(R.id.top_image_ordering_fragment);
        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager()
                .findFragmentById(R.id.ordering_fragment_container);
        navController = navHostFragment.getNavController();
        mainActivityViewModel.setOrderingViewModel(vm);
        mainActivityViewModel.getBackButtonState().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                mainActivityViewModel.setBackButtonState(false);
                int id = navController.getCurrentDestination().getId();
                if (id == R.id.shippingFragment) {
                    mainActivityViewModel.getNavController().navigate(R.id.cartFragment);
                    mainActivityViewModel.getBottomNavigationView().setVisibility(View.VISIBLE);
                } else if (id == R.id.payFragment) {
                    navController.popBackStack();
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.ordering_step_1));
                } else if (id == R.id.reviewOrderFragment) {
                    navController.popBackStack();
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.ordering_step_2));
                }
            }
        });



        /** посылается сигнал в дочерние фрагменты при нажатии на кнопку продолжить
         *  дочерние фрагменты собирают информацию конечную и, если она валидна, отправляют false
         *  в состояние кнопки продолжить и только в том случае происходит навигация */
        continueButton.setOnClickListener(v -> {
            vm.setButtonContinueState(true);
        });
        vm.getButtonContinueState().observe(getViewLifecycleOwner(), aBoolean -> {
            if (!aBoolean) {
                int id = navController.getCurrentDestination().getId();
                if (id == R.id.shippingFragment) {
                    imageView.setImageDrawable(getResources()
                            .getDrawable(R.drawable.ordering_step_2));
                    navController.navigate(R.id.action_shippingFragment_to_payFragment);
                } else if (id == R.id.payFragment) {
                    imageView.setImageDrawable(getResources()
                            .getDrawable(R.drawable.ordering_step_3));
                    continueButton.setText(R.string.pay_button);
                    navController.navigate(R.id.action_payFragment_to_reviewOrderFragment);
                } else if (id == R.id.reviewOrderFragment) {
                    mainActivityViewModel.getBottomNavigationView().setSelectedItemId(R.id.home_page);
                    mainActivityViewModel.getBottomNavigationView().setVisibility(View.VISIBLE);
                    mainActivityViewModel.sendOrderRequest();
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ordering, container, false);
        return view;
    }
}