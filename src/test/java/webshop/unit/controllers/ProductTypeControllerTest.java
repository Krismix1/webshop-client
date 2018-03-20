package webshop.unit.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import webshop.controllers.ProductTypeController;
import webshop.controllers.http_exceptions.Http404Exception;
import webshop.models.domain.ProductTypeVO;
import webshop.models.entities.ProductType;
import webshop.services.ProductTypeService;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
// https://dzone.com/articles/spring-boot-unit-testing-and-mocking-with-mockito
public class ProductTypeControllerTest {

    @Mock
    ProductTypeService productTypeService;

    @InjectMocks
    ProductTypeController productTypeController;

    @Before
    public void loadData() {
        when(productTypeService.fetchAll())
                .thenReturn(Collections.emptyList());

        ProductType productType = new ProductType();

        when(productTypeService.findByName("prod_1"))
                .thenReturn(Optional.of(productType));
    }

    @Test
    public void fetchAll() throws Exception {
        final Collection<ProductTypeVO> productTypeVOs = productTypeController.fetchAll();
        assertEquals(0, productTypeVOs.size());
    }

    @Test
    public void fetchOne() {
        final ProductTypeVO productTypeVO = productTypeController.fetchOne("prod_1");
        assertNotNull(productTypeVO);
    }

    @Test
    public void fetchOne_NotFound() {
        try {
            final ProductTypeVO productTypeVO = productTypeController.fetchOne("prod_2");
            assertTrue(false);
        } catch (Http404Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void postProductType() {

    }
}