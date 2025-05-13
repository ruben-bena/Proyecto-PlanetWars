/*
-- DELETE ALL TABLES
drop table Battle_log;
drop table Planet_battle_defense;
drop table Planet_battle_army;
drop table Enemy_army;
drop table Battle_stats;
drop table Planet_stats;
*/

CREATE TABLE Planet_stats
    (
        -- pk
        planet_id NUMBER 
                  GENERATED ALWAYS AS IDENTITY START WITH 1
                  PRIMARY KEY,

        -- planet data
        name                      VARCHAR2( 30 )  NOT NULL,
        resource_metal_amount     NUMBER( 12, 0 ) NOT NULL,
        resource_deuterion_amount NUMBER( 12, 0 ) NOT NULL,
        technology_defense_level  NUMBER( 3,0 )   NOT NULL,
        technology_attack_level   NUMBER( 3,0 )   NOT NULL,
        battles_counter           NUMBER( 3,0 )   NOT NULL,

        -- defense units
        missile_launcher_remaining NUMBER( 5,0 ) NOT NULL,
        ion_cannon_remaining       NUMBER( 5,0 ) NOT NULL,
        plasma_cannon_remaining    NUMBER( 5,0 ) NOT NULL,

        -- attack units
        light_hunter_remaining NUMBER( 5,0 ) NOT NULL,
        heavy_hunter_remaining NUMBER( 5,0 ) NOT NULL,
        battleship_remaining   NUMBER( 5,0 ) NOT NULL,
        armored_ship_remaining NUMBER( 5,0 ) NOT NULL
    );

CREATE TABLE Battle_stats 
    (
        -- pk, fk
        num_battle NUMBER
                   GENERATED ALWAYS AS IDENTITY START WITH 1
                   PRIMARY KEY,
        planet_id  NUMBER,

        resource_metal_acquired     NUMBER( 12, 0 ) NOT NULL,
        resource_deuterion_acquired NUMBER( 12, 0 ) NOT NULL,
        CONSTRAINT fk_battle_stats_planet_stats
            FOREIGN KEY ( planet_id )
            REFERENCES Planet_stats( planet_id )
            ON DELETE CASCADE
    );

CREATE TABLE Battle_log
    (
        -- pk, fk
        num_line   NUMBER
                   GENERATED ALWAYS AS IDENTITY START WITH 1
                   PRIMARY KEY,
        num_battle NUMBER NOT NULL,

        log_entry CLOB NOT NULL,

        CONSTRAINT fk_battle_log_battle_stats
            FOREIGN KEY ( num_battle )
            REFERENCES Battle_stats( num_battle )
            ON DELETE CASCADE
    );

CREATE TABLE Enemy_army
    (
        -- pk, fk
        enemy_army_id NUMBER
                      GENERATED ALWAYS AS IDENTITY START WITH 1
                      PRIMARY KEY,
        num_battle    NUMBER NOT NULL,

        -- rest
        light_hunter_threat    NUMBER NOT NULL,
        light_hunter_destroyed NUMBER NOT NULL,
        heavy_hunter_threat    NUMBER NOT NULL,
        heavy_hunter_destroyed NUMBER NOT NULL,
        battleship_threat      NUMBER NOT NULL,
        battleship_destroyed   NUMBER NOT NULL,
        armored_ship_threat    NUMBER NOT NULL,
        armored_ship_destroyed NUMBER NOT NULL,

        CONSTRAINT fk_battle_stats_num_battle
            FOREIGN KEY (num_battle)
            REFERENCES Battle_stats (num_battle)
            ON DELETE CASCADE
    );

CREATE TABLE Planet_battle_defense
    (
        -- pk, fk
        planet_battle_defense_id NUMBER
                                 GENERATED ALWAYS AS IDENTITY START WITH 1
                                 PRIMARY KEY,
        num_battle NUMBER,

        -- rest
        missile_launcher_built     NUMBER NOT NULL,
        missile_launcher_destroyed NUMBER NOT NULL,
        ion_cannon_built           NUMBER NOT NULL,
        ion_cannon_destroyed       NUMBER NOT NULL,
        plasma_canon_built         NUMBER NOT NULL,
        plasma_canon_destroyed     NUMBER NOT NULL,

        CONSTRAINT fk_planet_battle_defense_battle_stats
            FOREIGN KEY (num_battle)
            REFERENCES Battle_stats (num_battle)
            ON DELETE CASCADE
    );

CREATE TABLE Planet_battle_army
    (
        -- pk, fk
        planet_battle_army_id NUMBER
                              GENERATED ALWAYS AS IDENTITY START WITH 1
                              PRIMARY KEY,
        num_battle    NUMBER NOT NULL,

        -- rest
        light_hunter_threat    NUMBER NOT NULL,
        light_hunter_destroyed NUMBER NOT NULL,
        heavy_hunter_threat    NUMBER NOT NULL,
        heavy_hunter_destroyed NUMBER NOT NULL,
        battleship_threat      NUMBER NOT NULL,
        battleship_destroyed   NUMBER NOT NULL,
        armored_ship_threat    NUMBER NOT NULL,
        armored_ship_destroyed NUMBER NOT NULL,

        CONSTRAINT fk_battle_battle_army_battle_stats
            FOREIGN KEY (num_battle)
            REFERENCES Battle_stats (num_battle)
            ON DELETE CASCADE
    )