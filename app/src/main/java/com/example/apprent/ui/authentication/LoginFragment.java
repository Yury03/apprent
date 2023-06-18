package com.example.apprent.ui.authentication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.apprent.R;
import com.example.apprent.ui.authentication.adapters.LoginPagerAdapter;
import com.example.apprent.ui.main_activity.MainActivity;
import com.example.apprent.ui.main_activity.MainActivityViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class LoginFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private LoginFragmentVM vm;
    private MainActivityViewModel mainActivityViewModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm = new ViewModelProvider(this).get(LoginFragmentVM.class);
        vm.initAuthentication(getContext());
        mainActivityViewModel = ((MainActivity) getActivity()).getVM();//todo   | ? |
        mainActivityViewModel.getBottomNavigationView().setVisibility(View.INVISIBLE);
        sharedPreferences = mainActivityViewModel.getSharedPreferences();
        TabLayout tabLayout = view.findViewById(R.id.tab_buttons_authentication);
        ViewPager2 loginPager = view.findViewById(R.id.view_pager);
        LoginPagerAdapter loginPagerAdapter = new LoginPagerAdapter(this, vm);
        loginPager.setAdapter(loginPagerAdapter);
        new TabLayoutMediator(tabLayout, loginPager, (tab, position) -> {
            if (position == 0) {
                tab.setText(getString(R.string.sign_in));
            } else if (position == 1) {
                tab.setText(getString(R.string.sign_up));
            }
        }).attach();
        vm.getUserLiveData().observe(getViewLifecycleOwner(), state -> {
            switch (state) {
                case USER_SIGN_IN -> {
                    sharedPreferences.edit().putInt(getResources().getString(R.string.saved_log_in_key), state.stateId).apply();
//                    mainActivityVM.authCompleted();
//                    vm.authCompleted(getContext(), state)
                    mainActivityViewModel.getNavController().navigate(R.id.mainFragment);
                    mainActivityViewModel.getBottomNavigationView().setSelectedItemId(R.id.home_page);
                }
                case SIGN_UP -> {
                    sharedPreferences.edit().putInt(getResources().getString(R.string.saved_log_in_key), state.stateId).apply();
//                    mainActivityVM.authCompleted();
                    mainActivityViewModel.getNavController().navigate(R.id.mainFragment);
                    mainActivityViewModel.getBottomNavigationView().setSelectedItemId(R.id.home_page);
                    Toast.makeText(getContext(), "Спасибо за регистрацию!", Toast.LENGTH_LONG).show();
                }
                case RESTORE_ACCESS ->
                        Toast.makeText(getContext(), "Ссылка на восстановление была отправлена на почту", Toast.LENGTH_LONG).show();
                case SIGN_IN_ERROR ->
                        Toast.makeText(getContext(), "Неправильная почта или пароль", Toast.LENGTH_LONG).show();
                case SIGN_UP_ERROR ->
                        Toast.makeText(getContext(), "Пользователь с такой почтой уже зарегестрирован", Toast.LENGTH_LONG).show();
                case ADMIN_SIGN_IN -> {
                    mainActivityViewModel.getNavController().navigate(R.id.adminFragment);
                    mainActivityViewModel.getBottomNavigationView().setVisibility(View.INVISIBLE);
                    sharedPreferences.edit().putInt(getResources().getString(R.string.saved_log_in_key), state.stateId).apply();
                }
                case RESTORE_ACCESS_ERROR, INIT -> {
                    // ignore
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onStop() {
        super.onStop();
        mainActivityViewModel.getBottomNavigationView().setVisibility(View.VISIBLE);
    }
}