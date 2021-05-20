import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Action {
    FORWARD("F", "forward"),
    BACKWARDS("B", "backwards"),
    LEFT("L", "left"),
    RIGHT("R", "right");

    private final String shortName;
    private final String fullName;

    private static final Map<String, Action> lookup = new HashMap<>();

    static {
        for (Action a : Action.values()) {
            lookup.put(a.getShortName(), a);
        }
    }

    public static Action get(String shortName) {
        if (lookup.get(shortName) == null) {
            throw new IllegalArgumentException("Command not found");
        }
        return lookup.get(shortName);
    }


    @Override
    public String toString() {
        return fullName;
    }
}
