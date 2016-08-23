package com.shang.layoutformat;

import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.ArrayList;

public class Files {

    public static boolean isXmlFileOrDir(@Nullable VirtualFile file) {
        return file != null && (file.getName().endsWith(".xml") || file.isDirectory());
    }


    public static String getContent(VirtualFile file) {
        final String contents;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
            String currentLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((currentLine = br.readLine()) != null) {
                stringBuilder.append(currentLine);
                stringBuilder.append("\n");
            }
            contents = stringBuilder.toString();
        } catch (IOException e1) {
            return null;
        }
        return contents;
    }


    public static ArrayList<String> getContentLines(File file) {
        ArrayList<String> lines = new ArrayList<String>();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
            return lines;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
            return lines;
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
