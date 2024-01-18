package OOPhw5.notepad.model.repository.impl;

import OOPhw5.notepad.model.user;
import OOPhw5.notepad.model.repository.gbrepository;
import OOPhw5.notepad.util.uservalidator;
import OOPhw5.notepad.util.mapper.impl.usermapper;


import java.io.*;
import java.util.*;

public class userrepository implements gbrepository {
    private final usermapper mapper;
    private final String fileName;

    public static String prompt(String message) {
        Scanner in = new Scanner(System.in);
        System.out.print(message);
        return in.nextLine();
    }
    public static user createuser() {
        String firstName = prompt("Имя: ");
        String lastName = prompt("Фамилия: ");
        String phone = prompt("Номер телефона: ");
        return new user(firstName, lastName, phone);
    }

    public void userValid(user user) {
        uservalidator validator = new uservalidator();
        user.setFirstName(validator.isNameValid(user.getFirstName()));
        user.setLastName(validator.isNameValid(user.getLastName()));
    }

    public userrepository(String fileName) {
        this.fileName = fileName;
        this.mapper = new usermapper();
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void write(List<user> users) {
        List<String> lines = new ArrayList<>();
        for (user u : users) {
            lines.add(mapper.toInput(u));
        }
        saveAll(lines);
    }


    public List<String> readAll() {
        List<String> lines = new ArrayList<>();
        try {
            File file = new File(fileName);
            //создаем объект FileReader для объекта File
            FileReader fr = new FileReader(file);
            //создаем BufferedReader с существующего FileReader для построчного считывания
            BufferedReader reader = new BufferedReader(fr);
            // считаем сначала первую строку
            String line = reader.readLine();
            if (line != null) {
                lines.add(line);
            }
            while (line != null) {
                // считываем остальные строки в цикле
                line = reader.readLine();
                if (line != null) {
                    lines.add(line);
                }
            }
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public void saveAll(List<String> data) {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            for (String line : data) {
                // запись всей строки
                writer.write(line);
                // запись по символам
                writer.append('\n');
            }
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public List<user> findAll() {
        List<String> lines = readAll();
        List<user> users = new ArrayList<>();
        for (String line : lines) {
            users.add(mapper.toOutput(line));
        }
        return users;
    }

    @Override
    public user create(user user) {
        userValid(user);
        List<user> users = findAll();
        long max = 0L;
        for (user u : users) {
            long id = u.getId();
            if (max < id) {
                max = id;
            }
        }
        long next = max + 1;
        user.setId(next);
        users.add(user);
        write(users);
        return user;
    }

    @Override
    public Optional<user> findById(Long id) {
        List<user> users = findAll();
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<user> update(Long userId, user update) {
        List<user> users = findAll();
        user editUser = users.stream()
                .filter(u -> u.getId()
                        .equals(userId))
                .findFirst().orElseThrow(() -> new RuntimeException("User not found"));
        if (!update.getFirstName().isEmpty()) {
            editUser.setFirstName(update.getFirstName());
        }
        if (!update.getLastName().isEmpty()) {
            editUser.setLastName(update.getLastName());
        }
        if (!update.getPhone().isEmpty()) {
            editUser.setPhone(update.getPhone());
        }
        write(users);
        return Optional.of(update);
    }

    @Override
    public void delete(Long userId) {
        List<user> users = findAll();
        boolean removed = users.removeIf(user -> user.getId().equals(userId));

        if (removed) {
            write(users);
        } else {
            throw new RuntimeException("User not found");
        }
    }
}