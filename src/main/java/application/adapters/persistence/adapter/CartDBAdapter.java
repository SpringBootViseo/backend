package application.adapters.persistence.adapter;

import application.adapters.exception.CartAlreadyExistsException;
import application.adapters.exception.CartNotFoundException;
import application.adapters.mapper.mapperImpl.CartMapperImpl;
import application.adapters.persistence.entity.CartEntity;
import application.adapters.persistence.repository.CartRepository;
import application.domain.Cart;
import application.domain.Product;
import application.port.out.CartPort;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor

public class CartDBAdapter implements CartPort {
    private CartRepository cartRepository;
    private CartMapperImpl cartMapper;
    private static final Logger logger = LoggerFactory.getLogger(CartDBAdapter.class);

    @Override
    public Cart createCart(String idCart) {
        logger.trace("check if id Cart : {} exists",idCart);
        if(cartRepository.findById(idCart).isPresent()){
            logger.error("Cart with ID {} already exists", idCart);
            throw new CartAlreadyExistsException("Cart already exists!");
        }
        else{
            logger.debug("Creating a new cart with ID: {}", idCart);
            CartEntity cartEntity=cartRepository.save(new CartEntity(idCart,new ArrayList<>()));

             return cartMapper.cartEntityToCart(cartEntity) ;
        }
    }

    @Override
    public Cart addProduct(Cart cart, Product product) {
        List<Product> cartItemList = cart.getProductList();

        logger.trace("Check if the product with id : {} is already in the cart with id ",cart.getId(),product.getId());
        boolean containsProduct = cartItemList.stream()
                .anyMatch(cartItem -> cartItem.getId().equals(product.getId()));

        if (!containsProduct) {
            cartItemList.add(product);
            logger.trace("Added product with ID {} to the cart.", product.getId());
        } else {
            logger.trace("Product with ID {} is already in the cart. No action taken.", product.getId());
        }

        cart.setProductList(cartItemList);

        CartEntity savedCart = cartRepository.save(cartMapper.cartToCartEntity(cart));

        logger.debug("Cart updated with ID {} after adding product.", cart.getId());

        return cartMapper.cartEntityToCart(savedCart);
    }



    @Override
    public Cart deleteProduct(Cart cart, Product product) {
        logger.debug("Deleting product with ID {} from cart with ID {}", product.getId(), cart.getId());
        List<Product> cartItemEntityList= cart.getProductList().stream().filter(product1 -> !product1.getId().equals(product.getId())).collect(Collectors.toList());
        cart.setProductList(cartItemEntityList);
        CartEntity savedCart=cartRepository.save(cartMapper.cartToCartEntity(cart));
        logger.info("Product with ID {} has been successfully deleted from cart with ID {}", product.getId(), cart.getId());
        return cartMapper.cartEntityToCart(savedCart);
    }


    @Override
    public Cart getCart(String idCart) {
        logger.trace("Check if the Cart with id : {} already exist",idCart);
        if (cartRepository.findById(idCart).isPresent()) {
            logger.info("Fetching cart with ID: {}", idCart);
            CartEntity cart = cartRepository.findById(idCart).get();
            return cartMapper.cartEntityToCart(cart);
        } else {
            logger.error("Cart with ID {} not found", idCart);
            throw new CartNotFoundException(idCart);
        }
    }

    @Override
    public Boolean availableCart(String idCart) {
        logger.info("Checking cart availability for ID: {}", idCart);
        return cartRepository.findById(idCart).isPresent();
    }

    @Override
    public void deleteCart(Cart cart) {
        logger.info("Deleting cart with ID: {}", cart.getId());
        cart.setProductList(new ArrayList<>());
        cartRepository.save(cartMapper.cartToCartEntity(cart));
    }
}