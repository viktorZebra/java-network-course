import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class URLReaderTest {

    @Test
    public void getInfoSuccessTest() {
        assertDoesNotThrow(() -> URLReader.getInfo("https://sber.ru/"));
    }

    @Test
    public void getInfoFailTest() {
        assertThrows(IOException.class, () -> URLReader.getInfo("test.test"));
    }

}