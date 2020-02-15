with crimes as (
    select location_id,
           category,
           month
    from crime
    where (category = 'drugs'
        or category = 'possession-of-weapons'
        or category = 'theft-from-the-person'
        or category = 'shoplifting')
      and (month between '2019-01' and '2019-06')
      and location_id is not null
), stop_search as (
    select location_id,
           object_of_search,
           to_char(datetime, 'yyyy-MM') as "date"
    from stop_and_search
    where (object_of_search = 'Controlled drugs'
        or object_of_search = 'Firearms'
        or object_of_search = 'Offensive weapons'
        or object_of_search = 'Stolen goods')
      and (to_char(datetime, 'yyyy-MM') between '2019-01' and '2019-06')
      and outcome = 'Arrest'
      and location_id is not null
), data as (
    select distinct location_id,
                    month
    from crimes
    union distinct
    select location_id,
           "date"
    from stop_search
)
select d.location_id,
       name,
       d.month,
       CRDrugs.drugs,
       SSDrugs."controlled drugs",
       CRWeapons."possession of weapons",
       SSWeapons."Offensive weapons/Firearms",
       CRTheft."theft from the person/shoplifting",
       SSTheft."Stolen goods"
from "data" d
         left join (
    select location_id,
           month,
           count(*) as "drugs"
    from crimes
    where category='drugs'
    group by location_id, month
) as CRDrugs on d.location_id=CRDrugs.location_id and d.month = CRDrugs.month
         left join street on d.location_id = street.id
         left join (
    select location_id,
           "date",
           count(*) as "controlled drugs"
    from stop_search
    where object_of_search='Controlled drugs'
    group by location_id, "date"
) SSDrugs on d.location_id = SSDrugs.location_id and d.month = SSDrugs.date
         left join (
    select location_id,
           month,
           count(*) as "possession of weapons"
    from crimes
    where category='possession-of-weapons'
    group by location_id, month
) CRWeapons on d.location_id = CRWeapons.location_id and d.month = CRWeapons.month
         left join (
    select location_id,
           "date",
           count(*) as "Offensive weapons/Firearms"
    from stop_search
    where object_of_search='Offensive weapons'
       or object_of_search='Firearms'
    group by location_id, "date"
) SSWeapons on d.location_id = SSWeapons.location_id and d.month = SSWeapons.date
         left join (
    select location_id,
           month,
           count(*) as "theft from the person/shoplifting"
    from crimes
    where category='theft-from-the-person'
       or category='shoplifting'
    group by location_id, month
) CRTheft on d.location_id = CRTheft.location_id and d.month = CRTheft.month
         left join (
    select location_id,
           "date",
           count(*) as "Stolen goods"
    from stop_search
    where object_of_search='Stolen goods'
    group by location_id, "date"
) SSTheft on d.location_id = SSTheft.location_id and d.month = SSTheft.date
order by location_id