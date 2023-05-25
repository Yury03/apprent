package com.example.apprent.domain;

import com.example.apprent.domain.models.AuraUser;
import com.example.apprent.domain.usecase.AuthenticationCallback;
import com.example.apprent.domain.usecase.CategoryListCallback;
import com.example.apprent.domain.usecase.ProductListCallback;

public interface MainContract {
    interface GetItemsListData {
        void getCategoryList(CategoryListCallback callback);

        void getCategoryList(CategoryListCallback callback, String subCategory);

        void getProductsList(ProductListCallback callback, String category);

        void getSearchResults(ProductListCallback callback, String query, String path);

    }


    interface Authentication {
        void restoreAccess(AuthenticationCallback.restoreAccessCallback callback, AuraUser user);

        void signIn(AuthenticationCallback.signInCallback callback, AuraUser user);

        void signUp(AuthenticationCallback.signUpCallback callback, AuraUser user);

    }
}
