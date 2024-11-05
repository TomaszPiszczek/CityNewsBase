CREATE TABLE news_city (
                           news_id UUID NOT NULL,
                           city_id UUID NOT NULL,
                           PRIMARY KEY (news_id, city_id),
                           FOREIGN KEY (news_id) REFERENCES news(id) ON DELETE CASCADE,
                           FOREIGN KEY (city_id) REFERENCES city(id) ON DELETE CASCADE
);

CREATE TABLE news_state (
                            news_id UUID NOT NULL,
                            state_id UUID NOT NULL,
                            PRIMARY KEY (news_id, state_id),
                            FOREIGN KEY (news_id) REFERENCES news(id) ON DELETE CASCADE,
                            FOREIGN KEY (state_id) REFERENCES state(id) ON DELETE CASCADE
);

ALTER TABLE news DROP COLUMN city_id;
ALTER TABLE news DROP COLUMN state_id;
