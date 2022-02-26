package ru.work.trainsheep.data;

import android.os.Handler;
import android.os.Looper;
import ru.work.trainsheep.*;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class FakeServerRepository implements ServerRepository {
    Random random = new Random();
    ExecutorService executor = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void register(UserRegistrationData user, Consumer<Result<LoginResult>> callback) {
        executor.execute(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.post(() -> {
                if(random.nextBoolean())
                    callback.accept(Result.success(new LoginResult("SuperUser")));
                else
                    callback.accept(Result.error(new Exception("no correct username or login")));
            });
        });

    }

    @Override
    public void getAdverts(AdvertRequest request, Consumer<Result<AdvertResult>> callback) {
        executor.execute(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ArrayList<Note> notes = new ArrayList<>();
            for (int i = 0; i < request.getCountNotesOnPage(); i++) {
                notes.add(generateNote(request.getTags()));
            }
            handler.post(() -> {
                if(random.nextInt(100) > 2) {
                    callback.accept(Result.success(new AdvertResult(notes, request.getPage(), random.nextInt(100) + request.getPage() * request.getCountNotesOnPage(), request.getCountNotesOnPage())));
                }
                else
                    callback.accept(Result.error(new Exception("internal error try again")));
            });
        });
    }

    private List<String> tags;
    private List<String> companies;
    private List<String> headers;
    private List<String> contents;

    private Note generateNote(List<String> tags){

        Set<String> ntags = new HashSet<>(tags);
        int countTags = random.nextInt(5) + 1;
        for (int i = 0; i < countTags; i++) {
            ntags.add(getRandom(this.tags));
        }

        return new Note(new ArrayList<>(ntags.stream().sorted().collect(Collectors.toList())), getRandom(headers), getRandom(contents), getRandom(companies));
    }
    private String getRandom(List<String> list){
        return list.get(random.nextInt(list.size()));
    }
    public FakeServerRepository() {
        tags = Arrays.asList("Бэкенд", "Angular", "Фронтенд", "Middle", "JavaScript", "HTML", "JQuery", "Angular",
                "Linux", "Git", "PHP", "Golang", "React Native", "Sass", "React", "C#",  ".NET",  ".NET Core", "SQL", "python");

        companies = Arrays.asList("Группа «СВЭЛ»", "Прософт-Системы", "Сима-ленд", "HRS", "ГК «Экстрим»", "Uploadcare", "DNA Team", "Сбер", "Ceramic 3D");

        headers = Arrays.asList("Программист ERP",
                "Frontend-разработчик (JS, Angular)",
                "Backend-разработчик (Middle-to-Senior)",
                "Middle Frontend Developer / React Native",
                "Разработчик .NET",
                "Python разработчик",
                "Frontend-разработчик (React)",
                "Middle Unity3D Developer");

        contents = Arrays.asList(
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
    }
}
