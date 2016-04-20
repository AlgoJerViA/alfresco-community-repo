package org.alfresco.repo.domain.contentdata;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.alfresco.error.AlfrescoRuntimeException;
import org.alfresco.service.cmr.repository.ContentData;
import org.alfresco.util.Pair;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * DAO services for <b>alf_content_data</b> table
 * 
 * @author Derek Hulley
 * @author Steven Glover
 * @since 3.2
 */
public interface ContentDataDAO
{
    /**
     * Create a new ContentData instance.
     * 
     * @param contentData   the ContentData details
     * @return              the ContentData pair (id, ContentData) (never null)
     */
    Pair<Long, ContentData> createContentData(ContentData contentData);
    
    /**
     * Update a content data instance
     * 
     * @param id            the unique ID of the entity
     * @param contentData   the new data
     */
    void updateContentData(Long id, ContentData contentData);

    /**
     * Creates an immediately-orphaned content URL, if possible
     * 
     * @param contentUrl    the URL to create if it doesn't exist
     * @param orphanTime    the recorded orphan time or <tt>null</tt> to apply the current time
     * @return              Returns the ID-URL pair
     * @throws DataIntegrityViolationException      if the URL already exists
     */
    Pair<Long, String> createContentUrlOrphaned(String contentUrl, Date orphanTime);
    
    /**
     * @param id            the unique ID of the entity
     * @return              the ContentData pair (id, ContentData) or <tt>null</tt> if it doesn't exist
     * @throws              AlfrescoRuntimeException if the ID provided is invalid
     */
    Pair<Long, ContentData> getContentData(Long id);
    
    /**
     * @param nodeIds       the nodeIds
     * @throws              AlfrescoRuntimeException if an ID provided is invalid
     */
    public void cacheContentDataForNodes(Set<Long> nodeIds);

    /**
     * Delete an instance of content.
     * @param id            the unique ID of the entity
     * @throws              ConcurrencyFailureException if the ID does not exist
     */
    void deleteContentData(Long id);
    
    /**
     * Deletes all <b>alf_content_data</b> rows that are referenced by the given node
     * 
     * @param nodeId        the node ID
     * @param qnameIds      the content properties to target
     */
    void deleteContentDataForNode(Long nodeId, Set<Long> qnameIds);
    
    /**
     * Interface for callbacks during content URL enumeration
     * 
     * @author Derek Hulley
     * @since 3.2
     */
    public static interface ContentUrlHandler
    {
        void handle(Long id, String contentUrl, Long orphanTime);
    }
    
    /**
     * Enumerate all available content URLs that were orphaned on or before the given time
     * 
     * @param contentUrlHandler         the callback object to process the rows
     * @param maxOrphanTimeExclusive    the maximum orphan time (exclusive)
     * @param maxResults                the maximum number of results (1 or greater)
     */
    void getContentUrlsOrphaned(
            ContentUrlHandler contentUrlHandler,
            Long maxOrphanTimeExclusive,
            int maxResults);
    
    /**
     * Enumerate all available content URLs that were orphaned and cleanup for these urls failed
     * 
     * @param contentUrlHandler         the callback object to process the rows
     * @param maxResults                the maximum number of results (1 or greater)
     */
    void getContentUrlsKeepOrphaned(
            ContentUrlHandler contentUrlHandler,
            int maxResults);
    
    /**
     * Delete a batch of content URL entities.
     */
    int deleteContentUrls(List<Long> ids);
    
    /**
     * Get a content url entity by contentUrl
     * 
     * @return                          <tt>null</tt> if the url does not exist
     * 
     * @since 5.0
     * @param contentUrl
     * @return
     */
    ContentUrlEntity getContentUrl(String contentUrl);

    /**
     * Get a content url entity by contentUrlId
     * 
     * @return                          <tt>null</tt> if the url does not exist
     * 
     * @since 5.0
     * @param contentUrlId
     * @return
     */
    ContentUrlEntity getContentUrl(Long contentUrlId);

    /**
     * Get a content URL or create one if it does not exist
     * 
     * @since 5.1
     * @param contentUrlEntity
     */
    ContentUrlEntity getOrCreateContentUrl(String contentUrl);

    /**
     * Updates the content key for the given content url
     * 
     * @since 5.0
     * @param contentUrl
     * @param contentUrlKeyEntity
     */
    boolean updateContentUrlKey(String contentUrl, ContentUrlKeyEntity contentUrlKeyEntity);

    /**
     * Updates the content key for the given content url
     * 
     * @since 5.0
     * @param contentUrlId
     */
    boolean updateContentUrlKey(long contentUrlId, ContentUrlKeyEntity contentUrlKey);

    /**
     * Get symmetric keys entities for symmetric keys that have been encrypted using the given
     * master key, starting from 'fromId' and returning at most 'maxResults' entities.
     * 
     * @since 5.0
     * @param masterKeyAlias master key alias
     * @param fromId id
     * @param maxResults max results
     * @return
     */
    List<ContentUrlKeyEntity> getSymmetricKeysByMasterKeyAlias(String masterKeyAlias, long fromId, int maxResults);

    /**
     * Count symmetric keys entities for symmetric keys for all master keys
     * 
     * @since 5.0
     * @return
     */
    Map<String, Integer> countSymmetricKeysForMasterKeys();

    /**
     * Count symmetric keys entities for symmetric keys that have been encrypted using the given
     * master key
     * 
     * @since 5.0
     * @param masterKeyAlias
     * @return
     */
    int countSymmetricKeysForMasterKeyAlias(String masterKeyAlias);
}
