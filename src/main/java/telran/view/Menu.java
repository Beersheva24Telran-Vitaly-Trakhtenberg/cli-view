package telran.view;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Menu implements Item
{
    private String menu_name;
    private Item[] items;
    private String symbol = "_";
    private int n_symb = 20;

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setNSymbol(int n_symb) {
        this.n_symb = n_symb;
    }

    public Menu(Item...items)
    {
        this.items = Arrays.copyOf(items, items.length);
    }

    @Override
    public String displayName() {
        return menu_name;
    }

    @Override
    public void perform(InputOutput io) {
        displayTitle(io);
        Item item = null;
        boolean running = true;
        do {
            displayItems(io);
            int selectedItem = io.readNumberRange("Select item", "Wrong item number selection", 1, items.length).intValue();
            item = items[selectedItem - 1];
            try {
                item.perform(io);
                running = !item.isExit();
            } catch (RuntimeException e) {
                io.writeLine(e.getMessage());
                running = false;
            }
        } while(running);
    }

    private void displayItems(InputOutput io)
    {
        IntStream.range(0, items.length).forEach((i -> io.writeLine(String.format("%d. $s", i + 1, items[i].displayName()))));
    }

    private void displayTitle(InputOutput io)
    {
        io.writeString(symbol.repeat(n_symb));
        io.writeString(menu_name);
        io.writeLine(symbol.repeat(n_symb));
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
