/*
 * #%L
 * Alfresco Records Management Module
 * %%
 * Copyright (C) 2005 - 2024 Alfresco Software Limited
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
package org.alfresco.rest.rm.community.requests.gscore.api;

import static org.alfresco.rest.core.RestRequest.requestWithBody;
import static org.alfresco.rest.core.RestRequest.simpleRequest;
import static org.alfresco.rest.rm.community.util.ParameterCheck.mandatoryObject;
import static org.alfresco.rest.rm.community.util.ParameterCheck.mandatoryString;
import static org.alfresco.rest.rm.community.util.PojoUtility.toJson;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import org.alfresco.rest.core.RMRestWrapper;
import org.alfresco.rest.rm.community.model.hold.Hold;
import org.alfresco.rest.rm.community.model.hold.HoldChild;
import org.alfresco.rest.rm.community.model.hold.HoldChildCollection;
import org.alfresco.rest.rm.community.model.hold.HoldDeletionReason;
import org.alfresco.rest.rm.community.requests.RMModelRequest;

/**
 * Holds REST API Wrapper
 *
 * @author Damian Ujma
 */
public class HoldsAPI extends RMModelRequest
{

    /**
     * @param rmRestWrapper
     */
    public HoldsAPI(RMRestWrapper rmRestWrapper)
    {
        super(rmRestWrapper);
    }

    /**
     * Gets a hold.
     *
     * @param holdId The identifier of a hold
     * @param parameters The URL parameters to add
     * @return The {@link Hold} for the given {@code holdId}
     * @throws RuntimeException for the following cases:
     * <ul>
     *  <li>{@code holdId} is not a valid format</li>
     *  <li>authentication fails</li>
     *  <li>current user does not have permission to read {@code holdId}</li>
     *  <li>{@code holdId} does not exist</li>
     * </ul>
     */
    public Hold getHold(String holdId, String parameters)
    {
        mandatoryString("holdId", holdId);

        return getRmRestWrapper().processModel(Hold.class, simpleRequest(
            GET,
            "holds/{holdId}?{parameters}",
            holdId,
            parameters
                                                                        ));
    }

    /**
     * See {@link #getHold(String, String)}
     */
    public Hold getHold(String holdId)
    {
        mandatoryString("holdId", holdId);

        return getHold(holdId, EMPTY);
    }

    /**
     * Updates a hold.
     *
     * @param holdModel     The hold model which holds the information
     * @param holdId        The identifier of the hold
     * @param parameters          The URL parameters to add
     * @throws RuntimeException for the following cases:
     * <ul>
     *  <li>the update request is invalid or {@code holdId} is not a valid format or {@code holdModel} is invalid</li>
     *  <li>authentication fails</li>
     *  <li>current user does not have permission to update {@code holdId}</li>
     *  <li>{@code holdId} does not exist</li>
     * </ul>
     */
    public Hold updateHold(Hold holdModel, String holdId, String parameters)
    {
        mandatoryObject("holdModel", holdModel);
        mandatoryString("holdId", holdId);

        return getRmRestWrapper().processModel(Hold.class, requestWithBody(
            PUT,
            toJson(holdModel),
            "holds/{holdId}?{parameters}",
            holdId,
            parameters
                                                                            ));
    }

    /**
     * See {@link #updateHold(Hold, String, String)}
     */
    public Hold updateHold(Hold holdModel, String holdId)
    {
        mandatoryObject("holdModel", holdModel);
        mandatoryString("holdId", holdId);

        return updateHold(holdModel, holdId, EMPTY);
    }

    /**
     * Deletes a hold.
     *
     * @param holdId The identifier of a hold
     * @throws RuntimeException for the following cases:
     * <ul>
     *  <li>{@code holdId} is not a valid format</li>
     *  <li>authentication fails</li>
     *  <li>current user does not have permission to delete {@code holdId}</li>
     *  <li>{@code holdId} does not exist</li>
     * </ul>
     */
    public void deleteHold(String holdId)
    {
        mandatoryString("holdId", holdId);

        getRmRestWrapper().processEmptyModel(simpleRequest(
            DELETE,
            "holds/{holdId}",
            holdId
                                                          ));
    }

