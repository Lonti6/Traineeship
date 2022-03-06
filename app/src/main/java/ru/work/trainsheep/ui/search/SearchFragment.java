package ru.work.trainsheep.ui.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.apmem.tools.layouts.FlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.work.trainsheep.R;
import ru.work.trainsheep.databinding.FragmentSearchBinding;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;
    private FragmentSearchBinding binding;
    private final List<String> items = new ArrayList<>();
    private RecyclerView.Adapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel =
                new ViewModelProvider(this).get(SearchViewModel.class);

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        root.findViewById(R.id.search_field).setOnFocusChangeListener((v, hasFocus) -> {
            if (v.hasFocus())
                ((RelativeLayout)(v.getParent())).setBackgroundResource(R.drawable.border_style_color);
            else
                ((RelativeLayout)(v.getParent())).setBackgroundResource(R.drawable.border_style_gray);
        });

        List<String> headers = Arrays.asList("Программист ERP",
                "Frontend-разработчик (JS, Angular)",
                "Backend-разработчик (Middle-to-Senior)",
                "Middle Frontend Developer / React Native",
                "Разработчик .NET",
                "Python разработчик",
                "Frontend-разработчик (React)",
                "Middle Unity3D Developer");

        List<String> tags = Arrays.asList("Бэкенд", "Angular", "Фронтенд", "Middle", "JavaScript", "HTML", "JQuery", "Angular",
                "Linux", "Git", "PHP", "Golang", "React Native", "Sass", "React", "C#",  ".NET",  ".NET Core", "SQL", "python");

        List<String> contents = Arrays.asList(
                "Разработка новых приложений. Поддержание/развитие существующих приложений. Кодревью. Написание тестов. Участие в обсуждении и проектировании архитектуры и бизнес-логики.\n" +
                        "Уметь покрывать код тестами (unit tests, integration tests) - библиотека pytest. Django Rest Framework, Marshmallow. Наличие опыта разработки коммерческих приложений",
                "Розница и 1С Управление торговлей в роли разработчика\\программиста 1С. Программирование и документирование по стандартам 1С.\n" +
                        "Опыт работы в роли Ведущего разработчика или Разработчика 1С с любой из следующих конфигураций 1С «Розница»",
                "Казначейство в роли разработчика\\программиста 1С. Программирование и документирование по стандартам 1С. Доработка типовых конфигураций. Разработка нового функционала.\n" +
                        "Опыт работы в роли Ведущего разработчика или Разработчика 1С с любой из следующих конфигураций 1С «Бухгалтерия»",
                "Проектирование базы данных и оптимизация программных модулей. Разработка и выпуск программных модулей в Oracle PL/SQL. Организация и сопровождение процесса...\n" +
                        "Умение писать образцовый и сопровождаемый другими разработчиками код. Опыт работы в качестве аналитика\\разработчика банковского ПО, либо отчетности в проектах",
                "Мы являемся сертифицированными партнерами CRM систем (Битрикс24, AmoCRM и других) и занимаемся внедрением и интеграцией ПО. Мы работаем на рынке России и стран СНГ уже более 5-ти лет и имеем постоянный динамичный рост компании.\n" +
                        "\n" +
                        "Если ты хочешь постоянно учиться новому и работать над разными интересными и сложными проектами - ждем тебя!",
                "Готовы учиться и повышать свою квалификацию.\n" +
                        "Большая часть наших задач связана с созданием сложных, кастомных сценариев работы систем. Следовательно, Вам нужно будет решать интересные бизнес-задачи",
                "Поддержание проектов компании (CRM - система,разработка сайтов,Web - проектов от Лендинга до Web - сервисов).\n" +
                        "Знание языка программирования Python. Уверенное владение Linux. Знание Django. Git. Понимание принципов работы баз данных. Технический английский (будет преимуществом).\n",
                "Внедрение верстки в шаблон сайта. Разработка компонентов и модулей. Разработка бизнес-процессов в Битрикс24. Разработка приложений для маркетплейс Битрикс...\n" +
                        "Знание HTML‚ CSS, JS, jQuery. Опыт разработки на PHP MySQL. Умение решать нестандартные задачи / разрабатывать нестандартный функционал. Внимательность. Ответственность. Коммуникабельность.",
                "Разработка по подготовленному техническому заданию. Ведение клиента и решение поставленных им задач. Участие в разработке и внедрении тиражируемых решений.\n" +
                        "Мы 1С франчайзи, работаем с 2015 года, активно развиваемся, выпускаем собственные продукты по автоматизации. Знание языка запросов.",
                "Доработка ПО под разработанное устройство. Развитие и поддержка встроенного ПО для микроконтроллеров семейства ARM, STM 32, AVR, ESP32 на...\n" +
                        "C/C - уровень senior. Python — уровень juniour/middle. Хорошее знание Linux и ядра Linux. Опыт написания прошивок для микроконтроллеров ARM",
                "Необходимо участвовать в разработке веб-приложений. Есть задачи по клиентской части и по серверной. Серверная часть на PHP.\n" +
                        "Подтвержденные знания разработчика ПО."
        );

        List<String> companies = Arrays.asList("Группа «СВЭЛ»", "Прософт-Системы", "Сима-ленд", "HRS", "ГК «Экстрим»", "Uploadcare", "DNA Team", "Сбер", "Ceramic 3D");

        ArrayList<ArrayList<String>> tempTags = new ArrayList<>();
        ArrayList<String> descriptions = new ArrayList<>();
        ArrayList<String> tempCompanies = new ArrayList<>();

        for (int i = 0; i<(int)(Math.random()*11)+4; i++)
        {
            items.add(headers.get((int)(Math.random()*headers.size())));

            ArrayList<String> list = new ArrayList<>();
            for (int j = 0; j<(int)(Math.random()*4)+1; j++)
                list.add(tags.get((int)(Math.random()*tags.size())));
            tempTags.add(list);
            descriptions.add(contents.get((int)(Math.random()*contents.size())));
            tempCompanies.add(companies.get((int)(Math.random()*companies.size())));
        }

        adapter = new ItemAdapter(this.items, tempTags, descriptions, tempCompanies);


        RecyclerView recyclerView = root.findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
