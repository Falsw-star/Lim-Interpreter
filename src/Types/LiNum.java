package Types;

import Utils.Data;
import Utils.Functions;
import Utils.Variables;

import java.util.LinkedList;
import java.util.Objects;

public class LiNum extends Named {

    private Integer value;

    public LiNum(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public LiNum(String name, LiNum value) {
        this.name = name;
        this.value = value.getValue();
    }

    public LiNum(String name, String value, Variables vars) {
        this.name = name;
        this.value = valueOf(value.toCharArray(), vars).getValue();
    }

    public LiNum(String name) {
        this.name = name;
        this.value = 0;
    }

    public Integer getValue() {
        return value;
    }

    public LiNum setValue(Integer value) {
        this.value = value;
        return this;
    }

    public LiNum add(Integer value) {
        this.value += value;
        return this;
    }

    public LiNum add(LiNum liNum) {
        return add(liNum.getValue());
    }

    public LiNum subtract(Integer value) {
        this.value -= value;
        return this;
    }

    public LiNum subtract(LiNum liNum) {
        return subtract(liNum.getValue());
    }

    public LiNum multiply(Integer value) {
        this.value *= value;
        return this;
    }

    public LiNum multiply(LiNum liNum) {
        return multiply(liNum.getValue());
    }

    public LiNum divide(Integer value) {
        this.value /= value;
        return this;
    }

    public LiNum divide(LiNum liNum) {
        return divide(liNum.getValue());
    }

    public LiNum remainder(Integer value) {
        return new LiNum(this.name, this.value % value);
    }

    public LiNum remainder(LiNum liNum) {
        return remainder(liNum.getValue());
    }

    public LiNum equals(Integer value) {
        if (this.value.equals(value)) {
            return new LiNum(this.name, 1);
        } else {
            return new LiNum(this.name, 0);
        }
    }

    public LiNum equals(LiNum liNum) {
        return equals(liNum.getValue());
    }

    public LiNum not(Integer value) {
        if (value!=0) {
            return new LiNum(this.name, 0);
        } else {
            return new LiNum(this.name, 1);
        }
    }

    public LiNum not(LiNum liNum) {
        return not(liNum.getValue());
    }

    public static LiNum valueOf(String name, char[] value, Variables vars) {
        return new LiNum(name, valueOf(value, vars));
    }

    public static LiNum valueOf(char[] value, Variables vars) {
        LinkedList<String> nums = Functions.getStrings(value);

        String first = nums.poll();
        LiNum result = valueOf(first, vars);
        while (!nums.isEmpty()) {
            String op = nums.poll();
            LiNum liNum = valueOf(Objects.requireNonNull(nums.poll()), vars);

            switch (op) {
                case "=a":
                    result.add(liNum);
                    break;
                case "=s":
                    result.subtract(liNum);
                    break;
                case "=m":
                    result.multiply(liNum);
                    break;
                case "=d":
                    result.divide(liNum);
                    break;
                case "=r":
                    result = result.remainder(liNum);
                    break;
                case "=e":
                    result = result.equals(liNum);
                    break;
                case "=n":
                    result = result.not(liNum);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid operator: " + op);
            }
        }
        return result;
    }

    public static LiNum valueOf(String value, Variables vars) {
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
            return new LiNum("_", result);
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        Integer temp = this.value;

        while (temp != 0) {
            if (temp >= 1000) {
                result.append('m');
                temp -= 1000;
            } else if (temp >= 500) {
                result.append('d');
                temp -= 500;
            } else if (temp >= 100) {
                result.append('c');
                temp -= 100;
            } else if (temp >= 50) {
                result.append('l');
                temp -= 50;
            } else if (temp >= 10) {
                result.append('x');
                temp -= 10;
            } else if (temp >= 5) {
                result.append('v');
                temp -= 5;
            } else {
                result.append("i".repeat(Math.max(0, temp)));
                temp = 0;
            }
        }

        return result.toString();
    }

    @Override
    public LiString toLiString() {
        return new LiString(this.name, this.toString());
    }
}
