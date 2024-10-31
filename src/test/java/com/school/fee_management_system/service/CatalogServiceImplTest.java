package com.school.fee_management_system.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.school.fee_management_system.model.Catalog;
import com.school.fee_management_system.repository.CatalogRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CatalogServiceImpl.class})
@ExtendWith(SpringExtension.class)
class CatalogServiceImplTest {
    @MockBean
    private CatalogRepository catalogRepository;

    @Autowired
    private CatalogServiceImpl catalogServiceImpl;

    /**
     * Method under test: {@link CatalogServiceImpl#createCatalog(Catalog)}
     */
    @Test
    void testCreateCatalog() {
        // Arrange
        Catalog catalog = new Catalog();
        catalog.setFee(10.0d);
        catalog.setId("42");
        catalog.setName("Name");
        when(catalogRepository.save(Mockito.<Catalog>any())).thenReturn(catalog);

        Catalog catalog2 = new Catalog();
        catalog2.setFee(10.0d);
        catalog2.setId("42");
        catalog2.setName("Name");

        // Act
        Catalog actualCreateCatalogResult = catalogServiceImpl.createCatalog(catalog2);

        // Assert
        verify(catalogRepository).save(isA(Catalog.class));
        assertSame(catalog, actualCreateCatalogResult);
    }

    /**
     * Method under test: {@link CatalogServiceImpl#createCatalog(Catalog)}
     */
    @Test
    void testCreateCatalog2() {
        // Arrange
        when(catalogRepository.save(Mockito.<Catalog>any())).thenThrow(new RuntimeException("foo"));

        Catalog catalog = new Catalog();
        catalog.setFee(10.0d);
        catalog.setId("42");
        catalog.setName("Name");

        // Act and Assert
        assertThrows(RuntimeException.class, () -> catalogServiceImpl.createCatalog(catalog));
        verify(catalogRepository).save(isA(Catalog.class));
    }

    /**
     * Method under test: {@link CatalogServiceImpl#updateCatalog(Catalog)}
     */
    @Test
    void testUpdateCatalog() {
        // Arrange
        Catalog catalog = new Catalog();
        catalog.setFee(10.0d);
        catalog.setId("42");
        catalog.setName("Name");
        Optional<Catalog> ofResult = Optional.of(catalog);
        when(catalogRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

        Catalog catalog2 = new Catalog();
        catalog2.setFee(10.0d);
        catalog2.setId("42");
        catalog2.setName("Name");

        // Act
        String actualUpdateCatalogResult = catalogServiceImpl.updateCatalog(catalog2);

        // Assert
        verify(catalogRepository).findById(eq("42"));
        assertEquals("Catalog Details already present", actualUpdateCatalogResult);
    }

    /**
     * Method under test: {@link CatalogServiceImpl#updateCatalog(Catalog)}
     */
    @Test
    void testUpdateCatalog2() {
        // Arrange
        Catalog catalog = new Catalog();
        catalog.setFee(10.0d);
        catalog.setId("42");
        catalog.setName("Name");
        when(catalogRepository.save(Mockito.<Catalog>any())).thenReturn(catalog);
        Optional<Catalog> emptyResult = Optional.empty();
        when(catalogRepository.findById(Mockito.<String>any())).thenReturn(emptyResult);

        Catalog catalog2 = new Catalog();
        catalog2.setFee(10.0d);
        catalog2.setId("42");
        catalog2.setName("Name");

        // Act
        String actualUpdateCatalogResult = catalogServiceImpl.updateCatalog(catalog2);

        // Assert
        verify(catalogRepository).findById(eq("42"));
        verify(catalogRepository).save(isA(Catalog.class));
        assertEquals("Catalog Updated Successfully", actualUpdateCatalogResult);
    }

    /**
     * Method under test: {@link CatalogServiceImpl#updateCatalog(Catalog)}
     */
    @Test
    void testUpdateCatalog3() {
        // Arrange
        when(catalogRepository.findById(Mockito.<String>any()))
                .thenThrow(new RuntimeException("Catalog Updated Successfully"));

        Catalog catalog = new Catalog();
        catalog.setFee(10.0d);
        catalog.setId("42");
        catalog.setName("Name");

        // Act and Assert
        assertThrows(RuntimeException.class, () -> catalogServiceImpl.updateCatalog(catalog));
        verify(catalogRepository).findById(eq("42"));
    }

    /**
     * Method under test: {@link CatalogServiceImpl#getCatalogById(String)}
     */
    @Test
    void testGetCatalogById() {
        // Arrange
        Catalog catalog = new Catalog();
        catalog.setFee(10.0d);
        catalog.setId("42");
        catalog.setName("Name");
        Optional<Catalog> ofResult = Optional.of(catalog);
        when(catalogRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

        // Act
        Catalog actualCatalogById = catalogServiceImpl.getCatalogById("catalog1");

        // Assert
        verify(catalogRepository).findById(eq("catalog1"));
        assertSame(catalog, actualCatalogById);
    }

    /**
     * Method under test: {@link CatalogServiceImpl#getCatalogById(String)}
     */
    @Test
    void testGetCatalogById2() {
        // Arrange
        Optional<Catalog> emptyResult = Optional.empty();
        when(catalogRepository.findById(Mockito.<String>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> catalogServiceImpl.getCatalogById("catalog2"));
        verify(catalogRepository).findById(eq("catalog2"));
    }

    /**
     * Method under test: {@link CatalogServiceImpl#getCatalogById(String)}
     */
    @Test
    void testGetCatalogById3() {
        // Arrange
        when(catalogRepository.findById(Mockito.<String>any())).thenThrow(new RuntimeException("Catalog not found"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> catalogServiceImpl.getCatalogById("catalog3"));
        verify(catalogRepository).findById(eq("catalog3"));
    }

    /**
     * Method under test: {@link CatalogServiceImpl#getAllCatalogs()}
     */
    @Test
    void testGetAllCatalogs() {
        // Arrange
        ArrayList<Catalog> catalogList = new ArrayList<>();
        when(catalogRepository.findAll()).thenReturn(catalogList);

        // Act
        List<Catalog> actualAllCatalogs = catalogServiceImpl.getAllCatalogs();

        // Assert
        verify(catalogRepository).findAll();
        assertTrue(actualAllCatalogs.isEmpty());
        assertSame(catalogList, actualAllCatalogs);
    }

    /**
     * Method under test: {@link CatalogServiceImpl#getAllCatalogs()}
     */
    @Test
    void testGetAllCatalogs2() {
        // Arrange
        when(catalogRepository.findAll()).thenThrow(new RuntimeException("foo"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> catalogServiceImpl.getAllCatalogs());
        verify(catalogRepository).findAll();
    }
}
