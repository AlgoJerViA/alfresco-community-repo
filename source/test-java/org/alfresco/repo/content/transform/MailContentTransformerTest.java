package org.alfresco.repo.content.transform;

import java.io.File;

import org.alfresco.repo.content.MimetypeMap;
import org.alfresco.repo.content.filestore.FileContentReader;
import org.alfresco.repo.content.filestore.FileContentWriter;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.ContentWriter;
import org.alfresco.service.cmr.repository.TransformationOptions;
import org.alfresco.util.TempFileProvider;

/**
 * @see org.alfresco.repo.content.transform.MailContentTransformer
 * 
 * @author Kevin Roast
 */
public class MailContentTransformerTest extends AbstractContentTransformerTest
{
    private MailContentTransformer transformer;
    
    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        
        transformer = new MailContentTransformer();
        transformer.setMimetypeService(mimetypeService);
        transformer.setTransformerDebug(transformerDebug);
        transformer.setTransformerConfig(transformerConfig);
    }
    
    /**
     * @return Returns the same transformer regardless - it is allowed
     */
    protected ContentTransformer getTransformer(String sourceMimetype, String targetMimetype)
    {
        return transformer;
    }
    
    public void testIsTransformable() throws Exception
    {
        assertFalse(transformer.isTransformable(MimetypeMap.MIMETYPE_TEXT_PLAIN, -1, MimetypeMap.MIMETYPE_OUTLOOK_MSG, new TransformationOptions()));
        assertTrue(transformer.isTransformable(MimetypeMap.MIMETYPE_OUTLOOK_MSG, -1, MimetypeMap.MIMETYPE_TEXT_PLAIN, new TransformationOptions())); 
    }
    
    /**
     * Test transforming a valid msg file to text
     */
    public void testMsgToText() throws Exception
    {
        File msgSourceFile = loadQuickTestFile("msg");
        File txtTargetFile = TempFileProvider.createTempFile(getName() + "-target-1", ".txt");
        ContentReader reader = new FileContentReader(msgSourceFile);
        reader.setMimetype(MimetypeMap.MIMETYPE_OUTLOOK_MSG);
        ContentWriter writer = new FileContentWriter(txtTargetFile);
        writer.setMimetype(MimetypeMap.MIMETYPE_TEXT_PLAIN);
        
        transformer.transform(reader, writer);
        
        ContentReader reader2 = new FileContentReader(txtTargetFile);
        reader2.setMimetype(MimetypeMap.MIMETYPE_TEXT_PLAIN);
        assertTrue(reader2.getContentString().contains(QUICK_CONTENT));
    }
    
    /**
     * Test transforming a valid unicode msg file to text
     */
    public void testUnicodeMsgToText() throws Exception
    {
        File msgSourceFile = loadQuickTestFile("unicode.msg");
        File txtTargetFile = TempFileProvider.createTempFile(getName() + "-target-2", ".txt");
        ContentReader reader = new FileContentReader(msgSourceFile);
        reader.setMimetype(MimetypeMap.MIMETYPE_OUTLOOK_MSG);
        ContentWriter writer = new FileContentWriter(txtTargetFile);
        writer.setMimetype(MimetypeMap.MIMETYPE_TEXT_PLAIN);
        
        transformer.transform(reader, writer);
        
        ContentReader reader2 = new FileContentReader(txtTargetFile);
        reader2.setMimetype(MimetypeMap.MIMETYPE_TEXT_PLAIN);
        assertTrue(reader2.getContentString().contains(QUICK_CONTENT));
    }
    
    /**
     * Test transforming a chinese non-unicode msg file to
     *  text
     */
    public void testNonUnicodeChineseMsgToText() throws Exception
    {
        File msgSourceFile = loadQuickTestFile("chinese.msg");
        File txtTargetFile = TempFileProvider.createTempFile(getName() + "-target-2", ".txt");
        ContentReader reader = new FileContentReader(msgSourceFile);
        reader.setMimetype(MimetypeMap.MIMETYPE_OUTLOOK_MSG);
        ContentWriter writer = new FileContentWriter(txtTargetFile);
        writer.setMimetype(MimetypeMap.MIMETYPE_TEXT_PLAIN);
        
        transformer.transform(reader, writer);
        
        ContentReader reader2 = new FileContentReader(txtTargetFile);
        reader2.setMimetype(MimetypeMap.MIMETYPE_TEXT_PLAIN);
        
        // Check the quick text
        String text = reader2.getContentString();
        assertTrue(text.contains(QUICK_CONTENT));
        
        // Now check the non quick parts came out ok
        assertTrue(text.contains("(\u5f35\u6bd3\u502b)"));
        assertTrue(text.contains("\u683c\u5f0f\u6e2c\u8a66 )"));
    }
}
