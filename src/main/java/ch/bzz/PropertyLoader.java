package ch.bzz;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyLoader {

    public static Properties getProperties() {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("config.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return props;
    }
}
