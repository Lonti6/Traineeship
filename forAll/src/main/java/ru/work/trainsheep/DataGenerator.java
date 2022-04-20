package ru.work.trainsheep;

import lombok.val;
import lombok.var;
import ru.work.trainsheep.send.ChatBlock;
import ru.work.trainsheep.send.ChatMessage;
import ru.work.trainsheep.send.ChatResult;
import ru.work.trainsheep.send.CompanyNote;
import ru.work.trainsheep.send.VacancyNote;

import java.util.*;
import java.util.stream.Collectors;

public class DataGenerator {

    public final List<String> tags;
    public final List<String> companies;
    public final List<String> headers;
    public final List<String> contents;
    public final List<String> zpTypes;

    public final List<String> names;
    public final List<String> messages;
    public final List<String> icons;
    public final List<String> cities;
    public final Random random = new Random();

    public DataGenerator() {
        tags = Arrays.asList("Бэкенд", "Angular", "Фронтенд", "Middle", "JavaScript", "HTML", "JQuery", "Angular",
                "Linux", "Git", "PHP", "Golang", "React Native", "Sass", "React", "C#", ".NET", ".NET Core", "SQL", "python");

        companies = Arrays.asList("Группа «СВЭЛ»", "Прософт-Системы", "Сима-ленд", "HRS", "ГК «Экстрим»", "Uploadcare",
                "DNA Team", "Сбер", "Ceramic 3D", "Luxoft", "Яндекс", "СКБ Контур", "Tinkoff.ru", "Deutsche Bank",
                "Mail.ru Group", "Компания БКС", "Home Credit Bank", "ВымпелКом", "Andersen", "Revolut", "КРОК",
                "Grid Dynamics", "Skyeng", "Veeam Software");

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

        names = Arrays.asList("Не Никита Жильцов", "Диана Имранова", "Дмитрий Башкирцев", "Иван Малышев", "Кристина Казакова",
                "Анна Маркова", "Иван Вешняков", "Полина Попова", "Роман Пирогов", "Вероника Киселева", "Егор Карасев", "София Макарова","Калинина Ульяна", "Ефимов Егор", "Самсонов Ярослав", "Попов Илья", "Андрианова Аиша", "Соловьев Савелий", "Синицын Андрей", "Кравцова Софья", "Вдовин Филипп", "Жуков Фёдор", "Семенов Михаил", "Климов Иван", "Волкова Полина", "Белов Кирилл", "Ушакова Василиса", "Антонова Вероника", "Виноградова Алиса", "Булгаков Владимир", "Суворова Анастасия", "Титов Максим", "Иванов Андрей", "Лаптев Игорь", "Иванов Григорий", "Харитонова Виктория", "Иванова Алиса", "Трошина Юлия", "Тарасова Анастасия", "Белов Александр", "Власова Полина", "Иванов Святослав");
        messages = Arrays.asList("Уснувшая степь слушала его шум. — Не смей отказаться! По гроб жизни обидишь! Не выпьешь —.",
                "Не знаю, ослабел ли я от водки, или же бутылка была слишком.",
                "Я всё понял с первого взгляда, да едва ли в Европе есть еще мужчины, которые не умеют отличить с.",
                "Довелось мне тогда за пятачок в день водить по городу одну старушку слепую… Морозы были.",
                "Кум приходит в восторг. — Умная женщина, а бредишь, как старая нянька. «Что же еще.",
                "—А где же это самое… чем греться-то?— спросил он, испуганно глядя.",
                "Нет у меня ни приюта, ни близких, ни друзей, ни любимого дела. .",
                "Ни на что я не способен и в расцвете сил сгодился только.",
                "—Я и сам-то думаю!— ответил он крикливым, немного гнусавый.",
                "— Кто со мной?Губернаторша вышла, и за нею повалила из павильона вся.",
                "Тут были старик губернатор с женой, архиерей, председатель суда,.",
                "— Нехорошая примета. Ну, беда: заговорит он нас теперь!С.",
                "Молчать он не умел. Вы помните романс?		Очи черные, очи.",
                "Летом грешим, а зимою казнимся… да!Егор Иваныч быстро огляделся и всплеснул руками. .",
                "На дворе во всей своей холодной, нелюдимой красе стояла тихая.",
                "—Пора по домам!— сказал он, поднимаясь. Как ни горько было.",
                "—А у нас новость!— зашептала она. Кому угодно знать, как начинается.",
                "Крестовская застава — северный въезд в Москву. .",
                "Мой помощник, молодой чахоточный человек, ездил лечиться в город, где жил по целым.",
                "Что хорошего в ее приторной любви, впалой груди, в вялом.",
                "— Где же мне взять физического труда? В приказчики.",
                "На меня, уроженца севера, степь действовала, как вид заброшенного татарского кладбища. От.",
                "— Если кому будет холодно, тот может где-нибудь погреться!От.",
                "«Молодость моя погибла ни за грош, как ненужный окурок,— продолжал я.",
                "— Удивительно, как это он еще на коньках не катается. .",
                "Детей у меня не было, гостей, бывало, ко мне никаким калачом не заманишь, а сам я мог ездить в.",
                "—А у нас новость!— зашептала она. XII, ст. Я всё понял с первого взгляда, да едва ли в.",
                "Г. За малейшую провинность и неисполнение правил студентов арестовывали, исключали из.",
                "— Кто со мной?Губернаторша вышла, и за нею повалила из павильона вся.",
                ". В университетах был установлен полицейский режим. Небось уж и сердце болит у них, и животы.",
                "— И разве я стал бы вас беспокоить, если бы не крайняя нужда?.",
                "Что хорошего в ее приторной любви, впалой груди, в вялом взгляде? Я терплю ее, но не люблю. .",
                "—Да, уронил. Не понимая ровно ничего, я надел новый сюртук и пошел знакомиться.",
                "— Здравия желаю, ваше превосходительство! Ваше преосвященство, владыко святый! Всем прочим.",
                "Но это всё бы ничего, пустое дело, не суть важное.",
                "—Я и сам-то думаю!— ответил он крикливым, немного гнусавый тенорком,.",
                "Когда стрелка показывала без пяти двенадцать, я стал.",
                "—Да, уронил. Выбрали широкую часть реки между рынком и архиерейским двором, огородили.",
                "Муткуров (1852—1891), болгарский генерал, один из трех регентов (наряду со С. Муткуров.",
                "Припоминается мне один необыкновенный случай…И губернатор.",
                "Нищий—Милостивый государь! Будьте добры, обратите внимание на несчастного, голодного человека. .",
                "Два облачка уже отошли от луны и стояли поодаль с.",
                "—А у нас новость!— зашептала она. Хамовники —.",
                "Присяжный поверенный Скворцов поглядел на сизое, дырявое.",
                "Несмотря на скуку, которая ела меня, мы готовились встретить Новый год с.",
                "Вы помните романс?		Очи черные, очи.",
                "Теперь мне мороз нипочем, я без всякого внимания, знать его не хочу.",
                "; исполнял роль Андреа Росвейна. Баттенберг (1857—1893), в 1879—1886гг. Вас встречает.",
                "Дело в том, что у нас были припасены две бутылки шампанского, самого.",
                "Егор Иваныч всплеснул руками и продолжал:—А что было, когда мы зимой в Москву.",
                "Как застыла душа, то уж себя не помнишь: норовишь или старуху без водителя оставить, или.",
                "Вообще, прескучнейшая жизнь. Регенты намеревались при содействии Англии.",
                "] виноваты!— говорит он, лукаво подмигивая глазом. Ты не.",
                "—Такой проклятущий мороз, что хуже собаки всякой!— продолжал он.",
                "Но вы не чувствуете такого великодушия и продолжаете ворчать… Когда туалет.",
                "Весело мне жилось на полустанке или скучно, вы можете видеть из того, что на 20 верст.",
                "Дело в том, что у нас были припасены две бутылки шампанского, самого настоящего, с ярлыком вдовы.",
                "Но ведь вы ленивы, избалованы, пьяны! От вас, как из.",
                "— Кажется, пора бы забыть, но как взглянешь на водовозов, на школьников, на арестантиков в.",
                "— Бог с ним! Я так понимаю, ваше превосходительство,.",
                "Нижегородский вокзал — на юго-востоке. Стоишь на.",
                "Помогите, сделайте милость! Стыдно просить, но… вынуждают обстоятельства. —.",
                "Теперь скажите: что еще недоброе может со мной.",
                "Газеты сообщали о его намерении 11—12 января вернуться из Германии в.",
                "] мелкими чёртиками прыгают у вас перед глазами…Проходит полчаса… час….",
                "Новый завет. Я всё понял с первого взгляда, да едва ли в Европе есть еще мужчины, которые.",
                "Весь онемеешь, одеревенеешь, как статуй, идешь, и кажется тебе, что не ты это идешь, а кто-то.",
                "Поезд с шумом пролетел мимо меня и равнодушно посветил мне.",
                "За столом сидела маленькая женщина с большими черными глазами. Часика три.",
                "В самый разгар гулянья, часу в четвертом, в губернаторском павильоне, построенном.",
                "Оборвыш сначала оправдывался, божился, но потом умолк и, пристыженный, поник.",
                "Но вы не чувствуете такого великодушия и продолжаете.",
                "Но ведь вы ленивы, избалованы, пьяны! От вас, как из.",
                "Я долго глядел на него. Три дня не ел… не имею пятака на ночлег… клянусь.",
                "Помните?Скворцов покраснел и с выражением гадливости на лице.",
                "] и проч. Весело мне жилось на полустанке или скучно, вы.",
                "Он, как и всякий российский обыватель, имеет свой.",
                "На дворе во всей своей холодной, нелюдимой красе стояла тихая морозная ночь..",
                "—Будет вам лгать! Вы называли себя студентом и даже рассказали мне, за что вас.",
                "Этот хватает вас в объятия и тащит вас прямо к закусочному столу. —А я.",
                "Мы сидели за столом, лениво жевали и слушали, как в соседней комнате.",
                "Вот разве только преступником никогда не был, но на преступление я, кажется, неспособен,.",
                "Три дня не ел… не имею пятака на ночлег… клянусь богом! Восемь лет служил сельским учителем и.",
                "Пройдя версты две, я вернулся назад. —Ура-а!—.",
                "] виноваты!— говорит он, лукаво подмигивая глазом. Вытесняя газовое,.",
                "Он пил и конфузился, а старики молча глядели на него, и всем.",
                "Помните?Скворцов покраснел и с выражением гадливости на лице отошел от оборвыша. В самый.",
                "На меня, уроженца севера, степь действовала, как вид заброшенного татарского.",
                "Регенты намеревались при содействии Англии отмежеваться.",
                "«Молодость моя погибла ни за грош, как ненужный окурок,—.",
                "Скворцов поглядел на калоши, из которых одна была.",
                "Летом грешим, а зимою казнимся… да!Егор Иваныч быстро огляделся и.",
                "На дворе во всей своей холодной, нелюдимой красе стояла тихая морозная ночь..",
                "Ф. —Егор Иваныч, коньки вам надо купить!— встретил его губернатор. Много в моих.",
                "Крестовская застава — северный въезд в Москву. Беда,.",
                "Красный, вспотевший, вы выходите от него, садитесь в.",
                "Выпущенные афиши были громадны и обещали немало удовольствий: каток, оркестр военной.",
                "Кум приходит в восторг. За малейшую провинность и неисполнение правил студентов.",
                "Крестовская застава — северный въезд в Москву. Калужские ворота — въезд.",
                "С правдой умрешь с голоду и замерзнешь без ночлега! Вы верно.",
                "]:—В Лефортово, к Красным казармам![4 - В Лефортово, к Красным казармам! — В тогдашней.",
                "Газеты сообщали о его намерении 11—12 января вернуться из.",
                "Присяжный поверенный Скворцов поглядел на сизое, дырявое пальто просителя, на.",
                "Выйдешь, бывало, со старушкой и начинаешь мучиться. [17 - …не.",
                "Глядела она на меня так, как может глядеть только женщина, у которой на этом.",
                "—Ты уронил бутылку?— спросила она. —Дай бог, чтоб я бредила, но… непременно случится.",
                "От морозу и зол становишься и водку пьешь не в меру. XII, ст..",
                "Пей. Было устроено с благотворительной целью «народное» гулянье. .",
                "Родился я в дворянской семье, но не получил ни воспитания, ни.",
                "«Послание к евреям святого апостола Павла», гл. Нет, нет, ты решительно глуп! Тебе нужно жену.",
                "Дядя Семен Федорыч в самом деле деспот и злой, с ним трудно ужиться. —.",
                "—Царица небесная! А что было, когда меня в сидельцы в рыбную лавку.",
                "], теперь же от вас разит опопанаксом[13 - опопанакс — духи.",
                "Оборвыш сначала оправдывался, божился, но потом умолк и,.",
                "]:—В Лефортово, к Красным казармам![4 - В Лефортово, к Красным казармам! — В.",
                "— Я могу документы показать. —Хоть и здорово, но лучше б его.",
                "Мои мысли были так горьки, что мне казалось, что я мыслил вслух,.",
                "Это был городской голова, купец Еремеев, миллионер, N—ский.",
                "]У московских извозчиков есть теперь полости, но вы не цените такого великодушия.",
                "Пал жертвою доноса. 14). ] мелкими чёртиками прыгают у вас.",
                "—Я… я не лгу-с… — пробормотал он. Небось уж и сердце болит.",
                "— Нехорошая примета. Вот уж год, как хожу без места. —.",
                "—Помилуйте, на то теперь и зима, чтоб был мороз!— убеждали дамы.",
                "— Здравия желаю, ваше превосходительство! Ваше преосвященство,.",
                "] и остроносыми сапогами… Как сумасшедший срываетесь вы с места, целуете кузине руку и,.",
                ": «Новое время», 1886, №3887 и 3889, 23 и 25 декабря). — За то, что ты.",
                "Любила она меня безумно, рабски и не только мою красоту или.",
                "Вот разве только преступником никогда не был, но на преступление я, кажется, неспособен, суда же.",
                "Но это всё бы ничего, пустое дело, не суть важное. —Ну, с Новым годом, с новым счастьем!— сказал.",
                "Лицо ее побледнело и выражало ужас. —Извозчик, к Крестовской заставе!Брат вашей жены, Петя,.",
                "] и не Стамбулка[7 - Стамбулка — С. «Молодость моя погибла ни за грош, как ненужный.",
                "Дамы сидели в креслах, а мужчины толпились около широкой стеклянной двери и глядели на каток..",
                "Кому угодно знать, как начинается любовь, тот пусть.",
                "16 мая 1885г. —Такой проклятущий мороз, что хуже собаки всякой!—.",
                "Жена долго еще шептала мне какую-то чепуху про деспота.",
                "Впоследствии выяснилось, что он сторонник австро-германского влияния на Болгарию. «Что же еще.",
                "Теперь я молод, крепок, а она осунулась, состарилась,.",
                "Нижегородский вокзал — на юго-востоке. Помогите, сделайте милость! Стыдно просить,.",
                "Летом грешим, а зимою казнимся… да!Егор Иваныч быстро огляделся.",
                "Легкий ветерок пробежал по степи, неся глухой шум ушедшего.",
                "Он пил и конфузился, а старики молча глядели на него, и всем казалось, что у.",
                "— Стало быть, десять стаканов… Ну, еще бенедиктинцу,.",
                "Впоследствии выяснилось, что он сторонник австро-германского влияния на Болгарию. —Нет,.",
                "«Молодость моя погибла ни за грош, как ненужный окурок,— продолжал я думать. —Такой проклятущий.",
                "Весело мне жилось на полустанке или скучно, вы можете видеть из того, что.",
                "— Действительно, я… солгал! Я не студент и не сельский учитель. В самый разгар гулянья,.",
                "Жена долго еще шептала мне какую-то чепуху про деспота дядюшку, про слабость человеческую.",
                "Любви не было и нет. Припоминается мне один необыкновенный случай…И.",
                "Пей. —А я не знал, что у меня есть такая хорошенькая тетя!— сказал я. ] мелкими чёртиками.",
                "Этот хватает вас в объятия и тащит вас прямо к закусочному столу. .",
                "Кому угодно знать, как начинается любовь, тот пусть читает романы и.",
                "Беда, когда всё тело стынет. Он поглядел на меня сурово и уныло,.",
                "«Молодость моя погибла ни за грош, как ненужный окурок,— продолжал я думать. ]; это сокровище.",
                "] Помните?—Не… нет, не может быть!— пробормотал проситель, смущаясь. А придешь с мороза на ночлег.",
                "Предложи вам, так откажетесь. ] Будь я, анафема, трижды проклят, если не Англия!Вы послушали.",
                "— Действительно, я… солгал! Я не студент и не сельский учитель. Она ведь несчастная. .",
                "Калужские ворота — въезд в Москву с юго-запада. —Спасибо другу!—.",
                "Этот хватает вас в объятия и тащит вас прямо к закусочному столу. Вытесняя.",
                "— В русском переводе: «… не имеем здесь постоянного града, но ищем будущего».",
                "Гребенки (муз. С правдой умрешь с голоду и замерзнешь без ночлега! Вы.",
                "], дом Фуфочкина), садитесь на извозчика и говорите голосом Солонина, умирающего в.",
                "Вы помните романс?		Очи черные, очи страстные,		Очи.",
                "Красный, вспотевший, вы выходите от него, садитесь в сани и говорите.",
                "—Голубчик, ведь вы на полгода брали!— шепчет он. —.",
                "— Пей!Жена взяла свой стакан и уставилась на меня испуганными глазами..",
                "Думали они о том, что в человеке выше происхождения, выше сана, богатства и знаний,.",
                "—Спасибо другу!— восхищается кум. Я видел, как он остановился у зеленых огней.",
                "Не понимая ровно ничего, я надел новый сюртук и пошел.",
                "Я сказал несколько старых фраз насчет предрассудков, выпил.",
                ". Но что же мне делать? Верьте богу, нельзя без лжи! Когда я говорю.",
                "Гребенки (муз. Детей у меня не было, гостей, бывало, ко мне никаким калачом не.",
                "Егор Иваныч принялся за глинтвейн и, пока околоточный допивал свой.",
                "Поезд с шумом пролетел мимо меня и равнодушно посветил мне.",
                "Много в моих мыслях было правды, но много и нелепого,.",
                "Присяжный поверенный Скворцов поглядел на сизое, дырявое пальто просителя, на его.",
                "— Сущая казнь!—Это здорово,— сказал губернатор. Новый.",
                "— Прощайте! Послушайте,— обратился он к околоточному,— скажите там.",
                "Крестовская застава — северный въезд в Москву. Морозы до чрезвычайности, а лавка, словно.",
                "Я сказал несколько старых фраз насчет предрассудков, выпил полбутылки, пошагал из.",
                "За столом сидела маленькая женщина с большими черными глазами. Глядя, как.",
                "—Кто вам поверит?— продолжал возмущаться Скворцов. Летом грешим, а зимою казнимся… да!Егор.",
                "Пройдя версты две, я вернулся назад. ]:—В Лефортово, к Красным казармам![4 - В.",
                "Горе, горе, владыко святый! При таком морозе и бедность вдвое, и вор хитрее, и злодей.",
                "— Бывало, выходишь в лавку чуть свет… к девятому часу я уж совсем озябши, рожа.",
                "Нижегородский вокзал — на юго-востоке. Дамы сидели в креслах, а мужчины толпились.",
                "—Сударь!— сказал он, прикладывая руку к сердцу. Губернатор вздохнул. —Дай бог, чтоб я.",
                "—Хоть и здорово, но лучше б его вовсе не было,— сказал.",
                "Скворцов поглядел на калоши, из которых одна была глубокая, а другая мелкая, и.",
                "К нему подбежали артельщик и пожарный. Присяжный поверенный Скворцов.",
                "Вот уж год, как хожу без места. —Ба, жив курилка!— засмеялся.",
                "— Прощайте! Послушайте,— обратился он к.",
                "Ну, так что же из этого?—Нехорошо,— сказала она, ставя свой.",
                "— Умная женщина, а бредишь, как старая нянька. —Ах, это вы, Мишель?— стонет она,.",
                "На дворе во всей своей холодной, нелюдимой красе стояла тихая морозная ночь. .",
                "Помнится мне страшный, бешеный вихрь, который закружил.",
                "Праздник предполагался в возможно широких размерах. —.",
                "Создатель мой! Спервоначалу задаешь дрожака, как в лихорадке, жмешься и прыгаешь, потом.",
                "Дело в том, что у нас были припасены две бутылки шампанского,.",
                "Думали они о том, что в человеке выше происхождения,.",
                "Ну, так что же из этого?—Нехорошо,— сказала она, ставя свой.",
                "—А слыхал ты, душа моя, что.",
                "— Уходил, мерзавец! Извозчик, езжай в.",
                "Но ведь вы ленивы,.",
                ": «Новое время», 1886,.",
                "Я шел вдоль насыпи. С Новым.",
                "Кроме неудач и бед,.",
                "Всё это одна выдумка! Я в русском хоре.",
                "Софусь). Кум приходит.",
                "Газеты сообщали о его намерении 11—12 января.",
                "В конце 1886 — начале 1887г. — Кажется, пора.",
                "Он пил и конфузился, а старики молча глядели.",
                "Своею наглою ложью.",
                "[17 - …не имамы зде пребывающего града,.",
                "—Ура-а!— кричит он, увидев вас. 16 мая.",
                "— Тут Англия, брат![8 - Тут Англия, брат! —.",
                "— Мороз укрепляет человека,.",
                "— Нет покоя ни в будни, ни в праздники! На.",
                "Нет у меня ни приюта,.",
                "С Новым.",
                "В университетах был.",
                "Теперь скажите: что еще недоброе может со мной.",
                "Теперь я молод, крепок, а.",
                "—Дай бог, чтоб я бредила, но….",
                "—Кто вам поверит?— продолжал.",
                "Из степного полустанка, как видите,.",
                "Что же еще недоброе может случиться?»Вдали.",
                "— Кого ви-ижу! Как кстати ты пришел!Он.",
                "На полдороге он вдруг согнулся, подкрался сзади к.",
                "—Вот что, бегите к Саватину,— забормотал.",
                "Дело в том, что у нас.",
                "И архиерей рассказал, как он,.",
                "Но вы не чувствуете такого великодушия и.",
                "— Пей!Жена взяла свой стакан и.",
                "Нет, нет, ты решительно.",
                "] виноваты!— говорит он, лукаво.",
                "Легкий ветерок пробежал по степи, неся.",
                "У порога дома встретила меня жена. Ну,.",
                "14). ], дом Курдюковой) вы хозяйку.",
                "XII, ст. Дядя Семен Федорыч в.",
                "], дом Фуфочкина), садитесь на.",
                "В самый разгар гулянья, часу в четвертом, в.",
                "Два облачка уже отошли от луны и стояли поодаль.",
                "—Будет вам лгать! Вы называли себя студентом и.",
                "] и не Стамбулка[7 - Стамбулка — С. «Что.",
                "Единственным развлечением могли быть.",
                "[18 - …студент,.",
                "] и проч. Потом, словно.",
                "«Послание к евреям святого.",
                "— Я могу документы показать. ] Довелось.",
                "Но это всё бы ничего,.",
                "От своей совести нельзя прятаться: не люблю я.",
                "Было устроено с благотворительной целью.",
                "По требованию группы болгарских.",
                "—Ай, батюшки,—.",
                "Но вы не чувствуете.",
                "16 мая 1885г. — Мороз укрепляет человека,.",
                "— Бог с ним! Я так понимаю,.",
                "Пробка с треском вылетела из второй.",
                "Не понимая ровно ничего, я.",
                "— Тут Англия, брат![8 - Тут.",
                "Нет, нет, ты решительно глуп! Тебе.",
                "—Такой проклятущий мороз, что хуже.",
                "Припоминается мне один.",
                "Егор Иваныч всплеснул руками.",
                "Когда стаканы.",
                "XII, ст. Родился я в.",
                "«Что же еще недоброе может случиться? Потеря.",
                "Кому угодно знать, как.",
                "Я сказал несколько старых фраз.",
                "—Ну, спасибо!— говорит он..",
                "Кандидатура его была выдвинута.",
                "Пролилось вина не более.",
                "Когда стрелка показывала без пяти.",
                "Пройдя версты две, я.",
                "— Тут Англия, брат![8 - Тут Англия, брат! —.",
                "—Какая Наталья.",
                "Детей у меня не было, гостей,.",
                "Жена долго еще шептала мне.",
                "— Стало быть, десять стаканов… Ну, еще.",
                "Когда та оглянулась, он отскочил и, вероятно,.",
                "— Господа, поглядите,.",
                "Что и говорить! Мне теперь седьмой десяток пошел,.",
                "]:—В Лефортово, к Красным.",
                "Она очень добрая и.",
                "—Ну, с Новым годом, с.",
                "«Послание к евреям святого.",
                "Она лежит у себя в голубой гостиной на.",
                "]:—В Лефортово, к Красным казармам![4 - В.",
                "— Бывало, выходишь в лавку чуть.",
                "] Будь я, анафема, трижды проклят,.",
                "В университетах был.",
                "— Мороз укрепляет человека, бодрит. — В.",
                "—Голубчик, ведь вы на полгода брали!—.",
                "Что же может случиться? Молодость моя пропадает,.",
                "— И разве я стал бы вас беспокоить, если бы не.",
                "Были утверждены «Правила.",
                "Пробка с треском вылетела из второй.",
                "[17 - …не имамы зде пребывающего.",
                "—А, чёрррт подери!— бормочете вы.",
                "Вас встречает ваша, извините за.",
                "Я видел, как он остановился у зеленых огней.",
                "Летом она со своим.",
                "Оборвыш сначала.",
                "Когда та оглянулась, он отскочил и,.",
                "Еще минута, и вы с.",
                "— Я сельский учитель и,.",
                "Пей. И болел я, и деньги терял, и выговоры.",
                "Каравеловым) после.",
                "Но вы не чувствуете такого.",
                "—Ну, с Новым годом, с новым.",
                "]Семен Степаныч помешан на.",
                "Бедная, всё прощающая, всё выносящая фрачная.",
                "—Будет вам лгать! Вы называли себя студентом и.",
                "— За то, что ты такой хороший человек,.",
                "Не понимая ровно ничего, я надел новый сюртук и.",
                "—А, чёрррт подери!— бормочете вы сквозь.",
                "—Помилуйте, на то теперь и зима,.",
                "Горе, горе, владыко святый! При таком морозе и.",
                "— Пей!Жена взяла свой стакан и.",
                "[17 - …не имамы зде пребывающего града, но.",
                "] Довелось мне тогда.",
                "— Родители мои умерли, когда я.",
                "—Ай, батюшки,—.",
                "— Не смей отказаться! По гроб жизни обидишь! Не.",
                "Кум приходит в восторг..",
                "—А у нас новость!— зашептала она. Лицо ее.",
                "— Стало быть, десять стаканов… Ну, еще.",
                "Дядя Семен Федорыч в самом.",
                "Это значит, что в этом году с нами случится.",
                "Новый завет. Когда та.",
                "Крестовская застава — северный въезд в.",
                "Софусь). Теперь.",
                "Пал жертвою доноса. Печальные.",
                "Глаза ее весело смеялись, и всё лицо.",
                "Губернатор вздохнул. — Ваше.",
                "—Эх!— прошептал.",
                "— Тут Англия, брат![8 -.",
                "Нет, нет, ты решительно глуп!.",
                "Он пил и конфузился, а старики молча глядели на.",
                "— Здравия желаю, ваше превосходительство!.",
                "Губернатор и голова оживились, повеселели и,.",
                "Сокольницкая роща — сосновый бор на.",
                "— Где же мне взять физического.",
                "— Кого ви-ижу! Как кстати ты пришел!Он трижды.",
                "Скворцов поглядел на калоши, из.",
                "Что же еще недоброе.",
                "— За это самое, что ты меня.",
                "Нищий—Милостивый государь! Будьте добры,.",
                "— Кажется, всё пережито. Это.",
                "Ты не чувствуешь! Боже, какая я несчастная.",
                "Гибнет мое мужество,.",
                "Три дня не ел… не имею.",
                "От своей совести нельзя прятаться: не люблю я.",
                "—Живой старикашка!— сказал.",
                "— Не смей отказаться! По гроб жизни.");

        /*icons = Arrays.asList("https://images.unsplash.com/photo-1563694983011-6f4d90358083?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80",
                "https://images.unsplash.com/photo-1612810806546-ebbf22b53496?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1074&q=80",
                "https://images.unsplash.com/photo-1611162616305-c69b3fa7fbe0?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1074&q=80",
                "https://images.unsplash.com/photo-1611606063065-ee7946f0787a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1074&q=80",
                "https://images.unsplash.com/photo-1611944212129-29977ae1398c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1074&q=80",
                "https://images.pexels.com/photos/2737333/pexels-photo-2737333.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                "https://images.pexels.com/photos/7776468/pexels-photo-7776468.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                "https://images.pexels.com/photos/4584186/pexels-photo-4584186.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                "https://images.pexels.com/photos/6765373/pexels-photo-6765373.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                "https://images.pexels.com/photos/2549050/pexels-photo-2549050.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                "https://images.pexels.com/photos/4631882/pexels-photo-4631882.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                "https://images.pexels.com/photos/3402028/pexels-photo-3402028.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
        );*/
        icons = Arrays.asList("https://i.ytimg.com/vi/4D_6d9SjlVo/maxresdefault.jpg",
                "https://yt3.ggpht.com/ytc/AKedOLRUjbHkeM7qRuUlK2bQ-0P0hqm0ZByKMljUNonulA=s900-c-k-c0x00ffffff-no-rj",
                "https://i.ytimg.com/vi/s122452ZmUA/maxresdefault.jpg",
                "https://games.mail.ru/hotbox/content_files/gallery/75/de/warcraft_3_the_frozen_throne_art_a84cbb49.jpeg",
                "https://e2p.com/storage/products/September2019/879574.jpg",
                "https://i.ytimg.com/vi/IS7zaNHT-58/maxresdefault.jpg",
                "https://cs11.pikabu.ru/post_img/2018/06/07/8/og_og_152837707629983384.jpg",
                "https://i.ytimg.com/vi/qQWJavEipwM/maxresdefault.jpg",
                "https://c.wallhere.com/photos/fd/ae/might_and_magic_heroes_warrior_armor_sword_wings-728533.jpg!d",
                "https://secure.diary.ru/userdir/1/2/7/6/1276539/71047703.jpg",
                "https://static.wikia.nocookie.net/villains/images/4/4c/Alaric.jpg/revision/latest?cb=20130907122826",
                "http://s01.riotpixels.net/data/1a/ea/1aeaa527-bc9b-43fb-ab97-cb455b4c2be9.jpg.2160p.jpg/artwork.might-and-magic-heroes-7-trial-by-fire.3246x2160.2016-05-03.9.jpg"
        );
        cities = Arrays.asList("Москва", "Санкт-Петербург", "Новосибирск", "Екатеринбург", "Казань",
                            "Нижний Новгород", "Челябинск", "Самара", "Омск", "Ростов-на-Дону", "Уфа",
                            "Красноярск", "Воронеж", "Пермь", "Волгоград", "Краснодар", "Саратов", "Тюмень",
                            "Тольятти", "Ижевск", "Барнаул", "Ульяновск", "Иркутск", "Хабаровск", "Махачкала",
                            "Ярославль", "Владивосток", "Оренбург", "Томск", "Кемерово", "Семферопль", "Севастопль");

        zpTypes = Arrays.asList("Р/Нед", "Р/Мес", "Р/час");
    }

