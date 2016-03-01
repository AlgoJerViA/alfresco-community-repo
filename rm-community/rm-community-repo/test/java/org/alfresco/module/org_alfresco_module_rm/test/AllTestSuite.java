 
package org.alfresco.module.org_alfresco_module_rm.test;

import org.junit.extensions.cpsuite.ClasspathSuite;
import org.junit.extensions.cpsuite.ClasspathSuite.ClassnameFilters;
import org.junit.extensions.cpsuite.ClasspathSuite.SuiteTypes;
import org.junit.extensions.cpsuite.SuiteType;
import org.junit.runner.RunWith;

/**
 * Convenience test suite that runs all the tests.
 *
 * @author Roy Wetherall
 * @since 2.1
 */
@RunWith(ClasspathSuite.class)
@SuiteTypes({SuiteType.TEST_CLASSES, SuiteType.RUN_WITH_CLASSES, SuiteType.JUNIT38_TEST_CLASSES})
@ClassnameFilters({
    // Execute all test classes ending with "Test"
    ".*Test",
    // Exclude the ones ending with "UnitTest"
    "!.*UnitTest",
    // Put the test classes you want to exclude here
    "!.*DataLoadSystemTest",
    "!.*RM2072Test",
    "!.*RM2190Test",
    "!.*RM981SystemTest",
    "!.*RecordsManagementEventServiceImplTest",
    "!.*RmRestApiTest",
    "!.*NotificationServiceHelperSystemTest",
    "!.*RetryingTransactionHelperBaseTest",
    "!.*RMCaveatConfigServiceImplTest",
    // This test is running successfully locally but not on bamboo (if executed as a single test).
    // The problem can be reproduced if the whole test suite is run locally as well.
    // Tests should not be dependant on other test classes and should run in any order without any problems.
    "!.*EmailMapScriptTest"
})
public class AllTestSuite
{
}