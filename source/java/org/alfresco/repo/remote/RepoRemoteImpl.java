package org.alfresco.repo.remote;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.alfresco.service.cmr.remote.RepoRemote;
import org.alfresco.service.cmr.remote.RepoRemoteTransport;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.util.Pair;

/**
 * Client side implementation of RepoRemote interface.
 * @author britt
 */
public class RepoRemoteImpl implements RepoRemote
{
    /**
     * The underlying remote transport.
     */
    private RepoRemoteTransport fTransport;

    /**
     * The ticket holder.
     */
    private ClientTicketHolder fTicketHolder;

    /**
     * Default constructor.
     */
    public RepoRemoteImpl()
    {
    }
    
    /**
     * Set the transport instance.
     */
    public void setRepoRemoteTransport(RepoRemoteTransport transport)
    {
        fTransport = transport;
    }
    
    /**
     * Setter.
     * @param ticketHolder To set.
     */
    public void setClientTicketHolder(ClientTicketHolder ticketHolder)
    {
        fTicketHolder = ticketHolder;
    }
    
    /* (non-Javadoc)
     * @see org.alfresco.service.cmr.remote.RepoRemote#createDirectory(org.alfresco.service.cmr.repository.NodeRef, java.lang.String)
     */
    public NodeRef createDirectory(NodeRef base, String path) 
    {
        return fTransport.createDirectory(fTicketHolder.getTicket(), base, path);
    }

    /* (non-Javadoc)
     * @see org.alfresco.service.cmr.remote.RepoRemote#createFile(org.alfresco.service.cmr.repository.NodeRef, java.lang.String)
     */
    public OutputStream createFile(NodeRef base, String path) 
    {
        return new RepoRemoteOutputStream(fTransport.createFile(fTicketHolder.getTicket(), base, path),
                                          fTransport, fTicketHolder);
    }

    /* (non-Javadoc)
     * @see org.alfresco.service.cmr.remote.RepoRemote#getListing(org.alfresco.service.cmr.repository.NodeRef)
     */
    public Map<String, Pair<NodeRef, Boolean>> getListing(NodeRef dir) 
    {
        return fTransport.getListing(fTicketHolder.getTicket(), dir);
    }

    /* (non-Javadoc)
     * @see org.alfresco.service.cmr.remote.RepoRemote#getRoot()
     */
    public NodeRef getRoot() 
    {
        return fTransport.getRoot(fTicketHolder.getTicket());
    }

    /* (non-Javadoc)
     * @see org.alfresco.service.cmr.remote.RepoRemote#lookup(org.alfresco.service.cmr.repository.NodeRef, java.lang.String)
     */
    public Pair<NodeRef, Boolean> lookup(NodeRef base, String path) 
    {
        return fTransport.lookup(fTicketHolder.getTicket(), base, path);
    }

    /* (non-Javadoc)
     * @see org.alfresco.service.cmr.remote.RepoRemote#readFile(org.alfresco.service.cmr.repository.NodeRef)
     */
    public InputStream readFile(NodeRef fileRef) 
    {
        return new RepoRemoteInputStream(fTransport.readFile(fTicketHolder.getTicket(), fileRef),
                                         fTransport, fTicketHolder);
    }

    /* (non-Javadoc)
     * @see org.alfresco.service.cmr.remote.RepoRemote#readFile(org.alfresco.service.cmr.repository.NodeRef, java.lang.String)
     */
    public InputStream readFile(NodeRef base, String path) 
    {
        return new RepoRemoteInputStream(fTransport.readFile(fTicketHolder.getTicket(), base, path), 
                                         fTransport, fTicketHolder);
    }

    /* (non-Javadoc)
     * @see org.alfresco.service.cmr.remote.RepoRemote#removeNode(org.alfresco.service.cmr.repository.NodeRef)
     */
    public void removeNode(NodeRef toRemove) 
    {
        fTransport.removeNode(fTicketHolder.getTicket(), toRemove);
    }

    /* (non-Javadoc)
     * @see org.alfresco.service.cmr.remote.RepoRemote#removeNode(org.alfresco.service.cmr.repository.NodeRef, java.lang.String)
     */
    public void removeNode(NodeRef base, String path) 
    {
        fTransport.removeNode(fTicketHolder.getTicket(), base, path);
    }

    /* (non-Javadoc)
     * @see org.alfresco.service.cmr.remote.RepoRemote#rename(org.alfresco.service.cmr.repository.NodeRef, java.lang.String, java.lang.String)
     */
    public void rename(NodeRef base, String src, String dst) 
    {
        fTransport.rename(fTicketHolder.getTicket(), base, src, dst);
    }

    /* (non-Javadoc)
     * @see org.alfresco.service.cmr.remote.RepoRemote#writeFile(org.alfresco.service.cmr.repository.NodeRef, java.lang.String)
     */
    public OutputStream writeFile(NodeRef base, String path) 
    {
        return new RepoRemoteOutputStream(fTransport.writeFile(fTicketHolder.getTicket(), base, path),
                                          fTransport, fTicketHolder);
    }
}
