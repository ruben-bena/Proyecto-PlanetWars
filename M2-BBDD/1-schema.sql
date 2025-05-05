CREATE TABLE Planet_stats
    (
        -- pk
        planet_id NUMBER 
                  GENERATED ALWAYS AS IDENTITY START WITH 1 -- se genera el id automáticamente y no se puede elegir a mano (ALWAYS != BY DEFAULT)
                  PRIMARY KEY,

        -- planet data
        name                      VARCHAR2( 30 ) NOT NULL,
        resource_metal_amount     NUMBER( 12, 0 ) NOT NULL,
        resource_deuterion_amount NUMBER( 12, 0 ) NOT NULL,
        technology_defense_level  NUMBER( 3,0 ) NOT NULL,
        technology_attack_level   NUMBER( 3,0 ) NOT NULL,
        battles_counter           NUMBER( 3,0 ) NOT NULL,

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
                   GENERATED ALWAYS AS IDENTITY START WITH 1 -- se genera el id automáticamente y no se puede elegir a mano (ALWAYS != BY DEFAULT)
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
                   GENERATED ALWAYS AS IDENTITY START WITH 1 -- se genera el id automáticamente y no se puede elegir a mano (ALWAYS != BY DEFAULT)
                   PRIMARY KEY,
        planet_id  NUMBER,
        num_battle NUMBER,

        log_entry VARCHAR2(4000),

        CONSTRAINT fk_battle_log_battle_stats
            FOREIGN KEY ( num_battle )
            REFERENCES Battle_stats( num_battle )
            ON DELETE CASCADE
    );

CREATE TABLE Enemy_army
    (
        -- fk
        planet_id  NUMBER NOT NULL,
        num_battle NUMBER NOT NULL,

        -- rest
        light_hunter_threat    NUMBER,
        light_hunter_destroyed NUMBER,
        heavy_hunter_threat    NUMBER,
        heavy_hunter_destroyed NUMBER,
        battleship_threat      NUMBER,
        battleship_destroyed   NUMBER,
        armored_ship_threat    NUMBER,
        armored_ship_destroyed NUMBER,

        CONSTRAINT pk_enemy_army
            PRIMARY KEY (planet_id, num_battle),

        CONSTRAINT fk_enemy_army_battle_stats
            FOREIGN KEY (planet_id, num_battle)
            REFERENCES Battle_stats (planet_id, num_battle)
    );