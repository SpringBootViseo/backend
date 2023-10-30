package application.port;

import application.domain.Cart;
import application.domain.Product;
import application.port.in.CartUseCase;
import application.port.out.CartPort;
import application.port.out.ProductPort;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CartService implements CartUseCase {
    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    CartPort cartPort;
    ProductPort productPort;

    @Override
    public Cart createCart(String idCart) {
        logger.info("Creating a cart with id: {}", idCart);
        return cartPort.createCart(idCart);
    }

    @Override
    public Cart addProduct(String idCart, UUID idProduct) {
        logger.info("Adding product with id {} to cart with id {}", idProduct, idCart);

        Product product = productPort.getProduct(idProduct);
        Cart cart = this.getCart(idCart);
        return cartPort.addProduct(cart, product);
    }

    @Override
    public Cart deleteProduct(String idCart, UUID idProduct) {
        logger.info("Deleting product with id {} from cart with id {}", idProduct, idCart);

        Product product = productPort.getProduct(idProduct);
        Cart cart = this.getCart(idCart);
        return cartPort.deleteProduct(cart, product);
    }

    @Override
    public Cart getCart(String idCart) {
        logger.debug("call available cart with id: {}",idCart);
        if (this.availableCart(idCart)) {
            logger.info("Getting cart with id: {}", idCart);
            return cartPort.getCart(idCart);
        } else {
            logger.error(" cart with id : {} doesn't exist!",idCart);
            throw new NoSuchElementException("This cart doesn't exist!");
        }
    }

    @Override
    public Boolean availableCart(String idCart) {
        logger.info("Checking if cart with id {} is available", idCart);
        return cartPort.availableCart(idCart);
    }

    @Override
    public void deleteCart(String idCart) {
        logger.info("Deleting cart with id: {}", idCart);
        Cart cart = this.getCart(idCart);
        cartPort.deleteCart(cart);
    }
}
