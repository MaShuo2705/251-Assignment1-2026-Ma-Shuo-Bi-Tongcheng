package com.assignment.editor.config;
import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
public class ConfigManager
{
    private static volatile ConfigManager instance;
    private EditorConfig editorConfig;
    private ConfigManager()
    {
        loadConfig();
    }
    public static ConfigManager getInstance()
    {
        if(instance==null)
        {
            synchronized(ConfigManager.class)
            {
                if (instance==null)
                {
                    instance=new ConfigManager();
                }
            }
        }
        return instance;
    }
    private void loadConfig()
    {
        Yaml yaml=new Yaml();
        try(InputStream inputStream=this.getClass().getClassLoader().getResourceAsStream("default-config.yaml")) 
        {
            if(inputStream==null) 
            {
                System.err.println("Warning: Default configuration file not found, built-in default parameters will be used.");
                useFallbackConfig();
                return;
            }
            this.editorConfig=yaml.loadAs(inputStream, EditorConfig.class);
            System.out.println("Configuration file loaded successfully");
        } 
        catch (Exception e)
        {
            System.err.println("Configuration file parsing failed, built-in default parameters will be used." + e.getMessage());
            useFallbackConfig();
        }
    }
    private void useFallbackConfig()
    {
        EditorConfig.EditorSubConfig editor=new EditorConfig.EditorSubConfig();
        EditorConfig.WindowConfig window=new EditorConfig.WindowConfig();
        window.setTitle("Java Text Editor");
        window.setWidth(800);
        window.setHeight(600);
        window.setResizable(true);
        editor.setWindow(window);
        EditorConfig.AppearanceConfig appearance=new EditorConfig.AppearanceConfig();
        appearance.setFontFamily("Monospaced");
        appearance.setFontSize(14);
        appearance.setBackgroundColor("#FFFFFF");
        appearance.setForegroundColor("#000000");
        editor.setAppearance(appearance);
        this.editorConfig=new EditorConfig();
        this.editorConfig.setEditor(editor);
    }
    public EditorConfig.EditorSubConfig getConfig()
    {
        return editorConfig.getEditor();
    }
}