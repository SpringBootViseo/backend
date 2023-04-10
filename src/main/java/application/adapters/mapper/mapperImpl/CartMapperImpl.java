package application.adapters.mapper.mapperImpl;

import application.adapters.mapper.CartMapper;
import application.adapters.persistence.entity.CartEntity;
import application.adapters.persistence.entity.ProductEntity;
import application.adapters.web.presenter.CartResponseDTO;
import application.adapters.web.presenter.ProductDTO;
import application.domain.Cart;
import application.domain.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
@AllArgsConstructor
public class CartMapperImpl implements CartMapper {
    ProductMapperImpl productMapper;
    @Override
    public Cart cartEntityToCart(CartEntity cartEntity) {
        List<Product> cartItemList=productMapper.listProductEntityToListProduct(cartEntity.getProductEntityList());
        return new Cart(cartEntity.getId(),cartItemList);
    }

    @Override
    public CartEntity cartToCartEntity(Cart cart) {
        List<ProductEntity> cartItemEntityList=productMapper.listProductToListProductEntity(cart.getProductList());
        return new CartEntity(cart.getId(),cartItemEntityList);
    }

    @Override
    public List<Cart> listCartEntityToListCart(List<CartEntity> cartEntityList) {
        List<Cart> cartList=new ArrayList<>();
        for(CartEntity cartEntity:cartEntityList){
            cartList.add(this.cartEntityToCart(cartEntity));
        }
        return cartList;
    }

    @Override
    public List<CartEntity> listCartToListCartEntity(List<Cart> cartList) {
        List<CartEntity> cartEntityList=new ArrayList<>();
        for (Cart cart: cartList
             ) {
            cartEntityList.add(this.cartToCartEntity(cart));
        }
        return cartEntityList;
    }

    @Override
    public CartResponseDTO cartToCartResponseDTO(Cart cart) {
        List<ProductDTO> cartItemDTOList=productMapper.listProductToListProductDto(cart.getProductList());
        return new CartResponseDTO(cart.getId(),cartItemDTOList);
    }

    @Override
    public Cart cartResponseDTOToCar(CartResponseDTO cartResponseDTO) {
        List<Product> cartItemList=productMapper.listProductDTOToListProduct(cartResponseDTO.getCartItems());
        return new Cart(cartResponseDTO.getId(),cartItemList);
    }

    @Override
    public List<Cart> listCartResponseDTOToListCart(List<CartResponseDTO> cartResponseDTOList) {
        List<Cart> listCart=new ArrayList<>();
        for (CartResponseDTO cartDTO:cartResponseDTOList
             ) {
            listCart.add(this.cartResponseDTOToCar(cartDTO));
        }
        return listCart;
    }

    @Override
    public List<CartResponseDTO> listCartToListCartResponseDTO(List<Cart> cartList) {
        List<CartResponseDTO> cartResponseDTOList=new ArrayList<>();
        for(Cart cart:cartList){
            cartResponseDTOList.add(this.cartToCartResponseDTO(cart));
        }
        return cartResponseDTOList;
    }
}
