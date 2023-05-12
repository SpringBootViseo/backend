package application.port;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import application.domain.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import application.domain.Preference;
import application.domain.Product;
import application.port.out.PreferencePort;
import application.port.out.ProductPort;

public class PreferenceServiceTest {

    @Mock
    private PreferencePort preferencePort;

    @Mock
    private ProductPort productPort;

    @InjectMocks
    private PreferenceService preferenceService;

    private String idPreference;
    private UUID idProduct;
    private Preference preference;
    private Product product;
    Category category;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        idProduct = UUID.randomUUID();
        category=new Category(idProduct,"test","test","test");
        idPreference = "test";
        preference = new Preference("test",new ArrayList<>());
        product = new Product(idProduct, "testName", "testDescription", "testBrand", "testCategory", 20, 10, null, "testImage", 0.0, 0.0, 0.0, category);
    }
    @AfterEach
    void tearDown() {
        idProduct=null;
        category=null;
    }
    @Test
    public void testCreatePreference() {
        when(preferencePort.createPrefence(idPreference)).thenReturn(preference);
        Preference createdPreference = preferenceService.createPrefence(idPreference);
        assertEquals(preference, createdPreference);
    }

    @Test
    public void testAddProductToPreference() {
        Preference result = new Preference("test", List.of(product));
        given(productPort.getProduct(idProduct)).willReturn(product);
        given(preferenceService.availablePreference("test")).willReturn(true);

        given(preferenceService.getPreference("test")).willReturn(preference);
        given(preferencePort.addProduct(preference,product)).willReturn(result);

        assertEquals(result,preferenceService.addProduct("test",idProduct));


    }

    @Test
    public void testDeleteProductFromPreference() {
        // Set up mock objects
        when(productPort.getProduct(idProduct)).thenReturn(product);
        when(preferencePort.getPreference(idPreference)).thenReturn(preference);
        when(preferencePort.deleteProductFromPreference(preference, product)).thenReturn(preference);
        when(preferencePort.availablePreference(idPreference)).thenReturn(true);

        // Call the service method
        Preference updatedPreference = preferenceService.deleteProductFromPreference(idPreference, idProduct);

        // Verify the result
        assertEquals(preference, updatedPreference);
    }


    @Test
    public void testGetPreference() {
        given(preferenceService.availablePreference("test")).willReturn(true);
        given(preferencePort.getPreference("test")).willReturn(preference);
    }

    @Test
    public void testAvailablePreferenceWhenPreferenceExists() {
        when(preferencePort.availablePreference(idPreference)).thenReturn(true);
        Boolean available = preferenceService.availablePreference(idPreference);
        assertTrue(available);
    }

    @Test
    public void testAvailablePreferenceWhenPreferenceDoesNotExist() {
        when(preferencePort.availablePreference(idPreference)).thenReturn(false);
        assertThrows(NoSuchElementException.class, () -> preferenceService.getPreference(idPreference));
    }

}
