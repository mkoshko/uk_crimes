SELECT DISTINCT officer_defined_ethnicity                                                                                AS "Oficer Defined Ethnicity",
                COUNT(*)
                OVER (PARTITION BY officer_defined_ethnicity, date_part('Month', datetime), date_part('Year', datetime)) AS "Total number of occurrences",

FROM stop_and_search ss
         INNER JOIN outcome_object outcome ON ss.outcome_object_id = outcome.id
WHERE officer_defined_ethnicity != ''
  AND datetime >= '2019-01-01'
  AND datetime < '2019-06-01'
ORDER BY officer_defined_ethnicity;



SELECT DISTINCT to_char(datetime, 'Month')                                                                               AS "Month",
                officer_defined_ethnicity                                                                                AS "Oficer Defined Ethnicity",
                COUNT(*)
                OVER (PARTITION BY officer_defined_ethnicity, date_part('Month', datetime), date_part('Year', datetime)) AS "Total number of occurrences"
FROM stop_and_search ss
         INNER JOIN outcome_object outcome ON ss.outcome_object_id = outcome.id
WHERE officer_defined_ethnicity != ''
  AND datetime >= '2019-01-01'
  AND datetime < '2019-06-01'
ORDER BY officer_defined_ethnicity, to_char(datetime, 'Month');

