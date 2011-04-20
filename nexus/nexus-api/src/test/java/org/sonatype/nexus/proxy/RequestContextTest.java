/**
 * Copyright (c) 2008-2011 Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://www.sonatype.com/products/nexus/attributions.
 *
 * This program is free software: you can redistribute it and/or modify it only under the terms of the GNU Affero General
 * Public License Version 3 as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License Version 3
 * for more details.
 *
 * You should have received a copy of the GNU Affero General Public License Version 3 along with this program.  If not, see
 * http://www.gnu.org/licenses.
 *
 * Sonatype Nexus (TM) Open Source Version is available from Sonatype, Inc. Sonatype and Sonatype Nexus are trademarks of
 * Sonatype, Inc. Apache Maven is a trademark of the Apache Foundation. M2Eclipse is a trademark of the Eclipse Foundation.
 * All other trademarks are the property of their respective owners.
 */
package org.sonatype.nexus.proxy;

import junit.framework.TestCase;
import org.junit.Test;

public class RequestContextTest
    extends TestCase
{
    @Test
    public void testNullParent()
    {
        RequestContext requestContext = new RequestContext( null );
        assertNull( requestContext.getParentContext() );

        requestContext.setParentContext( null );
        assertNull( requestContext.getParentContext() );
    }

    @Test
    public void testValidParent()
    {
        RequestContext parentContext = new RequestContext( null );
        RequestContext requestContext = new RequestContext( parentContext );
        assertEquals( parentContext, requestContext.getParentContext() );

        requestContext.setParentContext( null );
        assertNull( requestContext.getParentContext() );

        requestContext = new RequestContext();
        assertNull( requestContext.getParentContext() );
        requestContext.setParentContext( parentContext );
        assertEquals( parentContext, requestContext.getParentContext() );
    }

    @Test
    public void testSelfParent()
    {
        RequestContext requestContext = new RequestContext();
        assertNull( requestContext.getParentContext() );

        try
        {
            requestContext.setParentContext( requestContext );
            fail( "Expected IllegalArgumentException" );
        }
        catch ( IllegalArgumentException expected )
        {
        }

        assertNull( requestContext.getParentContext() );
    }

    // 3-->2-->1-->3
    @Test
    public void testSelfAncestor()
    {
        RequestContext requestContext1 = new RequestContext();
        assertNull( requestContext1.getParentContext() );
        RequestContext requestContext2 = new RequestContext( requestContext1 );
        assertEquals( requestContext1, requestContext2.getParentContext() );
        RequestContext requestContext3 = new RequestContext( requestContext2 );
        assertEquals( requestContext2, requestContext3.getParentContext() );

        try
        {
            requestContext1.setParentContext( requestContext3 );
            fail( "Expected IllegalArgumentException" );
        }
        catch ( IllegalArgumentException expected )
        {
        }

        assertNull( requestContext1.getParentContext() );
    }
}
