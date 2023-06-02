package com.example.apprent.ui.cart_page;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.apprent.R;
import com.example.apprent.data.cart_database.entity.CartProductEntity;
import com.example.apprent.ui.main_activity.MainActivity;
import com.example.apprent.ui.main_activity.MainActivityVM;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CartListFullItem extends Fragment {
    private CartProductEntity cartProduct;
    private CartFragmentVM cartFragmentVM;
    private MainActivityVM mainActivityVM;
    private int index;
    private View mainView;
    private TextView finalPriceText;

    private class Counter {
        public static final int STATE_COUNTER = 0;
        public static final int STATE_PERIOD = 1;
        public static final int STATE_DATE = 2;
        private final ImageButton plus;
        private final ImageButton minus;
        private final TextView num;
        private final int STATE;
        private boolean minusButtonIsNotActive = false;

        @SuppressLint("DefaultLocale")
        Counter(int idPlusButton, int idMinusButton, int idNumText, int state) {
            plus = mainView.findViewById(idPlusButton);
            minus = mainView.findViewById(idMinusButton);
            num = mainView.findViewById(idNumText);
            this.STATE = state;
            switch (STATE) {
                case STATE_COUNTER -> {
                    if (cartProduct.getQuantity() == 1) {
                        minus.setImageResource(R.drawable.button_minus_not_active);
                        this.minusButtonIsNotActive = true;
                    }
                    num.setText(String.valueOf(cartProduct.getQuantity()));
                }
                case STATE_PERIOD -> {
                    if (cartProduct.getPeriod() == 1) {
                        minus.setImageResource(R.drawable.button_minus_not_active);
                        this.minusButtonIsNotActive = true;
                    }
                    num.setText(String.valueOf(cartProduct.getPeriod()));
                }
                case STATE_DATE -> {
                    Date date = new Date();
                    long difference = cartProduct.getDate().getTime() - date.getTime();
                    double days = (double) (difference / (24 * 60 * 60 * 1000));
                    if (days < 1) {
                        minus.setImageResource(R.drawable.button_minus_not_active);
                        this.minusButtonIsNotActive = true;
                    }
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
                    String dateString = dateFormat.format(cartProduct.getDate());
                    num.setText(dateString);
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            cartProduct = (CartProductEntity) args.getSerializable("entity");
            cartFragmentVM = (CartFragmentVM) args.getSerializable("CartFragmentVM");
            index = args.getInt("index", -1);
            if (cartProduct == null || cartFragmentVM == null || index == -1)
                Log.e("MyApp", "Arg is NULL [CartFragment->CartListFullItem]");

        }
        mainActivityVM = ((MainActivity) getActivity()).getVM();
        this.mainView = view;
        TextView productName = view.findViewById(R.id.cart_full_item_name);
        ImageView productImage = view.findViewById(R.id.cart_full_item_product_image);
        finalPriceText = view.findViewById(R.id.cart_full_item_final_price);

        finalPriceText.setText(getContext().getString(R.string.full_cart_item_final_price) + String.valueOf(cartProduct.getFinalPrice()) + " рублей");
        productName.setText(cartProduct.getName());
        loadImage(productImage, cartProduct.getImageUri());

        Counter counter = new Counter(R.id.product_counter_plus, R.id.product_counter_minus, R.id.product_counter_num, Counter.STATE_COUNTER);
        Counter period = new Counter(R.id.product_period_plus, R.id.product_period_minus, R.id.product_period_num, Counter.STATE_PERIOD);
        Counter date = new Counter(R.id.product_date_plus, R.id.product_date_minus, R.id.product_date_num, Counter.STATE_DATE);

        counter.plus.setOnClickListener(v -> {
            if (checkPlus(counter)) {
                cartProduct.setQuantity(cartProduct.getQuantity() + 1);
                counter.num.setText(String.valueOf(cartProduct.getQuantity()));
                updatePrice();
            }
        });
        counter.minus.setOnClickListener(v -> {
            if (checkMinus(counter)) {
                cartProduct.setQuantity(cartProduct.getQuantity() - 1);
                counter.num.setText(String.valueOf(cartProduct.getQuantity()));
                updatePrice();
            }
        });
        period.plus.setOnClickListener(v -> {
            if (checkPlus(period)) {
                cartProduct.setPeriod(cartProduct.getPeriod() + 1);
                updatePrice();
            }
            period.num.setText(String.valueOf(cartProduct.getPeriod()));
        });
        period.minus.setOnClickListener(v -> {
            if (checkMinus(period)) {
                cartProduct.setPeriod(cartProduct.getPeriod() - 1);
                period.num.setText(String.valueOf(cartProduct.getPeriod()));
                updatePrice();
            }
        });
        date.plus.setOnClickListener(v -> {
            long cartDate = cartProduct.getDate().getTime();
            if (checkPlus(date)) {
                cartProduct.setDate(new Date(cartDate + 24 * 60 * 60 * 1000));
                @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
                String dateString = dateFormat.format(cartProduct.getDate());
                date.num.setText(dateString);
            }
        });
        date.minus.setOnClickListener(v -> {
            long cartDate = cartProduct.getDate().getTime();
            if (checkMinus(date)) {
                cartProduct.setDate(new Date(cartDate - 24 * 60 * 60 * 1000));
                @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
                String dateString = dateFormat.format(cartProduct.getDate());
                date.num.setText(dateString);
            }
        });

        mainActivityVM.getBackButtonState().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                mainActivityVM.setBackButtonState(false);
                mainActivityVM.getNavController().popBackStack();
                cartFragmentVM.closeDetails();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void updatePrice() {
        int price = cartProduct.getFinalPrice();
        finalPriceText.setText(getContext().getString(R.string.full_cart_item_final_price) + String.valueOf(price) + " рублей");
    }


    private boolean checkMinus(Counter counter) {
        if (counter.STATE == Counter.STATE_DATE) {
            //todo-заменить-дату-на-0:00-текущих-суток-для-сравнения-integer--
            //todo upd: а какая датаg.getTime()?
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            Date midnightDate = calendar.getTime();
//            Date date = new Date();
            long difference = cartProduct.getDate().getTime() - midnightDate.getTime();
            double days = (double) (difference / (24 * 60 * 60 * 1000));
            if (days <= 1) {
                counter.minus.setImageResource(R.drawable.button_minus_not_active);//todo?
                counter.minusButtonIsNotActive = true;
                return false;
            }
            return true;
        } else {
            String count = (String) counter.num.getText();
            int c = Integer.parseInt(count);
            switch (c) {
                case 1 -> {
                    Toast.makeText(getContext(), "Нельзя уменьшить это значение", Toast.LENGTH_LONG).show();
                    return false;
                }
                case 2 -> {
                    counter.minus.setImageResource(R.drawable.button_minus_not_active);
                    counter.minusButtonIsNotActive = true;
                    if (counter.STATE == Counter.STATE_PERIOD) {
                        counter.num.setText(String.valueOf(cartProduct.getPeriod()));
                    } else {
                        counter.num.setText(String.valueOf(cartProduct.getQuantity()));
                    }
                    return true;
                }
                default -> {
                    return true;
                }
            }
        }
    }

    private boolean checkPlus(Counter counter) {
        if (counter.minusButtonIsNotActive) {
            counter.minus.setImageResource(R.drawable.button_minus);
            counter.minusButtonIsNotActive = false;
        }
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.cart_list_full_item, container, false);
    }

    private void loadImage(ImageView imageView, String uri) {
        Glide.with(getActivity().getApplicationContext())//todo
                .load(uri)
//                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .placeholder(R.drawable.skeleton)//todo присоединить либу для скелетонов
                .into(imageView);
    }

    @Override
    public void onPause() {
        super.onPause();
        cartFragmentVM.changeDataFromDB((int) cartProduct.getId(), cartProduct);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cartFragmentVM.closeDetails();
    }
}
