package com.tqs.packagepal.Controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

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
import com.tqs.packagepal.model.PickupPoint;
import com.tqs.packagepal.service.PickupPointService;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class PickupPointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PickupPointService pickupPointService;

    private List<PickupPoint> PickupPoints;
    private PickupPoint PickupPoint1;
    private PickupPoint PickupPoint2;
    private PickupPoint PickupPoint3;

    @BeforeEach
    void setUp() {
        PickupPoints = new ArrayList<>();

        PickupPoint1 = new PickupPoint("name1", "address1", "city1", "postalcode1", 1.0, 2.0);
        PickupPoint2 = new PickupPoint("name2", "address2", "city2", "postalcode2", 3.0, 4.0);
        PickupPoint3 = new PickupPoint("name3", "address3", "city3", "postalcode3", 5.0, 6.0);

        PickupPoints.add(PickupPoint1);
        PickupPoints.add(PickupPoint2);
        PickupPoints.add(PickupPoint3);
    }

    @Test
    void whenGetAllPickupPoints_thenReturnPickupPointsList() throws Exception {
        when(pickupPointService.getAllPickupPoints()).thenReturn(PickupPoints);

        mockMvc.perform(get("/api/v1/pickuppoints/")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(equalTo(3))))
            .andExpect(jsonPath("$[0].name", is(PickupPoint1.getName())))
            .andExpect(jsonPath("$[1].name", is(PickupPoint2.getName())))
            .andExpect(jsonPath("$[2].name", is(PickupPoint3.getName())));

        verify(pickupPointService, times(1)).getAllPickupPoints();
    }

    @Test
    void whenGetPickupPointById_thenReturnPickupPoint() throws Exception {
        Long id = 1L;
        PickupPoint1.setId(id);

        when(pickupPointService.getPickupPointById(PickupPoint1.getId())).thenReturn(Optional.of(PickupPoint1));

        mockMvc.perform(get("/api/v1/pickuppoints/" + Long.toString(id))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", is(PickupPoint1.getId().intValue())))
            .andExpect(jsonPath("$.name", is(PickupPoint1.getName())))
            .andExpect(jsonPath("$.address", is(PickupPoint1.getAddress())))
            .andExpect(jsonPath("$.city", is(PickupPoint1.getCity())))
            .andExpect(jsonPath("$.postalCode", is(PickupPoint1.getPostalCode())))
            .andExpect(jsonPath("$.lat", is(PickupPoint1.getLat())))
            .andExpect(jsonPath("$.lng", is(PickupPoint1.getLng())));

        verify(pickupPointService, times(1)).getPickupPointById(id);
    }

    @Test
    void whenGetPickupPointsByCity_thenReturnPickupPoints() throws Exception {
        String city = "city1";

        when(pickupPointService.getPickupPointsByCity(city)).thenReturn(Optional.of(PickupPoint1));

        mockMvc.perform(get("/api/v1/pickuppoints/city/" + city)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name", is(PickupPoint1.getName())))
            .andExpect(jsonPath("$.address", is(PickupPoint1.getAddress())))
            .andExpect(jsonPath("$.city", is(PickupPoint1.getCity())))
            .andExpect(jsonPath("$.postalCode", is(PickupPoint1.getPostalCode())))
            .andExpect(jsonPath("$.lat", is(PickupPoint1.getLat())))
            .andExpect(jsonPath("$.lng", is(PickupPoint1.getLng())));

        verify(pickupPointService, times(1)).getPickupPointsByCity(city);
    }

    @Test
    void whenGetPickupPointsByPostalCode_thenReturnPickupPoints() throws Exception {
        String postalCode = "postalcode1";

        when(pickupPointService.getPickupPointsByPostalCode(postalCode)).thenReturn(Optional.of(PickupPoint1));

        mockMvc.perform(get("/api/v1/pickuppoints/postalcode/" + postalCode)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name", is(PickupPoint1.getName())))
            .andExpect(jsonPath("$.address", is(PickupPoint1.getAddress())))
            .andExpect(jsonPath("$.city", is(PickupPoint1.getCity())))
            .andExpect(jsonPath("$.postalCode", is(PickupPoint1.getPostalCode())))
            .andExpect(jsonPath("$.lat", is(PickupPoint1.getLat())))
            .andExpect(jsonPath("$.lng", is(PickupPoint1.getLng())));

        verify(pickupPointService, times(1)).getPickupPointsByPostalCode(postalCode);
    }

    @Test
    void whenAddPickupPoint_thenReturnPickupPoint() throws Exception {
        when(pickupPointService.addPickupPoint(PickupPoint1)).thenReturn(PickupPoint1);

        mockMvc.perform(post("/api/v1/pickuppoints/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(PickupPoint1)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name", is(PickupPoint1.getName())))
            .andExpect(jsonPath("$.address", is(PickupPoint1.getAddress())))
            .andExpect(jsonPath("$.city", is(PickupPoint1.getCity())))
            .andExpect(jsonPath("$.postalCode", is(PickupPoint1.getPostalCode())))
            .andExpect(jsonPath("$.lat", is(PickupPoint1.getLat())))
            .andExpect(jsonPath("$.lng", is(PickupPoint1.getLng())));

        verify(pickupPointService, times(1)).addPickupPoint(PickupPoint1);
    }    

    @Test
    void whenDeletePickupPointById_thenStatusNoContent() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/v1/pickuppoints/" + id)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        verify(pickupPointService, times(1)).deletePickupPointById(id);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
