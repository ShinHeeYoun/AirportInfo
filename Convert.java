import java.nio.file.*;
import java.nio.charset.*;
public class Convert {
    public static void main(String[] args) throws Exception {
        Path p = Paths.get("src/main/resources/airport_data.csv");
        byte[] bytes = Files.readAllBytes(p);
        String s = new String(bytes, Charset.forName("EUC-KR"));
        Files.write(p, s.getBytes(StandardCharsets.UTF_8));
    }
}