final class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private final List<String> headers;
    private final ArrayList<ArrayList<String>> tags;
    private final ArrayList<String> descriptions;
    private final ArrayList<String> companies;

    public ItemAdapter(List<String> items, ArrayList<ArrayList<String>> tags, ArrayList<String> descriptions, ArrayList<String> companies) {
        this.headers = items;
        this.tags = tags;
        this.descriptions = descriptions;
        this.companies = companies;
    }


    private boolean isPositionFooter(int position) {
        return position > headers.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        if (isPositionFooter(position)) {
            return headers.size()-1;
        }
        return position;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == headers.size()-1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_search,
                    parent, false);
            return new FooterViewHolder(view);
        }
        else
            return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.advert_item, parent, false)){};
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder){}
        else {
            TextView name = holder.itemView.findViewById(R.id.advert_name);
            name.setText(headers.get(position));
            //LinearLayout tagsField = (LinearLayout) LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.flow_layout_item, null, false);

            FlowLayout tagsField = holder.itemView.findViewById(R.id.tags_field);
            tagsField.removeAllViews();

            //View view = LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.fragment_search, null, false);
            for (String tag : tags.get(position)) {
                LinearLayout layout = (LinearLayout) LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.tag_item, null, false);
                ((TextView) ((RelativeLayout) layout.getChildAt(0)).getChildAt(0)).setText(tag);
                tagsField.addView(layout);
            }

            TextView description = holder.itemView.findViewById(R.id.advert_description);
            description.setText(descriptions.get(position));

            TextView companyName = holder.itemView.findViewById(R.id.company_name);
            companyName.setText(companies.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return this.headers.size();
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public View View;
        public FooterViewHolder(View v) {
            super(v);
            View = v;
            // Добавьте компоненты пользовательского интерфейса здесь.
        }
    }
}