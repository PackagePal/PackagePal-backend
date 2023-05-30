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
import com.tqs.packagepal.service.PickupPointService;
import com.tqs.packagepal.model.Store;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class PackageControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PackageService packageService;

    @MockBean
    private PickupPointService pickupPointService;

    private List<Package> Packages;
    private Package Package1;
    private Package Package2;
    private Package Package3;
    private PickupPoint PickupPoint1;
    private PickupPoint PickupPoint2;
    private PickupPoint PickupPoint3;

    @BeforeEach
    void setUp() {
        Packages = new ArrayList<>();

        PickupPoint1 = new PickupPoint("name1", "address1", "city1", "postalcode1", 1.0, 2.0);
        PickupPoint2 = new PickupPoint("name2", "address2", "city2", "postalcode2", 3.0, 4.0);
        PickupPoint3 = new PickupPoint("name3", "address3", "city3", "postalcode3", 5.0, 6.0);

        Store Store1 = new Store("name1", "email1");
        Store Store2 = new Store("name2", "email2");
        Store Store3 = new Store("name3", "email3");

        Package1 = new Package("packageId1", "userName1", "userEmail1", DeliveryStatus.PENDING, PickupPoint1, Store1);
        Package2 = new Package("packageId2", "userName2", "userEmail2", DeliveryStatus.DELIVERED, PickupPoint2, Store2);
        Package3 = new Package("packageId3", "userName3", "userEmail3", DeliveryStatus.IN_TRANSIT, PickupPoint3, Store3);

        Packages.add(Package1);
        Packages.add(Package2);
        Packages.add(Package3);
    }

    @Test
    void whenGetAllPackages_thenReturnPackagesList() throws Exception {
        when(packageService.getAllPackages()).thenReturn(Packages);

        mvc.perform(get("/api/packages").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].packageId", is(Package1.getPackageId())))
                .andExpect(jsonPath("$[0].userName", is(Package1.getUserName())))
                .andExpect(jsonPath("$[0].userEmail", is(Package1.getUserEmail())))
                .andExpect(jsonPath("$[0].status", is(Package1.getStatus().toString())))
                .andExpect(jsonPath("$[0].pickupPoint.name", is(Package1.getPickupPoint().getName())))
                .andExpect(jsonPath("$[0].pickupPoint.address", is(Package1.getPickupPoint().getAddress())))
                .andExpect(jsonPath("$[0].pickupPoint.city", is(Package1.getPickupPoint().getCity())))
                .andExpect(jsonPath("$[0].pickupPoint.postalCode", is(Package1.getPickupPoint().getPostalCode())))
                .andExpect(jsonPath("$[0].pickupPoint.latitude", is(Package1.getPickupPoint().getLat())))
                .andExpect(jsonPath("$[0].pickupPoint.longitude", is(Package1.getPickupPoint().getLng())))
                .andExpect(jsonPath("$[0].store.name", is(Package1.getStore().getName())))
                .andExpect(jsonPath("$[0].store.email", is(Package1.getStore().getEmail())))
                .andExpect(jsonPath("$[1].packageId", is(Package2.getPackageId())))
                .andExpect(jsonPath("$[1].userName", is(Package2.getUserName())))
                .andExpect(jsonPath("$[1].userEmail", is(Package2.getUserEmail())))
                .andExpect(jsonPath("$[1].status", is(Package2.getStatus().toString())))
                .andExpect(jsonPath("$[1].pickupPoint.name", is(Package2.getPickupPoint().getName())))
                .andExpect(jsonPath("$[1].pickupPoint.address", is(Package2.getPickupPoint().getAddress())))
                .andExpect(jsonPath("$[1].pickupPoint.city", is(Package2.getPickupPoint().getCity())))
                .andExpect(jsonPath("$[1].pickupPoint.postalCode", is(Package2.getPickupPoint().getPostalCode())))
                .andExpect(jsonPath("$[1].pickupPoint.latitude", is(Package2.getPickupPoint().getLat())))
                .andExpect(jsonPath("$[1].pickupPoint.longitude", is(Package2.getPickupPoint().getLng())))
                .andExpect(jsonPath("$[1].store.name", is(Package2.getStore().getName())))
                .andExpect(jsonPath("$[1].store.email", is(Package2.getStore().getEmail())))
                .andExpect(jsonPath("$[2].packageId", is(Package3.getPackageId())))
                .andExpect(jsonPath("$[2].userName", is(Package3.getUserName())))
                .andExpect(jsonPath("$[2].userEmail", is(Package3.getUserEmail())))
                .andExpect(jsonPath("$[2].status", is(Package3.getStatus().toString())))
                .andExpect(jsonPath("$[2].pickupPoint.name", is(Package3.getPickupPoint().getName())))
                .andExpect(jsonPath("$[2].pickupPoint.address", is(Package3.getPickupPoint().getAddress())))
                .andExpect(jsonPath("$[2].pickupPoint.city", is(Package3.getPickupPoint().getCity())))
                .andExpect(jsonPath("$[2].pickupPoint.postalCode", is(Package3.getPickupPoint().getPostalCode())))
                .andExpect(jsonPath("$[2].pickupPoint.latitude", is(Package3.getPickupPoint().getLat())))
                .andExpect(jsonPath("$[2].pickupPoint.longitude", is(Package3.getPickupPoint().getLng())))
                .andExpect(jsonPath("$[2].store.name", is(Package3.getStore().getName())))
                .andExpect(jsonPath("$[2].store.email", is(Package3.getStore().getEmail())));
    }

    @Test
    void whenGetPackageById_thenReturnPackage() throws Exception {
        when(packageService.findPackageById(Package1.getPackageId())).thenReturn(Optional.of(Package1));

        mvc.perform(get("/api/packages/" + Package1.getPackageId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.packageId", is(Package1.getPackageId())))
                .andExpect(jsonPath("$.userName", is(Package1.getUserName())))
                .andExpect(jsonPath("$.userEmail", is(Package1.getUserEmail())))
                .andExpect(jsonPath("$.status", is(Package1.getStatus().toString())))
                .andExpect(jsonPath("$.pickupPoint.name", is(Package1.getPickupPoint().getName())))
                .andExpect(jsonPath("$.pickupPoint.address", is(Package1.getPickupPoint().getAddress())))
                .andExpect(jsonPath("$.pickupPoint.city", is(Package1.getPickupPoint().getCity())))
                .andExpect(jsonPath("$.pickupPoint.postalCode", is(Package1.getPickupPoint().getPostalCode())))
                .andExpect(jsonPath("$.pickupPoint.latitude", is(Package1.getPickupPoint().getLat())))
                .andExpect(jsonPath("$.pickupPoint.longitude", is(Package1.getPickupPoint().getLng())))
                .andExpect(jsonPath("$.store.name", is(Package1.getStore().getName())))
                .andExpect(jsonPath("$.store.email", is(Package1.getStore().getEmail())));
    }

    @Test
    void whenGetPackageById_thenReturnNotFound() throws Exception {
        when(packageService.findPackageById(Package1.getPackageId())).thenReturn(Optional.empty());

        mvc.perform(get("/api/packages/" + Package1.getPackageId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenCreatePackage_thenReturnPackage() throws Exception {
        when(packageService.addPackage(Package1)).thenReturn(Package1);
        mvc.perform(post("/api/packages").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(Package1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.packageId", is(Package1.getPackageId())))
                .andExpect(jsonPath("$.userName", is(Package1.getUserName())))
                .andExpect(jsonPath("$.userEmail", is(Package1.getUserEmail())))
                .andExpect(jsonPath("$.status", is(Package1.getStatus().toString())))
                .andExpect(jsonPath("$.pickupPoint.name", is(Package1.getPickupPoint().getName())))
                .andExpect(jsonPath("$.pickupPoint.address", is(Package1.getPickupPoint().getAddress())))
                .andExpect(jsonPath("$.pickupPoint.city", is(Package1.getPickupPoint().getCity())))
                .andExpect(jsonPath("$.pickupPoint.postalCode", is(Package1.getPickupPoint().getPostalCode())))
                .andExpect(jsonPath("$.pickupPoint.latitude", is(Package1.getPickupPoint().getLat())))
                .andExpect(jsonPath("$.pickupPoint.longitude", is(Package1.getPickupPoint().getLng())))
                .andExpect(jsonPath("$.store.name", is(Package1.getStore().getName())))
                .andExpect(jsonPath("$.store.email", is(Package1.getStore().getEmail())));
    }

    @Test
    void whenCreatePackage_thenReturnBadRequest() throws Exception {
        when(packageService.addPackage(Package1)).thenReturn(Package1);

        mvc.perform(post("/api/packages").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(Package1)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenDeletePackage_thenReturnOk() throws Exception {
        when(packageService.deletePackage(Package1.getPackageId())).thenReturn(true);

        mvc.perform(delete("/api/packages/" + Package1.getPackageId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void whenDeletePackage_thenReturnNotFound() throws Exception {
        when(packageService.deletePackage(Package1.getPackageId())).thenReturn(false);

        mvc.perform(delete("/api/packages/" + Package1.getPackageId()).contentType(MediaType.APPLICATION_JSON))
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
