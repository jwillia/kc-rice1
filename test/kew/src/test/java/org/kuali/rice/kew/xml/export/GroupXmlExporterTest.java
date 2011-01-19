/*
 * Copyright 2007-2008 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.rice.kew.xml.export;

import org.kuali.rice.kew.export.ExportDataSet;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kim.bo.Group;
import org.kuali.rice.kim.service.IdentityManagementService;
import org.kuali.rice.kim.service.KIMServiceLocator;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * This is a description of what this class does - jjhanso don't forget to fill this in.
 *
 * @author Kuali Rice Team (rice.collab@kuali.org)
 *
 */
public class GroupXmlExporterTest extends XmlExporterTestCase {

    /**
     * This overridden method ...
     *
     * @see org.kuali.rice.kew.xml.export.XmlExporterTestCase#assertExport()
     */
    @Override
    protected void assertExport() throws Exception {
        IdentityManagementService identityManagementService = KIMServiceLocator.getIdentityManagementService();
        List<? extends Group> oldGroups = identityManagementService.getGroupsForPrincipal(identityManagementService.getPrincipalByPrincipalName("ewestfal").getPrincipalId());

        ExportDataSet dataSet = new ExportDataSet();
        dataSet.getGroups().addAll(oldGroups);
        byte[] xmlBytes = KEWServiceLocator.getXmlExporterService().export(dataSet);
        assertTrue("XML should be non empty.", xmlBytes != null && xmlBytes.length > 0);

        StringBuffer output = new StringBuffer();
        for (int i=0; i < xmlBytes.length; i++){
            output.append((char)xmlBytes[i]);
        }
        System.out.print(output.toString());
        // now clear the tables
        //ClearDatabaseLifecycle clearLifeCycle = new ClearDatabaseLifecycle();
        //clearLifeCycle.getTablesToClear().add("EN_RULE_BASE_VAL_T");
        //clearLifeCycle.getTablesToClear().add("EN_RULE_ATTRIB_T");
        //clearLifeCycle.getTablesToClear().add("EN_RULE_TMPL_T");
        //clearLifeCycle.getTablesToClear().add("EN_DOC_TYP_T");
        //clearLifeCycle.start();
        //new ClearCacheLifecycle().stop();
        //KIMServiceLocatorInternal.getGroupService().

        // import the exported xml
        //loadXmlStream(new BufferedInputStream(new ByteArrayInputStream(xmlBytes)));
/*
        List newRules = KEWServiceLocator.getRuleService().fetchAllRules(true);
        assertEquals("Should have same number of old and new Rules.", oldRules.size(), newRules.size());
        for (Iterator iterator = oldRules.iterator(); iterator.hasNext();) {
            RuleBaseValues oldRule = (RuleBaseValues) iterator.next();
            boolean foundRule = false;
            for (Iterator iterator2 = newRules.iterator(); iterator2.hasNext();) {
                RuleBaseValues newRule = (RuleBaseValues) iterator2.next();
                if (oldRule.getDescription().equals(newRule.getDescription())) {
                    assertRuleExport(oldRule, newRule);
                    foundRule = true;
                }
            }
            assertTrue("Could not locate the new rule for description " + oldRule.getDescription(), foundRule);
        }
*/
    }

}
