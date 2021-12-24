/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.jmrogado.liferay.customer.service.impl;

import com.jmrogado.liferay.customer.model.Customer;
import com.jmrogado.liferay.customer.service.base.CustomerLocalServiceBaseImpl;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.aop.AopService;
import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.jmrogado.liferay.customer.model.Customer",
	service = AopService.class
)
public class CustomerLocalServiceImpl extends CustomerLocalServiceBaseImpl {
	@Override
	public Customer addCustomer(Customer customer) {
		long id = customer.getId();
		if (id == 0) {
			customer.setId(CounterLocalServiceUtil.increment("com.jmrogado.liferay.customer.model.Customer"));
		}

		return super.addCustomer(customer);
	}
}