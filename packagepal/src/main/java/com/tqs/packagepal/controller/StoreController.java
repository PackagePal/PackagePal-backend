package com.tqs.packagepal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tqs.packagepal.model.Store;
import com.tqs.packagepal.service.StoreService;

@RestController
@CrossOrigin(origins={"http://192.168.160.234:8080", "http://192.168.160.234:3000/",
                        "http://192.168.160.234:8080", "http://192.168.160.234:3001/",
                        "http://localhost:8080", "http://localhost:3000/",
                        "http://localhost:8080", "http://localhost:3001/"})
@RequestMapping("/api/v1/stores/")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @GetMapping
    public ResponseEntity<List<Store>> getAllStores() {
        List<Store> stores = storeService.getAllStores();
        return ResponseEntity.ok(stores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Store> getStoreById(@PathVariable Long id) {
        return storeService.getStoreById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Store> addStore(@RequestBody Store store) {
        Store addedStore = storeService.addStore(store);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedStore);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStoreById(@PathVariable Long id) {
        storeService.deleteStoreById(id);
        return ResponseEntity.noContent().build();
    }
}
