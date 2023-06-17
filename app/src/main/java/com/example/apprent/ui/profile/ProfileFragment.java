package com.example.apprent.ui.profile;

import static com.example.apprent.domain.models.AuraUser.State.USER_NOT_SIGN_IN;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.apprent.R;
import com.example.apprent.ui.main_activity.MainActivity;
import com.example.apprent.ui.main_activity.MainActivityViewModel;


public class ProfileFragment extends Fragment {
    private boolean infoLayoutVisible = false;
    private boolean personalDataLayoutVisible = false;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button information = view.findViewById(R.id.information);
        Button personalData = view.findViewById(R.id.personal_data);
        Button myCards = view.findViewById(R.id.my_cards);
        Button goToSupportChat = view.findViewById(R.id.ask_support);
        Button logoutButton = view.findViewById(R.id.logout_button);
        LinearLayout personalDataLayout = view.findViewById(R.id.personal_data_layout);
        LinearLayout informationLayout = view.findViewById(R.id.information_layout);
        logoutButton.setOnClickListener(v -> {
            MainActivityViewModel mainActivityViewModel = ((MainActivity) getActivity()).getVM();//todo   | ? |
            mainActivityViewModel.getNavController().navigate(R.id.authenticationFragment);
            mainActivityViewModel.getSharedPreferences()
                    .edit()
                    .putInt(getResources().getString(R.string.saved_log_in_key), USER_NOT_SIGN_IN.stateId)
                    .apply();
        });
        personalData.setOnClickListener(v -> {
            if (personalDataLayoutVisible) {
                collapseView(personalDataLayout);
                personalDataLayoutVisible = false;
            } else {
                expandView(personalDataLayout);
                personalDataLayoutVisible = true;
            }
        });
        information.setOnClickListener(v -> {
            if (infoLayoutVisible) {
                collapseView(informationLayout);
                infoLayoutVisible = false;
            } else {
                expandView(informationLayout);
                infoLayoutVisible = true;
            }
        });

    }

    private void expandView(LinearLayout personalDataLayout) {
        personalDataLayout.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.expand_animation);
        personalDataLayout.startAnimation(animation);
    }

    private void collapseView(LinearLayout informationLayout) {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.collapse_animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                informationLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        informationLayout.startAnimation(animation);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

}