 
package org.alfresco.repo.rule;

import java.util.HashSet;
import java.util.Set;

import org.alfresco.module.org_alfresco_module_rm.fileplan.FilePlanService;
import org.alfresco.module.org_alfresco_module_rm.model.RecordsManagementModel;
import org.alfresco.module.org_alfresco_module_rm.record.RecordService;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.AuthenticationUtil.RunAsWork;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.rule.Rule;
import org.alfresco.service.namespace.QName;

/**
 * Extended rule service implementation.
 *
 * @author Roy Wetherall
 * @since 2.1
 */
public class ExtendedRuleServiceImpl extends RuleServiceImpl
{
	/** indicates whether the rules should be run as admin or not */
    private boolean runAsAdmin = true;

    /** ignore types */
    private Set<QName> ignoredTypes = new HashSet<QName>();

    /** file plan service */
    private FilePlanService filePlanService;

    /** node service */
    protected NodeService nodeService;

    /** Record service */
    protected RecordService recordService;

    /**
     * @param nodeService	node service
     */
    public void setNodeService2(NodeService nodeService)
    {
        this.nodeService = nodeService;
    }

    /**
     * @param filePlanService   file plan service
     */
    public void setFilePlanService(FilePlanService filePlanService)
    {
        this.filePlanService = filePlanService;
    }

    /**
     * @param recordService   record service
     */
    public void setRecordService(RecordService recordService)
    {
        this.recordService = recordService;
    }

    /**
     * @param runAsAdmin  true if run rules as admin, false otherwise
     */
    public void setRunAsAdmin(boolean runAsAdmin)
    {
        this.runAsAdmin = runAsAdmin;
    }

    /**
     * Init method
     */
    @Override
    public void init()
    {
        super.init();

        // Specify a set of system types to be ignored by rule executions
        ignoredTypes.add(RecordsManagementModel.TYPE_DISPOSITION_SCHEDULE);
        ignoredTypes.add(RecordsManagementModel.TYPE_DISPOSITION_ACTION);
        ignoredTypes.add(RecordsManagementModel.TYPE_DISPOSITION_ACTION_DEFINITION);
        ignoredTypes.add(RecordsManagementModel.TYPE_EVENT_EXECUTION);
    }

    /**
     * @see org.alfresco.repo.rule.RuleServiceImpl#saveRule(org.alfresco.service.cmr.repository.NodeRef, org.alfresco.service.cmr.rule.Rule)
     */
    @Override
    public void saveRule(final NodeRef nodeRef, final Rule rule)
    {
        if (filePlanService.isFilePlanComponent(nodeRef))
        {
            AuthenticationUtil.runAsSystem(new RunAsWork<Void>()
            {
                @Override
                public Void doWork()
                {
                    ExtendedRuleServiceImpl.super.saveRule(nodeRef, rule);
                    return null;
                }

            });
        }
        else
        {
            super.saveRule(nodeRef, rule);
        }
    }

    /**
     * @see org.alfresco.repo.rule.RuleServiceImpl#removeRule(org.alfresco.service.cmr.repository.NodeRef, org.alfresco.service.cmr.rule.Rule)
     */
    @Override
    public void removeRule(final NodeRef nodeRef, final Rule rule)
    {
        if (filePlanService.isFilePlanComponent(nodeRef))
        {
            AuthenticationUtil.runAsSystem(new RunAsWork<Void>()
            {
                @Override
                public Void doWork()
                {
                    ExtendedRuleServiceImpl.super.removeRule(nodeRef, rule);
                    return null;
                }

            });
        }
        else
        {
            super.removeRule(nodeRef, rule);
        }
    }

    /**
     * @see org.alfresco.repo.rule.RuleServiceImpl#executeRule(org.alfresco.service.cmr.rule.Rule, org.alfresco.service.cmr.repository.NodeRef, java.util.Set)
     */
    @Override
    public void executeRule(final Rule rule, final NodeRef nodeRef, final Set<ExecutedRuleData> executedRules)
    {
        if (nodeService.exists(nodeRef))
        {
            QName typeQName = nodeService.getType(nodeRef);
            if (shouldRuleBeAppliedToNode(rule, nodeRef, typeQName))        
            {
                // check if this is a rm rule on a rm artifact
                if (filePlanService.isFilePlanComponent(nodeRef) &&
                	isFilePlanComponentRule(rule))
                {
                	// ignore and
                    if (!isIgnoredType(typeQName))
        	        {
        	        	if (runAsAdmin)
        	            {
        	            	AuthenticationUtil.runAs(new RunAsWork<Void>()
                            {
                                @Override
                                public Void doWork()
                                {
                                    ExtendedRuleServiceImpl.super.executeRule(rule, nodeRef, executedRules);
                                    return null;
                                }
                            }, AuthenticationUtil.getAdminUserName());
                    	}
                    	else
                    	{
                    		// run as current user
                    		ExtendedRuleServiceImpl.super.executeRule(rule, nodeRef, executedRules);
                    	}
        	        }
                }
                else
                {
                    // just execute the rule as the current user
                    super.executeRule(rule, nodeRef, executedRules);
                }
            }
        }
    }

    /**
     * Indicates whether the rule is a file plan component
     *
     * @param rule		rule
     * @return boolean	true if rule is set on a file plan component, false otherwise
     */
    private boolean isFilePlanComponentRule(Rule rule)
    {
        NodeRef nodeRef = getOwningNodeRef(rule);
        return filePlanService.isFilePlanComponent(nodeRef);
    }

    /**
     * @param  typeQName	type qname
     * @return boolean		true if ignore type, false otherwise
     */
    private boolean isIgnoredType(QName typeQName)
    {
        return ignoredTypes.contains(typeQName);
    }

    /**
     * Check if the rule is associated with the file plan component that the node it is being
     * applied to isn't a hold container, a hold, a transfer container, a transfer, an unfiled
     * record container, an unfiled record folder or unfiled content
     *
     * @param rule
     * @param nodeRef
     * @param typeQName
     * @return
     */
    private boolean shouldRuleBeAppliedToNode(final Rule rule, final NodeRef nodeRef, final QName typeQName)
    {
        return AuthenticationUtil.runAsSystem(new RunAsWork<Boolean>()
        {
            public Boolean doWork() throws Exception
            {
                boolean result = true;
                NodeRef ruleNodeRef = getOwningNodeRef(rule);
                if (filePlanService.isFilePlan(ruleNodeRef))
                {
                    // if this rule is defined at the root of the file plan then
                    // we do not want to apply
                    // it to holds/transfers/unfiled content...
                    result = !(RecordsManagementModel.TYPE_HOLD.equals(typeQName)
                            || RecordsManagementModel.TYPE_HOLD_CONTAINER.equals(typeQName)
                            || RecordsManagementModel.TYPE_TRANSFER.equals(typeQName)
                            || RecordsManagementModel.TYPE_TRANSFER_CONTAINER.equals(typeQName)
                            || RecordsManagementModel.TYPE_UNFILED_RECORD_CONTAINER
                                    .equals(typeQName)
                            || RecordsManagementModel.TYPE_UNFILED_RECORD_FOLDER.equals(typeQName)
                            || nodeService.hasAspect(nodeRef,
                                    RecordsManagementModel.ASPECT_TRANSFERRING)
                            || nodeService.hasAspect(nodeRef, RecordsManagementModel.ASPECT_FROZEN)
                            || !recordService.isFiled(nodeRef));
                }
                return result;
            }
        });
    }
}
