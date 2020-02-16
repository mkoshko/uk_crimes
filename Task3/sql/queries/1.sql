SELECT location,
       "street name",
       period,
       sum("CrimesNumber")
FROM (
         SELECT
             location_id AS "location",
             name AS "street name",
             concat('from ',
                    min(month) over (partition by location_id),
                    ' till ',
                    max(month) over(partition by (location_id))) AS "period",
             count(*) AS "CrimesNumber"
         FROM crime
                  INNER JOIN street st ON location_id = st.id
         WHERE month BETWEEN '2019-01' AND '2019-05'
         GROUP BY location_id, name, month
     ) tb
GROUP BY location, "street name", period
ORDER BY sum("CrimesNumber") DESC;