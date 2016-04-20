package org.alfresco.repo.domain.schema.script;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.commons.logging.Log;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit tests for the {@link ScriptBundleExecutorImpl} class.
 * 
 * @author Matt Ward
 */
@RunWith(MockitoJUnitRunner.class)
public class ScriptBundleExecutorImplTest
{
    // Class under test
    private ScriptBundleExecutorImpl bundleExecutor;
    private @Mock ScriptExecutor scriptExecutor;
    private @Mock Log log;
    
    @BeforeClass
    public static void setUpClass() throws Exception
    {
    }

    @Before
    public void setUp() throws Exception
    {
        bundleExecutor = new ScriptBundleExecutorImpl(scriptExecutor);
        bundleExecutor.log = log;
    }

    @Test
    public void canExecuteMultipleScripts() throws Exception
    {
        bundleExecutor.exec("/path/to/script/dir", "one.sql", "two.sql", "three.sql");
        
        InOrder inOrder = Mockito.inOrder(scriptExecutor);
        inOrder.verify(scriptExecutor).executeScriptUrl("/path/to/script/dir/one.sql");
        inOrder.verify(scriptExecutor).executeScriptUrl("/path/to/script/dir/two.sql");
        inOrder.verify(scriptExecutor).executeScriptUrl("/path/to/script/dir/three.sql");
    }
    
    @Test
    public void willAlwaysRunPostBatchScript() throws Exception
    {
        // The first of the "main" scripts will fail...
        Exception e = new RuntimeException("Script failure!"); 
        doThrow(e).when(scriptExecutor).executeScriptUrl("/path/to/script/dir/work01.sql");
        
        bundleExecutor.execWithPostScript("/path/to/script/dir", "post.sql", "pre.sql", "work01.sql", "work02.sql");
        
        InOrder inOrder = Mockito.inOrder(scriptExecutor);
        inOrder.verify(scriptExecutor).executeScriptUrl("/path/to/script/dir/pre.sql");
        inOrder.verify(scriptExecutor).executeScriptUrl("/path/to/script/dir/work01.sql");
        // work02.sql will NOT be executed, but the post-script will be.
        inOrder.verify(scriptExecutor, never()).executeScriptUrl("/path/to/script/dir/work02.sql");
        inOrder.verify(scriptExecutor).executeScriptUrl("/path/to/script/dir/post.sql");
        
        verify(log).error(anyString(), same(e));
    }
}
