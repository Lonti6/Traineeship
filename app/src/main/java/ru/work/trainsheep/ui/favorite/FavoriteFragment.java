package ru.work.trainsheep.ui.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ru.work.trainsheep.databinding.FragmentFavoriteBinding;

public class FavoriteFragment extends Fragment {

    private FavoriteViewModel favoriteViewModel;
    private FragmentFavoriteBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favoriteViewModel =
                new ViewModelProvider(this).get(FavoriteViewModel.class);

        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        favoriteViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}