package application.adapters.mapper.mapperImpl;

import application.adapters.mapper.CartItemMapper;
import application.adapters.persistence.entity.CartItemEntity;
import application.adapters.web.presenter.CartItemDTO;
import application.domain.Cart;
import application.domain.CartItem;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
@AllArgsConstructor

public class CartItemMapperImpl implements CartItemMapper {
    ProductMapperImpl productMapper;
    @Override
    public CartItem cartItemEntityToCartItem(CartItemEntity cartItemEntity) {
        return new CartItem(cartItemEntity.getProduct(),cartItemEntity.getQuatity());
    }

    @Override
    public CartItemEntity cartItemToCartItemEntity(CartItem cartItem) {
        return new CartItemEntity(cartItem.getProduct(),cartItem.getQuatity());
    }

    @Override
    public List<CartItem> listCartItemEntityTolistCartItem(List<CartItemEntity> cartItemEntityList) {
        List<CartItem> cartItemList=new ArrayList<>();
        for (CartItemEntity cartItemEntity:cartItemEntityList
             ) {
            cartItemList.add(this.cartItemEntityToCartItem(cartItemEntity));
        }
        return cartItemList;
    }

    @Override
    public List<CartItemEntity> listCartItemTolistCartItemEntity(List<CartItem> cartItemList) {
        List<CartItemEntity> cartItemEntityList=new ArrayList<>();
        for (CartItem cartItem: cartItemList
             ) {
            cartItemEntityList.add(this.cartItemToCartItemEntity(cartItem));
        }
        return cartItemEntityList;
    }

    @Override
    public CartItem cartItemDTOToCartItem(CartItemDTO cartItemDTO) {
        return new CartItem(productMapper.productToProductDto(cartItemDTO.getProduct()),cartItemDTO.getQuatity());
    }

    @Override
    public CartItemDTO cartItemToCartItemDTO(CartItem cartItem) {
        return new CartItemDTO(productMapper.productDtoToProduct(cartItem.getProduct()),cartItem.getQuatity());
    }

    @Override
    public List<CartItem> listCartItemDTOTolistCartItem(List<CartItemDTO> cartItemDTOList) {
        List<CartItem> cartItemList=new ArrayList<>();
        for (CartItemDTO cartItemDTO:
              cartItemDTOList) {
            cartItemList.add(this.cartItemDTOToCartItem(cartItemDTO));

        }
        return cartItemList;
    }

    @Override
    public List<CartItemDTO> listCartItemTolistCartItemDTO(List<CartItem> cartItems) {
        List<CartItemDTO> cartItemDTOList=new ArrayList<>();
        for(CartItem cartItem:cartItems){
            cartItemDTOList.add(this.cartItemToCartItemDTO(cartItem));
        }
        return cartItemDTOList;
    }
}
