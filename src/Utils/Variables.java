package Utils;

import Types.Named;
import Types.Num;

import java.util.HashMap;

public class Variables extends HashMap<String, Object> {

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

    public Num getNum(String name) {
        return (Num) this.get(name);
    }
}
