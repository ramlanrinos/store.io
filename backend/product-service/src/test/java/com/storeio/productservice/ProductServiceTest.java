package com.storeio.productservice;

import com.storeio.productservice.dto.BatchProductRequest;
import com.storeio.productservice.dto.CreateProductRequest;
import com.storeio.productservice.dto.ProductResponse;
import com.storeio.productservice.entity.Category;
import com.storeio.productservice.entity.Product;
import com.storeio.productservice.repository.CategoryRepository;
import com.storeio.productservice.repository.ProductRepository;
import com.storeio.productservice.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    private Category electronics;
    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        electronics = Category.builder()
                .id(1L)
                .name("Electronics")
                .slug("electronics")
                .description("Gadgets & Devices")
                .build();

        sampleProduct = Product.builder()
                .id(101L)
                .category(electronics)
                .name("Wireless Noise-Canceling Headphones")
                .sku("AUD-HEAD-001")
                .description("Premium Bluetooth headphones")
                .price(new BigDecimal("299.99"))
                .active(true)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void getProductById_Success() {
        when(productRepository.findById(101L)).thenReturn(Optional.of(sampleProduct));

        ProductResponse response = productService.getProductById(101L);

        assertNotNull(response);
        assertEquals(101L, response.getId());
        assertEquals("Wireless Noise-Canceling Headphones", response.getName());
        assertEquals(new BigDecimal("299.99"), response.getPrice());
        assertEquals("Electronics", response.getCategoryName());
    }

    @Test
    void createProduct_Success() {
        CreateProductRequest request = CreateProductRequest.builder()
                .categoryId(1L)
                .name("Wireless Noise-Canceling Headphones")
                .sku("AUD-HEAD-001")
                .description("Premium Bluetooth headphones")
                .price(new BigDecimal("299.99"))
                .build();

        when(productRepository.existsBySku("AUD-HEAD-001")).thenReturn(false);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(electronics));
        when(productRepository.save(any(Product.class))).thenReturn(sampleProduct);

        ProductResponse response = productService.createProduct(request);

        assertNotNull(response);
        assertEquals("AUD-HEAD-001", response.getSku());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void getProductsBatch_Success() {
        BatchProductRequest request = BatchProductRequest.builder()
                .productIds(Arrays.asList(101L))
                .build();

        when(productRepository.findByIdInAndActiveTrue(Arrays.asList(101L)))
                .thenReturn(Arrays.asList(sampleProduct));

        List<ProductResponse> responses = productService.getProductsBatch(request);

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals("AUD-HEAD-001", responses.get(0).getSku());
    }
}
