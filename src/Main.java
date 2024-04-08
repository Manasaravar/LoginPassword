import java.io.IOException;
//1)	Разработать серверную и клиентскую часть сервиса аутентификации.
// Клиент и сервер общаются друг с другом с помощью кастомных объектов DTO- dataTransferObject.
// На сервере лежит коллекция клиентов (можно просто хранить пары логин-пароль),
// клиент может выбрать три действия: регистрация, аутентификация и выход из системы
// (разрыв соединения). Сервер направляет действия клиента.
// Пример: посылается сообщение типа «введите через пробел логин и пароль»
// или «вы подключились к сервису аутентификации.
// Ваши действия: 1- аутентификация, 2- регистрация, 0- выход из системы».

public class Main {
    public static void main(String[] args) {
        final int PORT = 8080;
        final String HOST = "127.0.0.1";
        Thread serverThread = new Thread( () -> {
            try {
                new Server(PORT).run();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Thread clientThread = new Thread(() -> {
            new Client(HOST, PORT).run();
        });

        serverThread.start();
        clientThread.start();

        try {
            serverThread.join();
            clientThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
