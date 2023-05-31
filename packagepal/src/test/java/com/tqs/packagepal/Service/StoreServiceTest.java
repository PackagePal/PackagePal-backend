package com.tqs.packagepal.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import com.tqs.packagepal.model.Store;
import com.tqs.packagepal.repository.StoreRepository;
import com.tqs.packagepal.service.StoreService;

class StoreServiceTest {

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private StoreService storeService;

    private List<Store> Stores;
    private Store Store1;
    private Store Store2;

    @BeforeEach
    void setUp() {
        Stores = new ArrayList<>();
        Store1 = new Store("name1", "email1@ua.pt");
        Store2 = new Store("name2", "email2@ua.pt");

        Stores.add(Store1);
        Stores.add(Store2);

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenGetAllStores_thenReturnAllStores() {
        when(storeRepository.findAll()).thenReturn(Stores);

        List<Store> returnedStores = storeService.getAllStores();

        assertEquals(Stores, returnedStores);
        verify(storeRepository, times(1)).findAll();
    }

    @Test
    void whenGetStoreById_thenReturnStore() {
        Long id = 1L;
        when(storeRepository.findById(id)).thenReturn(Optional.of(Store1));

        Optional<Store> returnedStore = storeService.getStoreById(id);

        assertEquals(Optional.of(Store1), returnedStore);
        verify(storeRepository, times(1)).findById(id);
    }

    @Test
    void whenGetStoreByInvalidId_thenReturnEmptyOptional() {
        Long id = 200L;

        when(storeRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Store> returnedStore = storeService.getStoreById(id);

        assertEquals(Optional.empty(), returnedStore);
        verify(storeRepository, times(1)).findById(id);
    }

    @Test
    void whenGetStoreByName_thenReturnStore() {
        String name = "name1";
        when(storeRepository.findByName(name)).thenReturn(Optional.of(Store1));

        Optional<Store> returnedStore = storeService.getStoreByName(name);

        assertEquals(Optional.of(Store1), returnedStore);
        verify(storeRepository, times(1)).findByName(name);
    }

    @Test
    void whenGetStoreByInvalidName_thenReturnEmptyOptional() {
        String invalidName = "invalidName";

        when(storeRepository.findByName(invalidName)).thenReturn(Optional.empty());

        Optional<Store> returnedStore = storeService.getStoreByName(invalidName);

        assertEquals(Optional.empty(), returnedStore);
        verify(storeRepository, times(1)).findByName(invalidName);
    }

    @Test
    void whenAddValidStore_thenReturnCreatedStore() {
        when(storeRepository.findByName(Store1.getName())).thenReturn(Optional.empty());
        when(storeRepository.save(Store1)).thenReturn(Store1);

        Store createdStore = storeService.addStore(Store1);

        assertEquals(Store1, createdStore);
        verify(storeRepository, times(1)).findByName(Store1.getName());
        verify(storeRepository, times(1)).save(Store1);
    }

    @Test
    void whenAddInvalidStoreName_thenThrowException() {
        when(storeRepository.findByName(Store1.getName())).thenReturn(Optional.of(Store1));

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            storeService.addStore(Store1);
        });

        verify(storeRepository, times(1)).findByName(Store1.getName());
        verify(storeRepository, times(0)).save(Store1);
    }

    @Test
    void whenDeleteStoreById_thenVerifyDeletion() {
        Long id = 1L;

        storeService.deleteStoreById(id);

        verify(storeRepository, times(1)).deleteById(id);
    }

}
