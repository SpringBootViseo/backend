package application.port;

import application.domain.Cart;
import application.domain.Product;
import application.port.in.CartUseCase;
import application.port.out.CartPort;
import application.port.out.ProductPort;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;
@Service
@AllArgsConstructor
public class CartService implements CartUseCase {
    CartPort cartPort;
    ProductPort productPort;
    private final static Logger logger= LogManager.getLogger(CartService.class);
    @Override
    public Cart createCart(String idCart) {
        logger.info(String.format("create cart with id :"+idCart));
        return cartPort.createCart(idCart);

    }

    @Override
    public Cart addProduct(String idCart, UUID idProduct) {
        logger.debug("get product with id :"+idProduct);
        Product product=productPort.getProduct(idProduct);
        logger.debug("get cart with id :"+idCart);
        Cart cart=this.getCart(idCart);
        logger.info("add product "+product.toString()+" to cart"+cart.toString() );
        return cartPort.addProduct(cart,product);
    }

    @Override
    public Cart deleteProduct(String idCart, UUID idProduct) {
        logger.debug("get product with id :"+idProduct);
        Product product=productPort.getProduct(idProduct);
        logger.debug("get cart with id :"+idCart);
        Cart cart=this.getCart(idCart);
        logger.info("delete product "+product.toString()+" from cart"+cart.toString() );

        return cartPort.deleteProduct(cart,product);
    }


    @Override
    public Cart getCart(String idCart) {
        if(this.availableCart(idCart)){
            logger.info("get cart with id "+idCart);
            return cartPort.getCart(idCart);
        }

        else {
            logger.error("This cart doesn't exist!");
            throw new NoSuchElementException("This cart doesn't exist!");
        }
    }

    @Override
    public Boolean availableCart(String idCart) {
        logger.debug("Check if this cart is available ");
        return cartPort.availableCart(idCart);
    }

    @Override
    public void deleteCart(String idCart) {
        logger.debug("Get cart with id "+idCart);
        Cart cart = this.getCart(idCart);
        logger.info("Delete cart "+cart.toString());
        cartPort.deleteCart(cart);
    }
}