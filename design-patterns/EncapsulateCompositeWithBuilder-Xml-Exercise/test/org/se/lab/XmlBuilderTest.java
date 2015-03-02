package org.se.lab;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class XmlBuilderTest
{
    private XmlDocument document;
    
    @Before
    public void startup()
    {
        DocumentBuilder builder = new XmlBuilder();
        
        builder.beginElement("project");
        builder.addAttribute("name", "simple-ant-project");
        builder.addAttribute("default", "compile");

            builder.beginElement("property");
            builder.addAttribute("name", "src");
            builder.addAttribute("location", "./src/java");
            builder.endElement();
        
            builder.beginElement("property");
            builder.addAttribute("name", "build");
            builder.addAttribute("location", "./build");
            builder.endElement();
            
            builder.beginElement("target");
            builder.addAttribute("name", "compile");
                builder.beginElement("javac");
                builder.addAttribute("srcdir", "${src}");
                builder.addAttribute("destdir", "${build}");
                builder.endElement();
            builder.endElement();
        builder.endElement();
        
        document = ((XmlBuilder)builder).getDocument();        
    }
    
    
    @Test
    public void testAntFile()
    {        
        XmlElement root = document.getRoot();
        Assert.assertEquals("project", root.getElementName());
        Assert.assertEquals("name", root.getAttributes().get(0).getAttributeName());
        Assert.assertEquals("simple-ant-project", root.getAttributes().get(0).getValue());
        Assert.assertEquals("default", root.getAttributes().get(1).getAttributeName());
        Assert.assertEquals("compile", root.getAttributes().get(1).getValue());
        
        XmlElement propertySrc = root.getElements().get(0);
        Assert.assertEquals("property", propertySrc.getElementName());
        Assert.assertEquals("name", propertySrc.getAttributes().get(0).getAttributeName());
        Assert.assertEquals("src", propertySrc.getAttributes().get(0).getValue());
        Assert.assertEquals("location", propertySrc.getAttributes().get(1).getAttributeName());
        Assert.assertEquals("./src/java", propertySrc.getAttributes().get(1).getValue());
        
        XmlElement propertyBuild = root.getElements().get(1);
        Assert.assertEquals("property", propertyBuild.getElementName());
        Assert.assertEquals("name", propertyBuild.getAttributes().get(0).getAttributeName());
        Assert.assertEquals("build", propertyBuild.getAttributes().get(0).getValue());
        Assert.assertEquals("location", propertyBuild.getAttributes().get(1).getAttributeName());
        Assert.assertEquals("./build", propertyBuild.getAttributes().get(1).getValue());
    
        XmlElement targetCompile = root.getElements().get(2);
        Assert.assertEquals("target", targetCompile.getElementName());
        Assert.assertEquals("name", targetCompile.getAttributes().get(0).getAttributeName());
        Assert.assertEquals("compile", targetCompile.getAttributes().get(0).getValue());
        
        XmlElement taskJavaC = targetCompile.getElements().get(0);
        Assert.assertEquals("javac", taskJavaC.getElementName());
        Assert.assertEquals("srcdir", taskJavaC.getAttributes().get(0).getAttributeName());
        Assert.assertEquals("${src}", taskJavaC.getAttributes().get(0).getValue());
        Assert.assertEquals("destdir", taskJavaC.getAttributes().get(1).getAttributeName());
        Assert.assertEquals("${build}", taskJavaC.getAttributes().get(1).getValue());        
    }
}
