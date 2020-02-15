SELECT DISTINCT
    s.id,
    s.name,
    cn.category_name,
    COUNT(*) OVER (PARTITION BY s.id ORDER BY s.id) AS "CrimesNumber",
    ROUND(((COUNT(*) OVER (PARTITION BY s.id ORDER BY s.id) / COUNT(*) OVER()::numeric) * 100), 2) AS "Percentage"
FROM crime
INNER JOIN outcome_status os ON outcome_status_id = os.id
INNER JOIN category_name cn ON os.category_name_id = cn.id
INNER JOIN street s ON s.id = location_id
WHERE
    month >= '2019-01'
    AND month <='2019-05'
    AND cn.category_name='Defendant found not guilty'