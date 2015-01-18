
package com.tsystems.javaschool.tasks;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Prigodich Nikolay
 */
public class DuplicateFinderImpl implements DuplicateFinder
{
    public static void main(String[] args)
    {
        DuplicateFinder d = new DuplicateFinderImpl();
        System.out.println(d.process(new File("a.txt"), new File("b.txt")));
    }


    @Override
    public boolean process(File sourceFile, File targetFile)
    {
        Map<String, Integer> lines = readFile(sourceFile);
        if (lines == null) return false;

        String[] content = PrepareContent(lines);
        return writeFile(targetFile, content);
    }




    private Map<String, Integer> readFile(File basefile)
    {
        Map<String, Integer> lines = new HashMap<String, Integer>();
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(basefile));
            String s;
            while ((s = br.readLine()) != null)
            {
                if (lines.containsKey(s))
                {
                    Integer count = lines.get(s);
                    count++;
                    lines.put(s, count);
                }
                else
                {
                    lines.put(s, 1);
                }
            }
            br.close();
            return lines;
        }
        catch (IOException ex)
        {
            return null;
        }
    }

    private String[] PrepareContent(Map<String, Integer> lines)
    {
        String[] content = new String[lines.size()];
        SortedSet<String> keys = new TreeSet<String>(lines.keySet());
        int index = 0;
        for (String key : keys)
        {
            Integer value = lines.get(key);
            content[index] = key + "[" + value + "]";
            index++;
        }
        return content;
    }

    private boolean writeFile(File targetFile, String[] content)
    {
        try
        {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(targetFile, true)));
            for (String line : content)
            {
                out.println(line);
            }
            out.close();
            return true;
        }
        catch (IOException ex)
        {
            return false;
        }
    }
}
