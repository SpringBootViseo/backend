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
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CartDBAdapter implements CartPort {
    private CartRepository cartRepository;
    private CartMapperImpl cartMapper;
    @Override
    public Cart createCart(String idCart) {
        if(cartRepository.findById(idCart).isPresent()){
            throw new CartAlreadyExistsException("Cart already exists!");
        }
        else{
        CartEntity cartEntity=cartRepository.save(new CartEntity(idCart,new ArrayList<>()));


            return cartMapper.cartEntityToCart(cartEntity) ;
        }
    }

    @Override
    public Cart addProduct(Cart cart, Product product) {
        List<Product> cartItemList=cart.getProductList();
        boolean containsProduct = cartItemList.stream()
                .anyMatch(cartItem -> cartItem.getId().equals( product.getId()));

        if(!containsProduct){

            cartItemList.add(product);
        }
        cart.setProductList(cartItemList);

        CartEntity savedCart=cartRepository.save(cartMapper.cartToCartEntity(cart));
        return cartMapper.cartEntityToCart(savedCart);



    }

    @Override
    public Cart deleteProduct(Cart cart, Product product) {
        List<Product> cartItemEntityList= cart.getProductList().stream().filter(product1 -> !product1.getId().equals(product.getId())).collect(Collectors.toList());
        cart.setProductList(cartItemEntityList);
        CartEntity savedCart=cartRepository.save(cartMapper.cartToCartEntity(cart));
        return cartMapper.cartEntityToCart(savedCart);
    }


    @Override
    public Cart getCart(String idCart) {
        if(cartRepository.findById(idCart).isPresent()) {
            CartEntity cart = cartRepository.findById(idCart).get();
            return cartMapper.cartEntityToCart(cart);
        }
        else throw new CartNotFoundException(idCart);
    }

    @Override
    public Boolean availableCart(String idCart) {

        return cartRepository.findById(idCart).isPresent();
    }

    @Override
    public void deleteCart(Cart cart) {
        cart.setProductList(new ArrayList<>());
        cartRepository.save(cartMapper.cartToCartEntity(cart));
    }
}
