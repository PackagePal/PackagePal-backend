package com.tqs.packagepal.Controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.tqs.packagepal.model.Store;
import com.tqs.packagepal.service.StoreService;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class StoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StoreService storeService;

    private List<Store> Stores;
    private Store Store1;
    private Store Store2;
    private Store Store3;

    @BeforeEach
    void setUp() {
        Stores = new ArrayList<>();
        
        Store1 = new Store("name1", "email1"); 
        Store2 = new Store("name2", "email2");
        Store3 = new Store("name3", "email3");

        Stores.add(Store1);
        Stores.add(Store2);
        Stores.add(Store3);
    }

    @Test
    void whenGetAllStores_thenReturnStoresList() throws Exception {
        when(storeService.getAllStores()).thenReturn(Stores);

        mockMvc.perform(get("/api/v1/stores/")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(equalTo(3))))
            .andExpect(jsonPath("$[0].name", is(Store1.getName())))
            .andExpect(jsonPath("$[1].name", is(Store2.getName())))
            .andExpect(jsonPath("$[2].name", is(Store3.getName())));

        verify(storeService, times(1)).getAllStores();
    }

    @Test
    void whenGetStoreById_thenReturnStore() throws Exception {
        Long id = 1L;
        Store1.setId(id);
        
        when(storeService.getStoreById(Store1.getId())).thenReturn(Optional.of(Store1));

        mockMvc.perform(get("/api/v1/stores/" + Long.toString(id))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", is(Store1.getId().intValue())))
            .andExpect(jsonPath("$.name", is(Store1.getName())))
            .andExpect(jsonPath("$.email", is(Store1.getEmail())));

        verify(storeService, times(1)).getStoreById(Store1.getId());
    }

    @Test
    void whenAddStore_thenReturnStore() throws Exception {
        when(storeService.addStore(Store1)).thenReturn(Store1);

        mockMvc.perform(post("/api/v1/stores/")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{ \"name\": \"name1\", \"email\": \"email1\" }"))
            .andExpect(status().isCreated())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name", is(Store1.getName())))
            .andExpect(jsonPath("$.email", is(Store1.getEmail())));

        verify(storeService, times(1)).addStore(Store1);
    }

    @Test
    void whenDeleteStoreById_thenResponseStatusNoContent() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/v1/stores/" + Long.toString(id))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        verify(storeService, times(1)).deleteStoreById(id);
    }

}
