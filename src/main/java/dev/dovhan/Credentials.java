package dev.dovhan;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Credentials {
        public static String dbUsername;
        public static String dbPassword;
        public static String dbURL;

        public Credentials() throws IOException {
            List<String> lines = Files.readAllLines(Path.of("D:", "credentialsimporter"));
            dbUsername = lines.get(0);
            dbPassword = lines.get(1);
            dbURL = lines.get(2);
        }
}