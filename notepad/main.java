package OOPhw5.notepad;

import notepad.controller.usercontroller;
import notepad.model.repository.bgrepository;
import notepad.model.repository.impl.userrepository;
import notepad.view.userview;

import static notepad.util.DBConnector.DB_PATH;
import static notepad.util.DBConnector.createDB;

public class main {
    public static void main(String[] args) {
        createDB();
        gbrepository repository = new userrepository(DB_PATH);
        usercontroller controller = new usercontroller(repository);
        userview view = new userview(controller);
        view.run();

    }
}