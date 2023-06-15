package com.example.apprent.ui.ordering;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.example.apprent.R;
import com.example.apprent.ui.main_activity.MainActivity;
import com.google.android.material.textfield.TextInputEditText;


public class PayFragment extends Fragment {

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OrderingViewModel orderingVM = ((MainActivity) requireActivity()).getVM().getOrderingViewModel();
        SwitchCompat isPostponePayment = view.findViewById(R.id.postpone_payment);
        TextInputEditText cardNumber = view.findViewById(R.id.card_number);
        TextInputEditText cardDate = view.findViewById(R.id.card_date);
        TextInputEditText cardCVV = view.findViewById(R.id.card_cvv);
        TextInputEditText promoCode = view.findViewById(R.id.promo_code);
        Button applyPromoCode = view.findViewById(R.id.apply_promocode);
        applyPromoCode.setOnClickListener(v -> orderingVM.applyPromoCode(promoCode.getText().toString()));
        TextView enterCard = view.findViewById(R.id.enter_card);
        enterCard.setText("Оплата онлайн временно не работает");
        TextView finalPrice = view.findViewById(R.id.final_price_pay_fragment);
        orderingVM.getFinalPrice().observe(getViewLifecycleOwner(), s ->
                finalPrice.setText(getString(R.string.final_price_fragment_pay) + s + getString(R.string.currency)));
        isPostponePayment.setChecked(true);
        isPostponePayment.setClickable(false);
        cardNumber.setClickable(false);
        cardDate.setClickable(false);
        cardCVV.setClickable(false);
//        isPostponePayment.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) {
//                cardNumber.setClickable(false);
//                cardDate.setClickable(false);
//                cardCVV.setClickable(false);
//                cardNumber.setText("");
//                cardDate.setText("");
//                cardCVV.setText("");
//            } else {
//                cardNumber.setClickable(true);
//                cardDate.setClickable(true);
//                cardCVV.setClickable(true);
//            }
//        });
        orderingVM.getButtonContinueState().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                orderingVM.setPaid(isPostponePayment.isChecked());
                orderingVM.setButtonContinueState(false);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pay, container, false);
    }
}