WITH crimes AS (
    SELECT
        category AS CrimeCategory,
        month AS Month,
        COUNT(*) AS currentMonth
    FROM crime
    WHERE month >= '2019-01' AND month <= '2019-05'
    GROUP BY category, month
)
SELECT
    CrimeCategory,
    Month,
    currentMonth,
    LAG(crimes.currentMonth) OVER (PARTITION BY CrimeCategory ORDER BY Month) AS "previousMonth",
    currentMonth - LAG(crimes.currentMonth) OVER (PARTITION BY CrimeCategory ORDER BY Month) AS "Delta",
    ROUND((((currentMonth - LAG(crimes.currentMonth) OVER (PARTITION BY CrimeCategory ORDER BY Month))
         / LAG(crimes.currentMonth) OVER (PARTITION BY CrimeCategory ORDER BY Month)::numeric) * 100), 2) AS "Growth"
FROM crimes