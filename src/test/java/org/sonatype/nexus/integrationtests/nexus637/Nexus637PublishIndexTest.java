package org.sonatype.nexus.integrationtests.nexus637;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;
import org.sonatype.nexus.integrationtests.AbstractNexusIntegrationTest;
import org.sonatype.nexus.integrationtests.nexus533.TaskScheduleUtil;
import org.sonatype.nexus.rest.model.ScheduledServicePropertyResource;

/**
 * @author marvin
 * @description Test task Publish Indexes is working
 */
public class Nexus637PublishIndexTest
    extends AbstractNexusIntegrationTest
{

    public Nexus637PublishIndexTest()
    {
        super( "nexus-test-harness-repo" );
    }

    @Test
    public void publishIndex()
        throws Exception
    {
        File repositoryPath = new File( nexusBaseDir, "runtime/work/storage/nexus-test-harness-repo" );
        File index = new File( repositoryPath, ".index" );
        Assert.assertFalse( ".index shouldn't exists before publish index task is run.", index.exists() );

        ScheduledServicePropertyResource prop = new ScheduledServicePropertyResource();
        prop.setId( "repositoryOrGroupId" );
        prop.setValue( "nexus-test-harness-repo" );

        TaskScheduleUtil.runTask( "org.sonatype.nexus.tasks.PublishIndexesTask", prop );

        Assert.assertTrue( ".index should exists after publish index task was run.", index.exists() );
    }
}
