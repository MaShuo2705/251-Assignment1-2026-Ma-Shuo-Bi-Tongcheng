package com.assignment.editor.config;

import java.util.Map;

public class EditorConfig
{
    private EditorSubConfig editor;

    public static class EditorSubConfig
    {
        private WindowConfig window;
        private AppearanceConfig appearance;
        private EncodingConfig encoding;
        private BehaviorConfig behavior;
        private SyntaxHighlightConfig syntaxHighlighting;
        private PrintConfig print;

        public WindowConfig getWindow(){return window;}
        public void setWindow(WindowConfig window){this.window=window;}
        public AppearanceConfig getAppearance(){return appearance;}
        public void setAppearance(AppearanceConfig appearance){this.appearance=appearance;}
        public EncodingConfig getEncoding(){return encoding;}
        public void setEncoding(EncodingConfig encoding){this.encoding=encoding;}
        public BehaviorConfig getBehavior(){return behavior;}
        public void setBehavior(BehaviorConfig behavior){this.behavior=behavior;}
        public SyntaxHighlightConfig getSyntaxHighlighting(){return syntaxHighlighting;}
        public void setSyntaxHighlighting(SyntaxHighlightConfig syntaxHighlighting){this.syntaxHighlighting=syntaxHighlighting;}
        public PrintConfig getPrint(){return print;}
        public void setPrint(PrintConfig print){this.print=print;}
    }

    public static class WindowConfig
    {
        private String title;
        private int width;
        private int height;
        private boolean resizable;

        public String getTitle(){return title;}
        public void setTitle(String title){this.title=title;}
        public int getWidth(){return width;}
        public void setWidth(int width){this.width=width;}
        public int getHeight(){return height;}
        public void setHeight(int height){this.height=height;}
        public boolean isResizable(){return resizable;}
        public void setResizable(boolean resizable){this.resizable=resizable;}
    }

    public static class AppearanceConfig
    {
        private String fontFamily;
        private int fontSize;
        private String fontStyle;
        private String backgroundColor;
        private String foregroundColor;
        private boolean lineWrap;
        private boolean showLineNumbers;

        public String getFontFamily(){return fontFamily;}
        public void setFontFamily(String fontFamily) {this.fontFamily=fontFamily;}
        public int getFontSize(){return fontSize;}
        public void setFontSize(int fontSize){this.fontSize=fontSize;}
        public String getFontStyle(){return fontStyle;}
        public void setFontStyle(String fontStyle){this.fontStyle=fontStyle;}
        public String getBackgroundColor(){return backgroundColor;}
        public void setBackgroundColor(String backgroundColor){this.backgroundColor=backgroundColor;}
        public String getForegroundColor(){return foregroundColor;}
        public void setForegroundColor(String foregroundColor){this.foregroundColor=foregroundColor;}
        public boolean isLineWrap(){return lineWrap;}
        public void setLineWrap(boolean lineWrap){this.lineWrap=lineWrap;}
        public boolean isShowLineNumbers(){return showLineNumbers;}
        public void setShowLineNumbers(boolean showLineNumbers){this.showLineNumbers=showLineNumbers;}
    }

    public static class EncodingConfig
    {
        private String defaultCharset;
        private String fallbackCharset;

        public String getDefaultCharset(){return defaultCharset;}
        public void setDefaultCharset(String defaultCharset){this.defaultCharset=defaultCharset;}
        public String getFallbackCharset(){return fallbackCharset;}
        public void setFallbackCharset(String fallbackCharset){this.fallbackCharset=fallbackCharset;}
    }

    public static class BehaviorConfig
    {
        private int autoSaveInterval;
        private int maxRecentFiles;
        private boolean confirmExit;

        public int getAutoSaveInterval(){return autoSaveInterval;}
        public void setAutoSaveInterval(int autoSaveInterval){this.autoSaveInterval=autoSaveInterval;}
        public int getMaxRecentFiles(){return maxRecentFiles;}
        public void setMaxRecentFiles(int maxRecentFiles){this.maxRecentFiles=maxRecentFiles;}
        public boolean isConfirmExit(){return confirmExit;}
        public void setConfirmExit(boolean confirmExit){this.confirmExit=confirmExit;}
    }

    public static class SyntaxHighlightConfig
    {
        private boolean enabled;
        private Map<String,String> colors;

        public boolean isEnabled(){return enabled;}
        public void setEnabled(boolean enabled){this.enabled=enabled;}
        public Map<String,String>getColors(){return colors;}
        public void setColors(Map<String,String> colors){this.colors=colors;}
    }

    public static class PrintConfig
    {
        private Map<String,Double>pageMargins;
        private boolean headerEnabled;
        private boolean footerEnabled;

        public Map<String,Double>getPageMargins(){return pageMargins;}
        public void setPageMargins(Map<String, Double> pageMargins){this.pageMargins=pageMargins;}
        public boolean isHeaderEnabled(){return headerEnabled;}
        public void setHeaderEnabled(boolean headerEnabled){this.headerEnabled=headerEnabled;}
        public boolean isFooterEnabled(){return footerEnabled;}
        public void setFooterEnabled(boolean footerEnabled){this.footerEnabled=footerEnabled;}
    }

    public EditorSubConfig getEditor(){return editor;}
    public void setEditor(EditorSubConfig editor){this.editor=editor;}
}
