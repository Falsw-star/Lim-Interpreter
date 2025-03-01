package Types;

import Utils.Data;
import Utils.Variables;

import java.util.LinkedList;
import java.util.Objects;

public class Num extends Named {

    private Integer value;

    public Num(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public Num(String name, Num value) {
        this.name = name;
        this.value = value.getValue();
    }

    public Num(String name, String value, Variables vars) {
        this.name = name;
        this.value = valueOf(value.toCharArray(), vars).getValue();
    }

    public Num(String name) {
        this.name = name;
        this.value = 0;
    }

    public Integer getValue() {
        return value;
    }

    public Num setValue(Integer value) {
        this.value = value;
        return this;
    }

    public Num add(Integer value) {
        this.value += value;
        return this;
    }

    public Num add(Num num) {
        return add(num.getValue());
    }

    public Num subtract(Integer value) {
        this.value -= value;
        return this;
    }

    public Num subtract(Num num) {
        return subtract(num.getValue());
    }

    public Num multiply(Integer value) {
        this.value *= value;
        return this;
    }

    public Num multiply(Num num) {
        return multiply(num.getValue());
    }

    public Num divide(Integer value) {
        this.value /= value;
        return this;
    }

    public Num divide(Num num) {
        return divide(num.getValue());
    }

    public Num remainder(Integer value) {
        return new Num(this.name, this.value % value);
    }

    public Num remainder(Num num) {
        return remainder(num.getValue());
    }

    public Num equals(Integer value) {
        if (this.value.equals(value)) {
            return new Num(this.name, 1);
        } else {
            return new Num(this.name, 0);
        }
    }

    public Num equals(Num num) {
        return equals(num.getValue());
    }

    public Num not(Integer value) {
        if (value!=0) {
            return new Num(this.name, 0);
        } else {
            return new Num(this.name, 1);
        }
    }

    public Num not(Num num) {
        return not(num.getValue());
    }

    public static Num valueOf(String name, char[] value, Variables vars) {
        return new Num(name, valueOf(value, vars));
    }

    public static Num valueOf(char[] value, Variables vars) {
        LinkedList<String> nums = getStrings(value);

        String first = nums.poll();
        Num result = valueOf(first, vars);
        while (!nums.isEmpty()) {
            String op = nums.poll();
            Num num = valueOf(Objects.requireNonNull(nums.poll()), vars);

            switch (op) {
                case "=a":
                    result.add(num);
                    break;
                case "=s":
                    result.subtract(num);
                    break;
                case "=m":
                    result.multiply(num);
                    break;
                case "=d":
                    result.divide(num);
                    break;
                case "=r":
                    result = result.remainder(num);
                    break;
                case "=e":
                    result = result.equals(num);
                    break;
                case "=n":
                    result = result.not(num);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid operator: " + op);
            }
        }
        return new Num("_", result);
    }

    private static LinkedList<String> getStrings(char[] value) {
        boolean breakNext = false;
        LinkedList<String> nums = new LinkedList<>();
        StringBuilder current = new StringBuilder();
        for (int i = 0; i < value.length; i++) {

            if (breakNext) {
                breakNext = false;
                continue;
            }

            char c = value[i];

            if (c == '=') {
                nums.add(current.toString());
                current = new StringBuilder();
                current.append(c);
                current.append(value[i + 1]);
                breakNext = true;
                nums.add(current.toString());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        nums.add(current.toString());
        return nums;
    }

    public static Num valueOf(String value, Variables vars) {
        if (!Data.NUMCHARS.contains(value.charAt(0))) {
            return vars.getNum(value);
        } else {
            char[] chars = value.toCharArray();
            Integer result = 0;
            for (char c : chars) {
                if (!Data.NUMREFLECT.containsKey(c)) {
                    throw new IllegalArgumentException("Invalid character: " + c);
                }
                result += Data.NUMREFLECT.get(c);
            }
            return new Num("_", result);
        }
    }

    @Override
    public String toString() {
        return getValue().toString();
    }
}
