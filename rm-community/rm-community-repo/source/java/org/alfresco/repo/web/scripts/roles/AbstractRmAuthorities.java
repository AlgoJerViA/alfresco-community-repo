 
package org.alfresco.repo.web.scripts.roles;

import java.util.Map;

import org.alfresco.module.org_alfresco_module_rm.script.admin.RoleDeclarativeWebScript;
import org.alfresco.service.cmr.repository.NodeRef;
import org.apache.commons.lang.StringUtils;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;

/**
 * Abstract class for adding/removing a user/group to/from a role
 * This class contains the common methods needed in the sub classes.
 *
 * @author Tuna Aksoy
 * @since 2.1
 */
public class AbstractRmAuthorities extends RoleDeclarativeWebScript
{
    /** Constants for the url parameters */
    private static final String ROLE_ID = "roleId";
    private static final String AUTHORITY_NAME = "authorityName";

    /**
     * Util method for getting the nodeRef from the request
     *
     * @param req The webscript request
     * @return The nodeRef passed in the request
     */
    protected NodeRef getFilePlan(WebScriptRequest req)
    {
        NodeRef filePlan = super.getFilePlan(req);
        if (filePlan == null)
        {
            throw new WebScriptException(Status.STATUS_NOT_FOUND, "No filePlan was provided on the URL.");
        }
        return filePlan;
    }

    /**
     * Util method for getting the roleId from the request
     *
     * @param req The webscript request
     * @return The role id passed in the request
     */
    protected String getRoleId(WebScriptRequest req)
    {
        return getParamValue(req, ROLE_ID);
    }

    /**
     * Util method for getting the authorityName from the request
     *
     * @param req The webscript request
     * @return The authorityName passed in the request
     */
    protected String getAuthorityName(WebScriptRequest req)
    {
        return getParamValue(req, AUTHORITY_NAME);
    }

    /**
     * Helper method to get the value of parameter from the request
     *
     * @param req The webscript request
     * @param param The name of the parameter for which the value is requested
     * @return The value for the requested parameter
     */
    private String getParamValue(WebScriptRequest req, String param)
    {
        Map<String, String> templateVars = req.getServiceMatch().getTemplateVars();

        String authorityName = templateVars.get(param);
        if (StringUtils.isBlank(authorityName))
        {
            throw new WebScriptException(Status.STATUS_NOT_FOUND, "No '" + param + "' was provided on the URL.");
        }
        return authorityName;
    }
}
