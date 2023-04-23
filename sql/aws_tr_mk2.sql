-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2023-03-29 16:46:13.111

-- foreign keys
ALTER TABLE episode
    DROP FOREIGN KEY episode_season;

ALTER TABLE own
    DROP FOREIGN KEY own_season;

ALTER TABLE own
    DROP FOREIGN KEY own_user;

ALTER TABLE view
    DROP FOREIGN KEY view_episode;

ALTER TABLE view
    DROP FOREIGN KEY view_status;

ALTER TABLE view
    DROP FOREIGN KEY view_user;

-- tables
DROP TABLE episode;

DROP TABLE own;

DROP TABLE season;

DROP TABLE status;

DROP TABLE user;

DROP TABLE view;

-- End of file.



-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2023-03-29 16:46:13.111

-- tables
-- Table: episode
CREATE TABLE episode (
    id int  NOT NULL AUTO_INCREMENT,
    title varchar(100)  NOT NULL,
    stapi_episode_id varchar(50)  NOT NULL,
    season_id int  NOT NULL,
    CONSTRAINT episode_pk PRIMARY KEY (id)
);

-- Table: own
CREATE TABLE own (
    id int  NOT NULL AUTO_INCREMENT,
    user_id int  NOT NULL,
    season_id int  NOT NULL,
    CONSTRAINT own_pk PRIMARY KEY (id)
);

-- Table: season
CREATE TABLE season (
    id int  NOT NULL AUTO_INCREMENT,
    series varchar(50)  NOT NULL,
    season int  NOT NULL,
    stapi_season_id varchar(100)  NOT NULL,
    CONSTRAINT season_pk PRIMARY KEY (id)
);

-- Table: status
CREATE TABLE status (
    id int  NOT NULL AUTO_INCREMENT,
    status_title varchar(25)  NOT NULL,
    CONSTRAINT status_pk PRIMARY KEY (id)
);

-- Table: user
CREATE TABLE user (
    id int  NOT NULL AUTO_INCREMENT,
    user_name varchar(30)  NOT NULL,
    first_name varchar(30)  NOT NULL,
    last_name varchar(30)  NOT NULL,
    last_login datetime  NULL,
    CONSTRAINT user_pk PRIMARY KEY (id)
);

-- Table: view
CREATE TABLE view (
    id int  NOT NULL AUTO_INCREMENT,
    view_date datetime  NOT NULL,
    user_id int  NOT NULL,
    episode_id int  NOT NULL,
    status_id int  NOT NULL,
    CONSTRAINT view_pk PRIMARY KEY (id)
) COMMENT 'Linking table between user and episode';

-- foreign keys
-- Reference: episode_season (table: episode)
ALTER TABLE episode ADD CONSTRAINT episode_season FOREIGN KEY episode_season (season_id)
    REFERENCES season (id);

-- Reference: own_season (table: own)
ALTER TABLE own ADD CONSTRAINT own_season FOREIGN KEY own_season (season_id)
    REFERENCES season (id);

-- Reference: own_user (table: own)
ALTER TABLE own ADD CONSTRAINT own_user FOREIGN KEY own_user (user_id)
    REFERENCES user (id);

-- Reference: view_episode (table: view)
ALTER TABLE view ADD CONSTRAINT view_episode FOREIGN KEY view_episode (episode_id)
    REFERENCES episode (id);

-- Reference: view_status (table: view)
ALTER TABLE view ADD CONSTRAINT view_status FOREIGN KEY view_status (status_id)
    REFERENCES status (id);

-- Reference: view_user (table: view)
ALTER TABLE view ADD CONSTRAINT view_user FOREIGN KEY view_user (user_id)
    REFERENCES user (id);

-- End of file.

