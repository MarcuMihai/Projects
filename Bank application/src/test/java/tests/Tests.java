/*
 * Copyright 2015-2018 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v2.0 which
 * accompanies this distribution and is available at
 *
 * http://www.eclipse.org/legal/epl-v20.html
 */

package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dataAccess.ClientDAO;
import dataModel.Client;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class Tests {
	@Test
	public void testFindClients() {
		Client c1= ClientDAO.findById(2);
		assertEquals("Gas",c1.getName());
	}
}
