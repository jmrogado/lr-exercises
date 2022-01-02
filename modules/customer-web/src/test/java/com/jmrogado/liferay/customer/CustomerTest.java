package com.jmrogado.liferay.customer;

import com.jmrogado.liferay.customer.exception.DuplicateCustomerException;
import com.jmrogado.liferay.customer.exception.InvalidCustomerException;
import com.jmrogado.liferay.customer.exception.NoSuchCustomerException;
import com.jmrogado.liferay.customer.model.Customer;
import com.jmrogado.liferay.customer.service.CustomerLocalService;
import com.jmrogado.liferay.customer.util.CustomerPortletUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author jmrogado
 */

@RunWith(PowerMockRunner.class)
public class CustomerTest {

    private static final String EXISTING_EMAIL_ADDRESS = "existing_user@test.com";
    private static final String AVAILABLE_EMAIL_ADDRESS = "available_user@test.com";
    private static final String INVALID_EMAIL_ADDRESS = "invalid_email_address";
    private static final String VALID_FIRST_NAME = "Valid Firstname";
    private static final String VALID_LAST_NAME = "Valid Lastname";
    private static final String INVALID_FIRST_NAME = "";
    private static final String INVALID_LAST_NAME = "";

    @Mock
    final Customer mockCustomer = Mockito.mock(Customer.class);

    @Mock
    final CustomerLocalService mockCustomerLocalService = Mockito.mock(CustomerLocalService.class);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        List<Customer> customers = new ArrayList<Customer>();
        customers.add(mockCustomer);

        // Mock get all customers
        Mockito.when(mockCustomerLocalService.getCustomers(Mockito.anyInt(), Mockito.anyInt())).thenReturn(customers);

        // Mock find customer by emailAddress
        Mockito.when(mockCustomerLocalService.findByEmailAddress(EXISTING_EMAIL_ADDRESS)).thenReturn(mockCustomer);
        Mockito.when(mockCustomerLocalService.findByEmailAddress(AVAILABLE_EMAIL_ADDRESS)).thenThrow(NoSuchCustomerException.class);
    }

    @Test
    public void testGetCustomers() throws Exception {
        List<Customer> customers = mockCustomerLocalService.getCustomers(QueryUtil.ALL_POS, QueryUtil.ALL_POS);

        Assert.assertEquals(1, customers.size());
    }

    @Test(expected = InvalidCustomerException.class)
    public void testValidateRequiredFieldFirstName() throws InvalidCustomerException {
        CustomerPortletUtil.validateRequiredFields(INVALID_FIRST_NAME, VALID_LAST_NAME, new Date(), AVAILABLE_EMAIL_ADDRESS);
    }

    @Test(expected = InvalidCustomerException.class)
    public void testValidateRequiredFieldLastName() throws InvalidCustomerException {
        CustomerPortletUtil.validateRequiredFields(VALID_FIRST_NAME, INVALID_LAST_NAME, new Date(), AVAILABLE_EMAIL_ADDRESS);
    }

    @Test(expected = InvalidCustomerException.class)
    public void testValidateRequiredFieldBirthDate() throws InvalidCustomerException {
        CustomerPortletUtil.validateRequiredFields(VALID_FIRST_NAME, VALID_LAST_NAME, null, AVAILABLE_EMAIL_ADDRESS);
    }

    @Test(expected = InvalidCustomerException.class)
    public void testValidateRequiredFieldEmailAddress() throws InvalidCustomerException {
        CustomerPortletUtil.validateRequiredFields(VALID_FIRST_NAME, VALID_LAST_NAME, new Date(), "");
    }

    @Test(expected = InvalidCustomerException.class)
    public void testValidateEmailAddressFormat() throws InvalidCustomerException {
        CustomerPortletUtil.validateRequiredFields(VALID_FIRST_NAME, VALID_LAST_NAME, new Date(), INVALID_EMAIL_ADDRESS);
    }

    @Test()
    public void testValidateAllRequiredFields() throws InvalidCustomerException {
        CustomerPortletUtil.validateRequiredFields(VALID_FIRST_NAME, VALID_LAST_NAME, new Date(), AVAILABLE_EMAIL_ADDRESS);
    }

    @Test(expected = DuplicateCustomerException.class)
    public void testValidateExistingEmailAddress() throws Exception {
        CustomerPortletUtil.validateUniqueCustomer(mockCustomerLocalService, EXISTING_EMAIL_ADDRESS);
    }

}
