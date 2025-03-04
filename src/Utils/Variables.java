package Utils;

import Types.Named;

import java.util.HashMap;

public class Variables extends HashMap<String, Named> {

    public Variables() {
        super();
    }

    public Variables(Variables vars) {
        super(vars);
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
        return this;
    }

    @Override
    public Named get(Object key) {
        return super.get(key).get();
    }
}
