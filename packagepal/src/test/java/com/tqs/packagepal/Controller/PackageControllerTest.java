package com.tqs.packagepal.Controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tqs.packagepal.model.DeliveryStatus;
import com.tqs.packagepal.model.Package;
import com.tqs.packagepal.model.PickupPoint;
import com.tqs.packagepal.service.PackageService;
import com.tqs.packagepal.model.Store;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class PackageControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PackageService packageService;

    private List<Package> packages;
    private Package package1;
    private Package package2;
    private Package package3;
    private PickupPoint pickupPoint1;
    private PickupPoint pickupPoint2;
    private Store store1;
    private Store store2;
    private Store store3;

    @BeforeEach
    void setUp() {
        packages = new ArrayList<>();

        pickupPoint1 = new PickupPoint("name1", "address1", "city1", "postalcode1", 1.0, 2.0);
        pickupPoint2 = new PickupPoint("name2", "address2", "city2", "postalcode2", 3.0, 4.0);

        store1 = new Store("name1", "email1");
        store2 = new Store("name2", "email2");
        store3 = new Store("name3", "email3");

        package1 = new Package("packageId1", "userName1", "userEmail1", DeliveryStatus.PENDING, pickupPoint1, store1);
        package2 = new Package("packageId2", "userName2", "userEmail2", DeliveryStatus.DELIVERED, pickupPoint2, store2);
        package3 = new Package("packageId3", "userName3", "userEmail3", DeliveryStatus.IN_TRANSIT, pickupPoint1, store3);

        packages.add(package1);
        packages.add(package2);
        packages.add(package3);
    }

    @Test
    void whenGetAllPackages_thenReturnPackagesList() throws Exception {
        when(packageService.getAllPackages()).thenReturn(packages);

        mvc.perform(get("/api/v1/packages/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].packageId", is(package1.getPackageId())))
                .andExpect(jsonPath("$[0].userName", is(package1.getUserName())))
                .andExpect(jsonPath("$[0].userEmail", is(package1.getUserEmail())))
                .andExpect(jsonPath("$[0].status", is(package1.getStatus().toString())))
                .andExpect(jsonPath("$[0].pickupPoint.name", is(package1.getPickupPoint().getName())))
                .andExpect(jsonPath("$[0].pickupPoint.address", is(package1.getPickupPoint().getAddress())))
                .andExpect(jsonPath("$[0].pickupPoint.city", is(package1.getPickupPoint().getCity())))
                .andExpect(jsonPath("$[0].pickupPoint.postalCode", is(package1.getPickupPoint().getPostalCode())))
                .andExpect(jsonPath("$[0].pickupPoint.lat", is(package1.getPickupPoint().getLat())))
                .andExpect(jsonPath("$[0].pickupPoint.lng", is(package1.getPickupPoint().getLng())))
                .andExpect(jsonPath("$[0].store.name", is(package1.getStore().getName())))
                .andExpect(jsonPath("$[0].store.email", is(package1.getStore().getEmail())))
                .andExpect(jsonPath("$[1].packageId", is(package2.getPackageId())))
                .andExpect(jsonPath("$[1].userName", is(package2.getUserName())))
                .andExpect(jsonPath("$[1].userEmail", is(package2.getUserEmail())))
                .andExpect(jsonPath("$[1].status", is(package2.getStatus().toString())))
                .andExpect(jsonPath("$[1].pickupPoint.name", is(package2.getPickupPoint().getName())))
                .andExpect(jsonPath("$[1].pickupPoint.address", is(package2.getPickupPoint().getAddress())))
                .andExpect(jsonPath("$[1].pickupPoint.city", is(package2.getPickupPoint().getCity())))
                .andExpect(jsonPath("$[1].pickupPoint.postalCode", is(package2.getPickupPoint().getPostalCode())))
                .andExpect(jsonPath("$[1].pickupPoint.lat", is(package2.getPickupPoint().getLat())))
                .andExpect(jsonPath("$[1].pickupPoint.lng", is(package2.getPickupPoint().getLng())))
                .andExpect(jsonPath("$[1].store.name", is(package2.getStore().getName())))
                .andExpect(jsonPath("$[1].store.email", is(package2.getStore().getEmail())))
                .andExpect(jsonPath("$[2].packageId", is(package3.getPackageId())))
                .andExpect(jsonPath("$[2].userName", is(package3.getUserName())))
                .andExpect(jsonPath("$[2].userEmail", is(package3.getUserEmail())))
                .andExpect(jsonPath("$[2].status", is(package3.getStatus().toString())))
                .andExpect(jsonPath("$[2].pickupPoint.name", is(package3.getPickupPoint().getName())))
                .andExpect(jsonPath("$[2].pickupPoint.address", is(package3.getPickupPoint().getAddress())))
                .andExpect(jsonPath("$[2].pickupPoint.city", is(package3.getPickupPoint().getCity())))
                .andExpect(jsonPath("$[2].pickupPoint.postalCode", is(package3.getPickupPoint().getPostalCode())))
                .andExpect(jsonPath("$[2].pickupPoint.lat", is(package3.getPickupPoint().getLat())))
                .andExpect(jsonPath("$[2].pickupPoint.lng", is(package3.getPickupPoint().getLng())))
                .andExpect(jsonPath("$[2].store.name", is(package3.getStore().getName())))
                .andExpect(jsonPath("$[2].store.email", is(package3.getStore().getEmail())));
    }

    @Test
    void whenGetPackageById_thenReturnPackage() throws Exception {
        when(packageService.findPackageById(package1.getPackageId())).thenReturn(Optional.of(package1));

        mvc.perform(get("/api/v1/packages/" + package1.getPackageId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.packageId", is(package1.getPackageId())))
                .andExpect(jsonPath("$.userName", is(package1.getUserName())))
                .andExpect(jsonPath("$.userEmail", is(package1.getUserEmail())))
                .andExpect(jsonPath("$.status", is(package1.getStatus().toString())))
                .andExpect(jsonPath("$.pickupPoint.name", is(package1.getPickupPoint().getName())))
                .andExpect(jsonPath("$.pickupPoint.address", is(package1.getPickupPoint().getAddress())))
                .andExpect(jsonPath("$.pickupPoint.city", is(package1.getPickupPoint().getCity())))
                .andExpect(jsonPath("$.pickupPoint.postalCode", is(package1.getPickupPoint().getPostalCode())))
                .andExpect(jsonPath("$.pickupPoint.lat", is(package1.getPickupPoint().getLat())))
                .andExpect(jsonPath("$.pickupPoint.lng", is(package1.getPickupPoint().getLng())))
                .andExpect(jsonPath("$.store.name", is(package1.getStore().getName())))
                .andExpect(jsonPath("$.store.email", is(package1.getStore().getEmail())));
    }

    @Test
    void whenCreatePackage_thenReturnPackage() throws Exception {
        when(packageService.addPackage(package1)).thenReturn(package1);
        mvc.perform(post("/api/v1/packages/").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(package1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.packageId", is(package1.getPackageId())))
                .andExpect(jsonPath("$.userName", is(package1.getUserName())))
                .andExpect(jsonPath("$.userEmail", is(package1.getUserEmail())))
                .andExpect(jsonPath("$.status", is(package1.getStatus().toString())))
                .andExpect(jsonPath("$.pickupPoint.name", is(package1.getPickupPoint().getName())))
                .andExpect(jsonPath("$.pickupPoint.address", is(package1.getPickupPoint().getAddress())))
                .andExpect(jsonPath("$.pickupPoint.city", is(package1.getPickupPoint().getCity())))
                .andExpect(jsonPath("$.pickupPoint.postalCode", is(package1.getPickupPoint().getPostalCode())))
                .andExpect(jsonPath("$.pickupPoint.lat", is(package1.getPickupPoint().getLat())))
                .andExpect(jsonPath("$.pickupPoint.lng", is(package1.getPickupPoint().getLng())))
                .andExpect(jsonPath("$.store.name", is(package1.getStore().getName())))
                .andExpect(jsonPath("$.store.email", is(package1.getStore().getEmail())));
    }

    @Test
    void whenDeletePackage_thenReturnNotFound() throws Exception {
        when(packageService.deletePackage(package1.getPackageId())).thenReturn(false);

        mvc.perform(delete("/api/v1/packages/" + package1.getPackageId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
