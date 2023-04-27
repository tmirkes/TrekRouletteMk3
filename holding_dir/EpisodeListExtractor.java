package persistence;

import entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Set;

public class EpisodeListExtractor {
    private final Logger logger = LogManager.getLogger(this.getClass());

    public void getUserOwnList(User currentUser) {
        //logger.info(currentUser.getOwnsById());
//        Set<Own> owns = new ArrayList<>();
//        owns = currentUser.getOwnsById();
//        logger.info(owns);
    }
}