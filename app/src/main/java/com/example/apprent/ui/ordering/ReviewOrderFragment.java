package com.example.apprent.ui.ordering;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.apprent.R;
import com.example.apprent.ui.main_activity.MainActivity;
import com.example.apprent.ui.main_activity.MainActivityViewModel;
import com.google.android.material.textfield.TextInputEditText;


public class ReviewOrderFragment extends Fragment {

    private MainActivityViewModel mainActivityViewModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainActivityViewModel = ((MainActivity) requireActivity()).getVM();
        OrderingViewModel orderingVM = mainActivityViewModel.getOrderingViewModel();

        TextView userName = view.findViewById(R.id.name_review_order);
        TextView userSecondName = view.findViewById(R.id.second_name_review_order);
        TextView address = view.findViewById(R.id.delivery_address_review_order);
        TextView phoneNumber = view.findViewById(R.id.phone_review_order);
        TextInputEditText comment = view.findViewById(R.id.order_comment);

        userName.setText(orderingVM.getFirstName());
        userSecondName.setText(orderingVM.getSecondName());
        address.setText(orderingVM.getAddress());
        phoneNumber.setText(orderingVM.getPhoneNumber());

        orderingVM.getButtonContinueState().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                orderingVM.setComment(comment.getText().toString());
                orderingVM.sendOrder(getContext(), mainActivityViewModel);//todo? получить контекст активити?
                orderingVM.setButtonContinueState(false);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_review_order, container, false);
    }
}