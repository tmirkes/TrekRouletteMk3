package persistence;

import entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class EpisodeListExtractor {
    private final Logger logger = LogManager.getLogger(this.getClass());

    public void getUserOwnList(User currentUser) {
        //logger.info(currentUser.getOwnsById());
        List<Own> owns = new ArrayList<>();
        owns = currentUser.getOwnsById();
        logger.info(owns);
    }
}