package org.aldousdev.teas.service.cart;

import org.aldousdev.teas.dto.request.CartInputDto;
import org.aldousdev.teas.dto.response.CartProjection;
import org.aldousdev.teas.models.cart.Cart;
import org.aldousdev.teas.models.user.Account;

import java.util.List;

public interface CartService {

    // ALL
    List<CartProjection> getCartProjectionsByAccountId(Long accountId);
    List<Cart> getCartsByAccountId(Long accountId);


    // used for return to frontend
    CartProjection getCartProjectionById(Long id);
    Cart getCartById(Long id);

    // CUD
    Long createCart(CartInputDto cartInputDto, Account account);
    Long updateCart(Long id, CartInputDto cartInputDto, Account account);



    void deleteCartById(Long id, Account authenticatedAccount);


    void deleteCartsByAccountId(Long accountId, Account authenticatedAccount) ;






}
