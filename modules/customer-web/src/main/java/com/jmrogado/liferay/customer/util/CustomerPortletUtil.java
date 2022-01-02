package com.jmrogado.liferay.customer.util;

import com.jmrogado.liferay.customer.exception.DuplicateCustomerException;
import com.jmrogado.liferay.customer.exception.InvalidCustomerException;
import com.jmrogado.liferay.customer.exception.NoSuchCustomerException;
import com.jmrogado.liferay.customer.service.CustomerLocalService;
import com.liferay.portal.kernel.util.Validator;

import java.util.Date;

/**
 * @author jmrogado
 */
public class CustomerPortletUtil {
    public static void validateUniqueCustomer(CustomerLocalService customerLocalService, String emailAddress) throws DuplicateCustomerException {
        try {
            customerLocalService.findByEmailAddress(emailAddress);
            throw new DuplicateCustomerException();
        } catch (NoSuchCustomerException e) {
            // OK
        }
    }

    public static void validateRequiredFields(String firstName, String lastName, Date birthDate, String emailAddress) throws InvalidCustomerException {
        if (Validator.isBlank(firstName) || Validator.isBlank(lastName) || Validator.isBlank(emailAddress) || !(Validator.isEmailAddress(emailAddress)) || Validator.isNull(birthDate)) {
            throw new InvalidCustomerException();
        }
    }

}
