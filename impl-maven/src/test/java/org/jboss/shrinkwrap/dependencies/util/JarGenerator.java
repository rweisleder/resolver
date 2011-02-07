/**
 * 
 */
package org.jboss.shrinkwrap.dependencies.util;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * An utility to generate artifact jars
 * 
 * @author <a href="mailto:kpiwko@redhat.com>Karel Piwko</a>
 * 
 */
@RunWith(Parameterized.class)
public class JarGenerator
{
   private String name;
   private Class<?>[] content;

   @Parameters
   public static Collection<Object[]> jars()
   {
      Object[][] data = new Object[][] {
            { "test-managed-dependency", new Class<?>[] { Object.class, List.class}},
            { "test-managed-dependency-2", new Class<?>[] { List.class}},
            { "test-dependency", new Class<?>[] { Arrays.class }},
            { "test-dependency-with-exclusion", new Class<?>[] { Collections.class}},
            { "test-exclusion", new Class<?>[] { ArrayList.class, LinkedList.class}},            
            { "test-dependency-provided", new Class<?>[] { List.class, Map.class}},
            { "test-dependency-test", new Class<?>[] { ArrayList.class, HashMap.class}},
            { "test-parent", new Class<?>[] { File.class }},
            { "test-child", new Class<?>[] { InputStream.class }}, 
            { "test-remote-parent", new Class<?>[] {OutputStream.class }},
            { "test-deps-a", new Class<?>[] {System.class }},
            { "test-deps-b", new Class<?>[] {Field.class }},
            { "test-deps-c", new Class<?>[] {Integer.class }},
            { "test-deps-d", new Class<?>[] {Float.class, Double.class }},
            { "test-deps-e", new Class<?>[] {String.class, StringBuilder.class }},
            { "test-deps-f", new Class<?>[] {Thread.class }},
            { "test-deps-g", new Class<?>[] {Object.class, String.class }},
            { "test-deps-h", new Class<?>[] {Character.class, Byte.class }},
            { "test-deps-i", new Class<?>[] {System.class, PrintStream.class }},
      };

      return Arrays.asList(data);
   }

   public JarGenerator(String name, Class<?>[] content)
   {
      this.name = name;
      this.content = content;
   }

   @Test
   public void createJars()
   {
      JavaArchive archive = ShrinkWrap.create(JavaArchive.class, name).addClasses(content);

      archive.as(ZipExporter.class).exportTo(new File("target/" + name + ".jar"), true);
   }

}