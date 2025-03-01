package Types;

public abstract class Named {
    String name;

    public String getName() {
        return this.name;
    }

    public abstract LiString toLiString();
}
