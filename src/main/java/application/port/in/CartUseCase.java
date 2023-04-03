package application.port.in;

import application.domain.Cart;

import java.util.UUID;

public interface CartUseCase {
    Cart createCart(String idCart);
    Cart addProduct(String idCart, UUID idProduct);
    Cart deleteProduct(String idCart,UUID idProduct);
    Cart getCart(String idCart);
    Boolean availableCart(String idCart);
}
