package shoppingcart;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    private List<String> items;

    public ShoppingCart() {
        items = new ArrayList<>();
    }

    public int getItemCount() {
        return items.size();
    }
    public void addItem(String item) {

        if (item == null || item.isBlank()) {
            return;
        }
    items.add(item);
}
public void removeItem(String item) {

    items.remove(item);
}
}