    public VacancyNote generateVacancyNote(List<String> tags) {

        Set<String> ntags = new HashSet<>(tags);
        int countTags = random.nextInt(5) + 1;
        for (int i = 0; i < countTags; i++) {
            ntags.add(getRandom(this.tags));
        }

        return new VacancyNote(new ArrayList<>(ntags.stream().sorted().collect(Collectors.toList())),
                getRandom(headers),
                getRandom(contents),
                getRandom(companies),
                splitSalary((int)(Math.random()*20000))+" ₽/Час", random.nextBoolean(), random.nextInt(10000));
    }

    public CompanyNote generateCompanyNote() {
        return new CompanyNote(
                random.nextInt(10000),
                getRandom(companies),
                getRandom(contents),
                getRandom(icons), random.nextBoolean());
    }

    public ChatBlock generateChatBlock(){
        return ChatBlock.builder()
                .icon(getRandom(icons))
                .name(getRandom(names))
                .email("you")
                .lastMessage(getRandom(messages))
                .countUnreadMessages(random.nextBoolean() ? random.nextInt(10) : 0)
                .lastMessageDate(generateDate())
                .build();
    }
    public ChatMessage generateMessage(long date, String sender){
        return ChatMessage.builder()
                .message(getRandom(messages))
                .date(date)
                .sender(random.nextBoolean() ? sender : "you")
                .build();
    }
    public List<ChatBlock> generateChats(){
        val count = random.nextInt(30) + 5;
        val res = new ArrayList<ChatBlock>(count);
        for (int i = 0; i < count; i++) {
            res.add(generateChatBlock());
        }
        return res;
    }
    public ChatResult generateChatResult(int page, int count) {
        val res = new ArrayList<ChatMessage>(count);
        long date = generateDate();
        val sender = getRandom(companies);
        for (int i = 0; i < count; i++) {
            date = addRandom(date);
            res.add(generateMessage(date, sender));
        }
        return ChatResult.builder()
                .countAllMessages((page+1) * count + random.nextInt(100))
                .messages(res)
                .page(page)
                .name(sender)
                .icon(getRandom(icons))
                .build();
    }

    public String getRandomMessageText(){
        return getRandom(messages);
    }
    private String splitSalary(int salaryNum)
    {
        String salary = String.valueOf(salaryNum);
        StringBuilder builder = new StringBuilder();
        int q = 0;
        for (int i = salary.length()-1; i>-1; i--)
        {
            if (q == 2)
            {
                if (i != 0)
                    builder.insert(0, " "+salary.charAt(i));
                q = 0;
            }
            else
            {
                builder.insert(0, salary.charAt(i));
                q++;
            }
        }
        return builder.toString();
    }

    private static long START = 946080000000L;
    private long generateDate(){
        return new Date(START + random.nextLong() % (START / 10)).getTime();
    }
    public long addRandom(long start){
        return start + random.nextInt(601000) + 10000;
    }

    public <T> T getRandom(List<T> list) {
        return list.get(random.nextInt(list.size()));
    }

    public List<String> getCities()
    {
        return cities;
    }
}
