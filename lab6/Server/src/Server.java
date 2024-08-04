// из байтов получить объект типа Command





// Java program to illustrate Server side
// Implementation using DatagramSocket

import commands.*;
import exception.CommandNotFoundException;
import utility.CollectionManager;
import utility.CommandManager;

import javax.security.auth.login.LoginException;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.SQLDataException;
import java.util.HashMap;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int THREAD_POOL_SIZE = 10; // Задаем размер пула потоков

    public static void main(String[] args) throws Exception {
        CommandManager commandManager = new CommandManager();
        CollectionManager collectionManager = new CollectionManager();

        HashMap<String, Command> commands = commandManager.getCommands();
        commands.put("clear", new ClearCommand(collectionManager, commandManager));
        commands.put("info", new InfoCommand(collectionManager));
        commands.put("filter_less_than_personal_qualities_minimum", new FilterLessThanPersonalQualitiesMinimumCommand(collectionManager));
        commands.put("print_field_ascending_discipline", new PrintFieldAscendingPersonalQualitiesMinimumCommand(collectionManager));
        commands.put("remove_by_id", new RemoveByIdCommand(collectionManager, commandManager));
        commands.put("remove_all_by_personal_qualities_minimum", new RemoveByPersonalQualitiesMinimumCommand(collectionManager));
        commands.put("sort", new SortCommand(collectionManager));
        commands.put("add", new AddCommand(collectionManager));
        commands.put("show", new ShowCommand(collectionManager));
        commands.put("shuffle", new ShuffleCommand(collectionManager));
        commands.put("add_if_min", new AddIfMinCommand(collectionManager));
        commands.put("update_by_id", new UpdateIdCommand(collectionManager));
        commands.put("help", new HelpCommand(commandManager));
        commands.put("login", new LoginCommand(commandManager));
        commands.put("register", new RegisterUserCommand(commandManager));
        commands.put("exit", new ExitCommand());

        collectionManager.loadCollection();
        // Step 1 : Create a socket to listen at port 1234
        DatagramSocket datagramSocket = new DatagramSocket(1234);
        InetAddress serverAddress = InetAddress.getByName("localhost");


        byte[] receive = new byte[65535];
        byte[] sendData = new byte[65535];

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        Thread myThread1 = new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        //System.out.println(".");
                        DatagramPacket DpReceive = new DatagramPacket(receive, receive.length);
                        //System.out.println("пакет получен");
                        try {
                            datagramSocket.receive(DpReceive);
                        } catch (IOException e) {
                            System.out.println("Ошибка подключения потока.");
                        }
                        //System.out.println("....");
                        executor.execute(() -> {
                            try {
                                handleClient(DpReceive, datagramSocket, commandManager, collectionManager);
                            } catch (IOException e) {
                                System.out.println("Ошибка обработки пакета.");
                                ;
                            }
                        });
                    }
                }
            });
        myThread1.start();
        System.out.println("создали новый поток");

    }


    private static void handleClient(DatagramPacket DpReceive, DatagramSocket datagramSocket, CommandManager commandManager, CollectionManager collectionManager) throws IOException {
        InetAddress clientAddress = DpReceive.getAddress();
        int clientPort = DpReceive.getPort();
        try {
            byte[] sendData = new byte[65535];
            ByteArrayInputStream bis = new ByteArrayInputStream(DpReceive.getData());
            ObjectInputStream ois = new ObjectInputStream(bis);
            Object deserializedObject = ois.readObject();

            if (deserializedObject instanceof CommandData) {
                CommandData data = (CommandData)deserializedObject;

                System.out.println(data.returnUserName() + ": " + data.returnUserPassword() + "> " + data.returnCommandName());

                synchronized (collectionManager) {
                    String serverResponse = "";
                    try {
                        if (data.returnCommandName().equals("register") || data.returnCommandName().equals("login")) {
                            serverResponse = commandManager.executeCommand("", data.returnCommandName() + " " + data.returnArgs());

                        } else {
                            String loginResponse = commandManager.executeCommand(data.returnUserName(), "login " + data.returnUserName() + " " + data.returnUserPassword());
                            serverResponse = commandManager.executeCommand(data.returnUserName(), data.returnCommandName() + " " + data.returnArgs());
                        }
                    } catch (LoginException | CommandNotFoundException | RuntimeException e) {
                        serverResponse = e.getMessage();
                    }
                    sendData = serverResponse.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                    datagramSocket.send(sendPacket);
                }
            }
        } catch (Exception e) {
            System.err.println("error "+ e.getMessage() + ". " + e);


            String serverResponse = e.getMessage();
            byte[] sendData = serverResponse.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
            datagramSocket.send(sendPacket);
        }
    }
}
