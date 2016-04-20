
package org.alfresco.repo.workflow.activiti.variable;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.variable.ValueFields;
import org.activiti.engine.impl.variable.VariableType;
import org.alfresco.repo.jscript.ScriptNode;
import org.alfresco.repo.workflow.activiti.ActivitiScriptNode;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.NodeRef;

/**
 * Custom {@link VariableType} that allows storing {@link ActivitiScriptNode} as
 * a process variable in activiti, allowing node properties being used in
 * scripts.
 *
 * @author Frederik Heremans
 * @since 3.4.e
 */
public class ScriptNodeVariableType implements VariableType
{
    public static final String TYPE_NAME = "alfrescoScriptNode";
    
    private ServiceRegistry serviceRegistry;

    @Override
    public String getTypeName()
    {
        return TYPE_NAME;
    }

    @Override
    public boolean isCachable()
    {
        // The ScriptNode can be cached since it uses the serviceRegistry internally
        // for resolving actual values.
        return true;
    }

    @Override
    public boolean isAbleToStore(Object value)
    {
        if (value == null) 
        {
            return true;
        }
        return ScriptNode.class.isAssignableFrom(value.getClass());
    }
 
    @Override
    public void setValue(Object value, ValueFields valueFields)
    {
        String textValue = null;
        if (value != null) 
        {
            if (!(value instanceof ActivitiScriptNode)) 
            {
                throw new ActivitiException("Passed value is not an instance of ActivitiScriptNode, cannot set variable value.");
            }
            NodeRef reference = (((ActivitiScriptNode)value).getNodeRef());
            if (reference != null)
            {
                // Use the string representation of the NodeRef
                textValue = reference.toString();             
            }
        }
        valueFields.setTextValue(textValue);
    }

    @Override
    public Object getValue(ValueFields valueFields)
    {
        ScriptNode scriptNode = null;
        String nodeRefString = valueFields.getTextValue();
        if (nodeRefString != null) 
        {
            scriptNode = new ActivitiScriptNode(new NodeRef(nodeRefString), serviceRegistry);
        }
        return scriptNode;
    }

    public void setServiceRegistry(ServiceRegistry serviceRegistry)
    {
        this.serviceRegistry = serviceRegistry;
    }
}
