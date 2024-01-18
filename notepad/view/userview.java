package OOPhw5.notepad.view;

import notepad.controller.usercontroller;
import notepad.model.user;
import notepad.model.repository.impl.userrepository;

import java.util.Objects;

public class userview  {
    private final usercontroller usercontroller;

    public userview(usercontroller usercontroller) {
        this.usercontroller = usercontroller;
    }

    public void run(){
        while (true) {
            String command = userrepository.prompt(
                    """
                            1. Создать контакт\s
                            2. Просмотреть контакт\s
                            3. Изменить контакт\s
                            4. Просмотреть список контактов\s
                            5. Удалить контакт\s
                            6. Выход\s
                            Введите цифру команды:
                            """);
            if (Objects.equals(command, "6")) return;
            switch (command) {
                case "1":
                    user u = usercontroller.createUser();
                    usercontroller.saveUser(u);
                    break;
                case "2":
                    String id = usercontroller.prompt("User ID: ");
                    try {
                        user user = usercontroller.readUser(Long.parseLong(id));
                        System.out.println(user);
                        System.out.println();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "3":
                    String userId = usercontroller.prompt("Enter user id: ");
                    usercontroller.updateUser(userId, usercontroller.createUser());
                    break;

                case "4":
                    System.out.println(usercontroller.readAll());
                    break;
                case "5":
                    String deleteUserId = userrepository.prompt("Enter user the id you want to delete: ");
                    usercontroller.deleteUser(Long.valueOf(deleteUserId));
                    break;
            }
        }
    }
}