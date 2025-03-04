package Types;

public class ManaCradle extends Named {

    private LiNum value;
    private ManaTypes type;

    public ManaCradle(String name, LiNum value, Interactable source) {
        super(name);
        this.value = value;
    }

    public String toString() {
        return "";
    }

    @Override
    public LiString toLiString() {
        return new LiString("_", toString());
    }
}
