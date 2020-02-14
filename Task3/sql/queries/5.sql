WITH "all" AS (
    SELECT location_id,
           age_range,
           sum(count(*)) OVER (PARTITION BY location_id, age_range) AS "age_count",
           gender,
           sum(count(*)) OVER (PARTITION BY location_id, gender) AS "gender_count",
           officer_defined_ethnicity,
           sum(count(*)) OVER (PARTITION BY location_id, officer_defined_ethnicity) AS "ethnithity_count",
           object_of_search,
           sum(count(*)) OVER (PARTITION BY location_id, object_of_search) AS "object_count",
           outcome,
           sum(count(*)) OVER (PARTITION BY location_id, outcome) AS "outcome_count"
    FROM stop_and_search
    WHERE age_range<>''
      AND officer_defined_ethnicity<>''
      AND object_of_search<>''
      AND outcome<>''
      AND location_id IS NOT NULL
      AND datetime BETWEEN '2019-01-01' AND '2019-06-01'
    GROUP BY location_id, age_range, gender, officer_defined_ethnicity, object_of_search, outcome
)
SELECT DISTINCT location_id,
                st.name,
                first_value(age_range) OVER (PARTITION BY location_id ORDER BY age_count DESC) AS "Most popular age range",
                first_value(gender) OVER (PARTITION BY location_id ORDER BY gender_count DESC) AS "Most popular gender",
                first_value(officer_defined_ethnicity) OVER (PARTITION BY location_id ORDER BY ethnithity_count DESC) AS "Most popular ethnicity",
                first_value(object_of_search) OVER (PARTITION BY location_id ORDER BY object_count DESC) AS "Most popular object of search",
                first_value(outcome) OVER (PARTITION BY location_id ORDER BY outcome_count DESC) AS "Most popular outcome"
FROM "all"
INNER JOIN street st ON location_id = st.id
GROUP BY location_id, st.name, age_range, outcome, object_of_search, officer_defined_ethnicity, gender, age_count, gender_count, ethnithity_count, object_count, outcome_count;