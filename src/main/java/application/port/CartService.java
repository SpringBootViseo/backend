package application.port;

import application.domain.Cart;
import application.domain.Product;
import application.port.in.CartUseCase;
import application.port.out.CartPort;
import application.port.out.ProductPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;
@Service
@AllArgsConstructor
public class CartService implements CartUseCase {
    CartPort cartPort;
    ProductPort productPort;

    @Override
    public Cart createCart(String idCart) {

            return cartPort.createCart(idCart);

    }

    @Override
    public Cart addProduct(String idCart, UUID idProduct) {
        Product product=productPort.getProduct(idProduct);
        Cart cart=this.getCart(idCart);
        return cartPort.addProduct(cart,product);
    }

    @Override
    public Cart deleteProduct(String idCart, UUID idProduct) {
        Product product=productPort.getProduct(idProduct);
        Cart cart=this.getCart(idCart);
        return cartPort.deleteProduct(cart,product);
    }


    @Override
    public Cart getCart(String idCart) {
        if(this.availableCart(idCart))
        return cartPort.getCart(idCart);
        else throw new NoSuchElementException("This cart doesn't exist!");
    }

    @Override
    public Boolean availableCart(String idCart) {
        return cartPort.availableCart(idCart);
    }

    @Override
    public void deleteCart(String idCart) {
        Cart cart = this.getCart(idCart);
        cartPort.deleteCart(cart);
    }
}
