SELECT
category AS "CrimeCategory",
"month" AS "Month",
LAG(COUNT(*)) OVER(PARTITION BY category) AS "PreviousMonth",
COUNT(*) AS "CurrentMonth",
COUNT(*) - LAG(COUNT(*)) OVER(PARTITION BY category) AS "Delta",
ROUND((((COUNT(*) - LAG(COUNT(*)) OVER(PARTITION BY category)) / LAG(COUNT(*)) OVER(PARTITION BY category)::numeric) * 100), 2) AS "GrowthRate"
FROM crime
WHERE month >= '2019-01' AND month <= '2019-05'
GROUP BY category, month
ORDER BY category, month;