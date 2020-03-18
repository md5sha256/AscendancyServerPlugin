package com.gmail.andrewandy.ascendencyserverplugin.util;

import com.gmail.andrewandy.ascendencyserverplugin.AscendencyServerPlugin;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;

import java.io.*;

public class YamlLoader {

    private YAMLConfigurationLoader loader;

    private YamlLoader() {
    }

    public YamlLoader(String fileName) {
        File folder = AscendencyServerPlugin.getInstance().getDataFolder();
        File file = new File(folder.getAbsolutePath(), fileName);
        OutputStream os;
        try (InputStream inputStream = YamlLoader.class.getResourceAsStream(fileName);) {
            if (!file.isFile()) {
                file.createNewFile();
            }
            os = new FileOutputStream(file);
            os.write(Common.readFromStream(inputStream));
            os.close();
        } catch (IOException ex) {
            throw new IllegalStateException("Unnable to load or copy file from jar!", ex);
        }
        loader = YAMLConfigurationLoader.builder().setFile(file).build();
    }

    public YAMLConfigurationLoader getLoader() {
        return loader;
    }

}
