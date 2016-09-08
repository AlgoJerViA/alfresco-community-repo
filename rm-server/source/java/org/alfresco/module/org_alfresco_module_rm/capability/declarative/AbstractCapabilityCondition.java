/*
 * Copyright (C) 2005-2011 Alfresco Software Limited.
 *
 * This file is part of Alfresco
 *
 * Alfresco is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Alfresco is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Alfresco. If not, see <http://www.gnu.org/licenses/>.
 */
package org.alfresco.module.org_alfresco_module_rm.capability.declarative;

import java.util.Map;

import org.alfresco.module.org_alfresco_module_rm.RecordsManagementService;
import org.alfresco.module.org_alfresco_module_rm.fileplan.FilePlanService;
import org.alfresco.module.org_alfresco_module_rm.freeze.FreezeService;
import org.alfresco.module.org_alfresco_module_rm.model.RecordsManagementModel;
import org.alfresco.module.org_alfresco_module_rm.record.RecordService;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.transaction.TransactionalResourceHelper;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.security.PermissionService;
import org.springframework.beans.factory.BeanNameAware;

/**
 * Abstract capability condition.
 * 
 * @author Roy Wetherall
 */
public abstract class AbstractCapabilityCondition implements CapabilityCondition, 
                                                             BeanNameAware,
                                                             RecordsManagementModel
{
    /** transaction cache key */
    private static final String KEY_EVALUATE = "rm.transaction.evaluate";
    
    /** Capability condition name */
    protected String name;
    
    /** Services */
    protected RecordsManagementService rmService;
    protected RecordService recordService;
    protected PermissionService permissionService;
    protected NodeService nodeService;
    protected FreezeService freezeService;
    protected FilePlanService filePlanService;
    
    /**
     * @param rmService records management service
     */
    public void setRecordsManagementService(RecordsManagementService rmService)
    {
        this.rmService = rmService;
    }
    
    /**
     * @param recordService     record service
     */
    public void setRecordService(RecordService recordService)
    {
        this.recordService = recordService;
    }
    
    /**
     * @param permissionService permission service
     */
    public void setPermissionService(PermissionService permissionService)
    {
        this.permissionService = permissionService;
    }
    
    /**
     * @param nodeService   node service
     */
    public void setNodeService(NodeService nodeService)
    {
        this.nodeService = nodeService;
    }
    
    /**
     * @param freezeService   freeze service
     */
    public void setFreezeService(FreezeService freezeService)
    {
       this.freezeService = freezeService;
    }
    
    /**
     * @param filePlanService	file plan service
     */
    public void setFilePlanService(FilePlanService filePlanService) 
    {
		this.filePlanService = filePlanService;
	}
    
    /**
     * @see org.alfresco.module.org_alfresco_module_rm.capability.declarative.CapabilityCondition#getName()
     */
    @Override
    public String getName()
    {
        return name;
    }
    
    /**
     * @see org.alfresco.module.org_alfresco_module_rm.capability.declarative.CapabilityCondition#evaluate(org.alfresco.service.cmr.repository.NodeRef)
     */
    @Override
    public boolean evaluate(NodeRef nodeRef)
    {
        boolean result = false;
        
        // check transaction cache
        Map<String, Boolean> map = TransactionalResourceHelper.getMap(KEY_EVALUATE);
        String key = getName() + "|" + nodeRef.toString() + "|" + AuthenticationUtil.getRunAsUser();
        if (map.containsKey(key))
        {
            result = map.get(key);
        }
        else
        {
            result = evaluateImpl(nodeRef);
            map.put(key, result);
        }
        
        return result;
    }
    
    /**
     * Condition evaluate implementation
     */
    public abstract boolean evaluateImpl(NodeRef nodeRef);

    /**
     * @see org.springframework.beans.factory.BeanNameAware#setBeanName(java.lang.String)
     */
    @Override
    public void setBeanName(String name)
    {
        this.name = name;
    }
}
