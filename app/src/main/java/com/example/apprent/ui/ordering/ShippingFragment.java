package com.example.apprent.ui.ordering;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.example.apprent.R;
import com.example.apprent.ui.main_activity.MainActivity;
import com.google.android.material.textfield.TextInputEditText;


public class ShippingFragment extends Fragment {

    private TextInputEditText userFirstName;
    private TextInputEditText phoneNumber;
    private TextInputEditText deliveryAddress;
    private TextInputEditText userSecondName;
    private boolean isPickup = false;

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OrderingViewModel orderingVM = ((MainActivity) requireActivity()).getViewModel().getOrderingViewModel();
        userFirstName = view.findViewById(R.id.user_name_ordering);
        userSecondName = view.findViewById(R.id.user_second_name_ordering);
        deliveryAddress = view.findViewById(R.id.delivery_address);
        phoneNumber = view.findViewById(R.id.user_phone_ordering);
        TextView finalPrice = view.findViewById(R.id.final_price_shipping_fragment);
        SwitchCompat pickupSwitch = view.findViewById(R.id.is_pickup);
        pickupSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                deliveryAddress.setVisibility(View.INVISIBLE);
                isPickup = true;
            } else {
                if (deliveryAddress.getVisibility() == View.INVISIBLE) {
                    deliveryAddress.setVisibility(View.VISIBLE);
                }
                isPickup = false;
            }
        });
        orderingVM.getButtonContinueState().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean && fieldsAreValidity()) {
                if (isPickup) {
                    orderingVM.setAddress(getString(R.string.pickup_string));
                    orderingVM.setDelivery(false);
                } else {
                    orderingVM.setAddress(deliveryAddress.getText().toString());
                    orderingVM.setDelivery(true);
                }
                orderingVM.setPhoneNumber(phoneNumber.getText().toString());
                orderingVM.setFirstName(userFirstName.getText().toString());
                orderingVM.setSecondName(userSecondName.getText().toString());
                orderingVM.setButtonContinueState(false);
            }
        });
        orderingVM.getFinalPrice().observe(getViewLifecycleOwner(), s ->//todo разбить цены на аренду доставку и final
                finalPrice.setText(getString(R.string.final_price_shipping_fragment) + s + getString(R.string.currency)));
    }

    private boolean fieldsAreValidity() {
        boolean result;
        if (deliveryAddress.getText() != null && phoneNumber.getText() != null
                && userFirstName.getText() != null && userSecondName.getText() != null) {
            result = userFirstName.getText().toString().length() > 1;
            if (!result) {
                Toast.makeText(getContext(), R.string.name_error, Toast.LENGTH_LONG).show();
                return false;
            }
            result = userSecondName.getText().toString().length() > 1;
            if (!result) {
                Toast.makeText(getContext(), R.string.second_name_error, Toast.LENGTH_LONG).show();
                return false;
            }
            result = deliveryAddress.getText().toString().length() > 10 || isPickup;
            if (!result) {
                Toast.makeText(getContext(), R.string.address_error, Toast.LENGTH_LONG).show();
                return false;
            }
            result = phoneNumber.getText().toString().trim().length() == 11;
            if (!result) {
                Toast.makeText(getContext(), R.string.phone_error, Toast.LENGTH_LONG).show();
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shipping, container, false);
    }
}