WITH crimes AS (
    SELECT location_id,
           category,
           month
    FROM crime
    WHERE (category = 'drugs'
       OR category = 'possession-of-weapons'
       OR category = 'theft-FROM-the-person'
       OR category = 'shoplifting')
      AND (month BETWEEN '2019-01' AND '2019-06')
      AND location_id IS NOT NULL
), stop_search AS (
    SELECT location_id,
           object_of_search,
           to_char(datetime, 'yyyy-MM') AS "date"
    FROM stop_and_search
    WHERE (object_of_search = 'Controlled drugs'
       OR object_of_search = 'Firearms'
       OR object_of_search = 'Offensive weapons'
       OR object_of_search = 'Stolen goods')
      AND (to_char(datetime, 'yyyy-MM') BETWEEN '2019-01' AND '2019-06')
      AND outcome = 'Arrest'
      AND location_id IS NOT NULL
), data AS (
    SELECT distinct location_id,
                    month
    FROM crimes
    UNION DISTINCT
    SELECT location_id,
           "date"
    FROM stop_search
)
SELECT d.location_id,
       name,
       d.month,
       CRDrugs.drugs,
       SSDrugs."controlled drugs",
       CRWeapons."possessiON of weapons",
       SSWeapons."Offensive weapons/Firearms",
       CRTheft."theft FROM the person/shoplifting",
       SSTheft."Stolen goods"
FROM "data" d
LEFT JOIN (
    SELECT location_id,
           month,
           count(*) AS "drugs"
    FROM crimes
    WHERE category='drugs'
    GROUP BY location_id, month
) AS CRDrugs ON d.location_id=CRDrugs.location_id AND d.month = CRDrugs.month
LEFT JOIN street ON d.location_id = street.id
LEFT JOIN (
    SELECT location_id,
           "date",
           count(*) AS "controlled drugs"
    FROM stop_search
    WHERE object_of_search='Controlled drugs'
    GROUP BY location_id, "date"
) SSDrugs ON d.location_id = SSDrugs.location_id AND d.month = SSDrugs.date
         LEFT JOIN (
    SELECT location_id,
           month,
           count(*) AS "possessiON of weapons"
    FROM crimes
    WHERE category='possession-of-weapons'
    GROUP BY location_id, month
) CRWeapons ON d.location_id = CRWeapons.location_id AND d.month = CRWeapons.month
LEFT JOIN (
    SELECT location_id,
           "date",
           count(*) AS "Offensive weapons/Firearms"
    FROM stop_search
    WHERE object_of_search='Offensive weapons'
       OR object_of_search='Firearms'
    GROUP BY location_id, "date"
) SSWeapons ON d.location_id = SSWeapons.location_id AND d.month = SSWeapons.date
LEFT JOIN (
    SELECT location_id,
           month,
           count(*) AS "theft FROM the person/shoplifting"
    FROM crimes
    WHERE category='theft-FROM-the-person'
       OR category='shoplifting'
    GROUP BY location_id, month
) CRTheft ON d.location_id = CRTheft.location_id AND d.month = CRTheft.month
LEFT JOIN (
    SELECT location_id,
           "date",
           count(*) AS "Stolen goods"
    FROM stop_search
    WHERE object_of_search='Stolen goods'
    GROUP BY location_id, "date"
) SSTheft ON d.location_id = SSTheft.location_id AND d.month = SSTheft.date
ORDER BY location_id;