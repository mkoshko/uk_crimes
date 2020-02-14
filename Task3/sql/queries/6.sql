WITH crimes AS (
    SELECT location_id,
           category,
           month AS "Month",
           count(*) OVER (PARTITION BY location_id, category, month) AS "CRCount"
    FROM crime
    WHERE (category='drugs'
        OR category='possession-of-weapons'
        OR category='theft-from-the-person'
        OR category='shoplifting')
      AND month BETWEEN '2019-01' AND '2019-06'
      AND location_id IS NOT NULL
    ORDER BY month
), stopAndSearch AS (
    SELECT location_id,
           object_of_search,
           to_char(datetime, 'yyyy-MM') AS "Month",
           count(*) OVER (PARTITION BY location_id, object_of_search, to_char(datetime, 'yyyy-MM')) as "SSCount"
    FROM stop_and_search
    WHERE location_id IS NOT NULL
      AND to_char(datetime, 'yyyy-MM') BETWEEN '2019-01' AND '2019-05'
      AND (object_of_search='Controlled drugs'
        OR object_of_search='Firearms'
        OR object_of_search='Offensive weapons'
        OR object_of_search='Stolen goods')
    ORDER BY datetime
), drugs_to_controlled_drugs AS (
    SELECT DISTINCT crimes.location_id,
                    "CRCount" AS "drugs",
                    "SSCount" AS "Controlled drugs", crimes."Month"
    FROM crimes
             INNER JOIN stopAndSearch ON crimes.location_id = stopAndSearch.location_id
    WHERE category = 'drugs'
      AND object_of_search = 'Controlled drugs'
      AND crimes."Month" = stopAndSearch."Month"
), pow_to_of_frms AS (
    SELECT DISTINCT crimes.location_id,
                    "CRCount" AS "Possession of weapons",
                    SUM("SSCount") OVER (PARTITION BY crimes.location_id, "CRCount", crimes."Month") AS "offensive weapons/firearms",
                    crimes."Month"
    FROM crimes
             INNER JOIN stopAndSearch ON crimes.location_id = stopAndSearch.location_id
    WHERE category = 'possession-of-weapons'
      AND (object_of_search = 'Offensive weapons' OR object_of_search = 'Firearms')
      AND crimes."Month" = stopAndSearch."Month"
    GROUP BY crimes.location_id, "CRCount", "SSCount", crimes."Month"
), tftp_sh_to_sg AS (
    SELECT DISTINCT crimes.location_id,
                    SUM("CRCount")
                    OVER (PARTITION BY crimes.location_id, "SSCount", crimes."Month") AS "theft from the person/shoplifting",
                    "SSCount"                                                         AS "stolen goods",
                    crimes."Month"
    FROM crimes
             INNER JOIN stopAndSearch ON crimes.location_id = stopAndSearch.location_id
    WHERE (category = 'theft-from-the-person' OR category = 'shoplifting')
      AND object_of_search = 'Stolen goods'
      AND crimes."Month" = stopAndSearch."Month"
    GROUP BY crimes.location_id, "CRCount", "SSCount", crimes."Month"
)
SELECT street_id,
       "drugs",
       "Controlled drugs",
       "Possession of weapons",
       "offensive weapons/firearms",
       "theft from the person/shoplifting",
       "stolen goods"
FROM location
         FULL OUTER JOIN drugs_to_controlled_drugs d ON street_id = d.location_id
         FULL OUTER JOIN pow_to_of_frms p ON street_id = p.location_id
         FULL OUTER JOIN tftp_sh_to_sg t ON street_id = t.location_id
WHERE street_id = d.location_id
   OR street_id = p.location_id
   OR street_id = t.location_id

