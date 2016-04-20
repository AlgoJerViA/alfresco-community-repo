
package org.alfresco.repo.publishing;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.alfresco.service.cmr.publishing.NodePublishStatus;
import org.alfresco.service.cmr.publishing.PublishingDetails;
import org.alfresco.service.cmr.publishing.PublishingEvent;
import org.alfresco.service.cmr.publishing.PublishingQueue;
import org.alfresco.service.cmr.publishing.PublishingService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.util.ParameterCheck;

/**
 * @author Brian
 * @author Nick Smith
 * @since 4.0
 */
public class PublishServiceImpl implements PublishingService
{
    public static final String NAME = "PublishingService";

    private PublishingEventHelper publishingEventHelper;
    private PublishingRootObject rootObject;
    
    /**
     * @param rootObject the rootObject to set
     */
    public void setPublishingRootObject(PublishingRootObject rootObject)
    {
        this.rootObject = rootObject;
    }
    
    /**
     * @param publishingEventHelper the publishingEventHelper to set
     */
    public void setPublishingEventHelper(PublishingEventHelper publishingEventHelper)
    {
        this.publishingEventHelper = publishingEventHelper;
    }
    
    /**
     * {@inheritDoc}
     */
    public PublishingEvent getPublishingEvent(String id)
    {
        return publishingEventHelper.getPublishingEvent(id);
    }

    /**
     * {@inheritDoc}
     */
    public List<PublishingEvent> getPublishEventsForNode(NodeRef publishedNode)
    {
        NodeRef queueNode = rootObject.getPublishingQueue().getNodeRef();
        List<NodeRef> eventNodes = publishingEventHelper.getEventNodesForPublishedNode(queueNode, publishedNode);
        return publishingEventHelper.getPublishingEvents(eventNodes);
    }

    /**
     * {@inheritDoc}
     */
    public List<PublishingEvent> getUnpublishEventsForNode(NodeRef unpublishedNode)
    {
        NodeRef queueNode = rootObject.getPublishingQueue().getNodeRef();
        List<NodeRef> eventNodes = publishingEventHelper.getEventNodesForUnpublishedNode(queueNode, unpublishedNode);
        return publishingEventHelper.getPublishingEvents(eventNodes);
    }

    /**
     * {@inheritDoc}
     */
    public void cancelPublishingEvent(String id)
    {
        ParameterCheck.mandatory("id", id);
        publishingEventHelper.cancelEvent(id);
    }

    private PublishingQueue getPublishingQueue()
    {
        return rootObject.getPublishingQueue();
    }

    public Map<NodeRef, NodePublishStatus> checkPublishStatus(String channelId, Collection<NodeRef> nodes)
    {
//        Map<NodeRef, NodePublishStatus> results = new HashMap<NodeRef, NodePublishStatus>();
//        for (NodeRef node : nodes)
//        {
//            if(node!=null && results.containsKey(node)==false)
//            {
//                results.put(node, environmentHelper.checkNodeStatus(node, channelId));
//            }
//        }
//        return results;
        //TODO
        return null;
    }
    
    public Map<NodeRef, NodePublishStatus> checkPublishStatus(String channelId, NodeRef... nodes)
    {
//        return checkPublishStatus(channelId, Arrays.asList(nodes));
        //TODO
        return null;
    }
    
    public PublishingDetails createPublishingDetails()
    {
        return getPublishingQueue().createPublishingDetails();
    }

    /**
     * {@inheritDoc}
     */
    public String scheduleNewEvent(PublishingDetails publishingDetails)
    {
        return getPublishingQueue().scheduleNewEvent(publishingDetails);
    }

}