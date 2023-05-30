package com.tqs.packagepal.Repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.tqs.packagepal.model.Store;
import com.tqs.packagepal.repository.StoreRepository;

@DataJpaTest
class StoreRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private StoreRepository storeRepository;

    private ArrayList<Store> Stores;
    private Store Store1;
    private Store Store2;

    @BeforeEach
    void setUp() {
        Stores = new ArrayList<>();
        Store1 = new Store("name1", "email1@ua.pt");
        Store2 = new Store("name2", "email2@ua.pt");

        Stores.add(Store1);
        Stores.add(Store2);

        testEntityManager.persistAndFlush(Store1);
        testEntityManager.persistAndFlush(Store2);
    }

    @Test
    void whenGetStores_thenReturnAllStores() {
        List<Store> returnedStores = storeRepository.findAll();

        assertThat(returnedStores).containsExactlyInAnyOrderElementsOf(Stores);
    }

    @Test
    void whenGetStoreById_thenReturnStore() {
        Optional<Store> returnedStoreOptional = storeRepository.findById(Store1.getId());
        Store returnedStore = returnedStoreOptional.orElse(null);

        assertEquals(Store1, returnedStore);
    }

    @Test
    void whenGetStoreByInvalidId_thenReturnNull() {
        Long invalidId = 200L;
        Optional<Store> returnedStoreOptional = storeRepository.findById(invalidId);
        Store returnedStore = returnedStoreOptional.orElse(null);

        assertNull(returnedStore);
    }

    @Test
    void whenGetStoreByName_thenReturnStore() {
        Optional<Store> returnedStoreOptional = storeRepository.findByName(Store1.getName());
        Store returnedStore = returnedStoreOptional.orElse(null);

        assertEquals(Store1, returnedStore);
    }

    @Test
    void whenGetStoreByInvalidName_thenReturnNull() {
        String invalidName = "some name";
        Optional<Store> returnedStoreOptional = storeRepository.findByName(invalidName);
        Store returnedStore = returnedStoreOptional.orElse(null);

        assertNull(returnedStore);
    }

    @Test
    void whenGetStoreByEmail_thenReturnStore() {
        Optional<Store> returnedStoreOptional = storeRepository.findByEmail(Store1.getEmail());
        Store returnedStore = returnedStoreOptional.orElse(null);

        assertEquals(Store1, returnedStore);
    }

    @Test
    void whenGetStoreByInvalidEmail_thenReturnNull() {
        String invalidEmail = "some email";
        Optional<Store> returnedStoreOptional = storeRepository.findByEmail(invalidEmail);
        Store returnedStore = returnedStoreOptional.orElse(null);

        assertNull(returnedStore);
    }

}