package application.adapters.mapper;

import application.adapters.persistence.entity.CartEntity;
import application.adapters.web.presenter.CartResponseDTO;
import application.domain.Cart;

import java.util.List;

public interface CartMapper {
    Cart cartEntityToCart(CartEntity cartEntity);
    CartEntity cartToCartEntity(Cart cart);
    List<Cart> listCartEntityToListCart(List<CartEntity> cartEntityList);
    List<CartEntity> listCartToListCartEntity(List<Cart> cartList);
    CartResponseDTO cartToCartResponseDTO(Cart cart);
    Cart cartResponseDTOToCar(CartResponseDTO cartResponseDTO);
    List<Cart> listCartResponseDTOToListCart(List<CartResponseDTO> cartResponseDTOList);
    List<CartResponseDTO> listCartToListCartResponseDTO(List<Cart> cartList);

}
