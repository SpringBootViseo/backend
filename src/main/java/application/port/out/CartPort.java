package application.port.out;

import application.domain.Cart;
import application.domain.Product;

public interface CartPort {
    Cart createCart(String idcart);
    Cart addProduct(Cart cart, Product product);
    Cart deleteProduct(Cart cart,Product product);
    Cart getCart(String idCart);
    Boolean availableCart(String idCart);
    void deleteCart(Cart cart);
}
