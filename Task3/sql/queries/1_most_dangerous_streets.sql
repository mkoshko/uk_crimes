SELECT
    street_id AS "StreetID",
    name AS "StreetName",
    count(*) AS "CrimesNumber"
FROM crime
         INNER JOIN location ON crime.location_id = location.street_id
         INNER JOIN street street_table ON location.street_id = street_table.id
WHERE month >= '2019-01' AND month <= '2019-05'
GROUP BY street_id, name
ORDER BY count(*) DESC;