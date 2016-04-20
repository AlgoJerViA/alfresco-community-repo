package org.alfresco.filesys.auth.nfs;

import org.springframework.beans.factory.BeanNameAware;

/**
 * Represents a user mapping for the {@link AlfrescoRpcAuthenticator}.
 */
public class UserMapping implements BeanNameAware
{
    /** The name. */
    private String name;

    /** The uid. */
    private int uid;

    /** The gid. */
    private int gid;

    /**
     * Default constructor for container initialisation.
     */
    public UserMapping()
    {
    }

    /**
     * The Constructor.
     * 
     * @param name
     *            the name
     * @param uid
     *            the uid
     * @param gid
     *            the gid
     */
    public UserMapping(String name, int uid, int gid)
    {
        super();
        this.name = name;
        this.uid = uid;
        this.gid = gid;
    }

    /**
     * Gets the name.
     * 
     * @return the name
     */
    public String getName()
    {
        return this.name;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.beans.factory.BeanNameAware#setBeanName(java.lang.String)
     */
    public void setBeanName(String name)
    {
        this.name = name;
    }

    /**
     * Gets the uid.
     * 
     * @return the uid
     */
    public int getUid()
    {
        return this.uid;
    }

    /**
     * Sets the uid.
     * 
     * @param uid
     *            the new uid
     */
    public void setUid(int uid)
    {
        this.uid = uid;
    }

    /**
     * Gets the gid.
     * 
     * @return the gid
     */
    public int getGid()
    {
        return this.gid;
    }

    /**
     * Sets the gid.
     * 
     * @param gid
     *            the new gid
     */
    public void setGid(int gid)
    {
        this.gid = gid;
    }
}