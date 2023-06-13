package com.example.apprent.domain;

import com.example.apprent.domain.models.AuraUser;
import com.example.apprent.domain.models.Order;
import com.example.apprent.domain.usecase.authentication.AuthenticationCallback;
import com.example.apprent.domain.usecase.get_category.CategoryListCallback;
import com.example.apprent.domain.usecase.get_links.LinksCallback;
import com.example.apprent.domain.usecase.get_products.ProductListCallback;
import com.example.apprent.domain.usecase.orders.get.GetOrdersCallback;
import com.example.apprent.domain.usecase.orders.send.SendOrdersCallback;

public interface MainContract {
    interface GetListData {
        void getCategoryList(CategoryListCallback callback);

        void getCategoryList(CategoryListCallback callback, String subCategory);

        void getProductsList(ProductListCallback callback, String category);

        void getSearchResults(ProductListCallback callback, String query, String path);

        void getBannerImages(LinksCallback linksCallback);
    }


    interface Authentication {
        void restoreAccess(AuthenticationCallback.restoreAccessCallback callback, AuraUser user);

        void signIn(AuthenticationCallback.signInCallback callback, String login, String password);

        void signUp(AuthenticationCallback.signUpCallback callback, String login, String password);

    }

    interface GetOrders {
        void getOrders(GetOrdersCallback callback, String group);
    }

    interface SendOrders {
        void sendOrders(SendOrdersCallback callback, Order order);
    }
//    interface SendCartData{
//        void sendCartData(SendCartDataCallback callback, )
//    }
}
