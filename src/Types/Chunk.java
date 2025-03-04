package Types;

import Utils.Data;
import Utils.Functions;
import Utils.Variables;


public class Chunk extends Named {
    private final String code;
    private final String params;
    private Integer level;
    private Variables parentVars;

    public Chunk(String name, String params, String code, Integer level, Variables parentVars) {
        super(name);
        this.code = code;
        this.params = params;
        this.level = level + 1;
        this.parentVars = parentVars;
        if (this.level == 4) {
            this.level = 1;
        }
    }

    @Override
    public Named get() {
        Variables vars = new Variables(Functions.parse(this.params, Data.PARSERS, this.level));
        for (String key : vars.keySet()) {
            if (this.parentVars.containsKey(key)) {
                vars.put(key, this.parentVars.get(key));
            }
        }
        return Functions.parse(this.code, Data.PARSERS, this.level, vars).get(this.name);
    }

    public LiString toLiString() {
        return this.get().toLiString();
    }
}