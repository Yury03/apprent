package com.example.apprent.ui.profile;

import static com.example.apprent.domain.models.AuraUser.State.USER_NOT_SIGN_IN;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.apprent.R;
import com.example.apprent.ui.main_activity.MainActivity;
import com.example.apprent.ui.main_activity.MainActivityVM;


public class ProfileFragment extends Fragment {


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button logoutButton = view.findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(v -> {
            MainActivityVM mainActivityVM = ((MainActivity) getActivity()).getVM();//todo   | ? |
            mainActivityVM.getNavController().navigate(R.id.authenticationFragment);
            mainActivityVM.getSharedPreferences()
                    .edit()
                    .putInt(getResources().getString(R.string.saved_log_in_key), USER_NOT_SIGN_IN.stateId)
                    .apply();
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

}