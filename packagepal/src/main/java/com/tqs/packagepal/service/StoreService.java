package com.tqs.packagepal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.tqs.packagepal.model.Store;
import com.tqs.packagepal.repository.StoreRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    public Optional<Store> getStoreById(Long id) {
        return storeRepository.findById(id);
    }

    public Optional<Store> getStoreByName(String name) {
        return storeRepository.findByName(name);
    }

    public Store addStore(Store store) {
        Optional<Store> storeOptional = storeRepository.findByName(store.getName());
        if (storeOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Store already exists");
        }
        return storeRepository.save(store);
    }

    public void deleteStoreById(Long id) {
        storeRepository.deleteById(id);
    }
}