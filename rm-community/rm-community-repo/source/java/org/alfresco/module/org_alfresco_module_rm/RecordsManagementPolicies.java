 
package org.alfresco.module.org_alfresco_module_rm;

import java.io.Serializable;
import java.util.Map;

import org.alfresco.repo.policy.ClassPolicy;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;

/**
 * Interface containing records management policies
 *
 * @author Roy Wetherall
 */
public interface RecordsManagementPolicies
{
    /** Policy names */
    QName BEFORE_RM_ACTION_EXECUTION = QName.createQName(NamespaceService.ALFRESCO_URI, "beforeRMActionExecution");
    QName ON_RM_ACTION_EXECUTION = QName.createQName(NamespaceService.ALFRESCO_URI, "onRMActionExecution");
    QName BEFORE_CREATE_REFERENCE = QName.createQName(NamespaceService.ALFRESCO_URI, "beforeCreateReference");
    QName ON_CREATE_REFERENCE = QName.createQName(NamespaceService.ALFRESCO_URI, "onCreateReference");
    QName BEFORE_REMOVE_REFERENCE = QName.createQName(NamespaceService.ALFRESCO_URI, "beforeRemoveReference");
    QName ON_REMOVE_REFERENCE = QName.createQName(NamespaceService.ALFRESCO_URI, "onRemoveReference");

    /** Before records management action execution */
    interface BeforeRMActionExecution extends ClassPolicy
    {
        void beforeRMActionExecution(NodeRef nodeRef, String name, Map<String, Serializable> parameters);
    }

    /** On records management action execution */
    interface OnRMActionExecution extends ClassPolicy
    {
        void onRMActionExecution(NodeRef nodeRef, String name, Map<String, Serializable> parameters);
    }

    /** Before creation of reference */
    interface BeforeCreateReference extends ClassPolicy
    {
        void beforeCreateReference(NodeRef fromNodeRef, NodeRef toNodeRef, QName reference);
    }

    /** On creation of reference */
    interface OnCreateReference extends ClassPolicy
    {
        void onCreateReference(NodeRef fromNodeRef, NodeRef toNodeRef, QName reference);
    }

    /** Before removal of reference */
    interface BeforeRemoveReference extends ClassPolicy
    {
        void beforeRemoveReference(NodeRef fromNodeRef, NodeRef toNodeRef, QName reference);
    }

    /**
     * On removal of reference
     *
     * @since 1.0
     */
    interface OnRemoveReference extends ClassPolicy
    {
        /**
         * @param fromNodeRef   from node reference
         * @param toNodeRef     to node reference
         * @param reference     name of reference
         */
        void onRemoveReference(NodeRef fromNodeRef, NodeRef toNodeRef, QName reference);
    }

    /**
     * Before record file policy
     *
     * @since 2.2
     */
    interface BeforeFileRecord extends ClassPolicy
    {
        /** policy name */
        QName QNAME = QName.createQName(NamespaceService.ALFRESCO_URI, "beforeRecordFile");

        /**
         * @param nodeRef   node reference
         */
        void beforeFileRecord(NodeRef nodeRef);
    }

    /**
     * On record file policy
     *
     * @since 2.2
     */
    interface OnFileRecord extends ClassPolicy
    {
        /** policy name */
        QName QNAME = QName.createQName(NamespaceService.ALFRESCO_URI, "onRecordFile");

        /**
         * @param nodeRef   node reference
         */
        void onFileRecord(NodeRef nodeRef);
    }
}
