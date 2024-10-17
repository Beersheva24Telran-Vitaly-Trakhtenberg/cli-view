package telran.view;

import java.util.function.Consumer;

public interface Item
{
    String displayName();
    void perform(InputOutput io);
    boolean isExit();
    public static Item of(String display_name, Consumer<InputOutput> action, boolean is_exit)
    {
        return new Item() {

            @Override
            public String displayName() {
                return display_name;
            }

            @Override
            public void perform(InputOutput io) {
                action.accept(io);
            }

            @Override
            public boolean isExit() {
                return is_exit;
            }
        };
    }
    public static Item of(String display_name, Consumer<InputOutput> action)
    {
        return of(display_name, action, false);
    }
    public static Item ofExit()
    {
        return of("Exit", io -> {}, true);
    }
}
