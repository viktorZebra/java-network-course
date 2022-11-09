import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class lesson2 {
    private static final Logger log = LoggerFactory.getLogger(lesson2.class);

    public static void main(String[] args){

        try {

            if (args.length == 0) {
                log.warn("Не передан адрес HTTP ресурса в качестве аргумента коммандной строки");
                return;
            }

            URLReader.getInfo(args[0]);

        } catch (IOException exception) {
            log.error(exception.getMessage());
        }

    }
}
