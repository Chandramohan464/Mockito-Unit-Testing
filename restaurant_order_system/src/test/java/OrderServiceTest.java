import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mokito.MenuService;
import com.mokito.OrderService;
import com.mokito.PaymentDetails;
import com.mokito.PaymentService;

public class OrderServiceTest {
    @Mock
    private MenuService menuService;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPlaceOrder_ItemOutOfStock() {
        Long menuItemId = 1L;
        int quantity = 2;
        PaymentDetails paymentDetails = new PaymentDetails();
        when(menuService.isItemAvailable(menuItemId, quantity)).thenReturn(false);

        String result = orderService.placeOrder(menuItemId, quantity, paymentDetails);

        assertEquals("Item is out of stock.", result);
        verify(menuService).isItemAvailable(menuItemId, quantity);
        verify(paymentService, never()).processPayment(paymentDetails);
    }

    @Test
    public void testPlaceOrder_PaymentFails() {
        Long menuItemId = 1L;
        int quantity = 2;
        PaymentDetails paymentDetails = new PaymentDetails();
        when(menuService.isItemAvailable(menuItemId, quantity)).thenReturn(true);
        when(paymentService.processPayment(paymentDetails)).thenReturn(false);

        String result = orderService.placeOrder(menuItemId, quantity, paymentDetails);

        assertEquals("Payment failed.", result);
        verify(menuService).isItemAvailable(menuItemId, quantity);
        verify(paymentService).processPayment(paymentDetails);
    }

    @Test
    public void testPlaceOrder_SuccessfulOrder() {
        Long menuItemId = 1L;
        int quantity = 2;
        PaymentDetails paymentDetails = new PaymentDetails();
        when(menuService.isItemAvailable(menuItemId, quantity)).thenReturn(true);
        when(paymentService.processPayment(paymentDetails)).thenReturn(true);

        String result = orderService.placeOrder(menuItemId, quantity, paymentDetails);

        assertEquals("Order placed successfully.", result);
        verify(menuService).isItemAvailable(menuItemId, quantity);
        verify(paymentService).processPayment(paymentDetails);
    }
}
