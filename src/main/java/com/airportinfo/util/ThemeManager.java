package com.airportinfo.util;

import com.airportinfo.view.AppTheme;
import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.MaterialLiteTheme;
import mdlaf.themes.MaterialOceanicTheme;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * Handle theme colors. Singleton
 *
 * @author lalaalal
 */
public class ThemeManager {
    public static final LookAndFeel LITE_THEME = new MaterialLookAndFeel(new MaterialLiteTheme());
    public static final LookAndFeel DARK_THEME = new MaterialLookAndFeel(new MaterialOceanicTheme());
    private static ThemeManager instance;
    public AppTheme currentTheme;
    private final HashMap<String, Color> liteColor = new HashMap<>();
    private final HashMap<String, Color> darkColor = new HashMap<>();

    /**
     * Ensure only one instance exists.
     *
     * @return Instance of ThemeManager
     */
    public static ThemeManager getInstance() {
        if (instance == null)
            return instance = new ThemeManager();
        return instance;
    }

    /**
     * Get current theme default color.
     *
     * @param key Color key
     * @return Default color
     */
    public static Color getDefaultColor(String key) {
        return UIManager.getDefaults().getColor(key);
    }

    /**
     * Get default theme color.
     *
     * @param lookAndFeel Theme
     * @param key         Color key
     * @return Default color
     */
    public static Color getDefaultColor(LookAndFeel lookAndFeel, String key) {
        return lookAndFeel.getDefaults().getColor(key);
    }

    private ThemeManager() {
        addColor(AppTheme.Lite, "Custom.disabledForeground", Color.decode("#b8b8b8"));
        addColor(AppTheme.Dark, "Custom.disabledForeground", ThemeManager.getDefaultColor(DARK_THEME, "Label.disabledForeground"));

        addColor(AppTheme.Lite, "Toolbar.background", Color.decode("#636363"));
        addColor(AppTheme.Lite, "Toolbar.foreground", ThemeManager.getDefaultColor(ThemeManager.LITE_THEME, "Label.background"));
        addColor(AppTheme.Dark, "Toolbar.background", Color.decode("#32424A"));
        addColor(AppTheme.Dark, "Toolbar.foreground", ThemeManager.getDefaultColor(ThemeManager.DARK_THEME, "Label.foreground"));

        addColor(AppTheme.Lite, "MenuBar.background", Color.decode("#525252"));
        addColor(AppTheme.Lite, "MenuBar.foreground", ThemeManager.getDefaultColor(ThemeManager.LITE_THEME, "Label.background"));
        addColor(AppTheme.Lite, "MenuItem.hoverBackground", Color.decode("#393939"));
        addColor(AppTheme.Dark, "MenuBar.background", Color.decode("#2A383F"));
        addColor(AppTheme.Dark, "MenuBar.foreground", ThemeManager.getDefaultColor(ThemeManager.DARK_THEME, "Label.foreground"));
        addColor(AppTheme.Dark, "MenuItem.hoverBackground", Color.decode("#212C32"));

        addColor(AppTheme.Lite, "Tab.background", Color.decode("#494949"));
        addColor(AppTheme.Lite, "Tab.foreground", ThemeManager.getDefaultColor(ThemeManager.LITE_THEME, "Label.background"));
        addColor(AppTheme.Lite, "Tab.selectionBackground", Color.decode("#393939"));
        addColor(AppTheme.Dark, "Tab.background", Color.decode("#27343B"));
        addColor(AppTheme.Dark, "Tab.foreground", ThemeManager.getDefaultColor(ThemeManager.DARK_THEME, "Label.foreground"));
        addColor(AppTheme.Dark, "Tab.selectionBackground", Color.decode("#212C32"));

        addColor(AppTheme.Lite, "Separator.background", ThemeManager.getDefaultColor(ThemeManager.LITE_THEME, "Label.background"));
        addColor(AppTheme.Lite, "Separator.foreground", Color.decode("#A3A3A3"));
        addColor(AppTheme.Dark, "Separator.background", ThemeManager.getDefaultColor(ThemeManager.DARK_THEME, "Label.background"));
        addColor(AppTheme.Dark, "Separator.foreground", Color.decode("#546f7c"));
        
        addColor(AppTheme.Lite, "Table.hoverBackground", Color.decode("#E8F0FE"));
        addColor(AppTheme.Dark, "Table.hoverBackground", Color.decode("#304048"));
    }

    /**
     * Add theme color.
     *
     * @param theme Application Theme
     * @param key   Color key
     * @param color Color
     */
    public void addColor(AppTheme theme, String key, Color color) {
        if (theme == AppTheme.Lite)
            liteColor.put(key, color);
        else
            darkColor.put(key, color);
    }

    /**
     * Get defined color or theme default color.
     *
     * @param key Color key
     * @return Color
     */
    public Color getColor(String key) {
        if (currentTheme == AppTheme.Lite && liteColor.containsKey(key))
            return liteColor.get(key);
        if (currentTheme == AppTheme.Dark && darkColor.containsKey(key))
            return darkColor.get(key);
        return getDefaultColor(key);
    }
}
