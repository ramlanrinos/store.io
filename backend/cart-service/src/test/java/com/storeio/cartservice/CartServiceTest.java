package com.storeio.cartservice;

import com.storeio.cartservice.dto.AddToCartRequest;
import com.storeio.cartservice.dto.CartResponse;
import com.storeio.cartservice.dto.UpdateCartItemRequest;
import com.storeio.cartservice.entity.Cart;
import com.storeio.cartservice.entity.CartItem;
import com.storeio.cartservice.repository.CartItemRepository;
import com.storeio.cartservice.repository.CartRepository;
import com.storeio.cartservice.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @InjectMocks
    private CartService cartService;

    private Cart sampleCart;
    private CartItem sampleItem;

    @BeforeEach
    void setUp() {
        sampleCart = Cart.builder()
                .id(1L)
                .userId(101L)
                .items(new ArrayList<>())
                .updatedAt(LocalDateTime.now())
                .build();

        sampleItem = CartItem.builder()
                .id(10L)
                .cart(sampleCart)
                .productId(501L)
                .quantity(2)
                .addedAt(LocalDateTime.now())
                .build();

        sampleCart.getItems().add(sampleItem);
    }

    @Test
    void getCartByUserId_ExistingCart_Success() {
        when(cartRepository.findByUserId(101L)).thenReturn(Optional.of(sampleCart));

        CartResponse response = cartService.getCartByUserId(101L);

        assertNotNull(response);
        assertEquals(101L, response.getUserId());
        assertEquals(1, response.getItems().size());
        assertEquals(2, response.getTotalItemsCount());
    }

    @Test
    void addItemToCart_NewItem_Success() {
        AddToCartRequest request = AddToCartRequest.builder()
                .productId(502L)
                .quantity(3)
                .build();

        when(cartRepository.findByUserId(101L)).thenReturn(Optional.of(sampleCart));
        when(cartItemRepository.findByCartIdAndProductId(1L, 502L)).thenReturn(Optional.empty());
        when(cartRepository.save(any(Cart.class))).thenReturn(sampleCart);

        CartResponse response = cartService.addItemToCart(101L, request);

        assertNotNull(response);
        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    void updateCartItemQuantity_Success() {
        UpdateCartItemRequest request = UpdateCartItemRequest.builder()
                .quantity(5)
                .build();

        when(cartRepository.findByUserId(101L)).thenReturn(Optional.of(sampleCart));
        when(cartItemRepository.findById(10L)).thenReturn(Optional.of(sampleItem));

        CartResponse response = cartService.updateCartItemQuantity(101L, 10L, request);

        assertNotNull(response);
        assertEquals(5, sampleItem.getQuantity());
        verify(cartItemRepository, times(1)).save(sampleItem);
    }

    @Test
    void clearCart_Success() {
        when(cartRepository.findByUserId(101L)).thenReturn(Optional.of(sampleCart));

        cartService.clearCart(101L);

        assertTrue(sampleCart.getItems().isEmpty());
        verify(cartRepository, times(1)).save(sampleCart);
    }
}
