package com.trungbeso.dreamshops.services.cart;

import com.trungbeso.dreamshops.exception.ResourceNotFoundException;
import com.trungbeso.dreamshops.models.Cart;
import com.trungbeso.dreamshops.models.CartItem;
import com.trungbeso.dreamshops.repositories.ICartItemRepository;
import com.trungbeso.dreamshops.repositories.ICartRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartService implements ICartService{

	ICartRepository cartRepository;
	ICartItemRepository cartItemRepository;

	@Override
	public Cart getCart(Long id) {
		Cart cart = cartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
		BigDecimal totalAmount = cart.getTotalAmount();
		cart.setTotalAmount(totalAmount);

		return cartRepository.save(cart);
	}

	@Override
	public void clearCart(Long id) {
		Cart cart = getCart(id);
		cartItemRepository.deleteAllByCartId(id);
		cart.getItems().clear();
		cartRepository.deleteById(id);
	}

	@Override
	public BigDecimal getTotalPrice(Long id) {
		Cart cart = getCart(id);
	return cart.getTotalAmount();
		  //getItems()
//			  .stream()
//			  .map(CartItem::getTotalPrice)
//			  .reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
