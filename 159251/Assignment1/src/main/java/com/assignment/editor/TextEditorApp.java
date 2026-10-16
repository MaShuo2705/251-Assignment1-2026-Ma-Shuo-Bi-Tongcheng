package com.assignment.editor;
import com.assignment.editor.config.*;
public class TextEditorApp
{
    public static void main(String[]args)
    {
        ConfigManager configManager=ConfigManager.getInstance();
        EditorConfig.EditorSubConfig config=configManager.getConfig();
        System.out.println("Window title: "+config.getWindow().getTitle());
        System.out.println("Font size: "+config.getAppearance().getFontSize());
        System.out.println("Default charset: "+config.getEncoding().getDefaultCharset());
    }
}