    /**
     * Deletes a hold and stores a reason for deletion in the audit log.
     *
     * @param reason        The reason for hold deletion
     * @param holdId        The identifier of a hold
     * @throws RuntimeException for the following cases:
     * <ul>
     *  <li>{@code holdId} is not a valid format or {@code reason} is invalid</li>
     *  <li>authentication fails</li>
     *  <li>current user does not have permission to delete {@code holdId}</li>
     *  <li>{@code holdId} does not exist</li>
     * </ul>
     */
    public HoldDeletionReason deleteHoldWithReason(HoldDeletionReason reason, String holdId)
    {
        mandatoryObject("reason", reason);
        mandatoryString("holdId", holdId);

        return getRmRestWrapper().processModel(HoldDeletionReason.class, requestWithBody(
            POST,
            toJson(reason),
            "holds/{holdId}/delete",
            holdId
                                                          ));
    }

    /**
     * Adds the relationship between a child and a parent hold.
     *
     * @param holdChild The hold child model
     * @param holdId The identifier of a hold
     * @param parameters The URL parameters to add
     * @return The created {@link Hold}
     * @throws RuntimeException for the following cases:
     * <ul>
     *  <li>{@code holdId} is not a valid format or {@code holdId} is invalid</li>
     *  <li>authentication fails</li>
     *  <li>current user does not have permission to add children to {@code holdId}</li>
     *  <li>{@code holdId} does not exist</li>
     * </ul>
     */
    public HoldChild addChildToHold(HoldChild holdChild, String holdId, String parameters)
    {
        mandatoryObject("holdId", holdId);

        return getRmRestWrapper().processModel(HoldChild.class, requestWithBody(
            POST,
            toJson(holdChild),
            "holds/{holdId}/children",
            holdId,
            parameters));
    }

    /**
     * See {@link #addChildToHold(HoldChild, String, String)}
     */
    public HoldChild addChildToHold(HoldChild holdChild, String holdId)
    {
        return addChildToHold(holdChild, holdId, EMPTY);
    }

    /**
     * Gets the children of a hold.
     *
     * @param holdId The identifier of a hold
     * @param parameters The URL parameters to add
     * @return The {@link HoldChildCollection} for the given {@code holdId}
     * @throws RuntimeException for the following cases:
     * <ul>
     *  <li>authentication fails</li>
     *  <li>current user does not have permission to read {@code holdId}</li>
     *  <li>{@code holdId} does not exist</li>
     *</ul>
     */
    public HoldChildCollection getChildren(String holdId, String parameters)
    {
        mandatoryString("holdId", holdId);

        return getRmRestWrapper().processModels(HoldChildCollection.class, simpleRequest(
            GET,
            "holds/{holdId}/children",
            holdId,
            parameters
                                                                                   ));
    }

    /**
     * See {@link #getChildren(String, String)}
     */
    public HoldChildCollection getChildren(String holdId)
    {
        return getChildren(holdId, EMPTY);
    }

    /**
     * Deletes the relationship between a child and a parent hold.
     *
     * @param holdChildId The identifier of hold child
     * @param holdId The identifier of a hold
     * @param parameters The URL parameters to add
     * @throws RuntimeException for the following cases:
     * <ul>
     *  <li>{@code holdId} or {@code holdChildId} is invalid</li>
     *  <li>authentication fails</li>
     *  <li>current user does not have permission to delete children from {@code holdId}</li>
     *  <li>{@code holdId} does not exist</li>
     * </ul>
     */
    public void deleteHoldChild(String holdId, String holdChildId, String parameters)
    {
        mandatoryString("holdId", holdId);
        mandatoryString("holdChildId", holdChildId);

        getRmRestWrapper().processEmptyModel(simpleRequest(
            DELETE,
            "holds/{holdId}/children/{holdChildId}",
            holdId,
            holdChildId,
            parameters
                                                          ));
    }

    /**
     * See {@link #deleteHoldChild(String, String, String)}
     */
    public void deleteHoldChild(String holdId, String holdChildId)
    {
        deleteHoldChild(holdId, holdChildId, EMPTY);
    }
}
