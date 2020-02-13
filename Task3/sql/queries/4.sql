WITH arr AS (
    SELECT officer_defined_ethnicity, count(*) AS "Arrests"
    FROM stop_and_search
    WHERE officer_defined_ethnicity <> ''
      AND outcome = 'Arrest'
    GROUP BY officer_defined_ethnicity
),
     no_act AS (
         SELECT officer_defined_ethnicity, count(*) AS "No action"
         FROM stop_and_search
         WHERE officer_defined_ethnicity <> ''
           AND outcome = 'A no further action disposal'
         GROUP BY officer_defined_ethnicity
     ),
     other AS (
         SELECT officer_defined_ethnicity, count(*) AS "Others"
         FROM stop_and_search
         WHERE officer_defined_ethnicity <> ''
           AND outcome <> 'A no further action disposal'
           AND outcome <> 'Arrest'
           AND outcome <> ''
         GROUP BY officer_defined_ethnicity
     ),
     mp_obj AS (
         SELECT officer_defined_ethnicity, object_of_search
         FROM (
                  SELECT officer_defined_ethnicity, object_of_search, max(count(*)) over (partition by officer_defined_ethnicity) as "MAX", count(*) as "Total"
                  FROM stop_and_search
                  WHERE officer_defined_ethnicity <> ''
                    AND object_of_search <> ''
                  group by officer_defined_ethnicity, object_of_search
              ) AS SMTH
         where "Total" = "MAX"
         group by officer_defined_ethnicity, object_of_search
     )
SELECT DISTINCT ss.officer_defined_ethnicity AS "Ethnicity",
                count(*) OVER (PARTITION BY ss.officer_defined_ethnicity ORDER BY ss.officer_defined_ethnicity) AS "Total",
                ROUND(((arr."Arrests" / count(*) OVER (PARTITION BY ss.officer_defined_ethnicity ORDER BY ss.officer_defined_ethnicity)::numeric) * 100), 2) AS "Arrest rate",
                ROUND(((no_act."No action" / count(*) OVER (PARTITION BY ss.officer_defined_ethnicity ORDER BY ss.officer_defined_ethnicity)::numeric) * 100), 2) AS "No action rate",
                ROUND(((other."Others" / count(*) OVER (PARTITION BY ss.officer_defined_ethnicity ORDER BY ss.officer_defined_ethnicity)::numeric) * 100), 2) AS "Other outcomes rate",
                mp_obj.object_of_search
FROM stop_and_search ss
         INNER JOIN arr ON arr.officer_defined_ethnicity = ss.officer_defined_ethnicity
         INNER JOIN no_act ON no_act.officer_defined_ethnicity = ss.officer_defined_ethnicity
         INNER JOIN other ON other.officer_defined_ethnicity = ss.officer_defined_ethnicity
         INNER JOIN mp_obj ON mp_obj.officer_defined_ethnicity = ss.officer_defined_ethnicity
WHERE datetime BETWEEN '2019-01-01' AND '2019-06-01';