package Types;

public abstract class Named {
    String name;

    public Named(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Named get() {
        return this;
    }

    public abstract LiString toLiString();
}