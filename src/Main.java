import java.io.IOException;
//1)	����������� ��������� � ���������� ����� ������� ��������������.
// ������ � ������ �������� ���� � ������ � ������� ��������� �������� DTO- dataTransferObject.
// �� ������� ����� ��������� �������� (����� ������ ������� ���� �����-������),
// ������ ����� ������� ��� ��������: �����������, �������������� � ����� �� �������
// (������ ����������). ������ ���������� �������� �������.
// ������: ���������� ��������� ���� �������� ����� ������ ����� � �������
// ��� ��� ������������ � ������� ��������������.
// ���� ��������: 1- ��������������, 2- �����������, 0- ����� �� ��������.

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
