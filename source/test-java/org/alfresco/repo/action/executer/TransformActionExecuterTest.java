package org.alfresco.repo.action.executer;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.alfresco.repo.content.MimetypeMap;
import org.alfresco.repo.content.encoding.ContentCharsetFinder;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.MimetypeService;
import org.junit.Test;

/**
 * This class contains unit tests for {@link TransformActionExecuter}.
 * 
 * @author Neil McErlean
 */
public class TransformActionExecuterTest
{
    @Test
    public void transformName() throws Exception
    {
        MimetypeService dummyMimetypeService = new DummyMimetypeService("txt");
        
        // Case 1. A simple name with a reasonable extension.
        String original = "Letter to Bank Manager.doc";
        final String newMimetype = MimetypeMap.MIMETYPE_TEXT_PLAIN;
        String newName = TransformActionExecuter.transformName(dummyMimetypeService, original, newMimetype, true);
        assertEquals("Letter to Bank Manager.txt", newName);

        // Case 2. String after '.' is clearly not an extension
        original = "No.1 - First Document Title";
        newName = TransformActionExecuter.transformName(dummyMimetypeService, original, newMimetype, true);
        assertEquals(original + ".txt", newName);

        // Case 2b. String after '.' is clearly not an extension. Don't always add
        original = "No.1 - First Document Title";
        newName = TransformActionExecuter.transformName(dummyMimetypeService, original, newMimetype, false);
        assertEquals(original, newName);

        // Case 3. A name with no extension
        original = "Letter to Bank Manager";
        newName = TransformActionExecuter.transformName(dummyMimetypeService, original, newMimetype, true);
        assertEquals(original + ".txt", newName);
        
        // Case 3b. A name with no extension. Don't always add
        original = "Letter to Bank Manager";
        newName = TransformActionExecuter.transformName(dummyMimetypeService, original, newMimetype, false);
        assertEquals(original, newName);
        
        // Case 4. A name ending in a dot
        original = "Letter to Bank Manager.";
        newName = TransformActionExecuter.transformName(dummyMimetypeService, original, newMimetype, true);
        assertEquals(original + "txt", newName);
        
        // Case 4b. A name ending in a dot. Don't always add
        original = "Letter to Bank Manager.";
        newName = TransformActionExecuter.transformName(dummyMimetypeService, original, newMimetype, false);
        assertEquals(original, newName);
        
        // Case 5. Unknown mime type
        original = "Letter to Bank Manager.txt";
        final String unknownMimetype = "Marcel/Marceau";
        // The real MimetypeService returns a 'bin' extension for unknown mime types.
        dummyMimetypeService = new DummyMimetypeService("bin");
        
        newName = TransformActionExecuter.transformName(dummyMimetypeService, original, unknownMimetype, true);
        assertEquals("Letter to Bank Manager.bin", newName);
    }
}

/**
 * This is a very simple stub for the MimetypeService.
 * For this test, we're only interested in the values that come back from
 * the getExtension call.
 */
class DummyMimetypeService implements MimetypeService
{
    private final String result;
    public DummyMimetypeService(String result) { this.result = result; }
    public ContentCharsetFinder getContentCharsetFinder()        { return null;  }
    public Map<String, String> getDisplaysByExtension()          { return null;  }
    public Map<String, String> getDisplaysByMimetype()           { return null;  }
    public String getExtension(String mimetype)                  { return result;}
    public Map<String, String> getExtensionsByMimetype()         { return null;  }
    public String getMimetype(String extension)                  { return null;  }
    public List<String> getMimetypes()                           { return null;  }
    public Map<String, String> getMimetypesByExtension()         { return null;  }
    public String guessMimetype(String filename)                 { return null;  }
    public String guessMimetype(String filename,ContentReader reader){ return null; }
    public String guessMimetype(String filename,InputStream input){ return null; }
    public boolean isText(String mimetype)                       { return false; }
    public String getMimetypeIfNotMatches(ContentReader reader)  { return null;  }
    public Collection<String> getMimetypes(String extension)     { return null;  }
}