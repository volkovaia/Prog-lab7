# Prog-lab7
Доработать программу из лабораторной работы №6 следующим образом:

1) Организовать хранение коллекции в реляционной СУБД (PostgresQL). Убрать хранение коллекции в файле.
2) Для генерации поля id использовать средства базы данных (sequence).
3) Обновлять состояние коллекции в памяти только при успешном добавлении объекта в БД
4) Все команды получения данных должны работать с коллекцией в памяти, а не в БД
5) Организовать возможность регистрации и авторизации пользователей. У пользователя есть возможность указать пароль.
6) Пароли при хранении хэшировать алгоритмом SHA-512
7) Запретить выполнение команд не авторизованным пользователям.
8) При хранении объектов сохранять информацию о пользователе, который создал этот объект.
9) Пользователи должны иметь возможность просмотра всех объектов коллекции, но модифицировать могут только принадлежащие им.
10) Для идентификации пользователя отправлять логин и пароль с каждым запросом.
11) Необходимо реализовать многопоточную обработку запросов.
12) Для многопоточного чтения запросов использовать создание нового потока (java.lang.Thread)
13) Для многопотчной обработки полученного запроса использовать Fixed thread pool
14) Для многопоточной отправки ответа использовать Fixed thread pool
15) Для синхронизации доступа к коллекции использовать потокобезопасные аналоги коллекции из java.util.concurrent
16) Порядок выполнения работы:
17) В качестве базы данных использовать PostgreSQL.
18) Для подключения к БД на кафедральном сервере использовать хост pg, имя базы данных - studs, имя пользователя/пароль совпадают с таковыми для подключения к серверу.


Вопросы к защите лабораторной работы:

1) Многопоточность. Класс Thread, интерфейс Runnable. Модификатор synchronized.
2) Методы wait(), notify() класса Object, интерфейсы Lock и Condition.
3) Классы-сихронизаторы из пакета java.util.concurrent.
4) Модификатор volatile. Атомарные типы данных и операции.
5) Коллекции из пакета java.util.concurrent.
6) Интерфейсы Executor, ExecutorService, Callable, Future
7) Пулы потоков
8) JDBC. Порядок взаимодействия с базой данных. Класс DriverManager. Интерфейс Connection
9) Интерфейсы Statement, PreparedStatement, ResultSet, RowSet
10) Шаблоны проектирования.
