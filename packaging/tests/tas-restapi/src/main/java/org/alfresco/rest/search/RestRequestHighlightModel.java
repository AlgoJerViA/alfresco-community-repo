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
package org.alfresco.rest.search;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.alfresco.rest.core.IRestModel;
import org.alfresco.utility.model.TestModel;

/**
 * Generated by 'msuzuki' on '2017-02-23 13:41' from 'Alfresco Search REST API' swagger file 
 * Generated from 'Alfresco Search REST API' swagger file
 * Base Path {@linkplain /alfresco/api/-default-/public/search/versions/1}
 */
public class RestRequestHighlightModel extends TestModel implements IRestModel<RestRequestHighlightModel>
{
    @JsonProperty(value = "entry")
    RestRequestHighlightModel model;

    @Override
    public RestRequestHighlightModel onModel()
    {
        return model;
    }

    /**
    The string used to mark the start of a highlight in a fragment.
    */	        

    private String prefix;	    
    /**
    The string used to mark the end of a highlight in a fragment.
    */	        

    private String postfix;	    
    /**
    The maximum number of distinct highlight snippets to return for each highlight field.
    */	        

    private int snippetCount;	    
    /**
    The character length of each snippet.
    */	        

    private int fragmentSize;	    
    /**
    The number of characters to be considered for highlighting. Matches after this count will not be shown.
    */	        

    private int maxAnalyzedChars;	    
    /**
    If fragments over lap they can be  merged into one larger fragment
    */	        

    private boolean mergeContiguous;	    
    /**
    Should phrases be identified.
    */	        

    private boolean usePhraseHighlighter;	    
    /**
    The fields to highlight and field specific configuration properties for each field
    */	        

    private List<RestRequestFieldsModel> fields;	    

    public String getPrefix()
    {
        return this.prefix;
    }

    public void setPrefix(String prefix)
    {
        this.prefix = prefix;
    }				

    public String getPostfix()
    {
        return this.postfix;
    }

    public void setPostfix(String postfix)
    {
        this.postfix = postfix;
    }				

    public int getSnippetCount()
    {
        return this.snippetCount;
    }

    public void setSnippetCount(int snippetCount)
    {
        this.snippetCount = snippetCount;
    }				

    public int getFragmentSize()
    {
        return this.fragmentSize;
    }

    public void setFragmentSize(int fragmentSize)
    {
        this.fragmentSize = fragmentSize;
    }				

    public int getMaxAnalyzedChars()
    {
        return this.maxAnalyzedChars;
    }

    public void setMaxAnalyzedChars(int maxAnalyzedChars)
    {
        this.maxAnalyzedChars = maxAnalyzedChars;
    }				

    public boolean getMergeContiguous()
    {
        return this.mergeContiguous;
    }

    public void setMergeContiguous(boolean mergeContiguous)
    {
        this.mergeContiguous = mergeContiguous;
    }				

    public boolean getUsePhraseHighlighter()
    {
        return this.usePhraseHighlighter;
    }

    public void setUsePhraseHighlighter(boolean usePhraseHighlighter)
    {
        this.usePhraseHighlighter = usePhraseHighlighter;
    }				

    public List<RestRequestFieldsModel> getFields()
    {
        return this.fields;
    }

    public void setFields(List<RestRequestFieldsModel> fields)
    {
        this.fields = fields;
    }				
}
 