
-- simple join
select * from <baseModel> <?alias>
join <joinModel> <?alias> on <baseModel.id> = <joinModel.baseModel_id>



/* 
First use case: 
    base model and one join

 */

-- Select Phase
select * from base b

-- Select Phase selecting columns name

select
    b.name,
    b.age

from base b

-- SelectPhase selecting column name whit join

select
    b.name,
    b.age,
    m.name
from base b
join mando m on b.id = m.base_id

-- Join Phase
join mando m on b.id = m.base_id


-- where phase

SelectPhase
<joinPhase>
where 
    base_alias.columnSelected <predicado>

