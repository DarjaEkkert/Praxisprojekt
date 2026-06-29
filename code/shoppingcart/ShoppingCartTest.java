package shoppingcart;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ShoppingCartTest {

@Test
void neuerWarenkorbIstLeer() {

    ShoppingCart cart = new ShoppingCart();

    assertEquals(0, cart.getItemCount());
}

    @Test
void artikelKannHinzugefuegtWerden() {

    ShoppingCart cart = new ShoppingCart();

    cart.addItem("Buch");

    assertEquals(1, cart.getItemCount());
}
}