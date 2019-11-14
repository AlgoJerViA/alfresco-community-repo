/*
 * #%L
 * Alfresco Records Management Module
 * %%
 * Copyright (C) 2005 - 2019 Alfresco Software Limited
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

package org.alfresco.module.org_alfresco_module_rm.audit.extractor;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import org.alfresco.model.ContentModel;
import org.alfresco.module.org_alfresco_module_rm.fileplan.FilePlanService;
import org.alfresco.module.org_alfresco_module_rm.freeze.FreezeService;
import org.alfresco.module.org_alfresco_module_rm.model.RecordsManagementModel;
import org.alfresco.repo.audit.extractor.AbstractDataExtractor;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.Path;
import org.alfresco.service.cmr.rule.RuleService;
import org.alfresco.service.cmr.security.PermissionService;

/**
 * An extractor that extracts the <b>cm:name</b> path from the RM root down to
 * - and including - the node's own name.  This will only extract data if the
 * node is a {@link RecordsManagementModel#ASPECT_FILE_PLAN_COMPONENT fileplan component}.
 *
 * @see FilePlanService#getNodeRefPath(NodeRef)
 *
 * @author Derek Hulley
 * @since 3.2
 */
public final class FilePlanNamePathDataExtractor extends AbstractDataExtractor
{
    private NodeService nodeService;
    private FilePlanService filePlanService;
    private RuleService ruleService;
    private PermissionService permissionService;
    private FreezeService freezeService;

    /**
     * Used to check that the node in the context is a fileplan component
     */
    public void setNodeService(NodeService nodeService)
    {
        this.nodeService = nodeService;
    }

    /**
     * @param filePlanService	file plan service
     */
    public void setFilePlanService(FilePlanService filePlanService)
    {
		this.filePlanService = filePlanService;
	}

    /**
     * @param ruleService the ruleService to set
     */
    public void setRuleService(RuleService ruleService)
    {
        this.ruleService = ruleService;
    }

    /**
     * @param permissionService	permission service
     */
    public void setPermissionService(PermissionService permissionService)
    {
        this.permissionService = permissionService;
    }

    /**
     * @param freezeService	freeze service
     */
    public void setFreezeService(FreezeService freezeService)
    {
        this.freezeService = freezeService;
    }

    /**
     * @return              Returns <tt>true</tt> if the data is a NodeRef and it either represents
     *                      a fileplan component or is frozen
     */
    public boolean isSupported(Serializable data)
    {
        if (!(data instanceof NodeRef))
        {
            return false;
        }
        return nodeService.hasAspect((NodeRef)data, RecordsManagementModel.ASPECT_FILE_PLAN_COMPONENT) ||
            freezeService.isFrozen((NodeRef) data);
    }

    /**
     * @see org.alfresco.repo.audit.extractor.DataExtractor#extractData(java.io.Serializable)
     */
    public Serializable extractData(Serializable value)
    {
        String extractedData = null;

        ruleService.disableRules();
        try
        {
            NodeRef nodeRef = (NodeRef) value;
            StringBuilder sb = new StringBuilder(128);
            
            if (nodeService.hasAspect(nodeRef, RecordsManagementModel.ASPECT_FILE_PLAN_COMPONENT))
            {
                // Get path from the RM root
                List<NodeRef> nodeRefPath = filePlanService.getNodeRefPath(nodeRef);
    
                for (NodeRef pathNodeRef : nodeRefPath)
                {
                    String name = (String)nodeService.getProperty(pathNodeRef, ContentModel.PROP_NAME);
                    sb.append("/").append(name);
                }
            }
            else if (freezeService.isFrozen(nodeRef))
            {
                // Get path from the DM root
                Path nodeRefPath = nodeService.getPath(nodeRef);
                sb.append(nodeRefPath.toDisplayPath(nodeService, permissionService));
                // Get node name
                String name = (String)nodeService.getProperty(nodeRef, ContentModel.PROP_NAME);
                sb.append("/").append(name);
            }

            // Done
            extractedData = sb.toString();
        }
        finally
        {
            ruleService.enableRules();
        }

        return extractedData;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        if (!super.equals(o))
        {
            return false;
        }
        FilePlanNamePathDataExtractor that = (FilePlanNamePathDataExtractor) o;
        return Objects.equals(nodeService, that.nodeService) && Objects.equals(filePlanService, that.filePlanService)
            && Objects.equals(ruleService, that.ruleService);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(nodeService, filePlanService, ruleService);
    }
}
