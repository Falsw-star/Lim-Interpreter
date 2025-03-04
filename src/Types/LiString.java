package Types;

import Utils.Functions;
import Utils.Variables;

import java.util.LinkedList;
import java.util.Objects;

public class LiString extends Named {

    private String value;

    public LiString(String name, String value, Variables vars) {
        super(name);
        this.value = valueOf(value.toCharArray(), vars).getValue();
    }

    public LiString(String name, String value) {
        super(name);
        this.value = value;
    }

    public LiString(String name) {
        super(name);
        this.value = "";
    }

    public String getValue() {
        return this.value;
    }

    public LiString add(LiString liString) {
        this.value += liString.getValue();
        return this;
    }

    public LiString multiply(LiNum liNum) {
        this.value = this.value.repeat(liNum.getValue());
        return this;
    }

    public LiNum equals(String String) {
        if (this.value.equals(String)) {
            return new LiNum("_", 1);
        } else {
            return new LiNum("_", 0);
        }
    }

    public LiNum equals(LiString liString) {
        return equals(liString.getValue());
    }

    public LiNum length() {
        return new LiNum("_", this.value.length());
    }

    public LiString valueOf(char[] value, Variables vars) {
        LinkedList<String> strings = Functions.getStrings(value);

        LiString result = valueOf(Objects.requireNonNull(strings.poll()), vars).toLiString();
        while (!strings.isEmpty()) {
            String op = strings.poll();
            Named string = valueOf(Objects.requireNonNull(strings.poll()), vars);
            switch (op) {
                case "=a":
                    result.add(string.toLiString());
                    break;
                case "=m":
                    if (string instanceof LiNum) {
                        result.multiply((LiNum) string);
                    } else {
                        throw new IllegalArgumentException("Invalid syntax: LiString.multiply(" + string.getClass().getName() + ")");
                    }
                    break;
                case "=e":
                    result = result.equals(string.toLiString()).toLiString();
                    break;
                default:
                    throw new IllegalArgumentException("Invalid operator: " + op);
            }
        }
        return result;
    }

    public Named valueOf(String value, Variables vars) {
        if (vars.containsKey(value)) {
            return vars.get(value);
        } else {
            return new LiString("_", value);
        }
    }

    @Override
    public String toString() {
        return this.value;
    }

    public LiString toLiString() {
        return new LiString(this.name, this.value);
    }
}
