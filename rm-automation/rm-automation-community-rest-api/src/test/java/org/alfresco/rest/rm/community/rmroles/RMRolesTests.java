/*
 * #%L
 * Alfresco Records Management Module
 * %%
 * Copyright (C) 2005 - 2018 Alfresco Software Limited
 * %%
 * This file is part of the Alfresco software.
 * -
 * If the software was purchased under a paid Alfresco license, the terms of
 * the paid license agreement will prevail.  Otherwise, the software is
 * provided under the following open source license terms:
 * -
 * Alfresco is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * -
 * Alfresco is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * -
 * You should have received a copy of the GNU Lesser General Public License
 * along with Alfresco. If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */

package org.alfresco.rest.rm.community.rmroles;

import static com.google.common.collect.Sets.newHashSet;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.alfresco.rest.rm.community.base.BaseRMRestTest;
import org.alfresco.rest.v0.RMRolesAndActionsAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * API tests of RM roles.
 *
 * @author Tom Page
 * @since 2.7
 */
public class RMRolesTests extends BaseRMRestTest
{
    /** The display labels of the expected default RM roles. */
    private static final Set<String> ROLES = newHashSet("Records Management Administrator",
                "Records Management Manager", "Records Management Power User", "Records Management Security Officer",
                "Records Management User");
    /** The API for managing RM roles and capabilities. */
    @Autowired
    private RMRolesAndActionsAPI rmRolesAndActionsAPI;

    /** Check that the roles API returns the default RM roles. */
    @Test(description = "Check the default RM roles exist.")
    public void checkRMRolesExist()
    {
        Set<String> configuredRoles = rmRolesAndActionsAPI
                    .getConfiguredRoles(getAdminUser().getUsername(), getAdminUser().getPassword());
        ROLES.forEach(role -> assertTrue("Could not found role " + role, configuredRoles.contains(role)));
    }
}
