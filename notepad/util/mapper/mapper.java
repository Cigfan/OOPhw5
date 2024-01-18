package OOPhw5.notepad.util.mapper;

import OOPhw5.notepad.model.user;

public interface mapper {
    String toInput(user e);
    user toOutput(String str);
}