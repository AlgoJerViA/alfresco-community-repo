package org.alfresco.service.cmr.links;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.alfresco.repo.security.permissions.PermissionCheckValue;
import org.alfresco.service.cmr.repository.NodeRef;

/**
 * This class represents a Link in a site 
 * 
 * @author Nick Burch
 * @since 4.0
 */
public interface LinkInfo extends Serializable, PermissionCheckValue 
{
   /**
    * @return the NodeRef of the underlying link
    */
   NodeRef getNodeRef();
   
   /**
    * @return the NodeRef of the site container this belongs to
    */
   NodeRef getContainerNodeRef();
   
   /**
    * @return the System generated name for the link
    */
   String getSystemName();
   
   /**
    * @return the Title of the link
    */
   String getTitle();
   
   /**
    * Sets the Title of the link
    */
   void setTitle(String title);
   
   /**
    * @return the Description of the link
    */
   String getDescription();
   
   /**
    * Sets the Description of the link
    */
   void setDescription(String description);
   
   /**
    * @return the URL of the link
    */
   String getURL();

   /**
    * Sets the URL of the link
    */
   void setURL(String url);
   
   /**
    * @return the creator of the link
    */
   String getCreator();
   
   /**
    * @return the creation date and time
    */
   Date getCreatedAt();
   
   /**
    * @return the modification date and time
    */
   Date getModifiedAt();
   
   /**
    * Is this a internal link?
    */
   boolean isInternal();
   
   /**
    * Sets if this is an internal link or not
    */
   void setInternal(boolean internal);
   
   /**
    * @return the Tags associated with the link 
    */
   List<String> getTags();
}
