package webshop.unit_context.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import webshop.models.entities.ProductType;
import webshop.services.ProductTypeService;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductTypeControllerContextTest {

    @MockBean
    ProductTypeService productTypeService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void fetchAll() throws Exception {
        when(productTypeService.fetchAll())
                .thenReturn(Collections.singletonList(new ProductType()));

        mockMvc.perform(get("/api/products/types"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)));

        verify(productTypeService, atLeastOnce())
                .fetchAll();

    }

    @Test
    public void fetchOne() throws Exception {
        when(productTypeService.findByName("prod_1"))
                .thenReturn(Optional.of(new ProductType()));

        mockMvc.perform(get("/api/products/types/prod_1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void postProductType() {
    }
}