package application.adapters.mapper;

import application.adapters.persistence.entity.CartItemEntity;
import application.adapters.web.presenter.CartItemDTO;
import application.adapters.web.presenter.CartResponseDTO;
import application.domain.Cart;
import application.domain.CartItem;

import java.util.List;

public interface CartItemMapper {
    CartItem cartItemEntityToCartItem(CartItemEntity cartItemEntity);
    CartItemEntity cartItemToCartItemEntity(CartItem cartItem);
    List<CartItem> listCartItemEntityTolistCartItem(List<CartItemEntity> cartItemEntityList);
    List<CartItemEntity> listCartItemTolistCartItemEntity(List<CartItem> cartItemList);
    CartItem cartItemDTOToCartItem(CartItemDTO cartItemDTO);
    CartItemDTO cartItemToCartItemDTO(CartItem cartItem);
    List<CartItem> listCartItemDTOTolistCartItem(List<CartItemDTO> cartItemDTOList);
    List<CartItemDTO> listCartItemTolistCartItemDTO(List<CartItem> cartItems);

}
