package Utils;

import Types.Named;
import Types.LiNum;

import java.util.HashMap;

public class Variables extends HashMap<String, Named> {

    private String name;

    public Variables() {
        super();
    }

    public Variables create(Named var) {
        String name = var.getName();
        if (Data.NUMCHARS.contains(name.charAt(0))) {
            throw new IllegalArgumentException("Invalid variable name head: " + name);
        }
        if (this.containsKey(name)) {
            throw new IllegalArgumentException("Variable already exists: " + name);
        }
        this.put(name, var);
        this.name = name;
        return this;
    }

    public LiNum getNum(String name) {
        return (LiNum) this.get(name);
    }
}
