package OOPhw5.notepad.util;

public class uservalidator {
    public String isNameValid (String name){
        if (name.isEmpty()){
            throw new RuntimeException("Вы не ввели данные !");
        }
        return name.trim().replaceAll(" ","");
    }
}