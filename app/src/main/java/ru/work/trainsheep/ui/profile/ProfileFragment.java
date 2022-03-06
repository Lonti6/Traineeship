package ru.work.trainsheep.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Flow;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.apmem.tools.layouts.FlowLayout;

import java.util.Arrays;
import java.util.List;

import ru.work.trainsheep.R;
import ru.work.trainsheep.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment{
    private ProfileViewModel messagesViewModel;
    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        messagesViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        FlowLayout flowLayout = ((FlowLayout)root.findViewById(R.id.user_tags_field));
        flowLayout.removeAllViews();

        List<String> tags = Arrays.asList("Бэкенд", "Angular", "Фронтенд", "Middle", "JavaScript", "HTML", "JQuery", "Angular",
                "Linux", "Git", "PHP", "Golang", "React Native", "Sass", "React", "C#", ".NET", ".NET Core", "SQL", "python");

        for (int i = 0; i<((int)(Math.random()*7)+3); i++)
        {
            LinearLayout layout = (LinearLayout) LayoutInflater.from(root.getContext()).inflate(R.layout.tag_item, null, false);
            ((TextView) ((RelativeLayout) layout.getChildAt(0)).getChildAt(0)).setText(tags.get((int)(Math.random()*tags.size())));
            flowLayout.addView(layout);
        }


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
