package com.example.apprent.ui.ordering;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.apprent.R;
import com.example.apprent.data.cart_database.entity.CartEntity;
import com.example.apprent.data.network.orders.SendOrdersImpl;
import com.example.apprent.domain.models.Order;
import com.example.apprent.domain.usecase.orders.send.SendOrders;
import com.example.apprent.ui.main_activity.MainActivityViewModel;

import java.util.List;

public class OrderingViewModel extends ViewModel {
    private boolean isPaid;
    private final MutableLiveData<List<CartEntity>> productsList = new MutableLiveData<>();

    public void setComment(String comment) {
        this.comment = comment;
    }

    private String comment;

    public void setDelivery(boolean delivery) {
        isDelivery = delivery;
    }

    private boolean isDelivery;

    public LiveData<Boolean> getButtonContinueState() {
        return buttonContinueState;
    }

    private final MutableLiveData<Boolean> buttonContinueState = new MutableLiveData<>();
    private final MutableLiveData<String> finalPrice = new MutableLiveData<>();

    public LiveData<List<CartEntity>> getProductsList() {
        return productsList;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public String getComment() {
        return comment;
    }

    public boolean isDelivery() {
        return isDelivery;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    private String firstName;
    private String secondName;

    public String getAddress() {
        return address;
    }

    private String address;
    private String phoneNumber;


    public void setButtonContinueState(boolean b) {
        buttonContinueState.postValue(b);
    }

    public LiveData<String> getFinalPrice() {
        return this.finalPrice;
    }

    public void applyPromoCode(String toString) {

    }

    public void setProductList(List<CartEntity> cartProductsList) {
        this.productsList.postValue(cartProductsList);
        int finalPrice = 0;
        for (CartEntity cartEntity : cartProductsList) {
            finalPrice += cartEntity.getFinalPrice();
        }
        this.finalPrice.postValue(String.valueOf(finalPrice));
    }


    public void sendOrder(Context context, MainActivityViewModel mainActivityViewModel) {
        SendOrdersImpl sendOrders = new SendOrdersImpl(context);
        SendOrders sendOrdersUseCase = new SendOrders(sendOrders);
        Order order = new Order(131, isDelivery, isPaid, productsList.getValue(), phoneNumber,
                Order.State.EXPECTED, firstName, secondName, comment);
        order.setDeliveryAddress(address);
        sendOrdersUseCase.execute(Error -> {
            if (Error == Order.SendOrderError.ORDER_IS_SEND) {
                Toast.makeText(context, R.string.order_is_send, Toast.LENGTH_LONG).show();
                mainActivityViewModel.clearCart(productsList.getValue());
            } else {
                Toast.makeText(context, R.string.order_send_error, Toast.LENGTH_LONG).show();
            }
        }, order);
    }
}
