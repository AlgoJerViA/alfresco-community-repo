/* 
 * Copyright (C) 2005-2015 Alfresco Software Limited.
 *
 * This file is part of Alfresco
 *
 * Alfresco is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Alfresco is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Alfresco. If not, see http://www.gnu.org/licenses/.
 */
package org.alfresco.traitextender;

public class ExtensionPoint<E, M extends Trait>
{
    private Class<E> extensionAPI;

    private Class<M> traitAPI;

    public ExtensionPoint(Class<E> extensionAPI, Class<M> traitAPI)
    {
        super();
        this.extensionAPI = extensionAPI;
        this.traitAPI = traitAPI;
    }

    public Class<E> getExtensionAPI()
    {
        return this.extensionAPI;
    }

    public Class<M> getTraitAPI()
    {
        return this.traitAPI;
    }

    @Override
    public int hashCode()
    {
        return extensionAPI.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof ExtensionPoint)
        {
            ExtensionPoint<?,?> pointObj = (ExtensionPoint<?,?>) obj;
            return extensionAPI.equals(pointObj.extensionAPI)
                        && traitAPI.equals(pointObj.traitAPI);
        }
        else
        {
            return false;
        }
    }

    @Override
    public String toString()
    {
        return "{" + extensionAPI.toString() + "," + traitAPI.toString() + "}";
    }
}
