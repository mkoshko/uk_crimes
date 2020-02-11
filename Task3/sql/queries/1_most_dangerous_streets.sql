SELECT
       street_id AS "StreetID",
       name AS "StreetName",
       count(*) AS "CrimesNumber"
FROM crime
INNER JOIN location ON crime.location_id = location.street_id
INNER JOIN street street_table ON location.street_id = street_table.id
GROUP BY street_id, name
ORDER BY count(*) DESC;