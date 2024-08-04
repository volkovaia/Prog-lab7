// Java program to illustrate Client side
// Implementation using DatagramSocket

import commands.*;
import exception.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.System.exit;
//import Command



//надо, чтобы до того, как отправить на сервер, была проверка на корректность введённой команды

public class Client {
    public static void main(String args[]) throws IOException {

        String verifiedUserName = "NotAuthorizedUser";
        String verifiedUserPassword = "eYv4euWtr6tcvT0BoVMu";
        System.out.println("Авторизация пользователя:  введите login(/register) UserName Password");
        Scanner sc = new Scanner(System.in);

        // Step 1:Create the socket object for
        // carrying the data.

        // loop while user not enters "exit"
        while (true) {
            if (!(verifiedUserName.equals("NotAuthorizedUser") && verifiedUserPassword.equals("eYv4euWtr6tcvT0BoVMu"))) {
                System.out.print(verifiedUserName + ">");
            }
            String inp = sc.nextLine();
            inp = verifiedUserName + " " + verifiedUserPassword + " " + inp;
            CommandData commandData = null;

            //System.out.println(inp);
            try {
                commandData = new CommandData(inp);
            } catch (CommandNotFoundException e){
                System.out.println("Ошибка при распозновании названии команды");
                continue;
            } catch (UnknownUserException e) {
                System.out.println("Ошибка авторизации пользователя");
                continue;
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Ошибка распознования имени пользователя");
                continue;
            } catch (UnknownPasswordException e) {
                System.out.println("Ошибка распознования пароля пользователя");
                continue;
            } catch (ExitCommandRaised e) {
                System.out.println("Client: выход не авторизованного клиента");
                System.exit(0);
            }
            if (verifiedUserName.equals("NotAuthorizedUser") &&  verifiedUserPassword.equals("eYv4euWtr6tcvT0BoVMu") && !(commandData.getCommand().equals("login") || commandData.getCommand().equals("register"))) {
                System.out.println("Не авторизованные пользователи не имеют доступа к коллекции");
                continue;
            }
                byte buf[] = null;
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(commandData);
                objectOutputStream.close();
                buf = byteArrayOutputStream.toByteArray();

//                System.out.println("пакет получен")

                try {
                    DatagramSocket clientSocket = new DatagramSocket();
                    //InetAddress serverAddress = InetAddress.getLocalHost();
                    InetAddress serverAddress = InetAddress.getByName("localhost");
                    clientSocket.connect(serverAddress, 1234);

                    byte[] receiveData = new byte [1000000];
                    //Отправка пакетов на сервер
                    DatagramPacket sendPacket = new DatagramPacket(buf, buf.length, serverAddress, 1234);
                    clientSocket.send(sendPacket);


                    if (commandData.returnCommandName().equals("exit")) {
                        System.exit(0);
                    }

                    //Обработка ответов от сервера
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    clientSocket.receive(receivePacket);
                    String serverResponse = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    System.out.println("Получено от сервера: \n" + serverResponse);
                    if (serverResponse.equals("Пользователь успешно авторизован") || serverResponse.equals("Регистрация пользователя прошла успешно")) {
                        verifiedUserName = inp.split(" ")[3];
                        verifiedUserPassword = inp.split(" ")[4];
                    }
                    clientSocket.close();
                } catch (Exception e) {
                    System.out.println("Сервер временно недоступен.");
                }

            }
        }
    }

