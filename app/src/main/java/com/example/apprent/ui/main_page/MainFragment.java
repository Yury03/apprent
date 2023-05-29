package com.example.apprent.ui.main_page;import android.os.Bundle;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import androidx.annotation.NonNull;import androidx.annotation.Nullable;import androidx.fragment.app.Fragment;import androidx.lifecycle.Observer;import androidx.lifecycle.ViewModelProvider;import androidx.viewpager2.widget.ViewPager2;import com.example.apprent.R;import com.example.apprent.data.network.GetItemsListImpl;import com.example.apprent.domain.usecase.GetListLinks;import com.example.apprent.ui.common.adapters.ImagesPagerAdapter;import com.example.apprent.ui.main_activity.MainActivityVM;import java.util.List;public class MainFragment extends Fragment {    private MainActivityVM mainActivityVM;    private Bundle args;    private MainFragmentVM vm;    private MainFragment mainFragment;    @Override    public void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);    }    @Override    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {        super.onViewCreated(view, savedInstanceState);        args = getArguments();        if (args != null && args.getSerializable("MainActivityVM") != null) {            mainActivityVM = (MainActivityVM) args.getSerializable("MainActivityVM");        }        ViewPager2 bannerPager = view.findViewById(R.id.banner_view_pager);        vm = new ViewModelProvider(this).get(MainFragmentVM.class);        vm.loadBanners();        this.mainFragment = this;        vm.getBannerLinks().observe(getViewLifecycleOwner(), strings -> {            ImagesPagerAdapter imagesPagerAdapter = new ImagesPagerAdapter(mainFragment, strings);            bannerPager.setAdapter(imagesPagerAdapter);        });    }    @Override    public View onCreateView(LayoutInflater inflater, ViewGroup container,                             Bundle savedInstanceState) {        View view = inflater.inflate(R.layout.fragment_main, container, false);        return view;    }}