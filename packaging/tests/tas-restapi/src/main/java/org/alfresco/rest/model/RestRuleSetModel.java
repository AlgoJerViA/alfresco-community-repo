/*-
 * #%L
 * alfresco-tas-restapi
 * %%
 * Copyright (C) 2005 - 2022 Alfresco Software Limited
 * %%
 * This file is part of the Alfresco software.
 * If the software was purchased under a paid Alfresco license, the terms of
 * the paid license agreement will prevail.  Otherwise, the software is
 * provided under the following open source license terms:
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
 * #L%
 */
package org.alfresco.rest.model;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.alfresco.rest.core.IRestModel;
import org.alfresco.utility.model.TestModel;

/**
 * Generated by 'Kristian.Dimitrov@hyland.com' on '2022-07-13 15:53' from 'Alfresco Content Services REST API' swagger file
 * Generated from 'Alfresco Content Services REST API' swagger file
 * Base Path {@linkplain /alfresco/api/-default-/public/alfresco/versions/1}
 */
public class RestRuleSetModel extends TestModel implements IRestModel<RestRuleSetModel>
{
    @JsonProperty (value = "entry")
    RestRuleSetModel model;

    @Override
    public RestRuleSetModel onModel()
    {
        return model;
    }

    /**
     * Identifier for the rule set
     */
    @JsonProperty (required = true)
    private String id;
    /** The node id of the folder that owns this rule set */
    private String owningFolder;
    /** The reason why the rule set is included for the folder. */
    private String inclusionType;
    /** A list of node ids for folders that inherit this rule set. */
    private List<String> inheritedBy;

    public String getId()
    {
        return this.id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getOwningFolder()
    {
        return owningFolder;
    }

    public void setOwningFolder(String owningFolder)
    {
        this.owningFolder = owningFolder;
    }

    public String getInclusionType()
    {
        return inclusionType;
    }

    public void setInclusionType(String inclusionType)
    {
        this.inclusionType = inclusionType;
    }

    public List<String> getInheritedBy()
    {
        return inheritedBy;
    }

    public void setInheritedBy(List<String> inheritedBy)
    {
        this.inheritedBy = inheritedBy;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestRuleSetModel that = (RestRuleSetModel) o;
        return Objects.equals(id, that.id)
                && Objects.equals(owningFolder, that.owningFolder)
                && Objects.equals(inclusionType, that.inclusionType)
                && Objects.equals(inheritedBy, that.inheritedBy);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, owningFolder, inclusionType, inheritedBy);
    }
}
