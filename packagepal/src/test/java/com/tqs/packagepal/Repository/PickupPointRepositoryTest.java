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

import com.tqs.packagepal.model.PickupPoint;
import com.tqs.packagepal.repository.PickupPointRepository;

@DataJpaTest
class PickupPointRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private PickupPointRepository pickupPointRepository;

    private ArrayList<PickupPoint> PickupPoints;
    private PickupPoint PickupPoint1;
    private PickupPoint PickupPoint2;

    @BeforeEach
    void setUp() {
        PickupPoints = new ArrayList<>();

        PickupPoint1 = new PickupPoint("name1", "address1", "city1", "3810-018", 43.5677, 12.5465);
        PickupPoint2 = new PickupPoint("name2", "address2", "city2", "3445-858", 53.1234, -12.4352);

        PickupPoints.add(PickupPoint1);
        PickupPoints.add(PickupPoint2);

        testEntityManager.persistAndFlush(PickupPoint1);
        testEntityManager.persistAndFlush(PickupPoint2);
    }

    @Test
    void whenGetPickupPoints_thenReturnAllPickupPoints() {
        List<PickupPoint> returnedPickupPoints = pickupPointRepository.findAll();

        assertThat(returnedPickupPoints).containsExactlyInAnyOrderElementsOf(PickupPoints);
    }

    @Test
    void whenGetPickupPointById_thenReturnPickupPoint() {
        Optional<PickupPoint> returnedPickupPointOptional = pickupPointRepository.findById(PickupPoint1.getId());
        PickupPoint returnedPickupPoint = returnedPickupPointOptional.orElse(null);

        assertEquals(PickupPoint1, returnedPickupPoint);
    }

    @Test
    void whenGetPickupPointByInvalidId_thenReturnNull() {
        Long invalidId = 200L;
        Optional<PickupPoint> returnedPickupPointOptional = pickupPointRepository.findById(invalidId);
        PickupPoint returnedPickupPoint = returnedPickupPointOptional.orElse(null);

        assertNull(returnedPickupPoint);
    }

    @Test
    void whenGetPickupPointByName_thenReturnPickupPoint() {
        Optional<PickupPoint> returnedPickupPointOptional = pickupPointRepository.findByName(PickupPoint1.getName());
        PickupPoint returnedPickupPoint = returnedPickupPointOptional.orElse(null);

        assertEquals(PickupPoint1, returnedPickupPoint);
    }

    @Test
    void whenGetPickupPointByInvalidName_thenReturnNull() {
        String invalidName = "some name";
        Optional<PickupPoint> returnedPickupPointOptional = pickupPointRepository.findByName(invalidName);
        PickupPoint returnedPickupPoint = returnedPickupPointOptional.orElse(null);

        assertNull(returnedPickupPoint);
    }

    @Test
    void whenGetPickupPointByAddress_thenReturnPickupPoint() {
        Optional<PickupPoint> returnedPickupPointOptional = pickupPointRepository.findByAddress(PickupPoint1.getAddress());
        PickupPoint returnedPickupPoint = returnedPickupPointOptional.orElse(null);

        assertEquals(PickupPoint1, returnedPickupPoint);
    }

    @Test
    void whenGetPickupPointByInvalidAddress_thenReturnNull() {
        String invalidAddress = "some address";
        Optional<PickupPoint> returnedPickupPointOptional = pickupPointRepository.findByAddress(invalidAddress);
        PickupPoint returnedPickupPoint = returnedPickupPointOptional.orElse(null);

        assertNull(returnedPickupPoint);
    }

    @Test
    void whenGetPickupPointByCity_thenReturnPickupPoint() {
        Optional<PickupPoint> returnedPickupPointOptional = pickupPointRepository.findByCity(PickupPoint1.getCity());
        PickupPoint returnedPickupPoint = returnedPickupPointOptional.orElse(null);

        assertEquals(PickupPoint1, returnedPickupPoint);
    }

    @Test
    void whenGetPickupPointByInvalidCity_thenReturnNull() {
        String invalidCity = "some city";
        Optional<PickupPoint> returnedPickupPointOptional = pickupPointRepository.findByCity(invalidCity);
        PickupPoint returnedPickupPoint = returnedPickupPointOptional.orElse(null);

        assertNull(returnedPickupPoint);
    }

    @Test
    void whenGetPickupPointByPostalCode_thenReturnPickupPoint() {
        Optional<PickupPoint> returnedPickupPointOptional = pickupPointRepository.findByPostalCode(PickupPoint1.getPostalCode());
        PickupPoint returnedPickupPoint = returnedPickupPointOptional.orElse(null);

        assertEquals(PickupPoint1, returnedPickupPoint);
    }
}
