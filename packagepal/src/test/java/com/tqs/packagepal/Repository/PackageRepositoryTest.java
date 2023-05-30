package com.tqs.packagepal.Repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.tqs.packagepal.model.Package;
import com.tqs.packagepal.model.PickupPoint;
import com.tqs.packagepal.model.Store;
import com.tqs.packagepal.repository.PackageRepository;
import com.tqs.packagepal.model.DeliveryStatus;

@DataJpaTest
public class PackageRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private PackageRepository packageRepository;

    private ArrayList<Package> Packages;
    private Package Package1;
    private Package Package2;
    private PickupPoint PickupPoint;
    private Store Store;

    @BeforeEach
    void setUp() {
        Packages = new ArrayList<>();
        PickupPoint = new PickupPoint("name1", "address1", "city1", "3810-018", 43.5677, 12.5465);
        Store = new Store("name1", "email1@ua.pt");
        Package1 = new Package("1234", "userName1", "userEmail1@ua.pt", DeliveryStatus.IN_TRANSIT, PickupPoint, Store);
        Package2 = new Package("4321", "userName2", "userEmail2@ua.pt", DeliveryStatus.DELIVERED, PickupPoint, Store);

        Packages.add(Package1);
        Packages.add(Package2);

        testEntityManager.persistAndFlush(PickupPoint);
        testEntityManager.persistAndFlush(Store);
    }

    @Test
    void whenGetPackages_thenReturnAllPackages() {
        testEntityManager.persistAndFlush(Package1);
        testEntityManager.persistAndFlush(Package2);

        List<Package> returnedPackages = packageRepository.findAll();

        assertEquals(Packages, returnedPackages);
    }

    @Test
    void whenGetPackageById_thenReturnPackage() {
        testEntityManager.persistAndFlush(Package1);

        Optional<Package> returnedPackageOptional = packageRepository.findById(Package1.getId());
        Package returnedPackage = returnedPackageOptional.orElse(null);

        assertEquals(Package1, returnedPackage);
    }

    @Test
    void whenGetPackageByInvalidId_thenReturnNull() {
        Long invalidId = 200L;
        Optional<Package> returnedPackageOptional = packageRepository.findById(invalidId);
        Package returnedPackage = returnedPackageOptional.orElse(null);

        assertNull(returnedPackage);
    }

    @Test
    void whenGetPackageByPackageId_thenReturnPackage() {
        testEntityManager.persistAndFlush(Package1);

        Optional<Package> returnedPackageOptional = packageRepository.findByPackageId(Package1.getPackageId());
        Package returnedPackage = returnedPackageOptional.orElse(null);

        assertEquals(Package1, returnedPackage);
    }

    @Test
    void whenGetPackageByInvalidPackageId_thenReturnNull() {
        String invalidPackageId = "invalidId";
        Optional<Package> returnedPackageOptional = packageRepository.findByPackageId(invalidPackageId);
        Package returnedPackage = returnedPackageOptional.orElse(null);

        assertNull(returnedPackage);
    }

    @Test
    void whenGetPackageByPickupPoint_thenReturnPackage() {
        testEntityManager.persistAndFlush(Package1);
        List<Package> Package1List = new ArrayList<>(Arrays.asList(Package1));
        List<Package> returnedPackages = packageRepository.findByPickupPoint(Package1.getPickupPoint());

        assertEquals(Package1List, returnedPackages);
    }

    @Test
    void whenGetPackageByInvalidPickupPoint_thenReturnEmptyList() {
        PickupPoint pickupPoint = new PickupPoint();
        testEntityManager.persistAndFlush(pickupPoint);
        List<Package> returnedPackages = packageRepository.findByPickupPoint(pickupPoint);

        assertThat(returnedPackages.isEmpty());
    }

    @Test
    void whenGetPackageByStore_thenReturnPackage() {
        testEntityManager.persistAndFlush(Package1);
        List<Package> Package1List = new ArrayList<>(Arrays.asList(Package1));
        List<Package> returnedPackages = packageRepository.findByStore(Package1.getStore());

        assertEquals(Package1List, returnedPackages);
    }

    @Test
    void whenGetPackageByInvalidStore_thenReturnEmptyList() {
        Store store = new Store();
        testEntityManager.persistAndFlush(store);
        List<Package> returnedPackages = packageRepository.findByStore(store);

        assertThat(returnedPackages.isEmpty());
    }

    @Test
    void whenGetPackageByUserEmail_thenReturnPackages() {
        testEntityManager.persistAndFlush(Package1);
        testEntityManager.persistAndFlush(Package2);
        List<Package> Package1List = new ArrayList<>(Arrays.asList(Package1));
        List<Package> returnedPackages = packageRepository.findByUserEmail(Package1.getUserEmail());

        assertEquals(Package1List, returnedPackages);
    }

    @Test
    void whenGetPackageByInvalidUserEmail_thenReturnEmptyList() {
        String invalidUserEmail = "invalidemail@example.com";
        List<Package> returnedPackages = packageRepository.findByUserEmail(invalidUserEmail);

        assertThat(returnedPackages.isEmpty());
    }

}