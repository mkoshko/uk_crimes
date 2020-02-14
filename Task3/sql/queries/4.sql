WITH "all" AS (
    SELECT officer_defined_ethnicity, outcome, COUNT(*) AS "Total", SUM(COUNT(*)) OVER (PARTITION BY officer_defined_ethnicity) AS "sum"
    FROM stop_and_search
    WHERE officer_defined_ethnicity <> ''
      AND outcome <> ''
      AND datetime BETWEEN '2019-01-01' AND '2019-06-01'
    GROUP BY officer_defined_ethnicity, outcome
), arr AS (
    SELECT officer_defined_ethnicity,
           ROUND(("Total"::numeric / sum) * 100, 2) AS "Arrest rate"
    FROM "all"
    WHERE outcome='Arrest'
), no_act AS (
    SELECT officer_defined_ethnicity,
           ROUND(("Total"::numeric / sum) * 100, 2) AS "No action rate"
    FROM "all"
    WHERE outcome='A no further action disposal'
), other AS (
    SELECT officer_defined_ethnicity,
           ROUND(((sum("Total") / sum::numeric) * 100), 2) AS "Other actions rate"
    FROM "all"
    WHERE outcome<>'A no further action disposal'
      AND outcome<>'Arrest'
    GROUP BY officer_defined_ethnicity, sum
), mp_obj AS (
    SELECT officer_defined_ethnicity, object_of_search
    FROM (
             SELECT officer_defined_ethnicity, object_of_search, max(count(*)) over (partition by officer_defined_ethnicity) as "MAX", count(*) as "Total"
             FROM stop_and_search
             WHERE officer_defined_ethnicity <> ''
               AND object_of_search <> ''
             GROUP BY officer_defined_ethnicity, object_of_search
         ) AS mpobj
    WHERE "Total" = "MAX"
)
SELECT DISTINCT "all".officer_defined_ethnicity, "Arrest rate", "No action rate", "Other actions rate", object_of_search
FROM "all"
         INNER JOIN arr ON "all".officer_defined_ethnicity = arr.officer_defined_ethnicity
         INNER JOIN no_act ON "all".officer_defined_ethnicity = no_act.officer_defined_ethnicity
         INNER JOIN other ON "all".officer_defined_ethnicity = other.officer_defined_ethnicity
         INNER JOIN mp_obj ON "all".officer_defined_ethnicity = mp_obj.officer_defined_ethnicity