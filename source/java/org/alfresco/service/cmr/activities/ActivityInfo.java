package org.alfresco.service.cmr.activities;

import org.alfresco.service.cmr.model.FileInfo;
import org.alfresco.service.cmr.repository.NodeRef;

/**
 * Basic Activity information for use when posting Activities.
 *
 * @author Gethin James
 */
public class ActivityInfo
{
    private final NodeRef nodeRef;
    private final String parentPath;
    private final NodeRef parentNodeRef;
    private final String siteId;
    private final String fileName;
    private final boolean isFolder;
    private final FileInfo fileInfo;

    public ActivityInfo(NodeRef nodeRef, String parentPath, NodeRef parentNodeRef,
                        String siteId, String fileName, boolean isFolder)
    {
        super();
        this.nodeRef = nodeRef;
        this.parentPath = parentPath;
        this.parentNodeRef = parentNodeRef;
        this.siteId = siteId;
        this.fileName = fileName;
        this.isFolder = isFolder;
        this.fileInfo = null;
    }

    public ActivityInfo(String parentPath, NodeRef parentNodeRef, String siteId, FileInfo fileInfo)
    {
        super();
        this.nodeRef = fileInfo.getNodeRef();
        this.parentPath = parentPath;
        this.parentNodeRef = parentNodeRef;
        this.siteId = siteId;
        this.fileName = fileInfo.getName();
        this.isFolder = fileInfo.isFolder();
        this.fileInfo = fileInfo;
    }

    public FileInfo getFileInfo()
    {
        return fileInfo;
    }

    public NodeRef getNodeRef()
    {
        return nodeRef;
    }

    public String getParentPath()
    {
        return parentPath;
    }

    public NodeRef getParentNodeRef()
    {
        return parentNodeRef;
    }

    public String getSiteId()
    {
        return siteId;
    }

    public String getFileName()
    {
        return fileName;
    }

    public boolean isFolder()
    {
        return isFolder;
    }
}
